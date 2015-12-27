package com.zhiyicx.zycx.sociax.adapter;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.ListViewAppend;

import android.view.View;
import android.view.ViewGroup;

public class UserWeiboListAdapter extends WeiboListAdapter {

    private static ListViewAppend append;
    private static User user;
    private static int uid;

    public UserWeiboListAdapter(ThinksnsAbscractActivity context,
                                ListData<SociaxItem> data, int uid) {
        super(context, data);
        append = new ListViewAppend(context);
        UserWeiboListAdapter.uid = uid;
        user = new User();
        user.setUid(uid);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().userHeaderTimeline(user, (Weibo) obj, 5);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return super.getView(position, convertView, parent);
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().userFooterTimeline(user, (Weibo) obj, 5);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().userTimeline(user, count);
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

}
