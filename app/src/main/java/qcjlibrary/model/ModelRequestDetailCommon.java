package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:52:58 类描述：这个类是实现
 */

public class ModelRequestDetailCommon extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ModelRequestItem question;
    private List<ModelRequestAnswerComom> answer;
    private List<ModelRequestRelate> other_question;
    private List<ModelRequestFlag> topic_list;

    public ModelRequestDetailCommon() {
    }

    public ModelRequestItem getQuestion() {
        return question;
    }

    public void setQuestion(ModelRequestItem question) {
        this.question = question;
    }

    public List<ModelRequestAnswerComom> getAnswer() {
        return answer;
    }

    public void setAnswer(List<ModelRequestAnswerComom> answer) {
        this.answer = answer;
    }

    public List<ModelRequestRelate> getOther_question() {
        return other_question;
    }

    public void setOther_question(List<ModelRequestRelate> other_question) {
        this.other_question = other_question;
    }

    public List<ModelRequestFlag> getTopic_list() {
        return topic_list;
    }

    public void setTopic_list(List<ModelRequestFlag> topic_list) {
        this.topic_list = topic_list;
    }

}
