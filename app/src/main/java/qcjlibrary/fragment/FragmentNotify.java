package qcjlibrary.fragment;

import qcjlibrary.activity.MsgNotifyDetailActivity;
import qcjlibrary.adapter.NotifyAdapter;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.listview.base.CommonListView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午3:13:46 类描述：这个类是实现
 */

public class FragmentNotify extends BaseFragment {

    private CommonListView mCommonListView;
    private NotifyAdapter mAdapter;

    @Override
    public void initIntentData() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getLayoutId() {
        return R.layout.listview_common_layout;
    }

    @Override
    public void initView() {
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(5);
        mAdapter = new NotifyAdapter(this, null);
        mCommonListView.setAdapter(mAdapter);
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCommonListView.stepToNextActivity(parent, view, position,
                        MsgNotifyDetailActivity.class);

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

    @Override
    public void onClick(View v) {

    }

}
