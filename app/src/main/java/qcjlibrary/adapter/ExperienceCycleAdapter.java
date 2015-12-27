package qcjlibrary.adapter;

import java.util.List;

import qcjlibrary.activity.ExperienceCycleDetail;
import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.adapter.base.ViewHolder;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.ModelExperienceDetailItem1;
import qcjlibrary.model.ModelExperiencePostDetail;
import qcjlibrary.model.ModelExperiencePostDetailItem;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.base.Model;
import qcjlibrary.util.DateUtil;

import android.util.Log;
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

public class ExperienceCycleAdapter extends BAdapter {
    private ModelExperienceDetailItem1 mData;

    public ExperienceCycleAdapter(BaseActivity activity,
                                  ModelExperienceDetailItem1 detailItem1) {
        super(activity, null);
        this.mData = detailItem1;
    }

    public ExperienceCycleAdapter(BaseFragment fragment,
                                  ModelExperienceDetailItem1 detailItem1) {
        super(fragment, null);
        this.mData = detailItem1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = initView(holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindDataToView(holder, position);
        return convertView;
    }

    private void bindDataToView(ViewHolder holder, int position) {
        if (holder != null) {
            holder.tv_date_year.setVisibility(View.VISIBLE);
            if (position > 0) {
                holder.tv_date_year.setVisibility(View.GONE);
            }
            ModelExperiencePostDetailItem detailItem = (ModelExperiencePostDetailItem) mList
                    .get(position);
            holder.tv_date_month.setText(DateUtil.StampToMonth(detailItem
                    .getCtime()) + "月");
            holder.tv_date_day.setText(DateUtil.StampToDay(detailItem
                    .getCtime()));
            holder.tv_date_week.setText(DateUtil.StampToWeek(detailItem
                    .getCtime()));
            holder.tv_date_year.setText(DateUtil.StampToYear(detailItem
                    .getCtime()));
            holder.tv_date_content.setText(detailItem.getContent());
            holder.tv_howmany.setText("第" + detailItem.getChildCount() + "篇");
            if (detailItem.getIs_praise().equals("1")) {
                holder.iv_hand.setImageResource(R.drawable.zanicon02_red);
                holder.tv_zhengnengliang.setTextColor(mBaseActivity
                        .getResources().getColor(R.color.red));
            } else {
                holder.iv_hand.setImageResource(R.drawable.zanicon);
                holder.tv_zhengnengliang.setTextColor(mBaseActivity
                        .getResources().getColor(R.color.text_more_gray));
            }
            holder.tv_zhengnengliang.setText("正能量("
                    + detailItem.getPraiseCount() + ")");
            holder.rl_zan.setTag(detailItem);
            holder.rl_zan.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    ModelExperiencePostDetailItem detailItem = (ModelExperiencePostDetailItem) v
                            .getTag();
                    mBaseActivity.sendRequest(mApp.getExperienceImpl()
                            .doPraise(detailItem), ModelMsg.class, REQUEST_GET);
                }
            });
            holder.tv_more.setTag(detailItem.getUrl());
            holder.tv_more.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String url = (String) v.getTag();
                    mApp.startActivity_qcj(mBaseActivity,
                            ExperienceCycleDetail.class,
                            mBaseActivity.sendDataToBundle(url, null));
                }
            });

        }
    }

    /**
     * 初始化布局
     *
     * @param convertView
     * @param holder
     */
    private View initView(ViewHolder holder) {
        View view = null;
        if (holder != null) {
            view = mInflater.inflate(R.layout.item_experience_cycle, null);
            holder.tv_date_month = (TextView) view
                    .findViewById(R.id.tv_date_month);
            holder.tv_date_day = (TextView) view.findViewById(R.id.tv_date_day);
            holder.tv_date_week = (TextView) view
                    .findViewById(R.id.tv_date_week);
            holder.tv_date_year = (TextView) view
                    .findViewById(R.id.tv_date_year);
            holder.tv_date_content = (TextView) view
                    .findViewById(R.id.tv_date_content);
            holder.tv_more = (TextView) view.findViewById(R.id.tv_more);
            holder.tv_howmany = (TextView) view.findViewById(R.id.tv_howmany);
            holder.rl_zan = (RelativeLayout) view.findViewById(R.id.rl_zan);
            holder.iv_hand = (ImageView) view.findViewById(R.id.iv_hand);
            holder.tv_zhengnengliang = (TextView) view
                    .findViewById(R.id.tv_zhengnengliang);

        }
        return view;
    }

    @Override
    public void refreshNew() {
        sendRequest(mApp.getExperienceImpl().postDetail(mData),
                ModelExperiencePostDetail.class, REQUEST_GET, REFRESH_NEW);
    }

    @Override
    public void refreshHeader(Model item, int count) {
        sendRequest(mApp.getExperienceImpl().postDetail(mData),
                ModelExperiencePostDetail.class, REQUEST_GET, REFRESH_NEW);
    }

    @Override
    public void addHeadList(List<Model> list) {
        Log.i("mlisttest", "addHeadList");
        addHeadListWay2(list);
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
        if (object instanceof ModelExperiencePostDetail) {
            ModelExperiencePostDetail detail = (ModelExperiencePostDetail) object;
            return detail.getList();
        }
        return null;
    }
}
