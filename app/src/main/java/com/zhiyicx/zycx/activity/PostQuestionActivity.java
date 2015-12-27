package com.zhiyicx.zycx.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.fragment.QuestionCategoryFragment;
import com.zhiyicx.zycx.fragment.TopicsListFragment;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/1/7.
 */

public class PostQuestionActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "PostQuestionActivity";
    private EditText mEditText = null;
    private TextView mCateText = null;
    private View mContentView = null;
    private View mSpaceView = null;
    private Button mNextBtn = null;
    private QuestionCategoryFragment mCategoryFgmt = null;
    private TopicsListFragment mTopicsListFgmt = null;
    private String mContent = null;
    private int mType = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.question_edit_layout);
        mEditText = (EditText) findViewById(R.id.edit_question);
        mCateText = (TextView) findViewById(R.id.txt_cate);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.layout_cate).setOnClickListener(this);
        mNextBtn = (Button) findViewById(R.id.btn_next);
        mContentView = findViewById(R.id.content_view);
        mSpaceView = findViewById(R.id.fragment_space);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if (mCategoryFgmt != null && mCategoryFgmt.isVisible())
                    showCategory(false);
                else if (mTopicsListFgmt != null && mTopicsListFgmt.isVisible())
                    showTopicsList(false);
                else
                    finish();
                break;
            case R.id.btn_next:
                if (mNextBtn.getText().equals("下一步"))
                    next();
                else if (mNextBtn.getText().equals("提交"))
                    commit();
                break;
            case R.id.layout_cate:
                showCategory(true);
                break;
        }
    }

    private void next() {
        String txt = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(txt)) {
            Utils.showToast(this, "提问内容不能为空!");
            return;
        }
        mContent = Utils.getUTF8String(txt);
        if (mType == -1) {
            Utils.showToast(this, "请选择癌症种类!");
            return;
        }
        showTopicsList(true);
    }

    private void commit() {
        String url = MyConfig.QUESTION_ASK_URL + Utils.getTokenString(this) + "&cid=" + mType + "&content=" + mContent;
        if (mTopicsListFgmt != null) {
            String topics = mTopicsListFgmt.getTopics();
            if (!TextUtils.isEmpty(topics))
                url += "&topics=" + Utils.getUTF8String(topics);
        }
        NetComTools netComTools = NetComTools.getInstance(this);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "Question commit data, " + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        Utils.showToast(PostQuestionActivity.this, "提问成功!");
                        finish();
                    } else {
                        String msg = jsonObject.optString("message");
                        if (!TextUtils.isEmpty(msg)) {
                            Utils.showToast(PostQuestionActivity.this, msg);
                        }
                    }
                } catch (Exception e) {
                    Utils.showToast(PostQuestionActivity.this, "网络错误！");
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Question commit error, " + error);
            }
        });
    }

    private void showCategory(boolean en) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCategoryFgmt == null) {
            mCategoryFgmt = new QuestionCategoryFragment();
            mCategoryFgmt.setSelectListener(new QuestionCategoryFragment.SelectListener() {
                @Override
                public void OnSelect(int id, String title) {
                    mType = id;
                    mCateText.setText(title);
                    showCategory(false);
                    switchSoftInput();
                }
            });
            transaction.add(R.id.fragment_space, mCategoryFgmt);
        }
        if (en) {
            mContentView.setVisibility(View.GONE);
            mSpaceView.setVisibility(View.VISIBLE);
            transaction.show(mCategoryFgmt);
            switchSoftInput();
        } else {
            mSpaceView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
            transaction.hide(mCategoryFgmt);
        }
        transaction.commit();
    }


    private void showTopicsList(boolean en) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mTopicsListFgmt == null) {
            mTopicsListFgmt = TopicsListFragment.newInstance(mContent);
            transaction.add(R.id.fragment_space, mTopicsListFgmt);
        }
        if (en) {
            mNextBtn.setText("提交");
            mContentView.setVisibility(View.GONE);
            mSpaceView.setVisibility(View.VISIBLE);
            transaction.show(mTopicsListFgmt);
            switchSoftInput();
        } else {
            mNextBtn.setText("下一步");
            mSpaceView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
            transaction.hide(mTopicsListFgmt);
        }
        transaction.commit();
    }

    private void switchSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            //如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }
}
