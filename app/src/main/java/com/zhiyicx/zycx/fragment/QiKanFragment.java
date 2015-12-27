package com.zhiyicx.zycx.fragment;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.QiKanListAdapter;
import com.zhiyicx.zycx.activity.QiKanDetailsActivity;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.unit.Anim;
import com.zhiyicx.zycx.widget.LoadListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;

public class QiKanFragment extends BaseListFragment implements LoadListView.OnItemListener {
    final private static String TAG = "QiKanFragment";
    private Context mContext = null;
    private LoadListView mListView = null;
    private QiKanListAdapter mAdapter = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mCustView = inflater.inflate(R.layout.qikanfragment, container, false);
        mListView = (LoadListView) mCustView.findViewById(R.id.qikan_list);
        mListView.setOnItemListener(this);
        mListView.setDivider(null);
        loadData(false);
        return mCustView;
    }

    @Override
    public void onClick(View view, int position, long id) {

        Log.d(TAG, "get ZiXunlist item click position : " + position);
        JSONObject jsonObject = mAdapter.getItem(position - 1);
        try {
            int pid = jsonObject.getInt("pid");
            String title = jsonObject.getString("periodical_name");
            Intent intent = new Intent(mContext, QiKanDetailsActivity.class);
            intent.putExtra("pid", pid);
            intent.putExtra("title", title);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            Anim.in((Activity) mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(boolean isLoadNew) {
        ArrayList<JSONObject> data = new ArrayList<JSONObject>();
        mAdapter = new QiKanListAdapter(this, data);
        mListView.setAdapter(mAdapter, System.currentTimeMillis(), (Activity) mContext);
        mAdapter.loadInitData();
        mListView.setSelectionFromTop(0, 20);
    }
}
