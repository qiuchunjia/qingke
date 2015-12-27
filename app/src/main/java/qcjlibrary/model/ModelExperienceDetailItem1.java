package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperienceDetailItem1 extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /****
     * "post_id": "15", "weiba_id": "3", "post_uid": "8489", "title":
     * "do you think", "content": "Dddddd is only one who is the best thing",
     * "post_time": "1482595200", "update_time": null, "post_type": "postimage",
     * "ctime": "1449631445", "parent_id": "0", "reply_count": "0",
     * "read_count": "0", "last_reply_uid": "8489", "last_reply_time":
     * "1482595200", "tags": "地方，得分多，fgf", "digest": "0", "top": "0", "lock":
     * "0", "recommend": "0", "recommend_time": "0", "favorite": "0", "status":
     * "1", "is_del": "0", "feed_id": "0", "reply_all_count": "0", "attach":
     * "a:2:{i:0;i:7863;i:1;i:0;}", "childCount": 1, "total_comments": 0
     ****/
    private String post_id;
    private String weiba_id;
    private String post_uid;
    private String title;
    private String content;
    private String post_time;
    private String parent_id;
    private String recommend;

    public ModelExperienceDetailItem1() {
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getWeiba_id() {
        return weiba_id;
    }

    public void setWeiba_id(String weiba_id) {
        this.weiba_id = weiba_id;
    }

    public String getPost_uid() {
        return post_uid;
    }

    public void setPost_uid(String post_uid) {
        this.post_uid = post_uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "ModelExperienceDetailItem1 [post_id=" + post_id + ", weiba_id="
                + weiba_id + ", post_uid=" + post_uid + ", title=" + title
                + ", content=" + content + ", post_time=" + post_time
                + ", parent_id=" + parent_id + ", recommend=" + recommend + "]";
    }

}
