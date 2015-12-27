package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：上午10:48:31 类描述：这个类是实现
 */

public class ModelZiXun extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<ModelZiXunDetail> list;
    private List<ModelZiXunCategory> fenlei;

    public ModelZiXun() {
    }

    public List<ModelZiXunDetail> getList() {
        return list;
    }

    public void setList(List<ModelZiXunDetail> list) {
        this.list = list;
    }

    public List<ModelZiXunCategory> getFenlei() {
        return fenlei;
    }

    public void setFenlei(List<ModelZiXunCategory> fenlei) {
        this.fenlei = fenlei;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ModelZiXun [list=" + list + ", fenlei=" + fenlei + "]";
    }

}
