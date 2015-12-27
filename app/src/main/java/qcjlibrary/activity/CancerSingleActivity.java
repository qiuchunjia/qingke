package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.adapter.CancerTopicAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelExperience;
import qcjlibrary.model.ModelExperienceDetail;
import qcjlibrary.model.ModelExperienceDetailInfor;
import qcjlibrary.model.ModelExperienceDetailItem1;
import qcjlibrary.model.ModelExperienceSend;
import qcjlibrary.util.DisplayUtils;
import qcjlibrary.util.ToastUtils;
import qcjlibrary.widget.RoundImageView;
import qcjlibrary.widget.popupview.PopSingleCancer;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:35:07 类描述：这个类是实现
 */

public class CancerSingleActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;

    private RoundImageView iv_cancer_icon;
    private TextView tv_cancer_name;
    private TextView tv_cancer_content;
    private ImageView iv_bottom_arrow;
    private ModelExperience mExperienceData; // activity传过来的id
    ModelExperienceDetail mDetail; // 获取头部的信息

    @Override
    public String setCenterTitle() {
        return "肺癌";
    }

    @Override
    public void initIntent() {
        mExperienceData = (ModelExperience) getDataFromIntent(getIntent(), null);
        titleSetCenterTitle(mExperienceData.getWeiba_name());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancer_single;
    }

    @Override
    public void initView() {
        titleSetRightImage(R.drawable.chuangjianjingli);
        iv_cancer_icon = (RoundImageView) findViewById(R.id.iv_cancer_icon);
        tv_cancer_name = (TextView) findViewById(R.id.tv_cancer_name);
        tv_cancer_content = (TextView) findViewById(R.id.tv_cancer_content);
        iv_bottom_arrow = (ImageView) findViewById(R.id.iv_bottom_arrow);
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(DisplayUtils.dp2px(
                getApplicationContext(), 1));
        mAdapter = new CancerTopicAdapter(this, mExperienceData);
        mCommonListView.setAdapter(mAdapter);
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (mDetail != null) {
                    ModelExperienceDetailItem1 detailItem = (ModelExperienceDetailItem1) parent
                            .getItemAtPosition(position);
                    detailItem.setWeiba_id(mDetail.getInfo().getWeiba_id());
                    mCommonListView.stepToNextActivity(detailItem,
                            ExperienceCycleActivity.class);
                }
            }
        });

    }

    @Override
    public void initData() {
        Title title = getTitleClass();
        title.iv_title_right1.setOnClickListener(this);
        sendRequest(mApp.getExperienceImpl().detail(mExperienceData),
                ModelExperienceDetail.class, REQUEST_GET);
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelExperienceDetail) {
            mDetail = (ModelExperienceDetail) object;
            addDataToHead(mDetail.getInfo());
        }
        return object;

    }

    /**
     * @param info
     */
    private void addDataToHead(ModelExperienceDetailInfor info) {
        if (info != null) {
            mApp.displayImage(info.getLogo(), iv_cancer_icon);
            tv_cancer_name.setText(info.getWeiba_name());
            tv_cancer_content.setText(info.getIntro());
        }
    }

    @Override
    public void initListener() {
        iv_bottom_arrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_right1:
                if (mDetail != null) {
                    ModelExperienceSend send = new ModelExperienceSend();
                    send.setWeiba_id(mDetail.getInfo().getWeiba_id());
                    send.setTags(mDetail.getInfo().getTags());
                    mApp.startActivity_qcj(this, ExperienceSendActivity.class,
                            sendDataToBundle(send, null));
                } else {
                    ToastUtils.showToast("正在获取数据");
                }
                break;

            case R.id.iv_bottom_arrow:
                if (mDetail != null) {
                    PopSingleCancer popSingleCancer = new PopSingleCancer(this,
                            mDetail.getInfo(), this);
                    popSingleCancer.showPop(mTitlell, -1, 0, 0);
                }
                break;
        }

    }
}
