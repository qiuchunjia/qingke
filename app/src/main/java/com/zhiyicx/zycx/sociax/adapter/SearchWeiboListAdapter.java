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
import com.zhiyicx.zycx.sociax.modle.Weibo;

public class SearchWeiboListAdapter extends WeiboListAdapter {
    private String key;

    public SearchWeiboListAdapter(ThinksnsAbscractActivity context,
                                  ListData<SociaxItem> data, String key) {
        super(context, data);
        this.key = key;
    }

    public SearchWeiboListAdapter(ThinksnsAbscractActivity context,
                                  ListData<SociaxItem> data) {
        super(context, data);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().searchHeader(this.key, (Weibo) obj, 20);
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().searchFooter(this.key, (Weibo) obj, 20);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().search(this.key, count);
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

}
