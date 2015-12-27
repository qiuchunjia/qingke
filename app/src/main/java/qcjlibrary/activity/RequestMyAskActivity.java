package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.ExpertRequestAdapter;
import qcjlibrary.adapter.RequestAnswerAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelRequestItem;
import qcjlibrary.model.ModelRequestMyAsk;
import qcjlibrary.util.DisplayUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class RequestMyAskActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    public String setCenterTitle() {
        return "我的提问";
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
        mCommonListView.setDividerHeight(DisplayUtils.dp2px(
                getApplicationContext(), 10));
        mAdapter = new RequestAnswerAdapter(this, new ModelRequestMyAsk());
        mCommonListView.setAdapter(mAdapter);
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ModelRequestItem item = (ModelRequestItem) parent
                        .getItemAtPosition(position);
                if (item != null) {
                    if (item.getIs_expert().equals("0")) {
                        mCommonListView.stepToNextActivity(parent, view,
                                position, RequestDetailCommonActivity.class);
                    } else if (item.getIs_expert().equals("1")) {
                        mCommonListView.stepToNextActivity(parent, view,
                                position, RequestDetailExpertActivity.class);
                    }
                }

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
