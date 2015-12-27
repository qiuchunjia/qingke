package com.zhiyicx.zycx.sociax.modle;

import org.json.JSONException;
import org.json.JSONObject;

public class SystemNotify extends SociaxItem {

    private int sId;
    private String name;
    private String title;
    private String content;
    private int ctime;

    public SystemNotify() {
    }

    public SystemNotify(JSONObject data) throws JSONException {
        setName(data.getString("name"));
        setTitle(data.getJSONObject("data").getString("title"));
        setCtime(data.getJSONObject("data").getInt("ctime"));
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
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
