package qcjlibrary.model;

import java.util.List;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperiencePostDetail extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /****
     * "weiba_id": "3", "logo":
     * "http://qingko-img.b0.upaiyun.com/2015/0609/10/55764a580914e.jpg!60x60",
     * "weiba_name": "第三方", "tags": [ "地方", "得分多", "fgf", "豆腐干", "第三方公司", "wo",
     * "aa", "ooo", "ee" ], "follower_count": "5", "recommend": "1",
     * "thread_count": "10", "following": null
     ****/
    private ModelExperiencePostDetailInfo post_detail;
    private List<ModelExperiencePostDetailItem> list;

    public ModelExperiencePostDetailInfo getPost_detail() {
        return post_detail;
    }

    public void setPost_detail(ModelExperiencePostDetailInfo post_detail) {
        this.post_detail = post_detail;
    }

    public List<ModelExperiencePostDetailItem> getList() {
        return list;
    }

    public void setList(List<ModelExperiencePostDetailItem> list) {
        this.list = list;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
