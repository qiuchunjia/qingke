package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午4:47:24 类描述：这个类是实现 问问题需要的javabean
 */

public class ModelRequestAsk extends Model {

    /**
     * string oauth_token 必填
     * <p/>
     * string oauthtokensecret 必填
     * <p/>
     * qid 问题id 选填
     * <p/>
     * content 问题内容 必填
     * <p/>
     * question_detail 问题描述 选填
     * <p/>
     * is_expert 是否为专家问答 1-专家问答 0-普通问答 默认为0
     * <p/>
     * cid 癌种分类id 必填
     * <p/>
     * type 问题分类 0-治疗类 1-护理类 2-康复类
     * <p/>
     * topics 标签 选填
     */
    private static final long serialVersionUID = 1L;
    private String qid;
    private String content;
    private String question_detail;
    private String is_expert;
    private String cid;
    private String type;
    private String topics;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestion_detail() {
        return question_detail;
    }

    public void setQuestion_detail(String question_detail) {
        this.question_detail = question_detail;
    }

    public String getIs_expert() {
        return is_expert;
    }

    public void setIs_expert(String is_expert) {
        this.is_expert = is_expert;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
