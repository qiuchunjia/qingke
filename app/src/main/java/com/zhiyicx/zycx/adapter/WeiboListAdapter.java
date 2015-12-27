package com.zhiyicx.zycx.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.modle.ListViewAppend;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.net.StringDataListener;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.component.CustomerDialogNoTitle;
import com.zhiyicx.zycx.sociax.db.WeiboSqlHelper;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.WeiboDataItem;
import com.zhiyicx.zycx.util.Utils;

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

    public WeiboListAdapter(BaseListFragment context,
                            ListData<SociaxItem> data) {
        super(context, data);
        //initHeadImageFetcher();
        //initContentImageFetcher();

        append = new ListViewAppend(context);
        //append.setmHeadImageFetcher(mHeadImageFetcher);
        //append.setmContentImageFetcher(mContentImageFetcher);
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
        }
        weibo = this.getItem(position);
        /*
        if(position == this.list.size()-1 && position >=10){
		    doRefreshFooter();
        }
        */
        append.appendWeiboData(this.getItem(position), convertView);
        setZhanCount(convertView, position);
        return convertView;
    }

    private void setZhanCount(View view, int pos) {
        //Log.d("LISTViewAppend","Weibo feed id =" + weibo.getWeiboId() +"Zhan count=" + weibo.getZhanCount());
        final TextView zhanCount = (TextView) view.findViewById(R.id.zhan_count);
        zhanCount.setText(String.valueOf(weibo.getZhanCount()));
        final ImageView imageZhan = (ImageView) view.findViewById(R.id.img_zhan);
        if (weibo.isZhan())
            imageZhan.setImageResource(R.drawable.zan);
        else
            imageZhan.setImageResource(R.drawable.normal_zan);

        imageZhan.setOnClickListener(new ZhanClickListener(pos));
    }

    private class ZhanClickListener implements View.OnClickListener {

        private int mPos = 0;

        private ZhanClickListener(int pos) {
            mPos = pos;
        }

        @Override
        public void onClick(View v) {
            String url = MyConfig.WEB_ZAN_URL + Utils.getTokenString(mListContext.mContext) + "&feed_id=" + getItem(mPos).getWeiboId();
            if (getItem(mPos).isZhan())
                url = MyConfig.WEB_DELZAN_URL + Utils.getTokenString(mListContext.mContext) + "&feed_id=" + getItem(mPos).getWeiboId();
            //Log.d("LISTViewAppend", "zan url =" + url);
            NetComTools netComTools = NetComTools.getInstance(mListContext.mContext);
            netComTools.getNetString(url, new StringDataListener() {
                @Override
                public void OnReceive(String data) {
                    //Log.d("LISTViewAppend", "postZhan=" + data);
                    if ("1".equals(data.trim())) {
                        if (!getItem(mPos).isZhan()) {
                            //imageZhan.setImageResource(R.drawable.zan);
                            getItem(mPos).setZhanCount(getItem(mPos).getZhanCount() + 1);
                            //zhanCount.setText(String.valueOf(getItem(mPos).getZhanCount()));
                            getItem(mPos).setIsZhan(true);
                        } else {
                            //imageZhan.setImageResource(R.drawable.normal_zan);
                            int n = getItem(mPos).getZhanCount() - 1;
                            n = n < 0 ? 0 : n;
                            //zhanCount.setText(String.valueOf(n));
                            getItem(mPos).setZhanCount(n);
                            getItem(mPos).setIsZhan(false);
                        }
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void OnError(String error) {
                }
            });
        }
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
