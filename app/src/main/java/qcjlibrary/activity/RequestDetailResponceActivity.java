package qcjlibrary.activity;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelRequestAnswerComom;
import qcjlibrary.model.ModelRequestCommmetCommon;
import qcjlibrary.widget.RoundImageView;
import qcjlibrary.widget.popupview.PopDealAnwer;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:31:08 类描述：这个类是实现
 */

public class RequestDetailResponceActivity extends BaseActivity {
    private RoundImageView riv_other_icon;
    private TextView tv_other_username;
    private TextView tv_other_date;
    private TextView tv_other_content;
    private LinearLayout ll_replay;

    private EditText et_content;
    private TextView tv_send;

    ModelRequestAnswerComom mAnswerCommon;

    @Override
    public String setCenterTitle() {
        return "呵呵哒的解答";
    }

    @Override
    public void initIntent() {
        mAnswerCommon = (ModelRequestAnswerComom) getDataFromIntent(
                getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_detail_responce;
    }

    @Override
    public void initView() {
        if (!mAnswerCommon.isShoudGone()) {
            titleSetRightImage(R.drawable.more3);
        }
        riv_other_icon = (RoundImageView) findViewById(R.id.riv_other_icon);
        tv_other_username = (TextView) findViewById(R.id.tv_other_username);
        tv_other_date = (TextView) findViewById(R.id.tv_other_date);
        tv_other_content = (TextView) findViewById(R.id.tv_other_content);
        ll_replay = (LinearLayout) findViewById(R.id.ll_replay);

        et_content = (EditText) findViewById(R.id.et_content);
        tv_send = (TextView) findViewById(R.id.tv_send);

    }

    @Override
    public void initData() {
        Title title = getTitleClass();

        title.iv_title_right1.setOnClickListener(this);
        title.iv_title_right1.setOnClickListener(this);
        if (mAnswerCommon != null) {
            addDataToHead(mAnswerCommon);
            sendRequest(mApp.getRequestImpl().commentList(mAnswerCommon),
                    ModelRequestCommmetCommon.class, REQUEST_GET);
        }
    }

    /**
     * 添加数据到头部
     *
     * @param mAnswerCommon2
     */
    private void addDataToHead(ModelRequestAnswerComom mAnswerCommon2) {
        if (mAnswerCommon2 != null) {
            titleSetCenterTitle(mAnswerCommon2.getUser_name() + "的解答");
            mApp.displayImage(mAnswerCommon2.getUser_face(), riv_other_icon);
            tv_other_username.setText(mAnswerCommon2.getUser_name());
            tv_other_date.setText(mAnswerCommon2.getTime());
            tv_other_content.setText(mAnswerCommon2.getAnswer_content());
        }
    }

    private boolean isPop = false; // 是否点击的是popwindow界面

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelRequestCommmetCommon) {
            ModelRequestCommmetCommon common = (ModelRequestCommmetCommon) object;
            addDataToHead(common.getAnswer());
            addDataToll(common.getCommentList());
        }
        if (object instanceof ModelMsg) {
            if (judgeTheMsg(object)) {
                if (!isPop) {
                    sendRequest(mApp.getRequestImpl()
                                    .commentList(mAnswerCommon),
                            ModelRequestCommmetCommon.class, REQUEST_GET);
                    et_content.setText("");
                } else {
                    isPop = false;
                    setReturnResultSeri(true, null);
                    onBackPressed();
                }
            }
        }
        return object;
    }

    /**
     * 添加数据到线性布局里面
     *
     * @param commentList
     */
    private void addDataToll(List<ModelRequestAnswerComom> commentList) {
        if (commentList != null) {
            if (ll_replay.getChildCount() > 0) {
                ll_replay.removeAllViews();
            }
            for (int i = 0; i < commentList.size(); i++) {
                ModelRequestAnswerComom answerComom = commentList.get(i);
                View view = mInflater.inflate(
                        R.layout.item_requeset_detail_replay, null);
                /******************* 初始化控件 *********************/
                RoundImageView riv_other_icon = (RoundImageView) view
                        .findViewById(R.id.riv_other_icon);
                TextView tv_other_username = (TextView) view
                        .findViewById(R.id.tv_other_username);
                TextView tv_other_date = (TextView) view
                        .findViewById(R.id.tv_other_date);
                TextView tv_other_content = (TextView) view
                        .findViewById(R.id.tv_other_content);
                /******************* 初始化控件end *********************/
                mApp.displayImage(answerComom.getUser_face(), riv_other_icon);
                tv_other_username.setText(answerComom.getUser_name());
                tv_other_date.setText(answerComom.getTime());
                tv_other_content.setText(answerComom.getContent());
                ll_replay.addView(view);
            }
        }
    }

    @Override
    public void initListener() {
        tv_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_right1:
                PopDealAnwer popDealAnwer = new PopDealAnwer(this, mAnswerCommon,
                        this);
                popDealAnwer.showPop(ll_replay, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.tv_send:
                String content = et_content.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    mAnswerCommon.setContent(content);
                    sendRequest(mApp.getRequestImpl().answerComment(mAnswerCommon),
                            ModelMsg.class, REQUEST_GET);
                }
                break;
        }

    }

    @Override
    public Object onPopResult(Object object) {
        Object data = super.onPopResult(object);
        if (data instanceof String) {
            String type = (String) data;
            mAnswerCommon.setType(type);
            isPop = true;
            sendRequest(mApp.getRequestImpl().setBestAnswer(mAnswerCommon),
                    ModelMsg.class, REQUEST_GET);
        }

        return data;
    }
}
