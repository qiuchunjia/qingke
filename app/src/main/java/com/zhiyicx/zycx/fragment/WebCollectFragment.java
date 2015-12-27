package com.zhiyicx.zycx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.FavoriteAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.widget.WeiboList;

public class WebCollectFragment extends BaseListFragment {

    final private static String TAG = "WebCollectFragment";

    private FavoriteAdapter mFavoriteAdapter;
    private WeiboList mWeibolist;

    @Override
    public OnTouchListListener getListView() {
        return mWeibolist;
    }

    @Override
    public void doRefreshHeader() {
        if (mFavoriteAdapter != null)
            mFavoriteAdapter.doRefreshHeader();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustView = inflater.inflate(R.layout.web_list_layout, container, false);
        mWeibolist = (WeiboList) mCustView.findViewById(R.id.weiboList_home);
        loadData(false);
        return mCustView;
    }

    @Override
    public void loadData(boolean isLoadNew) {
        Thinksns app = (Thinksns) mContext.getApplicationContext();
        ListData<SociaxItem> tempWeiboDatas = app.getFavoritWeiboSql().getWeiboList();
        //if (tempWeiboDatas.size() != 0 && !isLoadNew)
        if (false) {
            mFavoriteAdapter = new FavoriteAdapter(this, tempWeiboDatas);
            mWeibolist.setAdapter(mFavoriteAdapter, System.currentTimeMillis(), mContext);
            mFavoriteAdapter.loadInitData();
            mWeibolist.setSelectionFromTop(0, 20);
            mFavoriteAdapter.doRefreshHeader();
        } else {
            ListData<SociaxItem> data = new ListData<SociaxItem>();
            mFavoriteAdapter = new FavoriteAdapter(this, data);
            mWeibolist.setAdapter(mFavoriteAdapter, System.currentTimeMillis(), mContext);
            mFavoriteAdapter.loadInitData();
            mWeibolist.setSelectionFromTop(0, 20);
        }
    }
}
