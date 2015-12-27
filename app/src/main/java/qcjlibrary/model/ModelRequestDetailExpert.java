package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:52:58 类描述：这个类是实现
 */

public class ModelRequestDetailExpert extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ModelRequestItem question;
    private ModelRequestAnswerComom answer;
    private List<ModelRequestAnswerComom> commentlist;
    private List<ModelRequestRelate> other_question;
    private List<ModelRequestFlag> topic_list;

    public ModelRequestDetailExpert() {
    }

    public ModelRequestAnswerComom getAnswer() {
        return answer;
    }

    public void setAnswer(ModelRequestAnswerComom answer) {
        this.answer = answer;
    }

    public List<ModelRequestAnswerComom> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<ModelRequestAnswerComom> commentlist) {
        this.commentlist = commentlist;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ModelRequestItem getQuestion() {
        return question;
    }

    public void setQuestion(ModelRequestItem question) {
        this.question = question;
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

    @Override
    public String toString() {
        return "ModelRequestDetailExpert [question=" + question + ", answer="
                + answer + ", commentlist=" + commentlist + ", other_question="
                + other_question + ", topic_list=" + topic_list + "]";
    }

}
