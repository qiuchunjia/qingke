package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.CaseHistoryAdapter;
import qcjlibrary.listview.base.CommonListView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class CaseHistoryActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private CaseHistoryAdapter mAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    public String setCenterTitle() {
        return "历史记录";
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
        mCommonListView.setDividerHeight(0);
        mAdapter = new CaseHistoryAdapter(this, null);
        mCommonListView.setAdapter(mAdapter);
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCommonListView.stepToNextActivity(parent, view, position,
                        CaseHistoryDetailActivity.class);
            }
        });
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
