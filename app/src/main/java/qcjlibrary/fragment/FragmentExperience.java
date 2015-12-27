package qcjlibrary.fragment;

import qcjlibrary.adapter.ExperienceAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.util.DisplayUtils;

import android.view.View;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午3:30:35 类描述：这个类是实现
 */

public class FragmentExperience extends BaseFragment {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;

    @Override
    public void initIntentData() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_experience_layout;
    }

    @Override
    public void initView() {
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(DisplayUtils.dp2px(mActivity, 1));
        mAdapter = new ExperienceAdapter(this, null);
        mCommonListView.setAdapter(mAdapter);

    }

    @Override
    public void initListener() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}
