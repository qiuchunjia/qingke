package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:43:27 类描述：这个类是实现食材
 */

public class ModelFoodSearch1 extends Model {

    /**
     * "side_name": "红枣粥", "id": "2", "imgSrc":
     * "http://qingko-img.b0.upaiyun.com/2014/1028/16/544f58709d531.jpg",
     * "fangzhi_cancer": "普遍适用", "gongxiao": "健脾胃，补气血"
     */
    private static final long serialVersionUID = 1L;
    private String side_name;
    private String id;
    private String imgSrc;
    private String fangzhi_cancer;
    private String gongxiao;

    public String getSide_name() {
        return side_name;
    }

    public void setSide_name(String side_name) {
        this.side_name = side_name;
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

    public String getFangzhi_cancer() {
        return fangzhi_cancer;
    }

    public void setFangzhi_cancer(String fangzhi_cancer) {
        this.fangzhi_cancer = fangzhi_cancer;
    }

    public String getGongxiao() {
        return gongxiao;
    }

    public void setGongxiao(String gongxiao) {
        this.gongxiao = gongxiao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ModelFoodSearch1 [side_name=" + side_name + ", id=" + id
                + ", imgSrc=" + imgSrc + ", fangzhi_cancer=" + fangzhi_cancer
                + ", gongxiao=" + gongxiao + "]";
    }

}
