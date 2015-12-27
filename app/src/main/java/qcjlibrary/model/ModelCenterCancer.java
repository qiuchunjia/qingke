package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:41:46 类描述：这个类是实现
 */

public class ModelCenterCancer extends Model {

    /**
     * { "cancer_id": "1", "title": "乳腺癌", "pid": "0", "sort": "0",
     * "ask_category_id": "31" },
     */
    private static final long serialVersionUID = 1L;
    private String cancer_id;
    private String title;
    private String pid;
    private String sort;
    private String ask_category_id;

    public ModelCenterCancer() {
    }

    public ModelCenterCancer(JSONObject data) {
        try {
            if (data.has("cancer_id")) {

                setCancer_id(data.getString("cancer_id"));
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
            if (data.has("ask_category_id")) {

                setAsk_category_id(data.getString("ask_category_id"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getCancer_id() {
        return cancer_id;
    }

    public void setCancer_id(String cancer_id) {
        this.cancer_id = cancer_id;
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

    public String getAsk_category_id() {
        return ask_category_id;
    }

    public void setAsk_category_id(String ask_category_id) {
        this.ask_category_id = ask_category_id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
