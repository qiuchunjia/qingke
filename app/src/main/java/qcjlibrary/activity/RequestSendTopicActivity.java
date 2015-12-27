package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.model.ModelRequestAsk;
import qcjlibrary.util.ToastUtils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:31:08 类描述：这个类是实现
 */

public class RequestSendTopicActivity extends BaseActivity {
    private EditText et_title;
    private EditText et_content;
    private TextView tv_num;
    private ImageView iv_choose_camera;
    private EditText et_edit;
    private TextView tv_send;

    private TextView tv_cure; // 治疗类
    private TextView tv_protect;// 护理类
    private TextView tv_good;// 康复类

    private ModelRequestAsk mAsk;

    private String mTitle;
    private String mContent;
    private String mType;

    @Override
    public String setCenterTitle() {
        return "发表问题";
    }

    @Override
    public void initIntent() {
        mAsk = (ModelRequestAsk) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_send_topic;
    }

    @Override
    public void initView() {
        titleSetRightTitle("下一步");
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_num = (TextView) findViewById(R.id.tv_num);
        iv_choose_camera = (ImageView) findViewById(R.id.iv_choose_camera);
        et_edit = (EditText) findViewById(R.id.et_edit);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_cure = (TextView) findViewById(R.id.tv_cure);
        tv_protect = (TextView) findViewById(R.id.tv_protect);
        tv_good = (TextView) findViewById(R.id.tv_good);
    }

    @Override
    public void initData() {
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(this);
        et_content.addTextChangedListener(mTextWatcher);

    }

    // 用于记录写了多少文字了
    private TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            tv_num.setText(s.length() + "/140");
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = et_content.getSelectionStart();
            editEnd = et_content.getSelectionEnd();
            Log.i("select", "start=" + editStart + ",end=" + editEnd);
            if (temp.length() > 140) {
                Toast.makeText(RequestSendTopicActivity.this, "你输入的字数已经超过了限制！",
                        Toast.LENGTH_SHORT).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                et_content.setText(s);
                et_content.setSelection(tempSelection);
            }
        }
    };

    @Override
    public void initListener() {
        tv_cure.setOnClickListener(this);
        tv_protect.setOnClickListener(this);
        tv_good.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        resetTextColorAndBg();
        switch (v.getId()) {

            case R.id.tv_cure:
                mType = "0";
                setTextColorAndBg(tv_cure);
                break;
            case R.id.tv_protect:
                mType = "1";
                setTextColorAndBg(tv_protect);
                break;
            case R.id.tv_good:
                mType = "2";
                setTextColorAndBg(tv_good);
                break;
            case R.id.tv_title_right:
                getDataFromView();
                if (judgeTheData()) {
                    mAsk.setType(mType);
                    mAsk.setType(mType);
                    mAsk.setContent(mContent);
                    mApp.startActivity_qcj(this, RequestChooseCancerActivity.class,
                            sendDataToBundle(mAsk, null));
                }
                break;

        }

    }

    /**
     * 重置颜色和背景
     */
    private void resetTextColorAndBg() {
        tv_cure.setBackgroundColor(getResources().getColor(
                R.color.main_white_pure_color));
        tv_protect.setBackgroundColor(getResources().getColor(
                R.color.main_white_pure_color));
        tv_good.setBackgroundColor(getResources().getColor(
                R.color.main_white_pure_color));
        tv_cure.setTextColor(getResources().getColor(R.color.text_gray));
        tv_protect.setTextColor(getResources().getColor(R.color.text_gray));
        tv_good.setTextColor(getResources().getColor(R.color.text_gray));
    }

    /**
     * 设置textview背景和颜色
     *
     * @param tv_cure2
     */
    private void setTextColorAndBg(TextView textview) {
        textview.setBackgroundResource(R.drawable.view_border_green_pure_0);
        textview.setTextColor(getResources().getColor(R.color.text_green));
    }

    /**
     * 从界面上获取数据
     */
    public void getDataFromView() {
        mTitle = et_title.getText().toString();
        mContent = et_content.getText().toString();
    }

    private boolean judgeTheData() {
        if (mTitle == null || mTitle.equals("")) {
            ToastUtils.showToast("标题不能为空");
            return false;
        }
        if (mContent == null || mContent.equals("")) {
            ToastUtils.showToast("内容不能为空");
            return false;
        }
        if (mType == null || mType.equals("")) {
            ToastUtils.showToast("请选择类型");
            return false;
        }
        return true;
    }

}
