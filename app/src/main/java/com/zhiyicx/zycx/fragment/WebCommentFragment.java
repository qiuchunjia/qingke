package com.zhiyicx.zycx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.CommentMyListAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.widget.CommentMyList;

public class WebCommentFragment extends BaseListFragment {

    final private static String TAG = "WebCommentFragment";

    private CommentMyListAdapter mCommentAdapter;
    private CommentMyList mCommentlist;

    @Override
    public OnTouchListListener getListView() {
        return mCommentlist;
    }

    @Override
    public void doRefreshHeader() {
        if (mCommentAdapter != null)
            mCommentAdapter.doRefreshHeader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustView = inflater.inflate(R.layout.web_commentfragment, container, false);
        mCommentlist = (CommentMyList) mCustView.findViewById(R.id.commentList_home);
        loadData(false);
        return mCustView;
    }

    @Override
    public void loadData(boolean isLoadNew) {
        Thinksns app = (Thinksns) mContext.getApplicationContext();
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        ListData<SociaxItem> tempAtMeDatas2 = app.getMyCommentSql().getDBCommentList();

        //if (tempAtMeDatas2.size() != 0 && !isLoadNew)
        if (false) {
            mCommentAdapter = new CommentMyListAdapter(this, tempAtMeDatas2);
            mCommentlist.setAdapter(mCommentAdapter, System.currentTimeMillis(), mContext);
            mCommentAdapter.loadInitData();
            mCommentlist.setSelectionFromTop(0, 20);
            mCommentAdapter.doRefreshHeader();
        } else {
            mCommentAdapter = new CommentMyListAdapter(this, data);
            mCommentlist.setAdapter(mCommentAdapter, System.currentTimeMillis(), mContext);
            mCommentAdapter.loadInitData();
            mCommentlist.setSelectionFromTop(0, 20);
        }
    }

}
