package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:44:26 类描述：这个类是实现搜索的javabean
 */

public class ModelRequestSearch extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String key;
    private String type;
    private String cat;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ModelRequestSearch [key=" + key + ", type=" + type + ", cat="
                + cat + "]";
    }

}
