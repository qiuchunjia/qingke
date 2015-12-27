package com.zhiyicx.zycx.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/1/6.
 */
public class QuestionCategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    final private static String TAG = "QuestionCategoryFragment";
    private Context mContext = null;
    private JSONArray mListJson = null;
    private GridView mGridView;
    private ProgressBar mProgbar = null;
    private SelectListener mSelectListener = null;

    public interface SelectListener {
        abstract public void OnSelect(int id, String title);
    }

    public void setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_category, container, false);
        mGridView = (GridView) view.findViewById(R.id.grid_category);
        mProgbar = (ProgressBar) view.findViewById(R.id.progressBar);
        mGridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
        initData();
    }

    private void initData() {
        String url = MyConfig.QUESTION_LIST_URL + Utils.getTokenString(mContext);
        NetComTools netComTools = NetComTools.getInstance(mContext);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                //Log.d(TAG, "Question list data:" + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        mListJson = data.getJSONArray("fenlei");
                        //Log.d(TAG, "category:" + array.toString());
                        mProgbar.setVisibility(View.GONE);
                        mGridView.setVisibility(View.VISIBLE);
                        mGridView.setAdapter(new CategoryAdapter());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question list error, " + error);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            JSONObject jsonObject = (JSONObject) mListJson.get(i);
            if (mSelectListener != null)
                mSelectListener.OnSelect(jsonObject.optInt("id"), jsonObject.optString("title"));
            //QuestionActivity.show(mContext, jsonObject.optInt("id"), jsonObject.optString("title"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CategoryAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private CategoryAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            if (mListJson == null)
                return 0;
            else
                return mListJson.length();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = null;
            if (view == null) {
                view = mInflater.inflate(R.layout.category_grid_item, null);
            }
            textView = (TextView) view.findViewById(R.id.txt_name);
            try {
                JSONObject jsonObject = (JSONObject) mListJson.get(i);
                textView.setText(jsonObject.optString("title"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }
    }
}
