package com.zhiyicx.zycx.sociax.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.UserDataInvalidException;
import com.zhiyicx.zycx.sociax.net.Request;

public class User extends SociaxItem implements Serializable {

    private static final String TAG = "User";
    protected String mUserName;
    protected String mPassword;
    protected String mToken;
    protected String mSecretToken;
    protected int mUid;

    protected String dipartment;
    protected String userEmail;
    protected String userPhone;
    protected String tel;
    protected String userTag;
    protected String QQ;
    protected String intro;

    protected String mProvince;
    protected String mCity;
    protected String mLocation;
    protected String mFace;
    protected String mSex;
    protected boolean isInBlackList;
    protected int mWeiboCount;
    protected int mFollowersCount;
    protected int mFollowedCount;
    protected boolean isFollowed;
    protected boolean isVerified;
    protected Weibo lastWeibo;
    protected String jsonString;
    protected String userJson;

    protected int isMyContact;

    protected List<String[]> otherFiled;
    protected String department;

    protected UserApprove usApprove;

    public String getUserJson() {
        return userJson;
    }

    public void setUserJson(String userJson) {
        this.userJson = userJson;
    }

    public User() {
        super();
    }

    public User(JSONObject data) throws DataInvalidException {
        super(data);
        try {
            this.initUserInfo(data);
            if (data.has("status") && !data.getString("status").equals("")) {
                this.setLastWeibo(new Weibo(data.getJSONObject("status")));
            }

            if (data.has("weibo") && !data.getString("status").equals("")) {
                this.setLastWeibo(new Weibo(data.getJSONObject("weibo")));
            }

            if (data.has("mini") && !data.getString("mini").equals("null")) {
                this.setLastWeibo(new Weibo(data.getJSONObject("mini")));
            }
            // 添加判定
            if (data.has("isInBlackList")) {
                this.setIsInBlackList(data.getInt("isInBlackList") == 1 ? true
                        : false);
            }
            // 是否收藏为联系人
            if (data.has("isMyContact")) {
                this.setIsMyContact(data.getInt("isMyContact"));
            }
            this.jsonString = data.toString();
        } catch (JSONException e) {
            throw new UserDataInvalidException();
        }
    }

    public void initUserInfo(JSONObject data) throws DataInvalidException {
        try {
            this.setUid(data.getInt("uid"));
            this.setUserName(data.getString("uname"));
            this.setProvince(data.getString("province"));
            this.setCity(data.getString("city"));
            this.setLocation(data.getString("location"));
            this.setSex(data.getString("sex"));
            this.setUserEmail(data.getString("email"));
            this.setIntro(data.getString("intro"));

            if (data.has("department"))
                if (!data.getString("department").equals("")) {
                    setDepartment(data.getJSONObject("department").getString(
                            "title"));
                }

            if (data.has("user_tag")) {
                this.setUserTag(data.getString("user_tag"));
            }
            if (data.has("avatar_middle")) {
                this.setFace(data.getString("avatar_middle"));
            }
            if (data.has("count_info")) {
                JSONObject countInfo = data.getJSONObject("count_info");
                if (countInfo.has("weibo_count"))
                    ;
                this.setWeiboCount(countInfo.getInt("weibo_count"));
                if (countInfo.has("follower_count"))
                    this.setFollowersCount(countInfo
                            .getString("follower_count").equals("false") ? 0
                            : countInfo.getInt("follower_count"));
                if (countInfo.has("following_count"))
                    this.setFollowedCount(countInfo
                            .getString("following_count").equals("false") ? 0
                            : countInfo.getInt("following_count"));
            }
            if (data.has("user_data")) {
                this.setFollowersCount(data.getJSONObject("user_data").getInt(
                        "follower_count"));
            }
            if (data.has("follow_state")) {
                JSONObject countInfo = data.getJSONObject("follow_state");
                this.setFollowed(countInfo.getString("following").equals("0") ? false
                        : true);
            }
            if (data.has("profile") && (!data.getString("profile").equals(""))) {
                JSONObject profile = data.getJSONObject("profile");
                if (profile.has("tel")) {
                    this.setTel(profile.getJSONObject("tel").getString("value"));
                    profile.remove("tel");
                }
                if (profile.has("mobile")) {
                    this.setUserPhone(profile.getJSONObject("mobile")
                            .getString("value"));
                    profile.remove("mobile");
                }

                if (profile.has("department")) {
                    this.setDepartment(profile.getJSONObject("department")
                            .getString("value"));
                    profile.remove("department");
                }

                if (profile.has("email")) {
                    profile.remove("email");
                }

                otherFiled = new ArrayList<String[]>();
                for (Iterator iterator = profile.keys(); iterator.hasNext(); ) {

                    String[] ofiled = new String[2];
                    String key = (String) iterator.next();

                    JSONObject temp = profile.getJSONObject(key);
                    ofiled[0] = temp.getString("name");
                    ofiled[1] = temp.getString("value");
                    otherFiled.add(ofiled);
                }
                this.setOtherFiled(otherFiled);
            }
            // 用户认证信息
            if (data.has("user_group")) {
                setUsApprove(new UserApprove(data));
            }

        } catch (JSONException e) {
            Log.d(TAG, "User-->解析出错 ； wm " + e.toString());
            // throw new UserDataInvalidException();
        }
    }

