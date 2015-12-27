package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:12:02 类描述：这个类是实现
 */

public class ModelNotifyCommment extends Model {

    /**
     * "comment_id": "412", "myname": "雅蠛蝶", "content": "Aaaa", "username":
     * "胡怀湧", "userface":
     * "http://qingko-img.b0.upaiyun.com/avatar/41/cc/c9/original.jpg!small.avatar.jpg?v1450085681"
     * , "time": "12月17日 10:11", "type": "ask", "original_answer_id": "29424",
     * "original_question_id": "100392", "original_content": "zhebi"
     */
    private static final long serialVersionUID = 1L;
    private String comment_id;
    private String myname;
    private String content;
    private String username;
    private String userface;
    private String time;
    private String type;
    private String original_answer_id;
    private String original_question_id;
    private String original_content;

    public ModelNotifyCommment() {

    }

    public ModelNotifyCommment(JSONObject data) {
        try {
            if (data.has("comment_id")) {

                setComment_id(data.getString("comment_id"));
            }
            if (data.has("username")) {

                setUsername(data.getString("username"));
            }
            if (data.has("myname")) {

                setMyname(data.getString("myname"));
            }
            if (data.has("content")) {

                setContent(data.getString("content"));
            }
            if (data.has("userface")) {

                setUserface(data.getString("userface"));
            }
            if (data.has("time")) {

                setTime(data.getString("time"));
            }
            if (data.has("original_answer_id")) {

                setOriginal_answer_id(data.getString("original_answer_id"));
            }
            if (data.has("original_question_id")) {

                setOriginal_question_id(data.getString("original_question_id"));
            }
            if (data.has("original_content")) {

                setOriginal_content(data.getString("original_content"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginal_answer_id() {
        return original_answer_id;
    }

    public void setOriginal_answer_id(String original_answer_id) {
        this.original_answer_id = original_answer_id;
    }

    public String getOriginal_question_id() {
        return original_question_id;
    }

    public void setOriginal_question_id(String original_question_id) {
        this.original_question_id = original_question_id;
    }

    public String getOriginal_content() {
        return original_content;
    }

    public void setOriginal_content(String original_content) {
        this.original_content = original_content;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
