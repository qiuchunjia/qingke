package qcjlibrary.activity;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.ExperienceCycleAdapter;
import qcjlibrary.adapter.RequestAnswerAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelRequest;
import qcjlibrary.model.ModelRequestSearch;
import qcjlibrary.model.base.Model;
import qcjlibrary.util.DisplayUtils;
import qcjlibrary.util.ToastUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class RequestSearchActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private EditText et_find;
    private ImageView iv_zoom;
    private List<Model> mItemList;
    private ModelRequestSearch mSearch;

    @Override
    public String setCenterTitle() {
        return "问答搜索";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_search;
    }

    @Override
    public void initView() {
        mSearch = new ModelRequestSearch();
        et_find = (EditText) findViewById(R.id.et_find);
        iv_zoom = (ImageView) findViewById(R.id.iv_zoom);
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(DisplayUtils.dp2px(
                getApplicationContext(), 20));
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position > 0) {
                    mCommonListView.stepToNextActivity(parent, view, position,
                            RequestSearchActivity.class);
                }
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelRequest) {
            ModelRequest request = (ModelRequest) object;
            Object data = request.getList();
            mItemList = (List<Model>) data;
            mAdapter = new RequestAnswerAdapter(this, mItemList, mSearch);
            mCommonListView.setAdapter(mAdapter);
        }
        judgeTheMsg(object);
        return object;
    }

    @Override
    public void initListener() {
        iv_zoom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_zoom:
                String searchStr = et_find.getText().toString();
                if (searchStr != null || !searchStr.equals("")) {
                    mSearch.setKey(searchStr);
                    ToastUtils.showToast("搜索中...");
                    sendRequest(mApp.getRequestImpl().search(mSearch),
                            ModelRequest.class, REQUEST_GET);
                } else {
                    ToastUtils.showToast("内容不能为空");
                }
                break;

        }
    }

}
