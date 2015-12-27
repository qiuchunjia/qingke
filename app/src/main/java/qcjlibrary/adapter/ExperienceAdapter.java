package qcjlibrary.adapter;

import java.util.ArrayList;
import java.util.List;

import qcjlibrary.activity.CancerCategoryActivity;
import qcjlibrary.activity.CancerSingleActivity;
import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.adapter.base.ViewHolder;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.ModelExperience;
import qcjlibrary.model.ModelExperienceIndex;
import qcjlibrary.model.base.Model;
import qcjlibrary.response.DataAnalyze;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ExperienceAdapter extends BAdapter {

    public ExperienceAdapter(BaseActivity activity, List<Model> list) {
        super(activity, list);
    }

    public ExperienceAdapter(BaseFragment fragment, List<Model> list) {
        super(fragment, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_experience, null);
            initView(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindDataToView(holder, position);
        return convertView;
    }

    @SuppressLint("NewApi")
    private void bindDataToView(ViewHolder holder, int position) {
        if (holder != null) {
            Model model = mList.get(position);
            if (model instanceof ModelExperienceIndex) {
                ModelExperienceIndex index = (ModelExperienceIndex) model;
                ModelExperience experience1 = index.getFirst();
                ModelExperience experience2 = index.getSecond();
                if (experience1 != null) {
                    // 绑定数据到第一个
                    mApp.displayImage(experience1.getLogo(),
                            holder.iv_cancer_icon);
                    holder.tv_cancer_name.setText(experience1.getWeiba_name());
                    holder.tv_cancer_numer.setText(experience1
                            .getFollower_count());
                    holder.tv_cancer_experence.setText(experience1
                            .getThread_count());
                    holder.rl_1.setTag(experience1);
                    holder.rl_1.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Model model = (Model) v.getTag();
                            mApp.startActivity_qcj(mBaseActivity,
                                    CancerSingleActivity.class,
                                    mBaseActivity.sendDataToBundle(model, null));
                        }
                    });
                }
                if (experience2 != null) {
                    holder.rl_2.setAlpha((float) 1.0);
                    // 绑定数据到第二个
                    mApp.displayImage(experience2.getLogo(),
                            holder.iv_cancer_icon2);
                    holder.tv_cancer_name2.setText(experience2.getWeiba_name());
                    holder.tv_cancer_numer2.setText(experience2
                            .getFollower_count());
                    holder.tv_cancer_experence2.setText(experience2
                            .getThread_count());
                    holder.rl_2.setTag(experience2);
                    holder.rl_2.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Model model = (Model) v.getTag();
                            mApp.startActivity_qcj(mBaseActivity,
                                    CancerSingleActivity.class,
                                    mBaseActivity.sendDataToBundle(model, null));
                        }
                    });
                } else {
                    holder.rl_2.setAlpha((float) 0.0);
                }
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
            holder.iv_cancer_icon = (ImageView) convertView
                    .findViewById(R.id.iv_cancer_icon);
            holder.tv_cancer_name = (TextView) convertView
                    .findViewById(R.id.tv_cancer_name);
            holder.tv_cancer_numer = (TextView) convertView
                    .findViewById(R.id.tv_cancer_numer);
            holder.tv_cancer_experence = (TextView) convertView
                    .findViewById(R.id.tv_cancer_experence);
            holder.iv_cancer_icon2 = (ImageView) convertView
                    .findViewById(R.id.iv_cancer_icon2);
            holder.tv_cancer_name2 = (TextView) convertView
                    .findViewById(R.id.tv_cancer_name2);
            holder.tv_cancer_numer2 = (TextView) convertView
                    .findViewById(R.id.tv_cancer_numer2);
            holder.tv_cancer_experence2 = (TextView) convertView
                    .findViewById(R.id.tv_cancer_experence2);
            holder.rl_1 = (RelativeLayout) convertView.findViewById(R.id.rl_1);
            holder.rl_2 = (RelativeLayout) convertView.findViewById(R.id.rl_2);

        }
    }

    @Override
    public void refreshNew() {
        sendRequest(mApp.getExperienceImpl().index(), ModelExperience.class,
                REQUEST_GET, REFRESH_NEW);
    }

    @Override
    public void refreshHeader(Model item, int count) {
    }

    @Override
    public void refreshFooter(Model item, int count) {
    }

    @Override
    public int getTheCacheType() {
        return 0;
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        return DataAnalyze.parseData(str, class1);
    }

    @Override
    public Object getReallyList(Object object, Class type2) {
        if (object instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<ModelExperience> list = (List<ModelExperience>) object;
            List<ModelExperienceIndex> resultList = new ArrayList<ModelExperienceIndex>();
            for (int i = 0; i < list.size(); i = i + 2) {
                ModelExperienceIndex experienceIndex = new ModelExperienceIndex();
                experienceIndex.setFirst(list.get(i));
                if ((i + 1) < list.size()) {
                    experienceIndex.setSecond(list.get(i + 1));
                }
                resultList.add(experienceIndex);
            }
            return resultList;
        }
        return null;
    }
}
