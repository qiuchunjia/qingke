package com.zhiyicx.zycx.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.activity.QuestionActivity;
import com.zhiyicx.zycx.modle.NetData;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.widget.CustomLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/18.
 */
public class QuestionListAdapter extends LoadListAdapter {

    final private static String TAG = "QuestionListAdapter";
    private Context mContext = null;
    private LayoutInflater mInflater;
    private int mType = 0;
    private boolean isTop = false;
    private boolean inSearch = false;

    public void setSearch(boolean en) {
        inSearch = en;
    }

    public void setTopic(boolean en) {
        isTop = en;
    }

    public QuestionListAdapter(BaseListFragment ctx, ArrayList<JSONObject> list, int type) {
        super(ctx, list);
        mContext = ctx.mContext;
        mType = type;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void refreshHeader(Handler handler, JSONObject obj) throws Exception {
        if (inSearch) {
            Message mainMsg = new Message();
            mainMsg.obj = "刷新成功";
            mainMsg.what = 1;
            handler.sendMessage(mainMsg);
            return;
        }
        int id = 0;
        if (obj != null)
            id = obj.optInt("question_id");
        if (!isTop)
            NetData.QuestionHeaderList(mContext, handler, mType, id);
        else {
            Message mainMsg = new Message();
            mainMsg.obj = "刷新成功";
            mainMsg.what = 1;
            handler.sendMessage(mainMsg);
        }
    }

    @Override
    public void refreshFooter(Handler handler, JSONObject obj) throws Exception {
        if (inSearch) {
            Message mainMsg = new Message();
            mainMsg.obj = "刷新成功";
            mainMsg.what = 1;
            handler.sendMessage(mainMsg);
            mListContext.getListView().hideFooterView();
            return;
        }
        int id = 0;
        if (obj != null)
            id = obj.optInt("question_id");
        if (!isTop)
            NetData.QuestionFooterList(mContext, handler, mType, id);
        else
            NetData.QuestionTopFooter(mContext, handler, mType, id);
    }

    @Override
    public void refreshNew(Handler handler, int count) throws Exception {
        if (!isTop)
            NetData.QuestionNewList(mContext, handler, mType);
        else
            NetData.QuestionTopNew(mContext, handler, mType);
    }

    @Override
    public void searchNew(Handler handler, String key) throws Exception {
        inSearch = true;
        NetData.QuestionSearchList(mContext, handler, key);
        mListContext.getListView().hideFooterView();
    }

    class ViewHolder {
        TextView mName;
        TextView mType;
        TextView mContent;
        TextView mTime;
        TextView mFlag;
        TextView mCnt;
        ImageView mPhoto;
        CustomLinearLayout mTop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.question_list_item, null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.mTime = (TextView) convertView.findViewById(R.id.txt_time);
            holder.mType = (TextView) convertView.findViewById(R.id.txt_type);
            holder.mContent = (TextView) convertView.findViewById(R.id.txt_content);
            //holder.mFlag = (TextView)convertView.findViewById(R.id.txt_flag);
            holder.mCnt = (TextView) convertView.findViewById(R.id.txt_cnt);
            holder.mPhoto = (ImageView) convertView.findViewById(R.id.img_photo);
            holder.mTop = (CustomLinearLayout) convertView.findViewById(R.id.top_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            JSONObject jsonObject = getItem(position);
            holder.mName.setText(jsonObject.getString("user_name"));
            holder.mContent.setText(jsonObject.getString("question_content"));
            String txt = jsonObject.optString("categoryname", "其它");
            if (txt.equals("null"))
                txt = "其它";
            holder.mType.setText(/*"癌症种类:" + */txt);
            holder.mTime.setText(jsonObject.getString("time"));
            holder.mCnt.setText(jsonObject.getInt("answer_count") + "人参与");
            JSONArray array = jsonObject.optJSONArray("topic_list");
            if (array != null) {
                setTopList(holder.mTop, array);
            }
            NetComTools.getInstance(mContext).loadNetImage(holder.mPhoto,
                    jsonObject.get("user_face").toString(),
                    R.drawable.header, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private void setTopList(CustomLinearLayout layout, JSONArray array) throws JSONException {
        layout.removeAllViews();
        TextView textView = new TextView(mContext);
        textView.setText("标签:");
        layout.addView(textView);
        int len = array.length();
        /*if(len > 3)
            len = 3;*/
        for (int i = 0; i < len; i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            int domain = jsonObject.optInt("domain", 0);
            String title = jsonObject.optString("topic_title");
            textView = new TextView(mContext);
            textView.setClickable(true);
            if (domain != 0 && !TextUtils.isEmpty(title))
                textView.setOnClickListener(new TopOnClickListener(domain, title));
            textView.setTextColor(Color.parseColor("#1CBE9D"));
            textView.setBackgroundResource(R.drawable.textclick_background);
            textView.setText(title);
            layout.addView(textView);
        }
    }

    private class TopOnClickListener implements View.OnClickListener {
        private int mTid = 0;
        private String mTitle = null;

        private TopOnClickListener(int tid, String title) {
            mTid = tid;
            mTitle = title;
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Top TextView click!");
            QuestionActivity.show(mContext, mTid, true, mTitle);
        }
    }
}
