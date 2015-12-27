package qcjlibrary.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperienceDetail extends Model {

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
    private ModelExperienceDetailInfor info;
    private List<ModelExperienceDetailItem1> posts;

    public ModelExperienceDetailInfor getInfo() {
        return info;
    }

    public void setInfo(ModelExperienceDetailInfor info) {
        this.info = info;
    }

    public List<ModelExperienceDetailItem1> getPosts() {
        return posts;
    }

    public void setPosts(List<ModelExperienceDetailItem1> posts) {
        this.posts = posts;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
