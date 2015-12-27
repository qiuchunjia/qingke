package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.ExpertRequestAdapter;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.util.DisplayUtils;

import android.view.View;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class ExpertRequestActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private ExpertRequestAdapter mAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    public String setCenterTitle() {
        return "专家提问";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.listview_common_layout;
    }

    @Override
    public void initView() {
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(DisplayUtils.dp2px(mApp, 10));

        mAdapter = new ExpertRequestAdapter(this, null);
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