    public User(int uid, String userName, String password, String token,
                String secretToken) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setToken(token);
        this.setSecretToken(secretToken);
        this.setUid(uid);
    }

    public boolean isNullForUid() {
        return this.getUid() == 0;
    }

    public String toJSON() {
        return this.jsonString;
    }

    public boolean isNullForUserName() {
        String temp = this.getUserName();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForProvince() {
        String temp = this.getProvince();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForCity() {
        String temp = this.getCity();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForLocation() {
        String temp = this.getLocation();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForFace() {
        String face = this.getFace();
        return face == null || face.equals(NULL);
    }

    public boolean isNullForSex() {
        String temp = this.getSex();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForWeiboCount() {
        return this.getWeiboCount() == 0;
    }

    public boolean isNullForFollowersCount() {
        return this.getFollowersCount() == 0;
    }

    public boolean isNullForFollowedCount() {
        return this.getFollowedCount() == 0;
    }

    public boolean isNullForLastWeibo() {
        return this.getLastWeibo() == null || !this.getLastWeibo().checkValid();
    }

    public boolean isNullForToken() {
        String temp = this.getToken();
        return temp == null || temp.equals(NULL);
    }

    public boolean isNullForSecretToken() {
        String temp = this.getSecretToken();
        return temp == null || temp.equals(NULL);
    }

    public boolean hasVerifiedInAndroid() {
        return !(this.isNullForToken() || this.isNullForSecretToken() || this
                .isNullForUid());
    }

    @Override
    public boolean checkValid() {
        boolean result = true;
        result = result
                && !(this.isNullForUid() || this.isNullForUserName() || this
                .isNullForSex());
        return result;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        Request.setToken(mToken);
        this.mToken = mToken;
    }

    public boolean getIsInBlackList() {
        return isInBlackList;
    }

    public void setIsInBlackList(boolean isInBlackList) {
        this.isInBlackList = isInBlackList;
    }

    public String getSecretToken() {
        return mSecretToken;
    }

    public void setSecretToken(String mSecretToken) {
        Request.setSecretToken(mSecretToken);
        this.mSecretToken = mSecretToken;
    }

    public int getUid() {
        return mUid;
    }

    public void setUid(int mUid) {
        this.mUid = mUid;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getFace() {
        return mFace;
    }

    public void setFace(String mFace) {
        this.mFace = mFace;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String mSex) {
        this.mSex = mSex;
    }

    public int getWeiboCount() {
        return mWeiboCount;
    }

    public void setWeiboCount(int mWeiboCount) {
        this.mWeiboCount = mWeiboCount;
    }

    public int getFollowersCount() {
        return mFollowersCount;
    }

    public void setFollowersCount(int mFollowersCount) {
        this.mFollowersCount = mFollowersCount;
    }

    public int getFollowedCount() {
        return mFollowedCount;
    }

    public void setFollowedCount(int mFollowedCount) {
        this.mFollowedCount = mFollowedCount;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Weibo getLastWeibo() {
        return lastWeibo;
    }

    public void setLastWeibo(Weibo lastWeibo) {
        this.lastWeibo = lastWeibo;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String qQ) {
        QQ = qQ;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String getUserface() {
        return this.getFace();
    }

    public List<String[]> getOtherFiled() {
        return otherFiled;
    }

    public void setOtherFiled(List<String[]> otherFiled) {
        this.otherFiled = otherFiled;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getIsMyContact() {
        return isMyContact;
    }

    public void setIsMyContact(int isMyContact) {
        this.isMyContact = isMyContact;
    }

    public UserApprove getUsApprove() {
        return usApprove;
    }

    public void setUsApprove(UserApprove usApprove) {
        this.usApprove = usApprove;
    }
}
