package com.zhiyicx.zycx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.WeiboListAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.widget.WeiboList;

/**
 * Created by Administrator on 2015/1/10.
 */
public class WebListFragment extends BaseListFragment {

    final private static String TAG = "WebListFragment";

    private WeiboListAdapter mWeiboAdapter;
    private WeiboList mWeibolist;

    @Override
    public OnTouchListListener getListView() {
        return mWeibolist;
    }

    @Override
    public void doRefreshHeader() {
        if (mWeiboAdapter != null)
            mWeiboAdapter.doRefreshHeader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mCustView == null)
            mCustView = inflater.inflate(R.layout.web_list_layout, container, false);
        mWeibolist = (WeiboList) mCustView.findViewById(R.id.weiboList_home);
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        mWeiboAdapter = new WeiboListAdapter(this, data);
        mWeibolist.setAdapter(mWeiboAdapter, System.currentTimeMillis(), mContext);
        loadData(false);
        return mCustView;
    }

    @Override
    public void loadData(boolean isLoadNew) {
        Thinksns app = (Thinksns) mContext.getApplicationContext();
        ListData<SociaxItem> tempWeiboDatas = app.getWeiboSql().getWeiboList();

        //if (tempWeiboDatas.size() != 0 && !isLoadNew)
        if (false) {
            //mWeiboAdapter = new WeiboListAdapter(this, tempWeiboDatas);
            //mWeibolist.setAdapter(mWeiboAdapter, System.currentTimeMillis(), mContext);
            mWeiboAdapter.loadInitData();
            mWeibolist.setSelectionFromTop(0, 20);
            doRefreshHeader();
        } else {
            //mWeiboAdapter.loadInitData();
            mWeiboAdapter.loadHomeInitData();
            mWeibolist.setSelectionFromTop(0, 20);
        }
    }
}
