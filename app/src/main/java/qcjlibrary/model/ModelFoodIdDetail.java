package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:31:08 类描述：这个类是实现
 */

public class ModelFoodIdDetail extends Model {
    private static final long serialVersionUID = 1L;

    private ModelFoodIdDetailInfo info;
    private String gongxiao;
    private String aizhong;
    private List<ModelFoodIdDetailInfo> foodRel;

    public ModelFoodIdDetailInfo getInfo() {
        return info;
    }

    public void setInfo(ModelFoodIdDetailInfo info) {
        this.info = info;
    }

    public String getGongxiao() {
        return gongxiao;
    }

    public void setGongxiao(String gongxiao) {
        this.gongxiao = gongxiao;
    }

    public String getAizhong() {
        return aizhong;
    }

    public void setAizhong(String aizhong) {
        this.aizhong = aizhong;
    }

    public List<ModelFoodIdDetailInfo> getFoodRel() {
        return foodRel;
    }

    public void setFoodRel(List<ModelFoodIdDetailInfo> foodRel) {
        this.foodRel = foodRel;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
