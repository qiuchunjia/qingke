package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:48:26 类描述：这个类是实现
 */

public class ModelRequestRelate extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * "qid":"99032", "title":"患者胃口不好可以喝桔子汁吗？", "view_count":"45",
     * "best_answer":"0", "is_expert":"0"
     */

    private String qid;
    private String title;
    private String view_count;
    private String best_answer;
    private String is_expert;

    public ModelRequestRelate() {
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getBest_answer() {
        return best_answer;
    }

    public void setBest_answer(String best_answer) {
        this.best_answer = best_answer;
    }

    public String getIs_expert() {
        return is_expert;
    }

    public void setIs_expert(String is_expert) {
        this.is_expert = is_expert;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
