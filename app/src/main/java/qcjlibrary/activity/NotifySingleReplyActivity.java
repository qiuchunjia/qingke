package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelNotifyCommment;
import qcjlibrary.model.ModelRequestAnswerComom;
import qcjlibrary.util.ToastUtils;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:18:33 类描述：这个类是实现
 */

public class NotifySingleReplyActivity extends BaseActivity {
    private ModelNotifyCommment mComment;
    private EditText et_content;

    private ModelRequestAnswerComom mSendComment; // 需要发送的comment

    @Override
    public String setCenterTitle() {
        return "回复";
    }

    @Override
    public void initIntent() {
        mComment = (ModelNotifyCommment) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notify_single_replay;
    }

    @Override
    public void initView() {
        et_content = (EditText) findViewById(R.id.et_content);
        titleSetRightTitle("回复评论");
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(this);

    }

    @Override
    public void initData() {
        if (mComment != null) {
            mSendComment = new ModelRequestAnswerComom();
            mSendComment.setComment_id(mComment.getComment_id());
        }

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:
                String content = et_content.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    mSendComment.setContent(content);
                    sendRequest(mApp.getRequestImpl().addComment(mSendComment),
                            ModelMsg.class, REQUEST_GET);
                }
                break;

            default:
                break;
        }

    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelMsg) {
            ToastUtils.showToast("回复成功");
            onBackPressed();
        }
        return object;
    }

}
