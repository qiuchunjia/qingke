package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午4:04:13 类描述：这个类是实现
 */

public class ModelFoodWayDetail extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ModelFoodWayDetailInfo info;
    private List<ModelFoodWayDetailInfo> sideList;

    public ModelFoodWayDetailInfo getInfo() {
        return info;
    }

    public void setInfo(ModelFoodWayDetailInfo info) {
        this.info = info;
    }

    public List<ModelFoodWayDetailInfo> getSideList() {
        return sideList;
    }

    public void setSideList(List<ModelFoodWayDetailInfo> sideList) {
        this.sideList = sideList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
