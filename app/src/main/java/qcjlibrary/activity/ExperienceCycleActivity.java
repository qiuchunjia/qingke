package qcjlibrary.activity;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.adapter.ExperienceCycleAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelExperienceDetailItem1;
import qcjlibrary.model.ModelExperiencePostDetail;
import qcjlibrary.model.ModelExperiencePostDetailInfo;
import qcjlibrary.model.ModelExperienceSend;
import qcjlibrary.util.DateUtil;
import qcjlibrary.widget.RoundImageView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class ExperienceCycleActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private ModelExperienceDetailItem1 mItemData;

    public RoundImageView iv_cycle_icon;
    public TextView tv_username;
    public TextView tv_has_update;
    public TextView tv_flag_key;
    public TextView tv_flag_value;
    public TextView tv_date;

    private ModelExperiencePostDetail mDetail;

    @Override
    public String setCenterTitle() {
        return "经历轨迹";
    }

    @Override
    public void initIntent() {
        mItemData = (ModelExperienceDetailItem1) getDataFromIntent(getIntent(),
                null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_experience_cycle;
    }

    @Override
    public void initView() {
        titleSetRightImage(R.drawable.chuangjianjingli);
        iv_cycle_icon = (RoundImageView) findViewById(R.id.iv_cycle_icon);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_has_update = (TextView) findViewById(R.id.tv_has_update);
        tv_flag_key = (TextView) findViewById(R.id.tv_flag_key);
        tv_flag_value = (TextView) findViewById(R.id.tv_flag_value);
        tv_date = (TextView) findViewById(R.id.tv_date);
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(0);
        mAdapter = new ExperienceCycleAdapter(this, mItemData);
        mCommonListView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        Title title = getTitleClass();
        title.iv_title_right1.setOnClickListener(this);
        sendRequest(mApp.getExperienceImpl().postDetail(mItemData),
                ModelExperiencePostDetail.class, REQUEST_GET);
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelExperiencePostDetail) {
            mDetail = (ModelExperiencePostDetail) object;
            addDataToHead(mDetail.getPost_detail());
        }
        if (judgeTheMsg(object)) {
            mAdapter.doRefreshHeader();
        }
        return object;
    }

    /**
     * 添加数据到头部
     *
     * @param post_detail
     */
    private void addDataToHead(ModelExperiencePostDetailInfo post_detail) {
        if (post_detail != null) {
            tv_has_update = (TextView) findViewById(R.id.tv_has_update);
            tv_flag_key = (TextView) findViewById(R.id.tv_flag_key);
            tv_flag_value = (TextView) findViewById(R.id.tv_flag_value);
            tv_date = (TextView) findViewById(R.id.tv_date);
            mApp.displayImage(post_detail.getUserface(), iv_cycle_icon);
            tv_username.setText(post_detail.getTitle());
            tv_has_update.setText("已更新" + post_detail.getChildCount() + "篇");
            List<String> tags = post_detail.getTags();
            for (String str : tags) {
                Log.i("tagtest", post_detail.getTags().toString());
            }
            // tv_flag_key.setText(post_detail.getTags());
            // tv_flag_value.setText(post_detail.getTitle());
            tv_date.setText(DateUtil.stamp2humanDate(post_detail.getCtime()));
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_right1:
                if (mDetail != null) {
                    Log.i("send", mItemData.toString());
                    ModelExperienceSend send = new ModelExperienceSend();
                    send.setWeiba_id(mItemData.getWeiba_id());
                    send.setParent_id(mItemData.getPost_id());
                    String tags = mDetail.getPost_detail().getTags().toString();
                    if (tags.contains("[")) {
                        tags = tags.replace("[", "");
                    }
                    if (tags.contains("]")) {
                        tags = tags.replace("]", "");
                    }
                    send.setTags(tags);
                    mApp.startActivity_qcj(this, ExperienceSendActivity.class,
                            sendDataToBundle(send, null));
                }
                break;

        }
    }
}
