package com.zhiyicx.zycx.adapter;

import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.db.AtMeSqlHelper;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.NotifyCount;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public class AtomAdapter extends WeiboListAdapter {

    private static final String TAG = "AtomAdapter";

    public AtomAdapter(BaseListFragment context,
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

        this.getApiUsers().unsetNotificationCount(NotifyCount.Type.atme,
                getMyUid());

        ListData<SociaxItem> weiboDatas = this.getApiStatuses().mentionsHeader(
                (Weibo) obj, PAGE_COUNT);

        Thinksns app = (Thinksns) this.context.getApplicationContext();
        AtMeSqlHelper atMeSqlHelper = app.getAtMeSql();

        if (weiboDatas.size() > 0) {
            int dbSize = atMeSqlHelper.getDBAtMeSize();
            if (dbSize >= 20) {
                atMeSqlHelper.deleteAtMe(weiboDatas.size());
            } else if ((dbSize + weiboDatas.size()) >= 20) {
                atMeSqlHelper.deleteAtMe(dbSize + weiboDatas.size() - 20);
            }
            for (int i = 0; i < weiboDatas.size(); i++) {
                atMeSqlHelper.addAtMe((Weibo) weiboDatas.get(i));
            }
        }
        return weiboDatas;
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return this.getApiStatuses().mentionsFooter((Weibo) obj, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {

        this.getApiUsers().unsetNotificationCount(NotifyCount.Type.atme,
                getMyUid());

        ListData<SociaxItem> weiboDatas = this.getApiStatuses().mentions(count);
        Thinksns app = (Thinksns) this.context.getApplicationContext();
        AtMeSqlHelper atMeSqlHelper = app.getAtMeSql();

        if (weiboDatas.size() > 0) {
            int dbSize = atMeSqlHelper.getDBAtMeSize();
            if (dbSize >= 20) {
                atMeSqlHelper.deleteAtMe(weiboDatas.size());
            } else if ((dbSize + weiboDatas.size()) >= 20) {
                atMeSqlHelper.deleteAtMe(dbSize + weiboDatas.size() - 20);
            }
            for (int i = 0; i < weiboDatas.size(); i++) {
                atMeSqlHelper.addAtMe((Weibo) weiboDatas.get(i));
            }
        }

        return weiboDatas;
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

}
