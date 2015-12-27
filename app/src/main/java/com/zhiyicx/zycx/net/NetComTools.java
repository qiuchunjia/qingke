package com.zhiyicx.zycx.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/3.
 * ������������ ���ο���qcj �鿴  �����������Ҫ����volley���
 */

public class NetComTools implements CookieJsonObjectRequest.CookieListener {

    private RequestQueue mRequestQueue;
    private static NetComTools mInstance = null;
    private ImageLoader mImageLoader = null;

    private static HashMap<String, String> mCookieContiner = new HashMap<String, String>();

    public static NetComTools getInstance(Context context) {
        if (mInstance == null)
            mInstance = new NetComTools((context));
        return mInstance;
    }

    private NetComTools(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void getNetString(String url, final StringDataListener listener) {
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                        listener.OnReceive(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                        listener.OnError(error.getMessage());
                    }
                });

        mRequestQueue.add(stringRequest);
    }


    public void getNetJson(final String url, final JsonDataListener listener) {
        JsonObjectRequest jsonObjectRequest = new CookieJsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        try {
                            response.put("url", url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listener.OnReceive(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                        listener.OnError(error.getMessage());
                    }
                }, this);

        mRequestQueue.add(jsonObjectRequest);
    }

    public void getNetImage(final String url, final ImgDataListener listener) {
        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap arg0) {
                listener.OnReceive(arg0);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                listener.OnError("load image error");
            }
        });
        mRequestQueue.add(imgRequest);
    }

    public void loadNetImage(ImageView imageView, String url, int defimg, int w, int h) {
        if (url == null || TextUtils.isEmpty(url))
            return;
        if (mImageLoader == null)
            mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defimg, defimg);
        try {
            mImageLoader.get(url, listener, w, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SaveCookies(NetworkResponse response) {

        Map<String, String> responseHeaders = response.headers;
        String rawCookies = responseHeaders.get("Set-Cookie");
        if (rawCookies == null) {
            return;
        } else {
            //for (int i = 0; i < headers.length; i++)
            {
                //String cookie = headers[i].getValue();
                String[] cookievalues = rawCookies.split(";");
                for (int j = 0; j < cookievalues.length; j++) {
                    String[] keyPair = cookievalues[j].split("=");
                    String key = keyPair[0].trim();
                    String value = keyPair.length > 1 ? keyPair[1].trim() : "";
                    mCookieContiner.put(key, value);
                }
            }
        }
    }

    @Override
    public Map<String, String> getCookies() {

        StringBuilder sb = new StringBuilder();
        Iterator iter = mCookieContiner.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            sb.append(key);
            sb.append("=");
            sb.append(val);
            sb.append(";");
        }

        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("Cookie", sb.toString());
        return headers;
    }

    private class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }
}
