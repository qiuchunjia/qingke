package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:12:02 类描述：这个类是实现
 */

public class ModelNotifyDig extends Model {

    /**
     * "diggid": "1219", "myname": "雅蠛蝶", "username": "zhiyicx", "userface":
     * "http://qingko-img.b0.upaiyun.com/avatar/d1/d5/92/original.jpg!small.avatar.jpg?v1449719204"
     * , "time": "03月12日 14:51", "feed_data":
     * "#wwwwww# (分享自@凡2014)[SITE_URL]...", "feed_id": "6434"
     */
    private static final long serialVersionUID = 1L;
    private String diggid;
    private String myname;
    private String username;
    private String userface;
    private String time;
    private String feed_data;
    private String feed_id;

    public ModelNotifyDig() {

    }

    public ModelNotifyDig(JSONObject data) {
        try {
            if (data.has("diggid")) {

                setDiggid(data.getString("diggid"));
            }
            if (data.has("myname")) {

                setMyname(data.getString("myname"));
            }
            if (data.has("username")) {

                setUsername(data.getString("username"));
            }
            if (data.has("userface")) {

                setUserface(data.getString("userface"));
            }
            if (data.has("time")) {

                setTime(data.getString("time"));
            }
            if (data.has("feed_data")) {

                setFeed_data(data.getString("feed_data"));
            }
            if (data.has("feed_id")) {

                setFeed_id(data.getString("feed_id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDiggid() {
        return diggid;
    }

    public void setDiggid(String diggid) {
        this.diggid = diggid;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFeed_data() {
        return feed_data;
    }

    public void setFeed_data(String feed_data) {
        this.feed_data = feed_data;
    }

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
