package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：上午11:09:37 类描述：这个类是实现
 * 主要是用于popwindow传送数据时候对数据的一种封装，当一个activity就一个pop的时候
 * ，就不需要这个类，但是当一个activity有多个pop需要对activit传送数据的时候就迫切需要这个类来区分了
 */

public class ModelPop extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String type; // 需要传送的类型，这个主要是用来actvity或者fragment来作为区分判断
    /*************
     * 数据类型区域
     ********************/
    private int dataInter; // 整形 （常用）
    private String dataStr;// 字符串类型 （常用）
    private float dataFloat;// 浮点型类型
    private Model dataModel;// model 类型
    private List<Model> dataLists; // list类型

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDataInter() {
        return dataInter;
    }

    public void setDataInter(int dataInter) {
        this.dataInter = dataInter;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public float getDataFloat() {
        return dataFloat;
    }

    public void setDataFloat(float dataFloat) {
        this.dataFloat = dataFloat;
    }

    public Model getDataModel() {
        return dataModel;
    }

    public void setDataModel(Model dataModel) {
        this.dataModel = dataModel;
    }

    public List<Model> getDataLists() {
        return dataLists;
    }

    public void setDataLists(List<Model> dataLists) {
        this.dataLists = dataLists;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
