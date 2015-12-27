package com.zhiyicx.zycx;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.modle.HttpReturnData;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.tools.PublicMethods;

public class ForgetPwdActivity2 extends Activity implements OnClickListener {
    @SuppressWarnings("unused")

    final private static String TAG = "SureNewPd";

    private EditText et_forget_newpasswd, et_forget_surenewpasswd;
    private ImageButton mIB_ForgetSure;
    HttpReturnData httpReturnData = new HttpReturnData();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forget_newpdsetting_layout);
        initView();
    }

    private void initView() {
        et_forget_newpasswd = (EditText) findViewById(R.id.et_forget_newpasswd);
        et_forget_surenewpasswd = (EditText) findViewById(R.id.et_forget_surenewpasswd);
        mIB_ForgetSure = (ImageButton) findViewById(R.id.IB_forgetSure);
        mIB_ForgetSure.setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IB_forgetSure:
                amendPassWd();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    /**
     * 修改密码
     */
    public void amendPassWd() {
        if (!equalsPassWd()) {
            Toast.makeText(getApplicationContext(), "两次密码不一样", Toast.LENGTH_SHORT).show();
        } else {
            String strFirstPassWd = et_forget_newpasswd.getText().toString().trim();
            // 获取Bundle的信息
            Bundle bundle = getIntent().getExtras();
            String accountNum = bundle.getString("accountNum");
            String accountCode = bundle.getString("accountCode");
            String path = MyConfig.FORGET_BTN_SUREPW_URL + "&uid=" + URLEncoder.encode(accountNum) + "&passwd="
                    + URLEncoder.encode(PublicMethods.encryptPassWd(PublicMethods.encryptMD5(strFirstPassWd), "THINKSNS")) + "&verify=" + URLEncoder.encode(accountCode);
            System.out.println("accountNum-----" + accountNum);
            System.out.println("accountCode++++++" + accountCode);
            NetComTools.getInstance(this).getNetJson(path, new JsonDataListener() {
                @Override
                public void OnReceive(JSONObject jsonObject) {
                    Log.d(TAG, "Reset password jsonObject:" + jsonObject.toString());
                    try {
                        boolean success = (Boolean) jsonObject.get("data");
                        if (success) {
                            Log.d(TAG, "Reset password success!");
                            String numsString = jsonObject.getString("code");
                            sureSuccess(numsString);
                        } else {
                            Log.d(TAG, "Reset password fail," + (String) jsonObject.get("message"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnError(String error) {
                    Log.d(TAG, "Reset password error, " + error);
                }
            });
        }
    }

    /**
     * 判断两次输入的密码是否一样
     */
    public boolean equalsPassWd() {
        String strFirstPassWd = et_forget_newpasswd.getText().toString().trim();
        String strSecondPassWd = et_forget_surenewpasswd.getText().toString().trim();
        if (strFirstPassWd.equals(strSecondPassWd)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断密码是否修改成功
     */
    public void sureSuccess(String code) {
        if (code.equals("0")) {
            Toast.makeText(getApplicationContext(), "修改成功，", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(ForgetPwdActivity2.this, LoginActivity.class);
            startActivity(intent);

        } else if (code.equals("10300")) {
            Toast.makeText(getApplicationContext(), "验证码错误，", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "密码设置失败，", Toast.LENGTH_SHORT).show();
        }
    }
}
