package com.zhiyicx.zycx.sociax.modle;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.MessageDataInvalidException;

public class Message extends SociaxItem {
    private int listId;
    private int memberUid;
    private int forNew;
    private int messageNum;
    private int toUid;
    private String toName;
    private String toUserUrl;
    private String ctime;
    private int listCtime;
    private int fromUid;
    private int type;
    private String title;
    private int memeberNum;
    private String minMax;
    private int mtime;
    private String content;
    private Message LastMessage;
    private String fromUname;
    private String fromFace;
    private int timeStmap;
    private int meesageId;
    private int newMsg;

    private String infoTitle;

    public Message() {
    }

    public Message(JSONObject data, boolean type) throws DataInvalidException {
        super(data);
        try {
            if (type) {
                this.setFromUid(data.getInt("from_uid"));
                this.setContent(data.getString("content"));
                if (data.has("to_uid")) {
                    this.setToUid(Integer.valueOf((data.getJSONArray("to_uid")
                            .get(0)).toString()));
                }
            } else {
                this.setListId(data.getInt("list_id"));
                this.setFromUid(data.getInt("from_uid"));
                this.setMeesageId(data.getInt("message_id"));
                this.setContent(data.getString("content"));
                this.setMtime(data.getInt("mtime"));
                this.setFromFace(data.getString("from_face"));
                this.setFromUname(data.getString("from_uname"));
                this.setTimeStmap(data.getInt("timestmap"));
                this.setCtime(data.getString("ctime"));
                if (data.has("new"))
                    this.setNewMsg(data.getInt("new"));
            }

        } catch (JSONException e) {
            Log.d("Message class construct", e.toString());
            throw new MessageDataInvalidException();
        }
    }

    public Message(JSONObject data) throws DataInvalidException {
        super(data);
        try {
            this.setListId(data.getInt("list_id"));
            this.setMemberUid(data.getInt("member_uid"));
            this.setForNew(data.getInt("new"));
            this.setMessageNum(data.getInt("message_num"));
            this.setCtime(data.getString("ctime"));
            this.setListCtime(data.getInt("list_ctime"));
            this.setFromUid(data.getInt("from_uid"));
            this.setType(data.getInt("type"));
            this.setTitle(data.getString("title"));
            this.setMemeberNum(data.getInt("member_num"));
            this.setMinMax(data.getString("min_max").equals("") ? "" : data
                    .getString("min_max"));
            this.setMtime(data.getInt("mtime"));
            this.setContent(data.getString("content"));
            this.setFromUname(data.getString("from_uname"));
            this.setFromFace(data.getString("from_face"));
            if (data.getString("last_message") != "") {
                this.setLastMessage(new Message(data
                        .getJSONObject("last_message"), true));
                this.setToUid(Integer.valueOf((data.getJSONObject(
                        "last_message").getJSONArray("to_uid").get(0))
                        .toString()));
            }
            if (data.has("to_user_info")) {
                JSONObject jsonTemp = new JSONObject(
                        data.getString("to_user_info"));

                for (Iterator iterator = jsonTemp.keys(); iterator.hasNext(); ) {
                    String key = (String) iterator.next();
                    JSONObject jsonToUser = jsonTemp.getJSONObject(key);
                    setToName(jsonToUser.getString("uname"));
                    setToUserUrl(jsonToUser.getString("avatar_middle"));
                }
            }
        } catch (JSONException e) {
            Log.d("Message class construct", e.toString());
            throw new MessageDataInvalidException();
        }
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public String getToNmae() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToUserUrl() {
        return toUserUrl;
    }

    public void setToUserUrl(String toUserUrl) {
        this.toUserUrl = toUserUrl;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getMemberUid() {
        return memberUid;
    }

    public void setMemberUid(int memberUid) {
        this.memberUid = memberUid;
    }

    public int getForNew() {
        return forNew;
    }

    public void setForNew(int forNew) {
        this.forNew = forNew;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getListCtime() {
        return listCtime;
    }

    public void setListCtime(int listCtime) {
        this.listCtime = listCtime;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMemeberNum() {
        return memeberNum;
    }

    public void setMemeberNum(int memeberNum) {
        this.memeberNum = memeberNum;
    }

    public String getMinMax() {
        return minMax;
    }

    public void setMinMax(String minMax) {
        this.minMax = minMax;
    }

    public int getMtime() {
        return mtime;
    }

    public void setMtime(int mtime) {
        this.mtime = mtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromUname() {
        return fromUname;
    }

    public void setFromUname(String fromUname) {
        this.fromUname = fromUname;
    }

    public String getFromFace() {
        return fromFace;
    }

    public void setFromFace(String fromFace) {
        this.fromFace = fromFace;
    }

    public int getTimeStmap() {
        return timeStmap;
    }

    public void setTimeStmap(int timeStmap) {
        this.timeStmap = timeStmap;
    }

    public int getNewMsg() {
        return newMsg;
    }

    public void setNewMsg(int newMsg) {
        this.newMsg = newMsg;
    }

    public int getMeesageId() {
        return meesageId;
    }

    public void setMeesageId(int meesageId) {
        this.meesageId = meesageId;
    }

    public Message getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        LastMessage = lastMessage;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
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

    public boolean isNullForMinMax() {
        return this.minMax.equals(null) || this.minMax.equals("");
    }

    @Override
    public String toString() {
        return "Message [listId=" + listId + ", memberUid=" + memberUid
                + ", forNew=" + forNew + ", messageNum=" + messageNum
                + ", toUid=" + toUid + ", ctime=" + ctime + ", listCtime="
                + listCtime + ", fromUid=" + fromUid + ", type=" + type
                + ", title=" + title + ", memeberNum=" + memeberNum
                + ", minMax=" + minMax + ", mtime=" + mtime + ", content="
                + content + ", LastMessage=" + LastMessage + ", fromUname="
                + fromUname + ", fromFace=" + fromFace + ", timeStmap="
                + timeStmap + ", meesageId=" + meesageId + ", newMsg=" + newMsg
                + "]";
    }

}
