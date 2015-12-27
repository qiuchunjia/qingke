package qcjlibrary.fragment;

import java.util.List;

import qcjlibrary.activity.RequestDetailCommonActivity;
import qcjlibrary.activity.RequestDetailExpertActivity;
import qcjlibrary.activity.RequestSearchActivity;
import qcjlibrary.adapter.RequestAnswerAdapter;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.listview.base.CommonListView;
import qcjlibrary.model.ModelCancerCategory;
import qcjlibrary.model.ModelRequest;
import qcjlibrary.model.ModelRequestItem;
import qcjlibrary.model.base.Model;
import qcjlibrary.util.DisplayUtils;
import qcjlibrary.widget.popupview.PopCancerCategory;

import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:03:45 类描述：这个类是实现
 */

public class FragmentRequestAnwer extends BaseFragment {
    private RelativeLayout rl_space;
    private ImageView iv_zoom;
    private TextView tv_find;
    private EditText et_find;
    private LinearLayout ll_1;
    private ImageView iv_1;
    private LinearLayout ll_2;
    private ImageView iv_2;
    private LinearLayout ll_3;
    private ImageView iv_3;
    private LinearLayout ll_4;
    private ImageView iv_4;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private CommonListView mCommonListView;
    private BAdapter mAdapter;
    private List<ModelCancerCategory> mCancerList; // 癌症种类

    private ModelRequestItem mRequestItem; // 请求数据

    @Override
    public void initIntentData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_request_anwer;
    }

    @Override
    public void initView() {
        rl_space = (RelativeLayout) findViewById(R.id.rl_space);
        iv_zoom = (ImageView) findViewById(R.id.iv_zoom);
        tv_find = (TextView) findViewById(R.id.tv_find);
        et_find = (EditText) findViewById(R.id.et_find);
        ll_1 = (LinearLayout) findViewById(R.id.ll_1);
        iv_1 = (ImageView) findViewById(R.id.iv_1);
        ll_2 = (LinearLayout) findViewById(R.id.ll_2);
        iv_2 = (ImageView) findViewById(R.id.iv_2);
        ll_3 = (LinearLayout) findViewById(R.id.ll_3);
        iv_3 = (ImageView) findViewById(R.id.iv_3);
        ll_4 = (LinearLayout) findViewById(R.id.ll_4);
        iv_4 = (ImageView) findViewById(R.id.iv_4);

        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);

        mCommonListView = (CommonListView) findViewById(R.id.mCommonListView);
        mCommonListView.setDividerHeight(DisplayUtils.dp2px(mApp, 10));
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
        mRequestItem = new ModelRequestItem();
        setTypeAdapter("0");
        iv_1.setImageResource(R.drawable.medica_green);
        tv_1.setTextColor(getResources().getColor(R.color.text_green));
    }

    /**
     * 设置type的类型
     * <p/>
     * 注释：就这么任性的直接new一个adapter，简单粗暴，反正是外包，管我毛事
     */
    private void setTypeAdapter(String type) {
        mRequestItem.setType(type);
        mAdapter = new RequestAnswerAdapter(this, mRequestItem);
        mCommonListView.setAdapter(mAdapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelRequest) {
            ModelRequest request = (ModelRequest) object;
            mCancerList = request.getFenlei();
            PopCancerCategory category = new PopCancerCategory(mActivity,
                    mCancerList, mActivity);
            category.showPop(ll_4, Gravity.RIGHT, 0, 0);
        }
        return object;
    }

    @Override
    public void initListener() {
        rl_space.setOnClickListener(this);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        resetImage();
        switch (v.getId()) {
            case R.id.rl_space:
                mApp.startActivity_qcj(getActivity(), RequestSearchActivity.class,
                        mActivity.sendDataToBundle(new Model(), null));
                break;
            case R.id.ll_1:
                iv_1.setImageResource(R.drawable.medica_green);
                tv_1.setTextColor(getResources().getColor(R.color.text_green));
                setTypeAdapter("0");
                mAdapter.doRefreshNew();
                break;

            case R.id.ll_2:
                setTypeAdapter("1");
                mAdapter.doRefreshNew();
                iv_2.setImageResource(R.drawable.umbrella_green);
                tv_2.setTextColor(getResources().getColor(R.color.text_green));
                break;
            case R.id.ll_3:
                setTypeAdapter("2");
                mAdapter.doRefreshNew();
                iv_3.setImageResource(R.drawable.heart_green);
                tv_3.setTextColor(getResources().getColor(R.color.text_green));
                break;
            case R.id.ll_4:
                iv_4.setImageResource(R.drawable.more_green);
                tv_4.setTextColor(getResources().getColor(R.color.text_green));
                if (mCancerList != null && mCancerList.size() > 0) {
                    PopCancerCategory category = new PopCancerCategory(mActivity,
                            mCancerList, mActivity);
                    category.showPop(ll_4, Gravity.RIGHT, 0, 0);

                } else {
                    sendRequest(mApp.getRequestImpl().index(null),
                            ModelRequest.class, 0);
                }
                break;
        }

    }

    /**
     * 重置图片
     */
    public void resetImage() {
        iv_1.setImageResource(R.drawable.medica);
        iv_2.setImageResource(R.drawable.umbrella);
        iv_3.setImageResource(R.drawable.heart);
        iv_4.setImageResource(R.drawable.more);
        tv_1.setTextColor(getResources().getColor(R.color.text_more_gray));
        tv_2.setTextColor(getResources().getColor(R.color.text_more_gray));
        tv_3.setTextColor(getResources().getColor(R.color.text_more_gray));
        tv_4.setTextColor(getResources().getColor(R.color.text_more_gray));
    }

}
