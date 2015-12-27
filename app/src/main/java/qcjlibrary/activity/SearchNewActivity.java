package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.util.UIUtils;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:07:29 类描述：这个类是实现
 */

public class SearchNewActivity extends BaseActivity {

    private EditText et_search;
    private TextView tv_sure;
    private TextView tv_webo;
    private TextView tv_request;
    private TextView tv_zhixun;
    private TextView tv_qclass;
    private TextView tv_food;
    private TextView tv_line;
    private ViewPager mViewpager;

    @Override
    public String setCenterTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_webo = (TextView) findViewById(R.id.tv_webo);
        tv_request = (TextView) findViewById(R.id.tv_request);
        tv_zhixun = (TextView) findViewById(R.id.tv_zhixun);
        tv_qclass = (TextView) findViewById(R.id.tv_qclass);
        tv_food = (TextView) findViewById(R.id.tv_food);
        tv_line = (TextView) findViewById(R.id.tv_line);
        mViewpager = (ViewPager) findViewById(R.id.mViewpager);
        tv_line.getLayoutParams().width = UIUtils
                .getWindowWidth(getApplicationContext()) / 5;
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
        // TODO Auto-generated method stub

    }
}
