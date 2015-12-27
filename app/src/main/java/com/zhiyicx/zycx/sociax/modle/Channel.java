package com.zhiyicx.zycx.sociax.modle;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhiyicx.zycx.sociax.exception.DataInvalidException;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date Dec 5, 2012
 */
public class Channel extends SociaxItem {

    private int id;
    private String cName;
    private String cUrl;
    private Integer sortId = 0;

    public Channel() {
    }

    public Channel(JSONObject data) throws DataInvalidException, JSONException {
        super(data);
        setId(data.getInt("channel_category_id"));
        setcName(data.getString("title"));
        setcUrl(data.getString("icon_url"));
        setSortId(data.getInt("sort"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcUrl() {
        return cUrl;
    }

    public void setcUrl(String cUrl) {
        this.cUrl = cUrl;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public boolean checkValid() {
        return false;
    }

    @Override
    public String getUserface() {
        return null;
    }

    @Override
    public int compareTo(SociaxItem another) {
        return this.sortId.compareTo(((Channel) another).getSortId());
    }
}
