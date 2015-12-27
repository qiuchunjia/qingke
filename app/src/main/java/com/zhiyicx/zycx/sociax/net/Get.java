package com.zhiyicx.zycx.sociax.net;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.exception.HostNotFindException;
import com.zhiyicx.zycx.sociax.unit.FlushedInputStream;
import com.zhiyicx.zycx.sociax.unit.JSONHelper;

public class Get extends Request {
    public Get() {
        super();
    }

    public Get(String url) {
        super(url);
    }

    public Get(Builder uri) {
        super(uri);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Request append(String name, Object value) {
        if (JSONHelper.isArray(value.getClass())
                || JSONHelper.isCollection(value.getClass())) {
            uri.appendQueryParameter(name, JSONHelper.toJSON(value));
        } else {
            if (JSONHelper.isNumber(value.getClass())) {
                uri.appendQueryParameter(name, value + "");
            } else {
                uri.appendQueryParameter(name, (String) value);
            }

        }
        return this;
    }

    public Bitmap download() throws HostNotFindException,
            ClientProtocolException, IOException {
        HttpRequestBase http = this.executeObject();
        HttpResponse httpResp;
        // try {
        httpResp = httpClient.execute(http);
        int code = httpResp.getStatusLine().getStatusCode();

        if (HttpStatus.SC_OK == code) {
            final HttpEntity entity = httpResp.getEntity();

            if (entity != null) {

                InputStream inputStream = null;
                FlushedInputStream flushed = null;
                try {
                    inputStream = entity.getContent();
                    flushed = new FlushedInputStream(inputStream);
                    final Bitmap bitmap = BitmapFactory.decodeStream(flushed);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                        entity.consumeContent();
                    }
                    if (flushed != null) {
                        flushed.close();
                    }
                }
            }
        } else if (HttpStatus.SC_NOT_FOUND == code) {
            throw new HostNotFindException(HttpHelper.getContext().getString(
                    R.string.host_not_find));
        }
        // } catch (Exception e) {
        // http.abort();
        // }
        return null;
    }

    @Override
    protected HttpRequestBase executeObject() {
        String url;
        if (this.url != null && !this.url.equals("")) {
            url = this.url;
        } else {
            Uri uriObj = uri.build();
            url = uriObj.toString();
        }

        Log.d(TAG, "Request with GET to" + url);
        HttpGet post = new HttpGet(url);
        post.addHeader("Accept-Encoding", "gzip");
        return post;
    }
}
