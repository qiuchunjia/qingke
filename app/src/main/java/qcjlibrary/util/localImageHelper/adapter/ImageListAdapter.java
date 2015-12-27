package qcjlibrary.util.localImageHelper.adapter;


import java.util.ArrayList;

import qcjlibrary.util.localImageHelper.LocalImageManager;
import qcjlibrary.util.localImageHelper.PhotoDirInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * @author Derek
 * @ClassName: ImageListAdapter
 * @Description: TODO()
 * @date 2013-11-12 下午3:39:27
 */

public class ImageListAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private Context mContext;
    private ArrayList<PhotoDirInfo> listDir;

    public ImageListAdapter(Context context, ArrayList<PhotoDirInfo> objects) {
        super();
        // TODO Auto-generated constructor stub
        this.mContext = context;
        listDir = objects;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * @return
     * @see android.widget.ArrayAdapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listDir != null ? listDir.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     * @see android.widget.ArrayAdapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        PhotoDirInfo localPhotoDirInfo = listDir.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.imagelist_item, parent,
                    false);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.iamge_icon);
            viewHolder.textView = (TextView) convertView
                    .findViewById(R.id.iamge_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(localPhotoDirInfo.getDirName() + "("
                + localPhotoDirInfo.getPicCount() + ")");
        String path = localPhotoDirInfo.getFirstPicPath();
        LocalImageManager.from(mContext).displayImage(viewHolder.imageView,
                path, R.drawable.default_image_small, 100, 100);
        return convertView;
    }

    /**
     * 存放列表项控件句柄
     */
    private class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

}
