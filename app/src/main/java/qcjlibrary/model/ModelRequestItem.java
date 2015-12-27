package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午5:13:48 类描述：这个类是实现
 */

public class ModelRequestItem extends Model {

    /**
     * "question_id": "100385", "question_content": "我想问问到底有什么问题？",
     * "categoryname": "乳腺癌", "published_uid": "8489", "user_face":
     * "http://qingko-img.b0.upaiyun.com/avatar/41/cc/c9/original.jpg!small.avatar.jpg?v1417435390"
     * , "user_name": "胡怀勇", "time": "15:46", "answer_count": "0",
     * "best_answer": "0", "is_expert": "0", "is_recommend": "0",
     * "answercontent": "", "answername": ""
     */
    private static final long serialVersionUID = 1L;

    private String question_id;
    private String question_content;
    private String categoryname;
    private String published_uid;
    private String user_face;
    private String user_name;
    private String time;
    private String answer_count;
    private String best_answer;
    private String is_expert;
    private String is_recommend;
    private String answercontent;
    private String answername;
    /**
     * item详情部分需要的字段
     **/
    private String question_detail;
    private String uid;
    private String category;

    /**
     * item详情部分需要的字段end
     **/

    private String id;// id 癌种分类id 选填
    private String status;// status 是否解决 选填
    private String type;// type提分分类 新增 选填（0-治疗类 1-护理类 2-康复类）


    public String getQuestion_id() {
        return question_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getPublished_uid() {
        return published_uid;
    }

    public void setPublished_uid(String published_uid) {
        this.published_uid = published_uid;
    }

    public String getUser_face() {
        return user_face;
    }

    public void setUser_face(String user_face) {
        this.user_face = user_face;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(String answer_count) {
        this.answer_count = answer_count;
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

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getAnswercontent() {
        return answercontent;
    }

    public void setAnswercontent(String answercontent) {
        this.answercontent = answercontent;
    }

    public String getAnswername() {
        return answername;
    }

    public void setAnswername(String answername) {
        this.answername = answername;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getQuestion_detail() {
        return question_detail;
    }

    public void setQuestion_detail(String question_detail) {
        this.question_detail = question_detail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ModelRequestItem [question_id=" + question_id
                + ", question_content=" + question_content + ", categoryname="
                + categoryname + ", published_uid=" + published_uid
                + ", user_face=" + user_face + ", user_name=" + user_name
                + ", time=" + time + ", answer_count=" + answer_count
                + ", best_answer=" + best_answer + ", is_expert=" + is_expert
                + ", is_recommend=" + is_recommend + ", answercontent="
                + answercontent + ", answername=" + answername + "]";
    }

}
