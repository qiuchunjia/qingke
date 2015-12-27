package com.zhiyicx.zycx.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2014/12/31.
 */
public class ClassCmtFragment extends Fragment {

    private JSONArray mListJson = null;
    private ListView mList = null;
    private Context mContext;
    private String mVid = null;

    static public ClassCmtFragment newInstance(String id) {
        ClassCmtFragment f = new ClassCmtFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.class_cmt_fragment, container, false);
        Bundle bundle = getArguments();
        mVid = bundle.getString("id");
        mList = (ListView) view.findViewById(R.id.list_cmt);
        //loadCmtData();
        return view;
    }

    public void loadCmtData(String id) {
        String url = MyConfig.QCLASS_CMTLIST_URL + Utils.getTokenString(mContext) + "&id=" + id;
        NetComTools netComTools = NetComTools.getInstance(mContext);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d("ClassCmtFragment", "Cmt list data:" + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        mListJson = jsonObject.getJSONArray("data");
                        CmtAdapter adapter = new CmtAdapter();
                        mList.setAdapter(adapter);
                    } else {
                        mListJson = null;
                        CmtAdapter adapter = new CmtAdapter();
                        mList.setAdapter(adapter);
                        /*String txt = jsonObject.optString("message");
                        if(!TextUtils.isEmpty(txt))
                            Utils.showToast(mContext, txt);*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d("ClassCmtFragment", "Cmt list data error" + error);
            }
        });
    }


    private class CmtAdapter extends BaseAdapter {

        class ViewHolder {
            TextView mName;
            TextView mTime;
            TextView mContent;
            ImageView mPhoto;
        }

        private LayoutInflater mInflater;

        private CmtAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            if (mListJson == null)
                return 1;
            else
                return mListJson.length();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (mListJson == null) {
                if (convertView == null) {
                    TextView textView = new TextView(mContext);
                    textView.setText("暂无评论内容！");
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setTextSize(15);
                    convertView = textView;
                }
                return convertView;
            }

            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.class_cmtlist_item, null);
                holder = new ViewHolder();
                holder.mName = (TextView) convertView.findViewById(R.id.txt_name);
                holder.mContent = (TextView) convertView.findViewById(R.id.txt_content);
                holder.mTime = (TextView) convertView.findViewById(R.id.txt_time);
                holder.mPhoto = (ImageView) convertView.findViewById(R.id.img_photo);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                JSONObject jsonObject = (JSONObject) mListJson.get(position);
                holder.mName.setText(jsonObject.getString("uname"));
                holder.mTime.setText(jsonObject.getString("time"));
                holder.mContent.setText(jsonObject.getString("content"));
                NetComTools.getInstance(mContext).loadNetImage(holder.mPhoto,
                        jsonObject.get("uface").toString(),
                        R.drawable.header, 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }
}
