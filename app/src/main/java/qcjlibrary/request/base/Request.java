package qcjlibrary.request.base;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/***********************************************************************
 * Module:  Request.java
 * Author:  qcj qq:260964739
 * Purpose: Defines the Class Request
 ***********************************************************************/

/**
 * 定制網絡請求 这里用android_async_http作为请求的
 *******/
public class Request {
    private AsyncHttpResponseHandler mListener;
    private AsyncHttpClient mClient;
    private static Request mRequest;

    /**
     * 采用单例模式来构建
     */
    private Request() {
        if (mClient == null) {
            mClient = new AsyncHttpClient();
        }
    }

    public static Request getSingleRequest() {
        if (mRequest == null) {
            mRequest = new Request(); // 饱汉模式！
        }
        return mRequest;
    }

    public AsyncHttpResponseHandler getListener() {
        return mListener;
    }

    public void setListener(AsyncHttpResponseHandler Listener) {
        this.mListener = Listener;
    }

    /**
     * @param url      主机地址
     * @param params   参数
     * @param Listener 数据返回
     */
    public void get(String url, RequestParams params,
                    AsyncHttpResponseHandler Listener) {
        mClient.get(url, params, Listener);
        Log.i("test", mClient.toString());
    }

    /**
     * @param url      主机地址
     * @param params   参数
     * @param Listener 数据返回
     */
    public void post(String url, RequestParams params,
                     AsyncHttpResponseHandler Listener) {
        mClient.post(url, params, Listener);
    }
}