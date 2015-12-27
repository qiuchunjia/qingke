package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.ZhiXunAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.CommonListView;

import android.view.View;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class ZhiXunFlagActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private TextView tv_flag_value;

    @Override
    public void onClick(View v) {

    }

    @Override
    public String setCenterTitle() {
        return "咨询标签";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhixun_flag;
    }

    @Override
    public void initView() {
        tv_flag_value = (TextView) findViewById(R.id.tv_flag_value);
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mAdapter = new ZhiXunAdapter(this, null);
        mCommonListView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initListener() {
        // TODO Auto-generated method stub

    }

}
