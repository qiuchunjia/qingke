package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.MeChooseAddressAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.config.Config;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelMeAddress;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelUser;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:33:01 类描述：这个类是实现
 */

public class MeChooseTowerActivity extends BaseActivity {
    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private ModelMeAddress mAddress;

    @Override
    public String setCenterTitle() {
        return "城镇";
    }

    @Override
    public void initIntent() {
        mAddress = (ModelMeAddress) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.listview_common_layout;
    }

    @Override
    public void initView() {
        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(0);
        mAdapter = new MeChooseAddressAdapter(this, mAddress);
        mCommonListView.setAdapter(mAdapter);
        mCommonListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ModelMeAddress address = (ModelMeAddress) parent
                        .getItemAtPosition(position);
                mAddress.setWholeAddress(mAddress.getWholeAddress()
                        + address.getTitle());
                mAddress.setWholeId(mAddress.getWholeId()
                        + address.getArea_id());
                if (TextUtils.isEmpty(mAddress.getType())) {
                    modifyTheUserInfor(mAddress);
                } else {
                    setReturnResultSeri(mAddress, Config.TYPE_ADDRESS);
                    onBackPressed();
                }

            }

        });
    }

    /**
     * 修改用户信息
     *
     * @param mAddress
     */
    private void modifyTheUserInfor(ModelMeAddress Address) {
        if (Address != null) {
            ModelUser user = new ModelUser();
            user.setLocation(Address.getWholeAddress());
            user.setCity_ids(Address.getWholeId());
            sendRequest(mApp.getUserImpl().edituserdata(user), ModelMsg.class,
                    REQUEST_GET);
        }
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (judgeTheMsg(object)) {
            onBackPressed();
        }
        return object;
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
