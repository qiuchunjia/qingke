package qcjlibrary.activity;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.RequestAnswerAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelCancerCategory;
import qcjlibrary.model.ModelRequestSearch;
import qcjlibrary.model.base.Model;
import qcjlibrary.util.DisplayUtils;

import android.view.View;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午11:56:18 类描述：这个类是实现
 */

public class CancerCategoryActivity extends BaseActivity {

    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private ModelCancerCategory mCategory;
    ModelRequestSearch mSearch = new ModelRequestSearch();
    private List<Model> mItemList;

    @Override
    public String setCenterTitle() {
        return "癌种类别";
    }

    @Override
    public void initIntent() {
        mCategory = (ModelCancerCategory) getDataFromIntent(getIntent(), null);
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
        if (mCategory != null) {
            titleSetCenterTitle(mCategory.getTitle() + "");
        }
    }

    @Override
    public void initData() {
        mSearch.setCat(mCategory.getId());
        mAdapter = new RequestAnswerAdapter(this, mSearch);
        mCommonListView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
    }

}
