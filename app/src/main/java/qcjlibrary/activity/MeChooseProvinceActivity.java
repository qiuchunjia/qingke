package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.MeChooseAddressAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.config.Config;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelMeAddress;
import qcjlibrary.model.base.Model;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class MeChooseProvinceActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private ModelMeAddress mAddress;
    private Model mReturnData;

    @Override
    public String setCenterTitle() {
        return "省份";
    }

    @Override
    public void initIntent() {
        Object object = getDataFromIntent(getIntent(), null);
        if (object instanceof ModelMeAddress) {
            mAddress = (ModelMeAddress) object;
        } else {
            mAddress = new ModelMeAddress();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.listview_common_layout;
    }

    @Override
    public void initView() {
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(0);
        mAdapter = new MeChooseAddressAdapter(this, new ModelMeAddress());
        mCommonListView.setAdapter(mAdapter);
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ModelMeAddress address = (ModelMeAddress) parent
                        .getItemAtPosition(position);
                mAddress.setArea_id(address.getArea_id());
                mAddress.setWholeAddress(address.getTitle() + " ");
                mAddress.setWholeId(address.getArea_id() + ",");
                mApp.startActivityForResult_qcj(MeChooseProvinceActivity.this,
                        MeChooseCityActivity.class,
                        sendDataToBundle(mAddress, null));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mReturnData = (Model) getReturnResultSeri(resultCode, data,
                Config.TYPE_ADDRESS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReturnData != null) {
            setReturnResultSeri(mReturnData, Config.TYPE_ADDRESS);
            onBackPressed();
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
    }

}
