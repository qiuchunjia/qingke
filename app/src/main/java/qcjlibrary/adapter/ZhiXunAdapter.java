package qcjlibrary.adapter;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.adapter.base.ViewHolder;
import qcjlibrary.api.api.ZhiXunImpl;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.ModelZiXun;
import qcjlibrary.model.ModelZiXunDetail;
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

public class ZhiXunAdapter extends BAdapter {

    private ModelZiXunDetail mDetail;

    public ZhiXunAdapter(BaseActivity activity, List<Model> list) {
        super(activity, list);
    }

    public ZhiXunAdapter(BaseFragment fragment, ModelZiXunDetail data) {
        super(fragment, null);
        mDetail = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_zhixun, null);
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
            ModelZiXunDetail modelZiXunDetail = (ModelZiXunDetail) mList
                    .get(position);
            if (modelZiXunDetail != null) {
                mApp.displayImage(modelZiXunDetail.getCover(), holder.iv_photo);
                holder.tv_content.setText(modelZiXunDetail.getTitle());
                holder.tv_date.setText(modelZiXunDetail.getcTime());
                // holder.tv_from = (TextView) convertView
                // .findViewById(R.id.tv_from);
                holder.tv_num.setText(modelZiXunDetail.getReadCount());
            }
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
            holder.iv_photo = (ImageView) convertView
                    .findViewById(R.id.iv_photo);
            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_from = (TextView) convertView.findViewById(R.id.tv_from);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
        }
    }

    @Override
    public void refreshNew() {
        requstMessage(mDetail, REFRESH_NEW);
    }

    @Override
    public void refreshHeader(Model item, int count) {
        if (item instanceof ModelZiXunDetail) {
            ModelZiXunDetail detail = (ModelZiXunDetail) item;
            detail.setLastid(detail.getId());
            requstMessage(detail, REFRESH_HEADER);
        }
    }

    @Override
    public void refreshFooter(Model item, int count) {
        if (item instanceof ModelZiXunDetail) {
            ModelZiXunDetail detail = (ModelZiXunDetail) item;
            detail.setMaxid(detail.getId());
            requstMessage(detail, REFRESH_FOOTER);
        }
    }

    /**
     * 请求咨询内容
     *
     * @param data
     * @param type
     */
    private void requstMessage(ModelZiXunDetail data, int type) {
        ZhiXunImpl xunImpl = new ZhiXunImpl();
        sendRequest(xunImpl.indexItem(data), ModelZiXun.class, 0, type);
    }

    @Override
    public int getTheCacheType() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getReallyList(Object object, Class type2) {
        if (object instanceof ModelZiXun) {
            ModelZiXun modelZiXun = (ModelZiXun) object;
            modelZiXun.getList();
            return modelZiXun.getList();
        }
        return null;
    }
}
