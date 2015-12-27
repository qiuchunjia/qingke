package com.zhiyicx.zycx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.QuestionListAdapter;
import com.zhiyicx.zycx.activity.QuestionDetailsActivity;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.util.Utils;
import com.zhiyicx.zycx.widget.LoadListView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/18.
 */
public class QuestionSearchFragment extends BaseListFragment implements LoadListView.OnItemListener {
    private QuestionListAdapter mAdapter;
    private LoadListView mListView = null;
    private String key = null;
    private boolean isLoad = false;

    @Override
    public OnTouchListListener getListView() {
        return mListView;
    }

    @Override
    public void doRefreshHeader() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustView = inflater.inflate(R.layout.question_list_layout, container, false);
        mListView = (LoadListView) mCustView.findViewById(android.R.id.list);
        mListView.setOnItemListener(this);
        ArrayList<JSONObject> data = new ArrayList<JSONObject>();
        mAdapter = new QuestionListAdapter(this, data, 0);
        mAdapter.setSearch(true);
        mListView.setAdapter(mAdapter, System.currentTimeMillis(), mContext);
        mListView.setVisibility(View.INVISIBLE);
        return mCustView;
    }

    @Override
    public void onClick(View view, int position, long id) {
        try {
            int qid = mAdapter.getItem(position - 1).getInt("question_id");
            Intent intent = new Intent(mContext, QuestionDetailsActivity.class);
            intent.putExtra("qid", qid);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void doSearch(String key) {
        if (TextUtils.isEmpty(key))
            return;
        this.key = Utils.getUTF8String(key);
        if (mContext != null) {
            loadData(false);
        }
    }

    @Override
    public void loadData(boolean isLoadNew) {
        mAdapter.loadSearchData(key);
        mListView.setSelectionFromTop(0, 20);
        isLoad = true;
    }
}
