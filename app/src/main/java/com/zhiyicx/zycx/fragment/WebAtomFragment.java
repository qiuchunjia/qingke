package com.zhiyicx.zycx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.AtomAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.widget.WeiboList;

public class WebAtomFragment extends BaseListFragment {
    final private static String TAG = "WebAtomFragment";

    private AtomAdapter mWeiboAdapter;
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
        mCustView = inflater.inflate(R.layout.web_list_layout, container, false);
        mWeibolist = (WeiboList) mCustView.findViewById(R.id.weiboList_home);
        loadData(false);
        return mCustView;
    }

    @Override
    public void loadData(boolean isLoadNew) {
        Thinksns app = (Thinksns) mContext.getApplicationContext();
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        ListData<SociaxItem> tempAtMeDatas = app.getAtMeSql().getAtMeList();

        //if (tempAtMeDatas.size() != 0 && !isLoadNew)
        if (false) {
            mWeiboAdapter = new AtomAdapter(this, tempAtMeDatas);
            mWeibolist.setAdapter(mWeiboAdapter, System.currentTimeMillis(), mContext);
            mWeiboAdapter.loadInitData();
            mWeibolist.setSelectionFromTop(0, 20);
            mWeiboAdapter.doRefreshHeader();
        } else {
            mWeiboAdapter = new AtomAdapter(this, data);
            mWeibolist.setAdapter(mWeiboAdapter, System.currentTimeMillis(), mContext);
            mWeiboAdapter.loadInitData();
            mWeibolist.setSelectionFromTop(0, 20);
        }
    }
}
