package com.zhiyicx.zycx.fragment;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.QClassListAdapter;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.widget.LoadListView;

/**
 * Created by Administrator on 2015/1/16.
 */
public class QClassListFragment extends BaseListFragment implements LoadListView.OnItemListener {
    final private static String TAG = "QClassListFragment";
    private QClassListAdapter mAdapter = null;
    private LoadListView mListView = null;
    private ArrayList<JSONObject> mData = null;
    private int mStatus = 0;
    private int mType = 0;

    public static QClassListFragment newInstanse(int type) {
        QClassListFragment f = new QClassListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public OnTouchListListener getListView() {
        return mListView;
    }

    @Override
    public void doRefreshHeader() {
        if (mAdapter != null)
            mAdapter.doRefreshHeader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustView = inflater.inflate(R.layout.qclass_list_layout, container, false);
        Bundle bundle = getArguments();
        mType = bundle.getInt("type");
        mListView = (LoadListView) mCustView.findViewById(android.R.id.list);
        mListView.setOnItemListener(this);
        mData = new ArrayList<JSONObject>();
        mAdapter = new QClassListAdapter(this, mData, mType);
        mAdapter.setStatus(mStatus);
        mListView.setAdapter(mAdapter, System.currentTimeMillis(), mContext);
        loadData(false);
        return mCustView;
    }

    @Override
    public void loadData(boolean isLoadNew) {
        if (mType == 0)
            mAdapter.loadHomeInitData();
        else
            mAdapter.loadInitData();
        mListView.setSelectionFromTop(0, 20);
    }

    public void setStatus(int status) {
        mStatus = status;
        if (mAdapter != null) {
            mAdapter.setStatus(status);
            mAdapter.clearList();
        }
    }

    public void loadNewData() {
        if (mAdapter != null) {
            mAdapter.clearList();
            mAdapter.loadInitData();
        }
    }

    @Override
    public void onClick(View view, int position, long id) {
        JSONObject jsonObject = mAdapter.getItem(position - 1);
        try {
//            int _id = jsonObject.getInt("course_id");
//            String img_url = jsonObject.getString("cover");
//            Intent intent = new Intent(mContext, QClassDetailsActivity.class);
//            intent.putExtra("id", _id);
//            intent.putExtra("cover", img_url);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(intent);
//            Anim.in(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
