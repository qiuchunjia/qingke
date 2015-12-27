package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperience extends Model {

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

    public ModelExperience() {
    }

    public ModelExperience(JSONObject data) {
        try {
            if (data.has("weiba_id")) {

                setWeiba_id(data.getString("weiba_id"));
            }
            if (data.has("logo")) {

                setLogo(data.getString("logo"));
            }

            if (data.has("weiba_name")) {

                setWeiba_name(data.getString("weiba_name"));
            }
            if (data.has("follower_count")) {

                setFollower_count(data.getString("follower_count"));
            }
            if (data.has("recommend")) {

                setRecommend(data.getString("recommend"));
            }
            if (data.has("thread_count")) {

                setThread_count(data.getString("thread_count"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String weiba_id;
    private String logo;
    private String weiba_name;
    private String follower_count;
    private String recommend;
    private String thread_count;

    public String getWeiba_id() {
        return weiba_id;
    }

    public void setWeiba_id(String weiba_id) {
        this.weiba_id = weiba_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWeiba_name() {
        return weiba_name;
    }

    public void setWeiba_name(String weiba_name) {
        this.weiba_name = weiba_name;
    }

    public String getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(String follower_count) {
        this.follower_count = follower_count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getThread_count() {
        return thread_count;
    }

    public void setThread_count(String thread_count) {
        this.thread_count = thread_count;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
