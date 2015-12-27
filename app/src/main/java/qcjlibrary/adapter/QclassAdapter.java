package qcjlibrary.adapter;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.adapter.base.ViewHolder;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.base.Model;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:06:10
 * <p/>
 * 类描述：这个类是实现专家提问列表
 */

public class QclassAdapter extends BAdapter {

    public QclassAdapter(BaseActivity activity, List<Model> list) {
        super(activity, list);
    }

    public QclassAdapter(BaseFragment fragment, List<Model> list) {
        super(fragment, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_qing_class, null);
            initView(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindDataToView(holder, position);
        return convertView;
    }

    private void bindDataToView(ViewHolder holder, int position) {
        if (holder != null) {
        }
    }

    /**
     * 初始化布局
     *
     * @param convertView
     * @param holder
     */
    private void initView(View convertView, ViewHolder holder) {
        if (convertView != null && holder != null) {
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_update = (TextView) convertView
                    .findViewById(R.id.tv_update);
            holder.iv_vedio = (ImageView) convertView
                    .findViewById(R.id.iv_vedio);

        }
    }

    @Override
    public void refreshNew() {
        sendRequest(null, null, 1, 1);
    }

    @Override
    public void refreshHeader(Model item, int count) {
        sendRequest(null, null, 1, 1);
    }

    @Override
    public void refreshFooter(Model item, int count) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getTheCacheType() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Model> getReallyList(Object object, Class type2) {
        // TODO Auto-generated method stub
        return null;
    }

}
