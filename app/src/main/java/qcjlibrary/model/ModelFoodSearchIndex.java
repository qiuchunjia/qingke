package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:43:27 类描述：这个类是实现
 */

public class ModelFoodSearchIndex extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<ModelFoodSearch0> foodList;
    private List<ModelFoodSearch1> sideList;

    public List<ModelFoodSearch0> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<ModelFoodSearch0> foodList) {
        this.foodList = foodList;
    }

    public List<ModelFoodSearch1> getSideList() {
        return sideList;
    }

    public void setSideList(List<ModelFoodSearch1> sideList) {
        this.sideList = sideList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
