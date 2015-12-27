package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:12:02 类描述：这个类是实现
 */

public class ModelNotifyNotice extends Model {

    /**
     * "id": "4197", "is_read": "1", "type": "answer", "time": "12月08日 10:09",
     * "username": "胡怀湧", "userface":
     * "http://qingko-img.b0.upaiyun.com/avatar/41/cc/c9/original.jpg!small.avatar.jpg?v1450085681"
     * , "answer_id": "29426", "answer_content": "看到了", "question_id": "100401",
     * "question_content": "testtest"
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String is_read;
    private String type;
    private String time;
    private String username;
    private String userface;
    private String answer_id;
    private String answer_content;
    private String question_id;
    private String question_content;
    private String content; // 经历通知和系统通知
    private String post_id;

    public ModelNotifyNotice() {

    }

    public ModelNotifyNotice(JSONObject data) {
        try {
            if (data.has("id")) {

                setId(data.getString("id"));
            }
            if (data.has("is_read")) {

                setIs_read(data.getString("is_read"));
            }
            if (data.has("type")) {

                setType(data.getString("type"));
            }
            if (data.has("time")) {

                setTime(data.getString("time"));
            }
            if (data.has("username")) {

                setUsername(data.getString("username"));
            }
            if (data.has("userface")) {

                setUserface(data.getString("userface"));
            }
            if (data.has("answer_id")) {

                setAnswer_id(data.getString("answer_id"));
            }
            if (data.has("answer_content")) {

                setAnswer_content(data.getString("answer_content"));
            }
            if (data.has("question_id")) {

                setQuestion_id(data.getString("question_id"));
            }
            if (data.has("question_content")) {

                setQuestion_content(data.getString("question_content"));
            }
            if (data.has("content")) {

                setContent(data.getString("content"));
            }
            if (data.has("post_id")) {

                setPost_id(data.getString("post_id"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

}
