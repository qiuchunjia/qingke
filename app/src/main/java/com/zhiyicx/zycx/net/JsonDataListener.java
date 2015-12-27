package com.zhiyicx.zycx.net;

import org.json.JSONObject;

/**
 * Created by Administrator on 2014/7/4.
 */
public interface JsonDataListener {

    public void OnReceive(JSONObject jsonObject);

    public void OnError(String error);
}
