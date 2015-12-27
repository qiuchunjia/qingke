package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperiencePostDetailItem extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /****
     * "post_id":"15", "ctime":"1449631445",
     * "content":"Dddddd is only one who is the best thing", "url":
     * "http://demo-qingko.zhiyicx.com/index.php?app=weiba&mod=Index&act=api_post_detail&post_id=15"
     * , "childCount":2, "praiseCount":"1", "is_praise":"0"
     ****/
    private String post_id;
    private String ctime;
    private String content;
    private String url;
    private String childCount;
    private String praiseCount;
    private String is_praise;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

}
