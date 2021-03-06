package com.zhiyicx.zycx.sociax.unit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.util.Log;

public class FormPost {
    // 如果是文本的文件的话那么通过map类传递进来如果是文件的话通过FormFile传递进来
    public static String post(String actionUrl, Map<String, String> params,
                              FormFile file) throws IOException {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        URL uri = new URL(actionUrl);
        Log.d("FormPost", "form fiel url" + uri);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000);
        conn.setDoInput(true);
        // 允许输入
        conn.setDoOutput(true);
        // 允许输出
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        // Post方式
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\""
                    + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }

        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        outStream.write(sb.toString().getBytes());

        // 发送文件数据
        StringBuilder sb1 = new StringBuilder();
        sb1.append(PREFIX);
        sb1.append(BOUNDARY);
        sb1.append(LINEND);
        sb1.append("Content-Disposition: form-data; name=\""
                + file.getFormnames() + "\"; filename=\"" + file.getFileName()
                + "\"" + LINEND);
        sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET
                + LINEND);
        sb1.append(LINEND);
        outStream.write(sb1.toString().getBytes());

        InputStream is = file.getInStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }

        is.close();
        outStream.write(LINEND.getBytes());

        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();

        // 得到响应码
        int res = conn.getResponseCode();
        InputStream in = conn.getInputStream();

        String result = null;
        if (res == 200) {
            int ch;
            StringBuilder sb2 = new StringBuilder();
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
            result = sb2.toString();
        }
        outStream.close();
        conn.disconnect();
        return result;
    }
}
