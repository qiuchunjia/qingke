package com.zhiyicx.zycx.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2014/12/31.
 */
public class ClassListFragment extends Fragment {

    private String mTitle = null;
    private JSONArray mListJson = null;
    private ListView mList = null;
    private Context mContext;
    private PlayListener mPlayListener = null;
    private String mdefId = null;

    public interface PlayListener {
        abstract public void OnPlay(String url, String id, String vid);
    }

    public void setPlayListener(PlayListener listener) {
        mPlayListener = listener;
    }

    static public ClassListFragment newInstance(String title, JSONArray list, String defid) {
        ClassListFragment f = new ClassListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("defid", defid);
        if (list != null)
            bundle.putString("list", list.toString());
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.class_list_fragment, container, false);
        Bundle bundle = getArguments();
        mTitle = bundle.getString("title");
        mdefId = bundle.getString("defid");
        String listString = bundle.getString("list");
        if (!TextUtils.isEmpty(listString)) {
            try {
                mListJson = new JSONArray(bundle.getString("list"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TextView titleView = (TextView) view.findViewById(R.id.txt_class_title);
        titleView.setText(mTitle);
        mList = (ListView) view.findViewById(R.id.list_class);
        initClassList();
        return view;
    }

    private void initClassList() {
        final ClassAdapter adapter = new ClassAdapter();
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject jsonObject = mListJson.getJSONObject(position);
                    //String vid = jsonObject.optString("vid");
                    String vurl = jsonObject.optString("v_url");
                    mdefId = jsonObject.getString("id");
                    String vid = jsonObject.getString("vid");

                    if (mPlayListener != null)
                        mPlayListener.OnPlay(vurl, mdefId, vid);
                    //ViewHolder holder = (ViewHolder)view.getTag();
                    //holder.mIndImg.setImageResource(R.drawable.class_index_on);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class ViewHolder {
        TextView mIndex;
        TextView mTitle;
        TextView mTimeCnt;
        ImageView mPlayBtn;
        ImageView mIndImg;
    }

    private class ClassAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private ClassAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            if (mListJson == null)
                return 0;
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.class_list_item, null);
                holder = new ViewHolder();
                holder.mTitle = (TextView) convertView.findViewById(R.id.txt_item_title);
                holder.mTimeCnt = (TextView) convertView.findViewById(R.id.txt_time_len);
                holder.mPlayBtn = (ImageView) convertView.findViewById(R.id.btn_play);
                holder.mIndex = (TextView) convertView.findViewById(R.id.txt_index);
                holder.mIndImg = (ImageView) convertView.findViewById(R.id.img_ind);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                JSONObject jsonObject = (JSONObject) mListJson.get(position);
                holder.mTitle.setText(jsonObject.getString("title"));
                holder.mTimeCnt.setText(jsonObject.getString("video_time"));
                holder.mIndex.setText("课时" + String.valueOf(position + 1));
                String id = jsonObject.getString("id");
                if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(mdefId)
                        && id.equals(mdefId))
                    holder.mIndImg.setImageResource(R.drawable.class_index_on);
                else
                    holder.mIndImg.setImageResource(R.drawable.class_index);

                //holder.mPlayBtn.setOnClickListener(new ClassClickListener(position));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    private class ClassClickListener implements View.OnClickListener {

        private int pos = 0;

        private ClassClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            try {
                JSONObject jsonObject = mListJson.getJSONObject(pos);
                //String vid = jsonObject.optString("vid");
                String vurl = jsonObject.optString("v_url");
                String id = jsonObject.getString("id");
                String vid = jsonObject.getString("vid");
                //Log.d("ClassListFragment", "Item data:" + jsonObject.toString());
                //Intent intent = new Intent(mContext, QClassPlayActivity.class);
                //intent.putExtra("vid", vurl);
                //intent.putExtra("vurl", vurl);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //mContext.startActivity(intent);
                if (mPlayListener != null)
                    mPlayListener.OnPlay(vurl, id, vid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}