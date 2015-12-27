package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;

import android.view.View;
import android.widget.LinearLayout;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午2:43:35 类描述：这个类是实现
 */

public class MeAplicationActivity extends BaseActivity {
    private LinearLayout ll_notify;
    private LinearLayout ll_food;

    @Override
    public String setCenterTitle() {
        return "我的应用";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.activity_me_aplication;
    }

    @Override
    public void initView() {
        ll_notify = (LinearLayout) findViewById(R.id.ll_notify);
        ll_food = (LinearLayout) findViewById(R.id.ll_food);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initListener() {
        ll_notify.setOnClickListener(this);
        ll_food.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_notify:

                break;

            case R.id.ll_food:

                break;
        }

    }

}
