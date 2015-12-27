package com.zhiyicx.zycx.sociax.modle;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zhiyicx.zycx.sociax.exception.CommentContentEmptyException;
import com.zhiyicx.zycx.sociax.exception.CommentDataInvalidException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.UpdateException;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;

public class Comment extends SociaxItem {
    private static final String TAG = "Comment";
    protected int uid;
    protected String userFace;
    protected int timestemp;
    protected Weibo status;
    protected String cTime;
    protected int replyUid;
    protected int commentId;
    protected int replyCommentId;
    protected int weiboId;
    protected String contents;
    protected String uname;
    protected String replyJson;

    protected String appName;

    protected String headUrl;

    public String getReplyJson() {
        return replyJson;
    }

    public void setReplyJson(String replyJson) {
        this.replyJson = replyJson;
    }

    protected Type type = Type.WEIBO;
    protected Comment replyComment;

    public enum Type {
        COMMENT, WEIBO
    }

    public Comment(JSONObject data) throws DataInvalidException {
        super(data);
        try {
            this.setUid(data.getInt("uid"));
            //this.setTimestemp(data.getInt("ctime"));
            this.setcTime(data.getString("ctime"));

            if (data.has("content"))
                this.setContent(data.getString("content"));

            if (data.has("sourceInfo")) {
                status = new Weibo(data.getJSONObject("sourceInfo"), 1);
                //this.setStatus(new Weibo(data.getJSONObject("sourceInfo"), 1));
            }

            if (data.has("comment_id"))
                this.setCommentId(data.getInt("comment_id"));
            if (data.has("user_info")) {
                this.setUname(data.getJSONObject("user_info").optString("uname"));
                this.setHeadUrl(data.getJSONObject("user_info").optString("avatar_middle"));
                //setUserface(getHeadUrl());
                if (status != null) {
                    //  status = new Weibo();
                    status.setUserface(getHeadUrl());
                    status.setUsername(getUname());
                }
            }
            if (data.has("reply_comment_id"))
                this.setReplyCommentId(data.getInt("reply_comment_id"));
            if (data.has("reply_uid"))
                this.setReplyUid(data.getInt("reply_uid"));
            if (data.has("weibo_id"))
                this.setWeiboId(data.getInt("weibo_id"));
            if (data.has("row_id") && !data.get("row_id").equals("[]"))
                this.setWeiboId(data.getInt("row_id"));

            if (data.has("type")) {
                String typeTemp = data.getString("type");
                this.setType(typeTemp);
            }
            if (this.getType() == Type.COMMENT
                    && !data.getString("comment").equals("false")) {
                this.setReplyJson(data.getJSONObject("comment").toString());
                this.setReplyComment(new Comment(data.getJSONObject("comment")));
            }
        } catch (JSONException e) {
            Log.d(TAG, "json error " + e.toString());
            throw new CommentDataInvalidException();
        }
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Comment() {
        // TODO Auto-generated constructor stub
    }

    public boolean isNullForUid() {
        return this.getUid() <= 0;
    }

    public boolean isNullForTimestemp() {
        return this.getTimestemp() <= 0;
    }

    public boolean isNullForCTime() {
        String temp = this.getcTime();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForReplyUid() {
        return this.getReplyUid() <= 0;
    }

    public boolean isNullForCommentId() {
        return this.getCommentId() <= 0;
    }

    public boolean isNullForReplyCommentId() {
        return this.getReplyCommentId() <= 0;
    }

    public boolean isNullForWeiboId() {
        return this.getWeiboId() <= 0;
    }

    public boolean isNullForContent() {
        String temp = this.getContent();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForReplyComment() {

        Comment temp = this.getReplyComment();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForType() {
        Type temp = this.getType();
        return temp == null;
    }

    public boolean isNullForStatus() {
        Weibo temp = this.getStatus();
        return temp == null;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(int timestemp) {
        this.timestemp = timestemp;
    }

    public Weibo getStatus() {
        return status;
    }

    public void setStatus(Weibo status) {
        this.status = status;
    }

    public String getContent() {
        return contents;
    }

    public void setContent(String content) {
        this.contents = content;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public int getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(int replyUid) {
        this.replyUid = replyUid;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(int replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setType(String type) {
        if (type.equals("comment"))
            this.type = Type.COMMENT;
        else if (type.equals("weibo"))
            this.type = Type.WEIBO;
    }

    public Comment getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(Comment replyComment) {
        this.replyComment = replyComment;
    }

    public void checkCommentCanAdd() throws DataInvalidException,
            UpdateException {
        if (this.isNullForContent())
            throw new CommentContentEmptyException();
        if (this.isNullForStatus())
            throw new WeiboDataInvalidException();
        if (this.isNullForType())
            throw new CommentDataInvalidException();
    }

    @Override
    public boolean checkValid() {
        boolean result = true;
        result = result
                && !(this.isNullForContent() || this.isNullForCommentId() || this
                .isNullForUid());
        if (!this.isNullForType() && this.getType() == Type.COMMENT) {
            result = result && !this.isNullForReplyComment();
        }
        if (this.getType() == Type.COMMENT) {
            result = result && !this.isNullForReplyComment();
        }
        return result;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    @Override
    public String getUserface() {
        return userFace;
    }

    public void setUserface(String userface) {
        this.userFace = userface;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
