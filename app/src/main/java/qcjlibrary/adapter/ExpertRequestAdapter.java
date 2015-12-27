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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:06:10
 * <p/>
 * 类描述：这个类是实现专家提问列表
 */

public class ExpertRequestAdapter extends BAdapter {

    public ExpertRequestAdapter(BaseActivity activity, List<Model> list) {
        super(activity, list);
    }

    public ExpertRequestAdapter(BaseFragment fragment, List<Model> list) {
        super(fragment, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_expert_request, null);
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
            holder.rl_agree.setVisibility(View.GONE);
            holder.tv_agree.setVisibility(View.GONE);
            holder.tv_noagree.setVisibility(View.GONE);
            if (position == 0) {
                holder.rl_agree.setVisibility(View.VISIBLE);
            } else if (position == 1) {
                holder.tv_agree.setVisibility(View.VISIBLE);
            } else if (position == 2) {
                holder.tv_noagree.setVisibility(View.VISIBLE);
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
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.tv_answer = (TextView) convertView
                    .findViewById(R.id.tv_answer);
            holder.tv_expert_answer = (TextView) convertView
                    .findViewById(R.id.tv_expert_answer);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.rl_agree = (RelativeLayout) convertView
                    .findViewById(R.id.rl_agree);
            holder.iv_yes = (ImageView) convertView.findViewById(R.id.iv_yes);
            holder.iv_no = (ImageView) convertView.findViewById(R.id.iv_no);
            holder.tv_agree = (TextView) convertView
                    .findViewById(R.id.tv_agree);
            holder.tv_noagree = (TextView) convertView
                    .findViewById(R.id.tv_noagree);
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
