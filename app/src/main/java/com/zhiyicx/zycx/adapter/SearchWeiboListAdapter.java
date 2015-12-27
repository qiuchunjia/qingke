package com.zhiyicx.zycx.adapter;

import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.sociax.android.Thinksns;
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

    public SearchWeiboListAdapter(BaseListFragment context,
                                  ListData<SociaxItem> data, String key) {
        super(context, data);
        this.key = key;
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        /*if (obj == null)
            return null;
		return getApiStatuses().searchHeader(this.key, (Weibo) obj, 20);*/
        return null;
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
        return null;//getApiStatuses().search(this.key, count);
    }

    @Override
    public ListData<SociaxItem> searchNew(String key)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        this.key = key;
        return getApiStatuses().search(key, 20);
        //return super.searchNew(key);
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

}
