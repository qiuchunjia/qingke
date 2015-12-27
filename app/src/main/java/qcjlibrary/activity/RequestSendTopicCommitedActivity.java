package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.model.ModelRequestAsk;
import qcjlibrary.model.ModelRequestItem;

import android.view.View;
import android.widget.Button;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:31:08 类描述：这个类是实现
 */

public class RequestSendTopicCommitedActivity extends BaseActivity {
    private Button btn_requests;
    private ModelRequestItem mRequestItem;
    private ModelRequestAsk mAsk;

    @Override
    public String setCenterTitle() {
        return "提示";
    }

    @Override
    public void initIntent() {
        mAsk = (ModelRequestAsk) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_send_topic_commited;
    }

    @Override
    public void initView() {
        titleSetRightTitle("关闭");
        btn_requests = (Button) findViewById(R.id.btn_requests);

    }

    @Override
    public void initData() {
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(this);

    }

    @Override
    public void initListener() {
        btn_requests.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_requests:
                if (mAsk != null) {
                    ModelRequestItem item = new ModelRequestItem();
                    item.setQuestion_id(mAsk.getQid());
                    if (mAsk.getIs_expert().equals("0")) {
                        mApp.startActivity_qcj(
                                RequestSendTopicCommitedActivity.this,
                                RequestDetailCommonActivity.class,
                                sendDataToBundle(item, null));
                    } else if (mAsk.getIs_expert().equals("1")) {
                        mApp.startActivity_qcj(
                                RequestSendTopicCommitedActivity.this,
                                RequestDetailExpertActivity.class,
                                sendDataToBundle(item, null));
                    }
                }
                break;
            case R.id.tv_title_right:
                onBackPressed();
                break;
        }

    }
}
