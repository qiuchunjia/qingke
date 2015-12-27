package com.zhiyicx.zycx.sociax.modle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date Jan 21, 2013
 */
public class RecentTopic extends SociaxItem {

    private String name;

    public RecentTopic() {
    }

    public RecentTopic(JSONObject data) throws JSONException {
        setName(data.getString("topic_name"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean checkValid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getUserface() {
        // TODO Auto-generated method stub
        return null;
    }

}
