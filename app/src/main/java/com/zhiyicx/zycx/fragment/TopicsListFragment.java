package com.zhiyicx.zycx.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/1/7.
 */
public class TopicsListFragment extends Fragment {

    final private static String TAG = "TopicsListFragment";
    private Context mContext = null;
    private TextView mTxtInfo = null;
    private EditText mEditTopics = null;
    private ListView mListView = null;
    private ProgressBar mProgBar = null;
    private String[] mDataArray = new String[]{};
    private String mTopics = "";
    private ArrayAdapter mAdapter = null;


    static public TopicsListFragment newInstance(String content) {
        TopicsListFragment f = new TopicsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.topics_list, container, false);
        mListView = (ListView) view.findViewById(R.id.top_list);
        mTxtInfo = (TextView) view.findViewById(R.id.txt_msg);
        mEditTopics = (EditText) view.findViewById(R.id.edit_topics);
        mProgBar = (ProgressBar) view.findViewById(R.id.progressBar);
        view.findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String txt = mEditTopics.getText().toString().trim();
                if (TextUtils.isEmpty(txt)) {
                    Toast.makeText(mContext, "标签不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inArray(txt)) {
                    Toast.makeText(mContext, "列表中已存在", Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] tmp = mDataArray;
                mDataArray = new String[tmp.length + 1];
                int i = 0;
                for (i = 0; i < tmp.length; i++)
                    mDataArray[i] = tmp[i];
                mDataArray[i] = txt;
                initList();
            }
        });
        Bundle bundle = getArguments();
        String content = bundle.getString("content");
        loadData(content);
        return view;
    }

    private boolean inArray(String s) {
        for (int i = 0; i < mDataArray.length; i++) {
            if (mDataArray[i].equals(s))
                return true;
        }
        return false;
    }

    private void loadData(String txt) {
        mProgBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        String url = MyConfig.QUESTION_TOPIC_URL + Utils.getTokenString(mContext) + "&content=" + txt;
        NetComTools netComTools = NetComTools.getInstance(mContext);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "Topics list data:" + jsonObject.toString());
                try {

                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        mDataArray = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            mDataArray[i] = ((JSONObject) array.get(i)).getString("title");
                        }
                        mTxtInfo.setText("根据你的问题,推荐添加以下标签:");
                        initList();
                    } else {
                        String msg = jsonObject.optString("message");
                        if (!TextUtils.isEmpty(msg)) {
                            mTxtInfo.setText(msg);
                        }
                        mProgBar.setVisibility(View.GONE);
                        mListView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Utils.showToast(mContext, "网络错误！");
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Topics list error, " + error);
            }
        });
    }

    private void initList() {
        mProgBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mAdapter = new ArrayAdapter<String>(mContext, R.layout.topicslist_item, mDataArray);
        mListView.setAdapter(mAdapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mTopics = "";
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mListView.setItemChecked(i, true);
            if (!TextUtils.isEmpty(mTopics))
                mTopics += ',';
            mTopics += mDataArray[i];
        }
        //mEditTopics.setText(mTopics);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                SparseBooleanArray a = mListView.getCheckedItemPositions();
                mTopics = "";
                for (int i = 0; i < mDataArray.length; i++) {
                    if (a.valueAt(i)) {
                        int val = (int) mListView.getAdapter().getItemId(a.keyAt(i));
                        if (!TextUtils.isEmpty(mTopics))
                            mTopics += ',';
                        mTopics += mDataArray[val];
                    }
                }
                Log.d(TAG, "mTopics=" + mTopics);
                //mEditTopics.setText(mTopics);
            }
        });
    }

    public String getTopics() {
        return mTopics;
    }
}
