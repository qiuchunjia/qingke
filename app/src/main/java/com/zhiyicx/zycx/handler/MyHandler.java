package com.zhiyicx.zycx.handler;

import android.os.Handler;
import android.os.Message;

/**
 * MyHandler
 *
 * @author Mr . H
 */
public class MyHandler extends Handler {
    public void onFailure(String content) {

    }

    public void onSuccess(String content) {

    }

    public void handleMessage(Message msg) {
        String content = (String) msg.obj;
        switch (msg.what) {
            case 1:
                onSuccess(content);
                break;
            case 2:
                onFailure(content);
                break;
        }
        super.handleMessage(msg);
    }
}
