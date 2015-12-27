package com.zhiyicx.zycx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/1/3.
 */
public class QiKanDetailsActivity extends Activity implements View.OnClickListener {

    final private static String TAG = "QiKanDetailsActivity";
    private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private ProgressBar mProgBar = null;
    private WebView mWebView = null;
    private String mUrl = null;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qikan_details_layout);
        mWebView = (WebView) findViewById(R.id.view_qikan);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mProgBar = (ProgressBar) findViewById(R.id.progressBar);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);
        int pid = getIntent().getIntExtra("pid", -1);
        mTitle = getIntent().getStringExtra("title");
        if (pid == -1)
            finish();
        loadData(pid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private void loadData(int id) {
        String url = MyConfig.QIKAN_DETAILS_URL + Utils.getTokenString(this) + "&pid=" + id;
        NetComTools netComTools = NetComTools.getInstance(this);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                //Log.d(TAG, "Qikan details data:" + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        mUrl = jsonObject.getString("data");
                        mWebView.loadUrl(mUrl);
                        mProgBar.setVisibility(View.GONE);
                        mWebView.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Qikan details error, " + error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                if (TextUtils.isEmpty(mUrl))
                    return;
                /*Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, mUrl);
                intent.setType("text/plain");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Intent.createChooser(intent, "分享到"));*/
                Utils.shareText(this, mController, "青稞网期刊分享:" + mTitle + " - ", mUrl);
                break;
        }
    }
}
