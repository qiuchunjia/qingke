package com.zhiyicx.zycx;

import java.net.URLEncoder;

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
import android.widget.Toast;

import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.tools.PublicMethods;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONObject;

/**
 * ����ע��
 *
 * @author Mr . H
 */
public class MailFrament extends Fragment implements JsonDataListener {

    final private static String TAG = "MailFrament";
    private EditText et_mail_name, et_mail_passwd, et_mail_surepasswd, et_register_name_mail;
    private String str_mail_name, str_mail_passwd, str_mail_surepasswd, str_register_name_mail;
    private ImageButton mIbBtnMailRegister;
    // ��RadioButton
    private RadioButton rb_mail_man;
    private RadioButton sw_Mail_ismyself;
    public int isMySelfNum = 0;
    public int ismanORwoman = 1;
    // Э��CheckBox
    private CheckBox cb_agreement_mail;

    private String mType_uid = null;
    private String mType = null;
    private String mType_token = null;

    public static MailFrament newInstance(String type, String uid, String token) {
        MailFrament f = new MailFrament();
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(uid) && !TextUtils.isEmpty(token)) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            bundle.putString("uid", uid);
            bundle.putString("token", token);
            f.setArguments(bundle);
        }
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_mail_layout, container, false);// ���������ļ�

        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString("type", null);
            mType_uid = bundle.getString("uid", null);
            mType_token = bundle.getString("token", null);
        }

        initView(rootView);

        rb_mail_man.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // �� 1
                    ismanORwoman = 1;
                } else {
                    // Ů 2
                    ismanORwoman = 2;
                }
            }
        });
        sw_Mail_ismyself.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // �Ǳ��� 1
                    isMySelfNum = 1;
                } else {
                    // ���Ǳ��� 0
                    isMySelfNum = 0;
                }

            }
        });
        mIbBtnMailRegister.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (!cb_agreement_mail.isChecked()) {
                    Toast.makeText(getActivity(), "���Ķ�Э��", Toast.LENGTH_SHORT).show();
                } else if (!checkEdit()) {
                    Toast.makeText(getActivity(), "请输入完整的注册信息", Toast.LENGTH_SHORT).show();
                } else if (!et_mail_passwd.getText().toString().trim().equals(et_mail_surepasswd.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "两次输入密码不同", Toast.LENGTH_SHORT).show();
                } else {
                    // �û�������
                    String userMail = URLEncoder.encode(et_mail_name.getText().toString().trim());
                    String userMailPassWd = URLEncoder.encode(PublicMethods.encryptPassWd(
                            PublicMethods.encryptMD5(et_mail_passwd.getText().toString().trim()), "THINKSNS"));
                    String userMailName = URLEncoder.encode(et_register_name_mail.getText().toString().trim());


                    String path = MyConfig.REGISTER_URL + "email=" + userMail + "&passwd=" + userMailPassWd + "&uid=" + userMailName
                            + "&location=�Ĵ��ɶ�" + "&is_me=" + isMySelfNum + "&sex=" + ismanORwoman;

                    if (mType_token != null && mType != null && mType_uid != null) {

                        path += "&type=" + mType + "&type_uid=" + mType_uid + "&access_token=" + mType_token;
                    }

                    NetComTools.getInstance(getActivity()).getNetJson(path, MailFrament.this);

                }
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
                String msg = jsonObject.optString("message");
                if (!TextUtils.isEmpty(msg))
                    Utils.showToast(getActivity(), msg);
                else
                    Utils.showToast(getActivity(), "帐户注册成功,请进入邮箱激活帐户");
                /*
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                String oauth_token = jsonObject1.getString("oauth_token");
                String oauth_token_secret = jsonObject1.getString("oauth_token_secret");
                int uid = jsonObject1.getInt("uid");
                if(TextUtils.isEmpty(oauth_token) || TextUtils.isEmpty(oauth_token_secret))
                {
                	Utils.showToast(getActivity(), "服务器返回错误");
                	return;
                }
                User tmpuser = new User(uid, et_register_name_mail.getText().toString().trim(), 
                		et_mail_passwd.getText().toString().trim(), oauth_token, oauth_token_secret);
                Thinksns.setMy(tmpuser);
                UserSqlHelper db = UserSqlHelper.getInstance(getActivity());
                PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(getActivity());
                db.addUser(tmpuser, true);
                preferenceUtil.saveString("oauth_token_secret", tmpuser.getSecretToken());
                preferenceUtil.saveString("oauth_token", tmpuser.getToken());
                preferenceUtil.saveInt("uid", tmpuser.getUid());
                
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                Anim.in(getActivity());
                */
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

    private boolean checkEdit() {
        if (TextUtils.isEmpty(et_mail_name.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(et_mail_passwd.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(et_register_name_mail.getText().toString().trim()))
            return false;
        return true;
    }

    private void initView(View rootView) {
        et_mail_name = (EditText) rootView.findViewById(R.id.et_mail_name);
        et_mail_passwd = (EditText) rootView.findViewById(R.id.et_email_passwd);
        et_mail_surepasswd = (EditText) rootView.findViewById(R.id.et_email_surepasswd);
        et_register_name_mail = (EditText) rootView.findViewById(R.id.et_register_name_mail);
        mIbBtnMailRegister = (ImageButton) rootView.findViewById(R.id.ibBtnMailRegister);
        sw_Mail_ismyself = (RadioButton) rootView.findViewById(R.id.sw_phone_ismyself);
        rb_mail_man = (RadioButton) rootView.findViewById(R.id.rb_phone_man);
        cb_agreement_mail = (CheckBox) rootView.findViewById(R.id.cb_agreement_mail);
        rootView.findViewById(R.id.ib_agreement_txt).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(getActivity(), AgreementActivity.class);
                startActivity(intent);
            }
        });
    }

}
