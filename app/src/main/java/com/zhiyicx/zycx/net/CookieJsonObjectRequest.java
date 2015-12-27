package com.zhiyicx.zycx.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2014/12/24.
 */
public class CookieJsonObjectRequest extends JsonObjectRequest {

    public interface CookieListener {
        public void SaveCookies(NetworkResponse response);

        public Map<String, String> getCookies();
    }

    private CookieListener mCookieLister = null;

    public CookieJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public CookieJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, CookieListener mCookieLister) {
        super(url, jsonRequest, listener, errorListener);
        this.mCookieLister = mCookieLister;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

        if (mCookieLister != null)
            mCookieLister.SaveCookies(response);
        return super.parseNetworkResponse(response);

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if (mCookieLister != null)
            return mCookieLister.getCookies();
        else
            return super.getHeaders();

    }
}
