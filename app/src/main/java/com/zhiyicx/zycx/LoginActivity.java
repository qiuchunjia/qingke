package com.zhiyicx.zycx;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import qcjlibrary.activity.base.BaseActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.zhiyicx.zycx.activity.HomeActivity;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.component.SmallDialog;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.db.UserSqlHelper;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.net.HttpHelper;
import com.zhiyicx.zycx.sociax.unit.Anim;
import com.zhiyicx.zycx.util.PreferenceUtil;
import com.zhiyicx.zycx.util.Utils;

/**
 * 登录类
 *
 * @author Mr . H
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    final private static String TAG = "LoginActivity";
    // 账号、密码、登录、快速注册、忘记密码
    private AutoCompleteTextView mAutoLoginUserName;
    private EditText mloginUserpd;
    private Button mBtnLogin;
    private TextView mQuickRegister, mForgetPassword;

    private static Worker thread = null;
    private static String myUrl = null;
    private static DialogHandler dialogHandler = null;
    protected static ActivityHandler handler = null;
    // QQ登录、人人登录、腾讯微博、新浪微博
    @SuppressWarnings("unused")
    private ImageView mQQLogin, mShareTencentWeb, mShareSinaWeb, mRenrenLogin;

    private static final String TYPE_RENREN = "renren";
    private static final String TYPE_QQ = "qq";
    private static final String TYPE_SINA = "sina";
    private static final String TYPE_TENCENT = "tencent";

    private String mType_uid = null;
    private String mType = null;
    private String mType_Access = null;
    private boolean mIsBind = false;

    private UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.login");

    @Override
    public String setCenterTitle() {
        return "登录";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        titleSetRightTitle("立即注册");
        HttpHelper.setContext(getApplicationContext());
        mAutoLoginUserName = (AutoCompleteTextView) findViewById(R.id.et_username);
        mloginUserpd = (EditText) findViewById(R.id.et_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mForgetPassword = (TextView) findViewById(R.id.tv_forget_pwd);
        mQQLogin = (ImageView) findViewById(R.id.iv_qq);
        mRenrenLogin = (ImageView) findViewById(R.id.iv_weibo);
        mShareTencentWeb = (ImageView) findViewById(R.id.iv_tengxun);
        mShareSinaWeb = (ImageView) findViewById(R.id.iv_weibo);
    }

    @Override
    public void initData() {
        UserSqlHelper db = UserSqlHelper.getInstance(this);
        mAutoLoginUserName.setAdapter(new ArrayAdapter<String>(this,
                R.layout.account_item, db.getUnameList()));
        thread = new Worker((Thinksns) this.getApplicationContext(),
                "Auth User");
        handler = new ActivityHandler(thread.getLooper(), this);

        if (Thinksns.getMySite() == null) {
            String[] configHttp = getBaseContext().getResources()
                    .getStringArray(R.array.site_url);
            myUrl = configHttp[0] + configHttp[1];
        } else {
            String[] tempUrl = Thinksns.dealUrl(Thinksns.getMySite().getUrl());
            myUrl = tempUrl[0] + "/" + tempUrl[1];
        }
    }

    @Override
    public void initListener() {
        mBtnLogin.setOnClickListener(this);
        mRenrenLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mType = TYPE_RENREN;
                otherLogin(SHARE_MEDIA.RENREN);
            }
        });
        mQQLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mType = TYPE_QQ;
                otherLogin(SHARE_MEDIA.QQ);
            }
        });
        mShareSinaWeb.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                mType = TYPE_SINA;
                otherLogin(SHARE_MEDIA.SINA);
            }
        });
        mShareTencentWeb.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mType = TYPE_TENCENT;
                otherLogin(SHARE_MEDIA.TENCENT);
            }
        });
        mQuickRegister = getTitleClass().tv_title_right;
        mQuickRegister.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mForgetPassword.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetPwdActivity1.class);
                startActivity(intent);
            }
        });

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104001628",
                "T8KvqK3e3EQOyBgA");
        qqSsoHandler.addToSocialSDK();

    }

    public void onClick(View v) {
        String userName = mAutoLoginUserName.getText().toString().trim();// URLEncoder.encode(PublicMethods.encryptName(mAutoLoginUserName.getText().toString().trim(),
        // "THINKSNS"));
        String userPassWd = mloginUserpd.getText().toString().trim();// URLEncoder.encode(PublicMethods.encryptPassWd(PublicMethods.encryptMD5(mloginUserpd.getText().toString().trim()),
        // "THINKSNS"));
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassWd)) {
            Utils.showToast(LoginActivity.this, "用户名或者密码为空");
        } else {
            SmallDialog dialog = new SmallDialog(this, "验证中,请稍后", 0);
            dialogHandler = new DialogHandler(dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Message msg = handler.obtainMessage();
            msg.what = 1;
            Bundle data = new Bundle();
            data.putString("username", userName);
            data.putString("password", userPassWd);
            if (mIsBind) // 帐号绑定
            {
                data.putString("type_uid", mType_uid);
                data.putString("type", mType);
                msg.what = 2;
            }
            msg.setData(data);
            handler.sendMessage(msg);
        }
    }

    private void showAlertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("是否已经拥有青稞帐号");
        dialog.setMessage("如果有，请在登陆页面登陆绑定，以后你就可以用第三方帐号登陆了");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "去注册",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("type", mType);
                        intent.putExtra("type_uid", mType_uid);
                        intent.putExtra("access_token", mType_Access);
                        intent.setClass(LoginActivity.this,
                                RegisterActivity1.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "绑定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        mIsBind = true;
                    }
                });
        dialog.show();
    }

    private void checkUser(String uid, String type) {
        String url = MyConfig.VERIFY_REG_URL + "&type_uid=" + uid + "&type="
                + type;
        NetComTools netComTools = NetComTools.getInstance(this);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "Check user data:" + jsonObject.toString());
                try {
                    int code = jsonObject.getInt("code");
                    if (code != 0) {
                        showAlertDialog();
                    } else {
                        JSONObject jsonObject1 = jsonObject
                                .getJSONObject("data");
                        String oauth_token = jsonObject1
                                .getString("oauth_token");
                        String oauth_token_secret = jsonObject1
                                .getString("oauth_token_secret");
                        int uid = jsonObject1.getInt("uid");
                        User tmpuser = new User(uid, String.valueOf(uid), null,
                                oauth_token, oauth_token_secret);
                        /*
						 * Thinksns app = thread.getApp(); Api.Users users =
						 * app.getUsers(); User user = users.show(tmpuser);
						 * user.setToken(tmpuser.getToken());
						 * user.setSecretToken(tmpuser.getSecretToken());
						 */
                        Thinksns.setMy(tmpuser);
                        UserSqlHelper db = UserSqlHelper
                                .getInstance(LoginActivity.this);
                        PreferenceUtil preferenceUtil = PreferenceUtil
                                .getInstance(LoginActivity.this);
                        db.addUser(tmpuser, true);
						/*
						 * if (!db.hasUname(data.getString("username")))
						 * db.addSiteUser(data.getString("username"));
						 */
                        preferenceUtil.saveString("oauth_token_secret",
                                tmpuser.getSecretToken());
                        preferenceUtil.saveString("oauth_token",
                                tmpuser.getToken());
                        preferenceUtil.saveInt("uid", tmpuser.getUid());
                        Intent intent = new Intent(LoginActivity.this,
                                HomeActivity.class);
                        LoginActivity.this.startActivity(intent);
                        Anim.in(LoginActivity.this);
                        LoginActivity.this.finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
            }
        });
    }

    private void otherLogin(final SHARE_MEDIA media) {
        mController.doOauthVerify(this, media,
                new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        if (value != null
                                && !TextUtils.isEmpty(value.getString("uid"))) {
                            Toast.makeText(LoginActivity.this, "授权成功.",
                                    Toast.LENGTH_SHORT).show();
                            getMediaInfo(media);
                        } else {
                            Toast.makeText(LoginActivity.this, "授权失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                    }

                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                    }
                });
    }

    private void getMediaInfo(final SHARE_MEDIA media) {
        mController.getPlatformInfo(LoginActivity.this, media,
                new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        // Toast.makeText(LoginActivity.this, "获取平台数据开始...",
                        // Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {

                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for (String key : keys) {
                                sb.append(key + "=" + info.get(key).toString()
                                        + "\r\n");
                            }
                            Log.d("TestData", sb.toString());
                            if (media == SHARE_MEDIA.QQ) {
                                mType_uid = info.get("screen_name").toString();
                            } else {
                                mType_uid = info.get("uid").toString();
                                mType_Access = info.get("access_token")
                                        .toString();
                            }
                            checkUser(mType_uid, mType);
                        } else {
                            Log.d("TestData", "发生错误：" + status);
                        }
                    }
                });
    }

    private final class DialogHandler extends Handler {
        private SmallDialog dialog;
        public static final int AUTH_ERROR = 0;
        public static final int CLOSE_DIALOG = 1;
        public static final int AUTH_DOWN = 2;

        public DialogHandler(SmallDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case AUTH_ERROR:
                    // dialog.setContent((String) msg.obj);
                    dialog.dismiss();
                    Utils.showToast(LoginActivity.this, (String) msg.obj);
                    break;
                case CLOSE_DIALOG:
                    if (isFinishing()) {
                        dialog.dismiss();
                        mloginUserpd.requestFocus();
                    }
                    break;
                case AUTH_DOWN:
                    Intent intent = new Intent(LoginActivity.this,
                            HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    LoginActivity.this.startActivity(intent);
                    Anim.in(LoginActivity.this);
                    dialog.dismiss();
                    LoginActivity.this.finish();
                    break;
            }
        }
    }

    private static final class ActivityHandler extends Handler {
        private static final long SLEEP_TIME = 2000;
        private static Context context = null;

        public ActivityHandler(Looper looper, Context context) {
            super(looper);
            ActivityHandler.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            Thinksns app = thread.getApp();
            Api.Oauth oauth = app.getOauth();
            Api.Users users = app.getUsers();
            // Api.Sites siteLog = app.getSites();
            User authorizeResult = null;
            Message errorMessage = new Message();
            Message errorStatus = new Message();
            UserSqlHelper db = UserSqlHelper
                    .getInstance(ActivityHandler.context);
            PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(context);
            User loginedUser = null;
            try {
                switch (msg.what) {
                    case 1:
                        authorizeResult = oauth.authorize(
                                data.getString("username"),
                                data.getString("password"));
                        loginedUser = users.show(authorizeResult);
                        loginedUser.setToken(authorizeResult.getToken());
                        loginedUser
                                .setSecretToken(authorizeResult.getSecretToken());
                        Thinksns.setMy(loginedUser);

                        if (data.containsKey("loginway")) {
                            db.clear();
                        }
                        db.addUser(loginedUser, true);
                        if (!db.hasUname(data.getString("username")))
                            db.addSiteUser(data.getString("username"));
                        errorMessage.arg1 = DialogHandler.AUTH_DOWN;
                        dialogHandler.sendMessage(errorMessage);
                        thread.quit();
                        preferenceUtil.saveString("oauth_token_secret",
                                authorizeResult.getSecretToken());
                        preferenceUtil.saveString("oauth_token",
                                authorizeResult.getToken());
                        preferenceUtil.saveInt("uid", authorizeResult.getUid());
                        break;
                    case 2:
                        authorizeResult = oauth.authorize(
                                data.getString("username"),
                                data.getString("password"),
                                data.getString("type_uid"), data.getString("type"));
                        loginedUser = users.show(authorizeResult);
                        loginedUser.setToken(authorizeResult.getToken());
                        loginedUser
                                .setSecretToken(authorizeResult.getSecretToken());
                        Thinksns.setMy(loginedUser);
                        if (data.containsKey("loginway")) {
                            db.clear();
                        }
                        db.addUser(loginedUser, true);
                        if (!db.hasUname(data.getString("username")))
                            db.addSiteUser(data.getString("username"));
                        errorMessage.arg1 = DialogHandler.AUTH_DOWN;
                        dialogHandler.sendMessage(errorMessage);
                        thread.quit();
                        preferenceUtil.saveString("oauth_token_secret",
                                authorizeResult.getSecretToken());
                        preferenceUtil.saveString("oauth_token",
                                authorizeResult.getToken());
                        preferenceUtil.saveInt("uid", authorizeResult.getUid());
                        break;
                }
            } catch (DataInvalidException e) {
                errorMessage.obj = e.getMessage();
                errorMessage.arg1 = DialogHandler.AUTH_ERROR;
                dialogHandler.sendMessage(errorMessage);
                thread.sleep(SLEEP_TIME);
                errorStatus.arg1 = DialogHandler.CLOSE_DIALOG;
                dialogHandler.sendMessage(errorStatus);
            } catch (VerifyErrorException e) {
                errorMessage.obj = e.getMessage();
                errorMessage.arg1 = DialogHandler.AUTH_ERROR;
                dialogHandler.sendMessage(errorMessage);
                thread.sleep(SLEEP_TIME);
                errorStatus.arg1 = DialogHandler.CLOSE_DIALOG;
                dialogHandler.sendMessage(errorStatus);
            } catch (ApiException e) {
                errorMessage.obj = e.getMessage();
                errorMessage.arg1 = DialogHandler.AUTH_ERROR;
                dialogHandler.sendMessage(errorMessage);
                thread.sleep(SLEEP_TIME);
                errorStatus.arg1 = DialogHandler.CLOSE_DIALOG;
                dialogHandler.sendMessage(errorStatus);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
