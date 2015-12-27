package com.zhiyicx.zycx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.QuestionListAdapter;
import com.zhiyicx.zycx.activity.QuestionDetailsActivity;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.widget.LoadListView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/5.
 */
public class QuestionListFragment extends BaseListFragment implements LoadListView.OnItemListener {

    final private static String TAG = "QuestionListFragment";
    private QuestionListAdapter mAdapter;
    private LoadListView mListView = null;
    private int mType = 0;
    private boolean mIsTopic = false;

    static public QuestionListFragment newInstance(int type, boolean topic) {
        QuestionListFragment f = new QuestionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putBoolean("topic", topic);
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
    public void loadData(boolean isLoadNew) {
        ArrayList<JSONObject> data = new ArrayList<JSONObject>();
        mAdapter = new QuestionListAdapter(this, data, mType);
        mAdapter.setTopic(mIsTopic);
        mListView.setAdapter(mAdapter, System.currentTimeMillis(), mContext);
        //mAdapter.loadInitData();
        mListView.setSelectionFromTop(0, 20);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustView = inflater.inflate(R.layout.question_list_layout, container, false);
        mListView = (LoadListView) mCustView.findViewById(android.R.id.list);
        Bundle bundle = getArguments();
        mType = bundle.getInt("type");
        mIsTopic = bundle.getBoolean("topic");
        mListView.setOnItemListener(this);
        loadData(false);
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
        // TODO Auto-generated method stub
        super.onResume();
        doRefreshHeader();
    }
}
