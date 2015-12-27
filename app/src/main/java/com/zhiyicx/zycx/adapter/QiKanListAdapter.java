package com.zhiyicx.zycx.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.modle.NetData;
import com.zhiyicx.zycx.net.NetComTools;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/15.
 */
public class QiKanListAdapter extends LoadListAdapter {

    private Context mContext = null;
    private LayoutInflater mInflater = null;

    public QiKanListAdapter(BaseListFragment ctx, ArrayList<JSONObject> list) {
        super(ctx, list);
        mContext = ctx.mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void refreshHeader(Handler handler, JSONObject obj) throws Exception {
        int id = 0;
        if (obj != null)
            id = obj.optInt("pid");
        NetData.QiKanRefHeaderList(mContext, handler, 20, id);
    }

    @Override
    public void refreshFooter(Handler handler, JSONObject obj) throws Exception {
        int id = 0;
        if (obj != null)
            id = obj.optInt("pid");
        NetData.QiKanRefFooterList(mContext, handler, 20, id);
    }

    @Override
    public void refreshNew(Handler handler, int count) throws Exception {
        NetData.QiKanNewList(mContext, handler, count);
    }

    @Override
    public void searchNew(Handler handler, String key) throws Exception {

    }

    class ViewHolder {
        TextView mTitle;
        TextView mIndex;
        TextView mTime;
        ImageView mPreview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.qikan_list_item, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.txt_name);
            holder.mTime = (TextView) convertView.findViewById(R.id.txt_time);
            holder.mIndex = (TextView) convertView.findViewById(R.id.txt_index);
            holder.mPreview = (ImageView) convertView.findViewById(R.id.img_preview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            JSONObject jsonObject = list.get(position);
            String txt = jsonObject.optString("periodical_name").trim();
            if (!TextUtils.isEmpty(txt))
                holder.mTitle.setText(txt);
            holder.mIndex.setText(jsonObject.optString("periodical_num"));
            holder.mTime.setText(jsonObject.optString("ctime"));
            NetComTools.getInstance(mContext).loadNetImage(holder.mPreview,
                    jsonObject.optString("cover"),
                    R.drawable.picture, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

}
