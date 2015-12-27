package com.zhiyicx.zycx.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.modle.NetData;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/16.
 */
public class QClassListAdapter extends LoadListAdapter {

    private Context mContext = null;
    private int mType = 0;
    private LayoutInflater mInflater = null;
    private String mKey = null;
    private int mStatus = 0;

    public QClassListAdapter(BaseListFragment ctx, ArrayList<JSONObject> list, int type) {
        super(ctx, list);
        mContext = ctx.mContext;
        mType = type;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    @Override
    public void refreshHeader(Handler handler, JSONObject obj) throws Exception {
        int id = 0;
        if (obj != null)
            id = obj.optInt("course_id");
        NetData.QClassRefHeaderList(mContext, handler, mStatus, mType, id);
    }

    @Override
    public void refreshFooter(Handler handler, JSONObject obj) throws Exception {
        int id = 0;
        if (obj != null)
            id = obj.optInt("course_id");
        if (mKey != null)
            NetData.QClassSearchList(mContext, handler, mStatus, mKey, -1);
        else
            NetData.QClassRefFooterList(mContext, handler, mStatus, mType, id);
    }

    @Override
    public void refreshNew(Handler handler, int count) throws Exception {
        NetData.QClassNewList(mContext, handler, mStatus, mType);
    }

    @Override
    public void searchNew(Handler handler, String key) throws Exception {
        mKey = key;
        NetData.QClassSearchList(mContext, handler, mStatus, key, -1);
    }


    class ViewHolder {
        TextView mTitle;
        TextView mWatchCnt;
        TextView mVideoCnt;
        ImageView mPreview;
        ImageButton mBtnDel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.qclass_list_item, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.txt_item_title);
            holder.mWatchCnt = (TextView) convertView.findViewById(R.id.txt_watch_num);
            holder.mVideoCnt = (TextView) convertView.findViewById(R.id.txt_video_num);
            holder.mPreview = (ImageView) convertView.findViewById(R.id.img_item_preview);
            holder.mBtnDel = (ImageButton) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            //Log.d(TAG, "QClassListAdpter getView");
            JSONObject jsonObject = list.get(position);
            holder.mTitle.setText(jsonObject.get("course_name").toString());
            holder.mWatchCnt.setText(jsonObject.get("watch_num") + "次观看");
            holder.mVideoCnt.setText(jsonObject.get("video_num") + "课时");
            NetComTools.getInstance(mContext).loadNetImage(holder.mPreview,
                    jsonObject.get("cover").toString(),
                    R.drawable.bg_loading, 144, 144);
            if (mStatus == 2) {
                holder.mBtnDel.setVisibility(View.VISIBLE);
                holder.mBtnDel.setOnClickListener(new DeleteListener(position));
            } else
                holder.mBtnDel.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.d("QClassView", "QClassListAdpter error");
            e.printStackTrace();
        }
        return convertView;
    }

    private class DeleteListener implements View.OnClickListener {
        private int mPos = 0;

        private DeleteListener(int pos) {
            mPos = pos;
        }

        @Override
        public void onClick(View view) {
            try {
                MydeleteItem(mPos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void MydeleteItem(final int pos) throws Exception {
        JSONObject jsonObject = getItem(pos);
        int cid = jsonObject.getInt("course_id");
        //int id = jsonObject.getInt("id");
        String url = MyConfig.QCLASS_DEL_RECORD_URL + Utils.getTokenString(mContext) + "&cid=" + cid;
        NetComTools netComTools = NetComTools.getInstance(mContext);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "delet item data, " + jsonObject.toString());
                try {
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        QClassListAdapter.super.deleteItem(pos);
                    } else {
                        String msg = jsonObject.optString("message");
                        if (!TextUtils.isEmpty(msg))
                            Utils.showToast(mContext, msg);
                        else
                            Utils.showToast(mContext, "删除记录失败!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
            }
        });
    }
}
