package qcjlibrary.adapter;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.adapter.base.ViewHolder;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.ModelExperience;
import qcjlibrary.model.ModelExperienceDetail;
import qcjlibrary.model.ModelExperienceDetailItem1;
import qcjlibrary.model.base.Model;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:06:10
 * <p/>
 * 类描述：呵呵哒的意思
 */

public class CancerTopicAdapter extends BAdapter {
    private ModelExperience mExperienceData;

    public CancerTopicAdapter(BaseActivity activity,
                              ModelExperience modelExperience) {
        super(activity, null);
        mExperienceData = modelExperience;
    }

    public CancerTopicAdapter(BaseFragment fragment,
                              ModelExperience modelExperience) {
        super(fragment, null);
        mExperienceData = modelExperience;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_cancer_topic, null);
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
            if (model instanceof ModelExperienceDetailItem1) {
                ModelExperienceDetailItem1 detailItem1 = (ModelExperienceDetailItem1) model;
                holder.tv_topic_title.setText(detailItem1.getContent());
                holder.tv_topic_advice.setVisibility(View.GONE);
                if (detailItem1.getRecommend().equals("1")) {
                    holder.tv_topic_advice.setVisibility(View.VISIBLE);
                }
                // holder.tv_topic_user.setText(detailItem1.get);
                // holder.tv_topic_update.setText(detailItem1.getContent());
                holder.tv_topic_date.setText(detailItem1.getPost_time());

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
            holder.tv_topic_title = (TextView) convertView
                    .findViewById(R.id.tv_topic_title);
            holder.tv_topic_advice = (TextView) convertView
                    .findViewById(R.id.tv_topic_advice);
            holder.tv_topic_user = (TextView) convertView
                    .findViewById(R.id.tv_topic_user);
            holder.tv_topic_update = (TextView) convertView
                    .findViewById(R.id.tv_topic_update);
            holder.tv_topic_date = (TextView) convertView
                    .findViewById(R.id.tv_topic_date);
        }
    }

    @Override
    public void refreshNew() {
        sendRequest(mApp.getExperienceImpl().detail(mExperienceData),
                ModelExperienceDetail.class, REQUEST_GET, REFRESH_NEW);
    }

    @Override
    public void refreshHeader(Model item, int count) {
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
    public Object getReallyList(Object object, Class type2) {
        if (object instanceof ModelExperienceDetail) {
            ModelExperienceDetail detail = (ModelExperienceDetail) object;
            return detail.getPosts();
        }
        return null;
    }

}
