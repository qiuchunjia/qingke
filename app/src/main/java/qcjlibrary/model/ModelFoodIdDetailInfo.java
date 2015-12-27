package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:31:08 类描述：这个类是实现
 */

public class ModelFoodIdDetailInfo extends Model {
    /****
     * "id":"1", "type_id":"2", "food_name":"玉米",
     * "food_anticancer":"卵磷脂、亚油酸、维生素E、B族维生素、赖氨酸、胡萝卜素、谷胱甘肽、膳食纤维",
     * "food_effect":"调中健胃，益肺宁心，除湿利尿，降血脂，降血糖",
     * "food_tumor":"防治癌症发生；抑制癌细胞；促进毒素排出，抑制和减轻抗癌药物的副作用。",
     * "food_suitable":"肿瘤患者病后体虚", "food_taboo":"无",
     * "food_usage":"与豆类搭配，不可长期作为单一主食，勿食霉变玉米", "food_forcancer":"所有癌种",
     * "state":"0", "liulan":"204", "imgSrc":
     * "http://qingko-img.b0.upaiyun.com/2014/1028/15/544f483ed1316.jpg",
     *****/
    private String id;
    private String type_id;
    private String food_name;
    private String food_anticancer;
    private String food_effect;
    private String food_tumor;
    private String food_suitable;
    private String food_taboo;
    private String food_usage;
    private String food_forcancer;
    private String state;
    private String liulan;
    private String imgSrc;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_anticancer() {
        return food_anticancer;
    }

    public void setFood_anticancer(String food_anticancer) {
        this.food_anticancer = food_anticancer;
    }

    public String getFood_effect() {
        return food_effect;
    }

    public void setFood_effect(String food_effect) {
        this.food_effect = food_effect;
    }

    public String getFood_tumor() {
        return food_tumor;
    }

    public void setFood_tumor(String food_tumor) {
        this.food_tumor = food_tumor;
    }

    public String getFood_suitable() {
        return food_suitable;
    }

    public void setFood_suitable(String food_suitable) {
        this.food_suitable = food_suitable;
    }

    public String getFood_taboo() {
        return food_taboo;
    }

    public void setFood_taboo(String food_taboo) {
        this.food_taboo = food_taboo;
    }

    public String getFood_usage() {
        return food_usage;
    }

    public void setFood_usage(String food_usage) {
        this.food_usage = food_usage;
    }

    public String getFood_forcancer() {
        return food_forcancer;
    }

    public void setFood_forcancer(String food_forcancer) {
        this.food_forcancer = food_forcancer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLiulan() {
        return liulan;
    }

    public void setLiulan(String liulan) {
        this.liulan = liulan;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
