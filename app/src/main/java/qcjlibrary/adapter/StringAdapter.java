package qcjlibrary.adapter;

import com.zhiyicx.zycx.R;

import qcjlibrary.adapter.base.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * author：qiuchunjia time：下午2:59:55 类描述：这个类是实现
 */

public class StringAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mData;
    private LayoutInflater mInflater;

    public StringAdapter(Context context, String[] strings) {
        this.mContext = context;
        this.mData = strings;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_me_choose_province,
                    null);
            holder.tv_choose_adress = (TextView) convertView
                    .findViewById(R.id.tv_choose_adress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_choose_adress.setText(mData[position]);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
