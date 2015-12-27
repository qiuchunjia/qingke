package qcjlibrary.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperiencePostDetailInfo extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /****
     * "post_id": "6", "status": "1", "title": "dsa", "post_uid": "40", "tags":
     * [ "地方", "wo", "aa" ], "ctime": "1447813240", "userface":
     * "http://qingko-img.b0.upaiyun.com/avatar/d6/45/92/original.jpg!small.avatar.jpg?v1450151369"
     * , "childCount": 3
     ****/
    private String post_id;
    private String status;
    private String title;
    private String post_uid;
    private List<String> tags;
    private String ctime;
    private String userface;
    private String childCount;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost_uid() {
        return post_uid;
    }

    public void setPost_uid(String post_uid) {
        this.post_uid = post_uid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
