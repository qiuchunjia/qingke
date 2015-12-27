package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperienceDetailInfor extends Model {

    /**
     * "weiba_id": "3", "cid": "1", "weiba_name": "第三方", "uid": "8271", "ctime":
     * "1433815655", "logo":
     * "http://qingko-img.b0.upaiyun.com/2015/0609/10/55764a580914e.jpg!60x60",
     * "intro": "岁的法国斯蒂芬尕达斯犯规岁的法国斯蒂芬尕达斯犯规岁的法国斯蒂芬尕达斯犯规岁的法国斯蒂芬尕达斯犯规",
     * "who_can_post": "1", "who_can_reply": "0", "follower_count": "5",
     * "thread_count": "10", "admin_uid": "0", "tags":
     * "地方,得分多,fgf,豆腐干,第三方公司,wo,aa,ooo,ee", "recommend": "1", "status": "1",
     * "is_del": "0", "notify": "", "user_name": "zhiyicx"
     */
    private static final long serialVersionUID = 1L;

    private String weiba_id;
    private String cid;
    private String weiba_name;
    private String uid;
    private String ctime;
    private String logo;
    private String intro;
    private String follower_count;
    private String thread_count;
    private String tags;
    private String user_name;

    public ModelExperienceDetailInfor() {
    }

    public String getWeiba_id() {
        return weiba_id;
    }

    public void setWeiba_id(String weiba_id) {
        this.weiba_id = weiba_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getWeiba_name() {
        return weiba_name;
    }

    public void setWeiba_name(String weiba_name) {
        this.weiba_name = weiba_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(String follower_count) {
        this.follower_count = follower_count;
    }

    public String getThread_count() {
        return thread_count;
    }

    public void setThread_count(String thread_count) {
        this.thread_count = thread_count;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
