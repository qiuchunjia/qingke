package com.zhiyicx.zycx;

import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.activity.HomeActivity;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.db.UserSqlHelper;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.unit.Anim;
import com.zhiyicx.zycx.tools.PublicMethods;
import com.zhiyicx.zycx.util.PreferenceUtil;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONObject;

/**
 * 手机注册
 *
 * @author Mr . H
 */
public class PhoneFrament extends Fragment implements JsonDataListener {
    final private static String TAG = "PhoneFrament";
    private ImageButton mIbBtnAgreenment, mIbBtnPhoneRegister;
    private EditText et_phone, et_code, et_passwd, et_sure, et_register_name;
    private TextView tv_send_code;
    private RadioButton sw_phone_ismyself;
    // 男RadioButton
    private RadioButton rb_phone_man;
    // 协议CheckBox
    private CheckBox cb_agreement_phone;
    public int isMySelfNum = 0;
    public int ismanORwoman = 1;

    private String mType_uid = null;
    private String mType = null;
    private String mType_token = null;

    public static PhoneFrament newInstance(String type, String uid, String token) {
        PhoneFrament f = new PhoneFrament();
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(uid)
                && !TextUtils.isEmpty(token)) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            bundle.putString("uid", uid);
            bundle.putString("token", token);
            f.setArguments(bundle);
        }
        return f;
    }

    private boolean checkEdit() {
        if (TextUtils.isEmpty(et_phone.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(et_passwd.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(et_register_name.getText().toString().trim()))
            return false;
        return true;
    }

    @SuppressLint("NewApi")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_phone_layout,
                container, false);// 关联布局文件/

        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString("type", null);
            mType_uid = bundle.getString("uid", null);
            mType_token = bundle.getString("token", null);
        }
        // 查找按钮
        initView(rootView);
        String str_surepasswd = et_sure.getText().toString().trim();
        rb_phone_man.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    // 男 1
                    ismanORwoman = 1;
                } else {
                    // 女 2
                    ismanORwoman = 2;
                }
            }
        });

        sw_phone_ismyself
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            // 是本人 1
                            isMySelfNum = 1;
                        } else {
                            // 不是本人 0
                            isMySelfNum = 0;
                        }

                    }
                });
        tv_send_code.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                String str_phonenum = et_phone.getText().toString().trim();
                String path = MyConfig.GETCODE_URL + "mobile=" + str_phonenum
                        + "&notify=SEND_CODE&type=register";
                NetComTools.getInstance(getActivity()).getNetJson(path,
                        new JsonDataListener() {
                            @Override
                            public void OnReceive(JSONObject jsonObject) {
                                Log.d(TAG,
                                        "Send code response:"
                                                + jsonObject.toString());
                                try {
                                    boolean success = (Boolean) jsonObject
                                            .get("data");
                                    if (success) {
                                        Log.d(TAG, "Send code success!");
                                        Toast.makeText(getActivity(),
                                                "短信发送成功!", 1).show();
                                    } else {
                                        Log.d(TAG,
                                                "Send code fail,"
                                                        + jsonObject
                                                        .getString("message"));
                                        Toast.makeText(
                                                getActivity(),
                                                "发送失败,"
                                                        + jsonObject
                                                        .getString("message"),
                                                1).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void OnError(String error) {
                                Log.d(TAG, "Send code error :" + error);
                            }
                        });
            }
        });
        mIbBtnPhoneRegister.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (!cb_agreement_phone.isChecked()) {
                    Toast.makeText(getActivity(), "阅读协议", Toast.LENGTH_SHORT)
                            .show();
                } else if (!checkEdit()) {
                    Toast.makeText(getActivity(), "请输入完整的注册信息",
                            Toast.LENGTH_SHORT).show();
                } else if (!et_passwd.getText().toString().trim()
                        .equals(et_sure.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "两次输入密码不同",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String str_phonenum = et_phone.getText().toString().trim();
                    String str_code = et_code.getText().toString().trim();
                    String str_userpasswd = PublicMethods.encryptPassWd(
                            PublicMethods.encryptMD5(et_passwd.getText()
                                    .toString().trim()), "THINKSNS");
                    String str_username = et_register_name.getText().toString()
                            .trim();

                    String path = MyConfig.REGISTER_URL + "mobile="
                            + URLEncoder.encode(str_phonenum) + "&verifyCode="
                            + str_code + "&passwd="
                            + URLEncoder.encode(str_userpasswd) + "&uid="
                            + URLEncoder.encode(str_username)
                            + "&location=四川成都" + "&is_me=" + isMySelfNum
                            + "&sex=" + ismanORwoman/*
                                                     * +"&_session=hequanli"+
													 * "&_debug=hequanli"
													 */;
                    if (mType_token != null && mType != null
                            && mType_uid != null) {
                        // String token = mType_token.substring(0,
                        // mType_token.indexOf('|')-1);
                        // String refresh_token =
                        // mType_token.substring(mType_token.indexOf('|')+1,
                        // mType_token.length()-1);
                        // Log.d(TAG, "token=" + token);
                        // Log.d(TAG, "refresh_token=" + refresh_token);
                        path += "&type=" + mType + "&type_uid=" + mType_uid
                                + "&access_token=" + mType_token;
                    }
                    NetComTools.getInstance(getActivity()).getNetJson(path,
                            PhoneFrament.this);
                }

            }
        });
        mIbBtnAgreenment.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AgreementActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void OnReceive(JSONObject jsonObject) {
        Log.d(TAG, "Register response:" + jsonObject.toString());
        try {
            int code = jsonObject.getInt("code");
            if (code != 0) {
                String msg = jsonObject.optString("message");
                if (!TextUtils.isEmpty(msg))
                    Utils.showToast(getActivity(), msg);
                else
                    Utils.showToast(getActivity(), "注册失败");
            } else {
                Log.d(TAG, "Register success!");
                Utils.showToast(getActivity(), "注册成功");
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                String oauth_token = jsonObject1.getString("oauth_token");
                String oauth_token_secret = jsonObject1
                        .getString("oauth_token_secret");
                int uid = jsonObject1.getInt("uid");
                if (TextUtils.isEmpty(oauth_token)
                        || TextUtils.isEmpty(oauth_token_secret)) {
                    Utils.showToast(getActivity(), "服务器返回错误");
                    return;
                }
                User tmpuser = new User(uid, et_register_name.getText()
                        .toString().trim(), et_passwd.getText().toString()
                        .trim(), oauth_token, oauth_token_secret);
                Thinksns.setMy(tmpuser);
                UserSqlHelper db = UserSqlHelper.getInstance(getActivity());
                PreferenceUtil preferenceUtil = PreferenceUtil
                        .getInstance(getActivity());
                db.addUser(tmpuser, true);
                preferenceUtil.saveString("oauth_token_secret",
                        tmpuser.getSecretToken());
                preferenceUtil.saveString("oauth_token", tmpuser.getToken());
                preferenceUtil.saveInt("uid", tmpuser.getUid());
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                Anim.in(getActivity());
                getActivity().finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnError(String error) {
        Log.d(TAG, "Register error :" + error);
    }

    private void initView(View rootView) {
        mIbBtnAgreenment = (ImageButton) rootView
                .findViewById(R.id.ib_agreement_txt);
        mIbBtnPhoneRegister = (ImageButton) rootView
                .findViewById(R.id.ibBtnPhoneRegister);
        et_phone = (EditText) rootView.findViewById(R.id.et_phone);
        tv_send_code = (TextView) rootView.findViewById(R.id.tv_send_code);
        et_code = (EditText) rootView.findViewById(R.id.et_code);
        et_passwd = (EditText) rootView.findViewById(R.id.et_passwd);
        et_sure = (EditText) rootView.findViewById(R.id.et_sure);
        et_register_name = (EditText) rootView
                .findViewById(R.id.et_register_name);
        sw_phone_ismyself = (RadioButton) rootView
                .findViewById(R.id.sw_phone_ismyself);
        rb_phone_man = (RadioButton) rootView.findViewById(R.id.rb_phone_man);
        cb_agreement_phone = (CheckBox) rootView
                .findViewById(R.id.cb_agreement_phone);
    }
}
