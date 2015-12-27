package com.zhiyicx.zycx.sociax.modle;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.UserDataInvalidException;

public class SearchUser extends User {
    private static final String TAG = "SearchUser";

    public SearchUser(JSONObject data) throws DataInvalidException {
        if (data.equals("null"))
            throw new DataInvalidException();
        try {
            this.initUserInfo(data);
            if (data.has("mini") && !data.getString("mini").equals("null")) {
                this.setLastWeibo(new Weibo(data.getJSONObject("mini"),
                        "SEARCH_USER"));
            }
        } catch (JSONException e) {
            Log.d(TAG, "serachUser wm " + e.toString());
            throw new UserDataInvalidException();
        }
    }

    @Override
    public void initUserInfo(JSONObject data) throws DataInvalidException {
        try {
            this.setUid(data.getInt("uid"));
            this.setUserName(data.getString("uname"));

            if (data.has("location") && data.getString("location") != null)
                this.setLocation(data.getString("location"));

            this.setFace(data.getString("avatar_middle"));
            this.setSex(data.getString("sex"));
            // this.setFollowersCount(data.getInt("followers_count"));
            // this.setFollowedCount(data.getInt("followed_count"));
            if (data.has("follow_state")) {
                JSONObject countInfo = data.getJSONObject("follow_state");
                this.setFollowed(countInfo.getString("following").equals("0") ? false
                        : true);
            }
            // this.setVerified(data.getInt("is_init") == 0 ? false : true);
        } catch (JSONException e) {
            Log.d(TAG, "serachUser wm " + e.toString());
            throw new UserDataInvalidException();
        }
    }

    @Override
    public boolean checkValid() {
        // TODO Auto-generated method stub
        return super.checkValid();
    }

}
