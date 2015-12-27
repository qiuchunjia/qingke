package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午5:29:11 类描述：这个类是实现
 */

public class ModelCancerCategory extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ModelCancerCategory [id=" + id + ", title=" + title + "]";
    }

}
