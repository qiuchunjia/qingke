package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：上午10:20:39 类描述：这个类是实现
 */

public class ModelFoodCategory extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String class_name;
    private String sort;
    private String count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
