package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:51:40 类描述：这个类是实现
 */

public class ModelRequestFlag extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
     * "domain":"1976", "title":"测试"
     */
    private String domain;
    private String title;
    private boolean isChoose; // 判断是否选择,用于选择标签的时候

    public ModelRequestFlag(JSONObject data) {
        try {
            if (data.has("domain")) {

                setDomain(data.getString("domain"));
            }
            if (data.has("title")) {

                setTitle(data.getString("title"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean isChoose) {
        this.isChoose = isChoose;
    }

    public ModelRequestFlag() {
        // TODO Auto-generated constructor stub
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
