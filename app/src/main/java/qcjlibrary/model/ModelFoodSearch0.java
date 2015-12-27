package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:43:27 类描述：这个类是实现食材
 */

public class ModelFoodSearch0 extends Model {

    /**
     *
     */
    private String food_name;
    private String id;
    private String imgSrc;
    private String food_forcancer;
    private String food_effect;

    private static final long serialVersionUID = 1L;

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getFood_forcancer() {
        return food_forcancer;
    }

    public void setFood_forcancer(String food_forcancer) {
        this.food_forcancer = food_forcancer;
    }

    public String getFood_effect() {
        return food_effect;
    }

    public void setFood_effect(String food_effect) {
        this.food_effect = food_effect;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
