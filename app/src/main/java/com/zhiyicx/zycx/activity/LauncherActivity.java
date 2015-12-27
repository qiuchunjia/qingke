package com.zhiyicx.zycx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;

import com.zhiyicx.zycx.LoginActivity;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.net.HttpHelper;
import com.zhiyicx.zycx.sociax.unit.Anim;

/**
 * 欢迎页面跳转登陆界面
 *
 * @author Administrator
 */
public class LauncherActivity extends Activity {
    private static final String TAG = "LauncherActivity";
    private static ThinksnsAbscractActivity activity;
    private static Worker initThread = null;
    protected static ActivityHandler handler = null;
    protected static int INIT_OK = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        this.initApp();
        HttpHelper.setContext(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initApp() {
        initThread = new Worker((Thinksns) this.getApplicationContext());
        handler = new ActivityHandler(initThread.getLooper());
        Message msg = handler.obtainMessage(INIT_OK);
        handler.sendMessage(msg);
    }

    private final class ActivityHandler extends Handler {
        public ActivityHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == INIT_OK) {
                Thinksns app = (Thinksns) LauncherActivity.this.getApplicationContext();
                app.initApi();
                Intent intent;
                if (app.HasLoginUser()) {
                    intent = new Intent(LauncherActivity.this, HomeActivity.class);
                } else {
                    Bundle data = new Bundle();
                    try {
                        Api.Status status = app.initOauth();
                        if (status == Api.Status.RESULT_ERROR) {
                            data.putBoolean("status", false);
                            data.putString("message",
                                    LauncherActivity.this.getResources().getString(R.string.request_key_error));
                        } else {
                            data.putBoolean("status", true);
                        }
                    } catch (ApiException e1) {
                        data.putBoolean("status", false);
                        data.putString("message", e1.getMessage());
                    }
                    intent = new Intent(LauncherActivity.this, LoginActivity.class);
                    intent.putExtras(data);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                LauncherActivity.this.startActivity(intent);
                initThread.quit();
                Anim.in(LauncherActivity.this);
                LauncherActivity.this.finish();
            }
        }
    }
}