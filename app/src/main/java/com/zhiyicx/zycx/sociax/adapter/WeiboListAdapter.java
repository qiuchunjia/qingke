package com.zhiyicx.zycx.sociax.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.db.WeiboSqlHelper;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.ListViewAppend;
import com.zhiyicx.zycx.sociax.unit.WeiboDataItem;

public class WeiboListAdapter extends SociaxListAdapter {

    private static ListViewAppend append;
    private Weibo weibo;

    @Override
    public Weibo getFirst() {
        // TODO Auto-generated method stub
        return (Weibo) super.getFirst();
    }

    @Override
    public Weibo getLast() {
        // TODO Auto-generated method stub
        return (Weibo) super.getLast();
    }

    @Override
    public Weibo getItem(int position) {
        // TODO Auto-generated method stub
        return (Weibo) super.getItem(position);
    }

    public WeiboListAdapter(ThinksnsAbscractActivity context,
                            ListData<SociaxItem> data) {
        super(context, data);
        //	initHeadImageFetcher();
        //	initContentImageFetcher();

        append = new ListViewAppend(context);
        //	append.setmHeadImageFetcher(mHeadImageFetcher);
        //	append.setmContentImageFetcher(mContentImageFetcher);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WeiboDataItem weiboDataItem = null;
        if (convertView == null) {
            weiboDataItem = new WeiboDataItem();
            convertView = this.inflater.inflate(R.layout.weibolist, null);
            weiboDataItem.username = (TextView) convertView
                    .findViewById(R.id.user_name);
            weiboDataItem.weiboCtime = (TextView) convertView
                    .findViewById(R.id.weibo_ctime);
            weiboDataItem.weiboContent = (TextView) convertView
                    .findViewById(R.id.weibo_content);
            weiboDataItem.header = (ImageView) convertView
                    .findViewById(R.id.user_header);
            weiboDataItem.weiboFrom = (TextView) convertView
                    .findViewById(R.id.weibo_from);
            convertView.setTag(weiboDataItem);
        } else {
            weiboDataItem = (WeiboDataItem) convertView.getTag();
        }

        weibo = this.getItem(position);
        boolean canRefresh = false;
        if (refresh == null) {
            canRefresh = true;
        } else {
            canRefresh = refresh.isClickable();
        }
        /*
		 * if(position == this.list.size()-1 && canRefresh && position >=10){
		 * doRefreshFooter(); }
		 */
        // Weibo weibo = this.getItem(position);
        append.appendWeiboData(this.getItem(position), convertView);
        return convertView;
    }

    @Override
    public void doRefreshHeader() {
        super.doRefreshHeader();
    }

    @Override
    public void doRefreshFooter() {
        super.doRefreshFooter();
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

    @Override
    public void addFooter(ListData<SociaxItem> list) {
        super.addFooter(list);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {

        if (obj == null)
            return refreshNew(PAGE_COUNT);

        //ListData<SociaxItem> weiboDatas = getApiStatuses().friendsHeaderTimeline((Weibo) obj, PAGE_COUNT);
        ListData<SociaxItem> weiboDatas = getApiStatuses().publicHeaderTimeline((Weibo) obj, PAGE_COUNT);

        Thinksns app = (Thinksns) this.context.getApplicationContext();
        WeiboSqlHelper weiboSqlHelper = app.getWeiboSql();

        if (weiboDatas.size() > 0) {
            int dbSize = weiboSqlHelper.getDBWeiboSize();
            if (dbSize >= 20) {
                weiboSqlHelper.deleteWeibo(weiboDatas.size());
            } else if ((dbSize + weiboDatas.size()) >= 20) {
                weiboSqlHelper.deleteWeibo(dbSize + weiboDatas.size() - 20);
            }
            for (int i = 0; i < weiboDatas.size(); i++) {
                weiboSqlHelper.addWeibo((Weibo) weiboDatas.get(i));
            }
        }
        return weiboDatas;
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        //return getApiStatuses().friendsFooterTimeline((Weibo) obj, PAGE_COUNT);
        return getApiStatuses().publicFooterTimeline((Weibo) obj, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {

        //ListData<SociaxItem> weiboDatas = getApiStatuses().friendsTimeline(count);
        ListData<SociaxItem> weiboDatas = getApiStatuses().publicTimeline(count);

        Thinksns app = (Thinksns) this.context.getApplicationContext();
        WeiboSqlHelper weiboSqlHelper = app.getWeiboSql();

        if (weiboDatas.size() > 0) {
            int dbSize = weiboSqlHelper.getDBWeiboSize();
            if (dbSize >= 20) {
                weiboSqlHelper.deleteWeibo(weiboDatas.size());
            } else if ((dbSize + weiboDatas.size()) >= 20) {
                weiboSqlHelper.deleteWeibo(dbSize + weiboDatas.size() - 20);
            }
            for (int i = 0; i < weiboDatas.size(); i++) {
                weiboSqlHelper.addWeibo((Weibo) weiboDatas.get(i));
            }
        }
        return weiboDatas;
    }

}
