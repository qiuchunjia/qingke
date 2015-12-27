package com.zhiyicx.zycx.sociax.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

import android.net.Uri;
import android.util.Log;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.exception.HostNotFindException;

public abstract class Request {
    protected static final String TAG = "HttpRequest";
    protected HttpClient httpClient;
    protected Uri.Builder uri;
    protected static String token;
    protected static String secretToken;
    protected String url;
    protected ThinksnsHttpClient thinsnsHttpClient;

    public Request() {
        thinsnsHttpClient = new ThinksnsHttpClient();
        httpClient = ThinksnsHttpClient.getHttpClient();
        // httpRequest.addHeader("Accept-Encoding", "gzip");
    }

    public Request(String url) {
        this.url = url;
        thinsnsHttpClient = new ThinksnsHttpClient();
        httpClient = ThinksnsHttpClient.getHttpClient();
    }

    public Request(Uri.Builder uri) {
        this.uri = uri;
        thinsnsHttpClient = new ThinksnsHttpClient();
        httpClient = ThinksnsHttpClient.getHttpClient();
    }

    public synchronized void setUri(Uri.Builder uri) {
        this.uri = uri;
        // 在有token的时候每次请求都追加一个token;
        if (!"".equals(token)) {
            uri.appendQueryParameter("oauth_token", token);
        }
        if (!"".equals(secretToken)) {
            uri.appendQueryParameter("oauth_token_secret", secretToken);
        }
    }

    public static String getToken() {
        return Request.token;
    }

    public static String getSecretToken() {
        return Request.secretToken;
    }

    public static void setToken(String token) {
        Request.token = token;
    }

    public static void setSecretToken(String secretToken) {
        Request.secretToken = secretToken;
    }

    /**
     * 已经创建好了http对象。对象开始进行调用和请求运行
     *
     * @return JSONArray
     * %@throws ResponseTimeoutException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public synchronized Object run() throws ClientProtocolException,
            IOException, HostNotFindException {
        if ("".equals(uri))
            throw new ClientProtocolException("非法调用，执行请求时必须设置uri对象");
        HttpRequestBase http = this.executeObject();
        HttpResponse httpResp = null;
        String result = "ERROR";

        try {
            httpResp = httpClient.execute(http);
            int code = httpResp.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == code) {
                result = getJsonStringFromGZIP(httpResp);
                // result =
                // EntityUtils.toString(httpResp.getEntity(),HTTP.UTF_8);
            } else if (HttpStatus.SC_NOT_FOUND == code) {
                throw new HostNotFindException(HttpHelper.getContext()
                        .getString(R.string.host_not_find));
            }
            Log.d("Request", "Request" + code);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        return result;
    }

    private String getJsonStringFromGZIP(HttpResponse response) {
        String jsonString = null;
        try {
            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int headerData = getShort(header);
            // Gzip 流 的前两个字节是 0x1f8b
            if (result != -1 && headerData == 0x1f8b) {
                Log.d("HttpTask", " use GZIPInputStream  ");
                is = new GZIPInputStream(bis);
            } else {
                Log.d("Request", " not use GZIPInputStream");
                is = bis;
            }
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            StringBuffer sb = new StringBuffer();
            while ((readSize = reader.read(data)) > 0) {
                sb.append(data, 0, readSize);
            }
            jsonString = sb.toString();
            bis.close();
            reader.close();
        } catch (Exception e) {
            Log.e("Request", e.toString());
        }
        // Log.d("HttpTask", "getJsonStringFromGZIP net output : " + jsonString
        // );
        return jsonString;
    }

    private int getShort(byte[] data) {
        return (data[0] << 8) | data[1] & 0xFF;
    }

    public abstract Request append(String name, Object value);

    protected abstract HttpRequestBase executeObject();
}
