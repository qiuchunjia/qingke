package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelUser;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午2:39:22 类描述：这个类是实现
 */

public class SettingOneLineEditActivity extends BaseActivity {
    private EditText et_oneline;
    private int mCurrentPosition = -1;

    // 根据传过来的数据来设置title的名字
    public final static int DECLARATION = 0; // 宣言;
    public final static int NICK = 1; // 昵称;
    public final static int GENDER = 2; // 性别;
    public final static int BIRTHDAY = 3; // 生日;
    public final static int LOCATION = 4; // 地址;
    public final static int CANCERCATEGORY = 5; // 癌种;
    ModelUser mUserData = new ModelUser();

    @Override
    public String setCenterTitle() {
        return "设置";
    }

    @Override
    public void initIntent() {
        mCurrentPosition = (Integer) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_oneline;
    }

    @Override
    public void initView() {
        titleSetRightTitle("修改");
        et_oneline = (EditText) findViewById(R.id.et_oneline);
        judgeTheTitle(mCurrentPosition);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:
                String content = et_oneline.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    sendContent(mCurrentPosition, content);
                }
                break;

        }

    }

    /**
     * 根据activity传来的值来判断从而设置title的名字
     *
     * @param mCurrentPosition2
     */
    private void judgeTheTitle(int position) {
        switch (position) {
            case DECLARATION:
                titleSetCenterTitle("抗癌宣言");
                break;

            case NICK:
                titleSetCenterTitle("昵称");
                break;

            case GENDER:
                titleSetCenterTitle("性别");
                break;

            case BIRTHDAY:
                titleSetCenterTitle("生日");
                break;
            case LOCATION:
                titleSetCenterTitle("地址");
                break;

            case CANCERCATEGORY:
                titleSetCenterTitle("癌种");
                break;

        }
    }

    /**
     * 发送消息
     *
     * @param position
     * @param content
     */
    private void sendContent(int position, String content) {
        switch (position) {
            case DECLARATION:
                mUserData.setIntro(content);
                sendRequest(mApp.getUserImpl().edituserdata(mUserData),
                        ModelMsg.class, REQUEST_GET);
                break;

            case NICK:
                mUserData.setUname(content);
                sendRequest(mApp.getUserImpl().edituserdata(mUserData),
                        ModelMsg.class, REQUEST_GET);
                break;

            case GENDER:
                break;

            case BIRTHDAY:
                mUserData.setBirthday(content);
                sendRequest(mApp.getUserImpl().edituserdata(mUserData),
                        ModelMsg.class, REQUEST_GET);
                break;
            case LOCATION:
                break;

            case CANCERCATEGORY:
                break;

        }
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (judgeTheMsg(object)) {
            onBackPressed();
        }
        return object;
    }

}
