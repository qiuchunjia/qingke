package com.zhiyicx.zycx.adapter;

import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.api.Api.Favorites;
import com.zhiyicx.zycx.sociax.db.FavoritWeiboSqlHelper;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.NotifyCount;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public class FavoriteAdapter extends WeiboListAdapter {

    public FavoriteAdapter(BaseListFragment context,
                           ListData<SociaxItem> data) {
        super(context, data);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {

        if (obj == null) {
            return refreshNew(PAGE_COUNT);
        }

        ListData<SociaxItem> weiboDatas = this.getApiStatuses().indexHeader(
                (Weibo) obj, PAGE_COUNT);
        this.getApiUsers().unsetNotificationCount(NotifyCount.Type.atme,
                getMyUid());
        Thinksns app = (Thinksns) this.context.getApplicationContext();
        FavoritWeiboSqlHelper faWeiboSqlHelper = app.getFavoritWeiboSql();

        if (weiboDatas.size() > 0) {
            int dbSize = faWeiboSqlHelper.getDBWeiboSize();
            if (dbSize >= 20) {
                faWeiboSqlHelper.deleteWeibo(weiboDatas.size());
            } else if ((dbSize + weiboDatas.size()) >= 20) {
                faWeiboSqlHelper.deleteWeibo(dbSize + weiboDatas.size() - 20);
            }
            for (int i = 0; i < weiboDatas.size(); i++) {
                faWeiboSqlHelper.addWeibo((Weibo) weiboDatas.get(i));
            }
        }
        return weiboDatas;
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return this.getApiStatuses().indexFooter((Weibo) obj, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {

        ListData<SociaxItem> weiboDatas = this.getApiStatuses().index(count);
        Thinksns app = (Thinksns) this.context.getApplicationContext();
        FavoritWeiboSqlHelper faWeiboSqlHelper = app.getFavoritWeiboSql();

        if (weiboDatas != null && weiboDatas.size() > 0) {
            int dbSize = faWeiboSqlHelper.getDBWeiboSize();
            if (dbSize >= 20) {
                faWeiboSqlHelper.deleteWeibo(weiboDatas.size());
            } else if ((dbSize + weiboDatas.size()) >= 20) {
                faWeiboSqlHelper.deleteWeibo(dbSize + weiboDatas.size() - 20);
            }
            for (int i = 0; i < weiboDatas.size(); i++) {
                faWeiboSqlHelper.addWeibo((Weibo) weiboDatas.get(i));
            }
        }
        return weiboDatas;
    }

    private Favorites getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getFavorites();
    }

}
