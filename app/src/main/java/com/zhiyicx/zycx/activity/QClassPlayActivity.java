package com.zhiyicx.zycx.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhiyicx.zycx.util.Utils;

/**
 * Created by Administrator on 2015/1/2.
 */
public class QClassPlayActivity extends Activity {

    private WebView mPlayView = null;

    final static String mHtmlData =
            "<div id=\"youkuplayer\" style=\"width:%s;height:%s\"></div>\n" +
                    "        <script type=\"text/javascript\" src=\"http://player.youku.com/jsapi\">\n" +
                    "        player = new YKU.Player('youkuplayer',{\n" +
                    "        styleid: '1',\n" +
                    "        client_id: 'bf20bc0aca083ec6',\n" +
                    "        vid: '%s',\n" +
                    "        autoplay: true,\n" +
                    "        show_related: true,\n" +
                    "        embsig: '%s'\n" +
                    "        });\n" +
                    "        </script>\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mPlayView = new WebView(this);
        setContentView(mPlayView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mPlayView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mPlayView.setPadding(0, 0, 0, 0);

        /*String vid = getIntent().getStringExtra("vid");
        String stamp = String.valueOf(System.currentTimeMillis() / 1000);
        String secert = "d34ce7d56040bc1b0cfc2cc153901cb0";
        String SIGNATURE = "";
        try {
            SIGNATURE = MD5.encryptMD5(vid + '_' + stamp + '_' + secert);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        String embsig = "1_" + stamp + '_' + SIGNATURE;

        if(TextUtils.isEmpty(vid))
            finish();
        String data = String.format(mHtmlData,"100%","100%", vid, embsig);*/
        String url = getIntent().getStringExtra("vurl");
        mPlayView.getSettings().setJavaScriptEnabled(true);
        mPlayView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mPlayView.loadUrl(url + Utils.getTokenString(this));
    }


}
