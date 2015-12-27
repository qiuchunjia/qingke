package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:45:29 类描述：这个类是实现
 */

public class ModelRequestAnswerComom extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /***
     * "answer_id":"29417", "answer_content":"你好，请问你", "time":"12-04",
     * "uid":"8489", "user_name":"胡怀勇", "user_face":
     * "http://qingko-img.b0.upaiyun.com/avatar/41/cc/c9/original.jpg!small.avatar.jpg?v1417435390"
     * , "against_count":"0", "agree_count":"0", "comment_count":"0",
     * "is_best":"0"
     */
    private String answer_id;
    private String answer_content;
    private String time;
    private String uid;
    private String user_name;
    private String user_face;
    private String against_count;
    private String agree_count;
    private String comment_count;
    private String is_best;

    private String qid;
    private String Auid;

    /*************
     * 点击普通问答需要字段进入下一个页面
     ******************/
    private String comment_id;
    private String content;
    private String userid;
    private String username;
    private String from; // 判断是专家还是用户

    private String type; // 设置为最佳答案的时候需选择

    private boolean shoudGone; // 是否需要隐藏设置最佳答案的按钮，这个主要是用于消息的页面的跳转的时候使用

    public boolean isShoudGone() {
        return shoudGone;
    }

    public void setShoudGone(boolean shoudGone) {
        this.shoudGone = shoudGone;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ModelRequestAnswerComom() {
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAuid() {
        return Auid;
    }

    public void setAuid(String auid) {
        Auid = auid;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_face() {
        return user_face;
    }

    public void setUser_face(String user_face) {
        this.user_face = user_face;
    }

    public String getAgainst_count() {
        return against_count;
    }

    public void setAgainst_count(String against_count) {
        this.against_count = against_count;
    }

    public String getAgree_count() {
        return agree_count;
    }

    public void setAgree_count(String agree_count) {
        this.agree_count = agree_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getIs_best() {
        return is_best;
    }

    public void setIs_best(String is_best) {
        this.is_best = is_best;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
