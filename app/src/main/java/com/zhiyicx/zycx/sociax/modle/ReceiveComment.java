package com.zhiyicx.zycx.sociax.modle;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhiyicx.zycx.sociax.exception.DataInvalidException;

public class ReceiveComment extends Comment {
    private User user;
    private String uname;
    private String jsonUser;

    public String getJsonUser() {
        return jsonUser;
    }

    public ReceiveComment() {
        super();
    }

    public void setJsonUser(String jsonUser) {
        this.jsonUser = jsonUser;
    }

    public ReceiveComment(JSONObject data) throws DataInvalidException {
        super(data);
        try {
            if (data.has("user_info")) {
                this.setUser(new User(data.getJSONObject("user_info")));
                this.setJsonUser(data.getJSONObject("user_info").toString());
            }
        } catch (JSONException e) {
            throw new DataInvalidException();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUname() {
        return uname;
    }

    @Override
    public void setUname(String uname) {
        this.uname = uname;
    }

}
