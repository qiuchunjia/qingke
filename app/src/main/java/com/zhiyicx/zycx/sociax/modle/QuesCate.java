package com.zhiyicx.zycx.sociax.modle;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类说明： 问题实体类
 *
 * @author Povol
 * @version 1.0
 * @date 2012-7-27
 */
public class QuesCate extends SociaxItem implements Serializable {

    private int qCateId;
    private String qCateName;

    private ListData<SociaxItem> childList;
    private boolean hasChild;

    public QuesCate() {
    }

    public QuesCate(JSONObject data) throws JSONException {
        qCateId = data.getInt("id");
        qCateName = data.getString("name");
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

    public int getqCateId() {
        return qCateId;
    }

    public void setqCateId(int qCateId) {
        this.qCateId = qCateId;
    }

    public String getqCateName() {
        return qCateName;
    }

    public void setqCateName(String qCateName) {
        this.qCateName = qCateName;
    }

    @Override
    public String toString() {
        return "QuesCate [qCateId=" + qCateId + ", qCateName=" + qCateName
                + "]";
    }

    public ListData<SociaxItem> getChildList() {
        return childList;
    }

    public void setChildList(ListData<SociaxItem> childList) {
        this.childList = childList;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

}
