package qcjlibrary.adapter;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.adapter.base.ViewHolder;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.ModelCenterCancer;
import qcjlibrary.model.ModelMeAddress;
import qcjlibrary.model.base.Model;
import qcjlibrary.response.DataAnalyze;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:06:10
 * <p/>
 * 类描述：这个类是实现专家提问列表
 */

public class MeChooseCancerAdapter extends BAdapter {

    public MeChooseCancerAdapter(BaseActivity activity) {
        super(activity, null);
    }

    public MeChooseCancerAdapter(BaseFragment fragment) {
        super(fragment, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_me_choose_province,
                    null);
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
            Model model = mList.get(position);
            if (model instanceof ModelCenterCancer) {
                ModelCenterCancer cancer = (ModelCenterCancer) model;
                holder.tv_choose_adress.setText(cancer.getTitle());
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
            holder.tv_choose_adress = (TextView) convertView
                    .findViewById(R.id.tv_choose_adress);

        }
    }

    @Override
    public void refreshNew() {
        sendRequest(mApp.getUserImpl().cancerlist(), ModelCenterCancer.class,
                0, REFRESH_NEW);
    }

    @Override
    public void refreshHeader(Model item, int count) {
    }

    @Override
    public void refreshFooter(Model item, int count) {

    }

    @Override
    public int getTheCacheType() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Log.i("arealist", str.toString());
        return DataAnalyze.parseData(str, class1);
    }

    @Override
    public Object getReallyList(Object object, Class type2) {
        if (object instanceof List<?>) {
            return object;
        }
        return null;
    }
}
