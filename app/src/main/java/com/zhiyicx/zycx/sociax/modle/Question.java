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
public class Question extends SociaxItem implements Serializable {

    private int qId;
    private String qTitle;
    private String qAnswer;
    private int isTop;

    private int repostCount; // 分享数
    private int collCount; // 收藏数
    private int tipCount; // 顶

    private int ifcoll; // 是否收藏 1是收藏
    private int iftips; // 是否顶

    public Question() {
    }

    public Question(JSONObject data) throws JSONException {
        qId = data.getInt("faq_id");
        qTitle = data.getString("question_cn");
        qAnswer = data.getString("answer_cn");
        if (data.has("top"))
            isTop = data.getInt("top");
        if (data.has("repost_count"))
            repostCount = data.getInt("repost_count");
        if (data.has("coll_count"))
            collCount = data.getInt("coll_count");
        if (data.has("tips_count"))
            tipCount = data.getInt("tips_count");

        if (data.has("ifcoll"))
            ifcoll = data.getInt("ifcoll");
        if (data.has("iftips"))
            iftips = data.getInt("iftips");

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

    @Override
    public String toString() {
        return "Question [qId=" + qId + ", qTitle=" + qTitle + ", qAnswer="
                + qAnswer + "]";
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getqTitle() {
        return qTitle;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public String getqAnswer() {
        return qAnswer;
    }

    public void setqAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getRepostCount() {
        return repostCount;
    }

    public void setRepostCount(int repostCount) {
        this.repostCount = repostCount;
    }

    public int getCollCount() {
        return collCount;
    }

    public void setCollCount(int collCount) {
        this.collCount = collCount;
    }

    public int getTipCount() {
        return tipCount;
    }

    public void setTipCount(int tipCount) {
        this.tipCount = tipCount;
    }

    public int getIfcoll() {
        return ifcoll;
    }

    public void setIfcoll(int ifcoll) {
        this.ifcoll = ifcoll;
    }

    public int getIftips() {
        return iftips;
    }

    public void setIftips(int iftips) {
        this.iftips = iftips;
    }

}
