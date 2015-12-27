package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：上午10:49:29 类描述：这个类是实现
 */

public class ModelFoodSearch extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /*****
     * string key 搜索内容 选填
     * <p/>
     * int state 食疗类型（0食材，1食疗方） 必填
     * <p/>
     * int type_id 食材类型 选填
     * <p/>
     * int p 页数 选填
     * <p/>
     * string table 食疗方类型（普通食疗方sidefood,癌种食疗方cancer,对症食疗方symptom） 选填
     *****/
    private String key;
    private int state;
    private int type_id;
    private int p;
    private String table;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
