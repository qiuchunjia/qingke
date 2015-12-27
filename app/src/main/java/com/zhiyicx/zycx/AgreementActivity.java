package com.zhiyicx.zycx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;

import org.json.JSONObject;

/**
 * 协议类
 *
 * @author Mr . H
 */
public class AgreementActivity extends Activity {
    private ImageButton im_agreementBtn;
    private TextView mTextView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_agreement_layout);
        im_agreementBtn = (ImageButton) findViewById(R.id.im_agreementBtn);
        im_agreementBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
        mTextView = (TextView) findViewById(R.id.txt_context);
        getText();
    }

    private void getText() {
        String url = MyConfig.GET_PROTOCOL;
        NetComTools netComTools = NetComTools.getInstance(this);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                try {
                    String txt = jsonObject.getString("data");
                    mTextView.setText(txt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {

            }
        });
    }
}
