package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：上午10:48:31 类描述：这个类是实现
 */

public class ModelRequest extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<ModelCancerCategory> fenlei;
    private List<ModelRequestItem> list;

    public ModelRequest() {
    }

    public List<ModelCancerCategory> getFenlei() {
        return fenlei;
    }

    public void setFenlei(List<ModelCancerCategory> fenlei) {
        this.fenlei = fenlei;
    }

    public List<ModelRequestItem> getList() {
        return list;
    }

    public void setList(List<ModelRequestItem> list) {
        this.list = list;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ModelRequest [fenlei=" + fenlei + ", list=" + list + "]";
    }

}
