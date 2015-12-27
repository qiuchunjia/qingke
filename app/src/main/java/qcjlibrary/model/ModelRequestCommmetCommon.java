package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：上午10:17:26 类描述：这个类是实现
 */

public class ModelRequestCommmetCommon extends Model {

    private ModelRequestAnswerComom answer;
    private List<ModelRequestAnswerComom> commentList;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ModelRequestCommmetCommon() {

    }

    public ModelRequestAnswerComom getAnswer() {
        return answer;
    }

    public void setAnswer(ModelRequestAnswerComom answer) {
        this.answer = answer;
    }

    public List<ModelRequestAnswerComom> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ModelRequestAnswerComom> commentList) {
        this.commentList = commentList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
