package com.zhiyicx.zycx.sociax.adapter;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.ApiNotifytion;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.Follow;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.MessageType;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FollowListAdapter extends UserListAdapter {
    private static String TAG = "FollowList";
    public static final int FOLLOWER = 0; // 粉丝
    public static final int FOLLOWING = 1; // 关注
    public static final int FOLLOW_EACH = 2; // 互相关注

    private static ImageView userheader;
    private static TextView username;
    private static TextView lastWeibo;
    private static Button followButton;

    // private static ListViewAppend append;
    private int nowType;
    private static User user;
    private ThinksnsAbscractActivity context;
    private static final int ADD_FOLLOWED = 0;
    private static final int DEL_FOLLOWED = 1;

    @Override
    public Follow getFirst() {
        return (Follow) super.getFirst();
    }

    @Override
    public Follow getLast() {
        return (Follow) super.getLast();
    }

    @Override
    public Follow getItem(int position) {
        return (Follow) super.getItem(position);
    }

    public FollowListAdapter(ThinksnsAbscractActivity context,
                             ListData<SociaxItem> data, int type, User user) {
        super(context, data);
        FollowListAdapter.user = user;
        nowType = type;
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        switch (nowType) {
            case FOLLOWING:
                if (obj == null) {
                    return refreshNew(PAGE_COUNT);
                }
                return getApiStatuses().followingHeader(user, (Follow) obj,
                        PAGE_COUNT);
            case FOLLOWER:
                if (obj == null) {
                    return refreshNew(PAGE_COUNT);
                }
                getApiNotifytion().setNotifyRead(
                        MessageType.new_follower.toString());
                return getApiStatuses().followersHeader(user, (Follow) obj,
                        PAGE_COUNT);
            case FOLLOW_EACH:
                if (obj == null) {
                    return refreshNew(PAGE_COUNT);
                }
                return getApiStatuses().followEachHeader(user, (Follow) obj,
                        PAGE_COUNT);
        }
        return null;
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        switch (nowType) {
            case FOLLOWING:
                return getApiStatuses().followingFooter(user, (Follow) obj,
                        PAGE_COUNT);
            case FOLLOWER:
                return getApiStatuses().followersFooter(user, (Follow) obj,
                        PAGE_COUNT);
            case FOLLOW_EACH:
                return getApiStatuses().followEachFooter(user, (Follow) obj,
                        PAGE_COUNT);
        }
        return null;
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        switch (nowType) {
            case FOLLOWING:
                return getApiStatuses().following(user, PAGE_COUNT);
            case FOLLOWER:
                getApiNotifytion().setNotifyRead(
                        MessageType.new_follower.toString());
                return getApiStatuses().followers(user, PAGE_COUNT);
            case FOLLOW_EACH:
                return getApiStatuses().followEach(user, PAGE_COUNT);
        }
        return null;
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

    private ApiNotifytion getApiNotifytion() {
        Thinksns app = thread.getApp();
        return app.getApiNotifytion();
    }
}
