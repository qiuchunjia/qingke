package com.zhiyicx.zycx.sociax.api;

import java.io.File;

import android.graphics.Bitmap;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.NotifyCount;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;

public interface ApiUsers {
    static final String MOD_NAME = "User";
    static final String SHOW = "show";
    static final String UPLOAD_FACE = "upload_face";
    static final String NOTIFICATION_COUNT = "notificationCount";

    static final String FOLLOWERS = "user_followers"; // 粉丝列表
    static final String FOOLOWING = "user_following"; // 关注列表
    static final String FOLLOWEACH = "user_friends"; // 互相关注

    static final String FOLLOW_CREATE = "follow_create"; // 关注
    static final String FOLLOW_DESTROY = "follow_destroy"; // 取消关注

    static final String RECENT_USER = "search_at"; // 最近@联系人
    static final String RECENT_TOPIC = "search_topic"; // 推荐话题

    static final String UNSET_NOTIFICATION_COUNT = "unsetNotificationCount";
    static final String NOTIFICATIONLIST = "getNotificationList";

    static final String GET_USER_CATEGORY = "get_user_category"; //返回用户分类

    static final String SEARCH_BY_UESR_CATEGORY = "search_by_uesr_category"; //按官方推荐分类搜索用户
    static final String SEARCH_BY_TAG = "search_by_tag"; // // 按标签搜索用户
    static final String SEARCH_BY_AREA = "search_by_area"; // 按地区搜索用户
    static final String SEARCH_BY_VERIFY_CATEGORY = "search_by_verify_category"; // 按认证分类搜索用户

    static final String GET_USER_FOLLOWER = "get_user_follower";
    static final String CHECKIN = "checkin";
    static final String NEIGHBOR = "neighbors";

    public User show(User user) throws ApiException, DataInvalidException,
            VerifyErrorException;

    public NotifyCount notificationCount(int uid) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public boolean unsetNotificationCount(NotifyCount.Type type, int uid)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public boolean uploadFace(File file) throws ApiException;

    public boolean checkint(String la, String lo) throws ApiException;

    public boolean uploadFace(Bitmap bitmap, File file) throws ApiException;

    public ListData<SociaxItem> getNotificationList(int uid)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> getRecentTopic() throws ApiException;

    public ListData<SociaxItem> getRecentAt() throws ApiException;

    public ListData<SociaxItem> getUserCategory(String type) throws ApiException;

    public ListData<SociaxItem> searchByUesrCategory(String key) throws ApiException;

    public ListData<SociaxItem> searchByArea(String key) throws ApiException;

    public ListData<SociaxItem> searchByTag(String key) throws ApiException;

    public ListData<SociaxItem> searchByVerifyCategory(String key) throws ApiException;

    public ListData<SociaxItem> getUserFollower() throws ApiException;

    public ListData<SociaxItem> getNeighbor(String la, String lo)
            throws ApiException;

}
