package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午4:23:35 类描述：这个类是实现麻痹呵呵哒
 */

public class ModelMeAddress extends Model {

    /**
     * "area_id": "930841", "title": "河北省", "pid": "0", "sort": "3"
     */
    private static final long serialVersionUID = 1L;
    private String area_id;
    private String title;
    private String pid;
    private String sort;
    private String wholeAddress = ""; // 拼接完整的地址
    private String wholeId = "";// 拼接完整的id

    private String type; // activity根据type的类型来判断需要处理的逻辑

    public ModelMeAddress() {

    }

    public ModelMeAddress(JSONObject data) {
        try {
            if (data.has("area_id")) {

                setArea_id(data.getString("area_id"));
            }
            if (data.has("title")) {
                setTitle(data.getString("title"));
            }
            if (data.has("pid")) {

                setPid(data.getString("pid"));
            }
            if (data.has("sort")) {

                setSort(data.getString("sort"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getWholeAddress() {
        return wholeAddress;
    }

    public void setWholeAddress(String wholeAddress) {
        this.wholeAddress = wholeAddress;
    }

    public String getWholeId() {
        return wholeId;
    }

    public void setWholeId(String wholeId) {
        this.wholeId = wholeId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
