package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.RequestAnswerAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelRequestFlag;
import qcjlibrary.util.DisplayUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:31:08 类描述：这个类是实现
 */

public class RequestFlagActivity extends BaseActivity {
    private TextView tv_flag_value;
    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private ModelRequestFlag mFlag;

    @Override
    public String setCenterTitle() {
        return "问答标签";
    }

    @Override
    public void initIntent() {
        mFlag = (ModelRequestFlag) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_flag;
    }

    @Override
    public void initView() {
        tv_flag_value = (TextView) findViewById(R.id.tv_flag_value);
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(DisplayUtils.dp2px(mApp, 10));
        mAdapter = new RequestAnswerAdapter(this, mFlag);
        mCommonListView.setAdapter(mAdapter);
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCommonListView.stepToNextActivity(parent, view, position,
                        RequestDetailCommonActivity.class);
            }
        });
    }

    @Override
    public void initData() {
        if (mFlag != null) {
            tv_flag_value.setText("\"" + mFlag.getTitle() + "\"");
        }

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }

    }
}
