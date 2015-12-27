package com.zhiyicx.zycx;

import qcjlibrary.activity.base.BaseActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 注册类
 *
 * @author Mr . H
 */
public class RegisterActivity2 extends BaseActivity {

    private EditText et_pwd;
    private EditText et_pwd_sure;
    private Button btn_register;

    @Override
    public String setCenterTitle() {
        return "注册";
    }

    @Override
    public void initIntent() {
        // TODO
        // Intent intent = getIntent();
        // String type_uid = intent.getStringExtra("type_uid");
        // String type = intent.getStringExtra("type");
        // String token = intent.getStringExtra("access_token");

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register2;
    }

    @Override
    public void initView() {
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd_sure = (EditText) findViewById(R.id.et_pwd_sure);
        btn_register = (Button) findViewById(R.id.btn_register);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_register:
                // TODO
                break;
        }

    }

}
