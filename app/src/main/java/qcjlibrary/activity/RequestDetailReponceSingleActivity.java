package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelRequestAnswerComom;
import qcjlibrary.model.ModelRequestItem;
import qcjlibrary.util.ToastUtils;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午2:49:47 类描述：这个类是实现
 */

public class RequestDetailReponceSingleActivity extends BaseActivity {
    private EditText et_content;
    private Button btn_sure;
    private ModelRequestItem mRequestItem;
    private ModelRequestAnswerComom mAnwer;

    @Override
    public String setCenterTitle() {
        return "回复";
    }

    @Override
    public void initIntent() {
        mRequestItem = (ModelRequestItem) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_detail_responce_single;
    }

    @Override
    public void initView() {
        et_content = (EditText) findViewById(R.id.et_content);
        btn_sure = (Button) findViewById(R.id.btn_sure);
    }

    @Override
    public void initData() {
        if (mRequestItem != null) {
            mAnwer = new ModelRequestAnswerComom();
            mAnwer.setQid(mRequestItem.getQuestion_id());
            mAnwer.setAuid(mRequestItem.getPublished_uid());
        }
    }

    @Override
    public void initListener() {
        btn_sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                String content = et_content.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    mAnwer.setContent(content);
                    sendRequest(mApp.getRequestImpl().saveAnswer(mAnwer),
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
        if (judgeTheMsg(object)) {
            ToastUtils.showToast("评论成功");
            setReturnResultSeri(true, null);
            onBackPressed();
        }
        return object;
    }

}
