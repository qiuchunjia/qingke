package com.zhiyicx.zycx.net;

import android.graphics.Bitmap;


/**
 * Created by Administrator on 2015/1/11.
 */
public interface ImgDataListener {
    public void OnReceive(Bitmap bitmap);

    public void OnError(String error);
}
