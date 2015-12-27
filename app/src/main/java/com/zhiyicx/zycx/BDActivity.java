package com.zhiyicx.zycx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;

public class BDActivity extends Activity {

    private BDInterstitialAd appxInterstitialAdView;
    private String TAG = "HomeActivity";

    private RelativeLayout appxBannerContainer;
    private static BDBannerAd bannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd);

        initBannerAD();

        initInterstitialAD();

    }

    private void showADs() {
        // չʾ�������ǰ�����ȼ���¹���Ƿ�������
        if (appxInterstitialAdView.isLoaded()) {
            appxInterstitialAdView.showAd();
        } else {
            Log.i(TAG, "AppX Interstitial Ad is not ready");
            appxInterstitialAdView.loadAd();
        }
    }

    private void initInterstitialAD() {
        // ���������ͼ
        // ����ʱ��ʹ����ȷ��ApiKey�͹��λID
        // �˴�ApiKey���ƹ�λID���ǲ����õ�
        // ������ʽ�ύӦ�õ�ʱ����ȷ�ϴ������Ѿ���Ϊ��Ӧ�ö�Ӧ��Key��ID
        // �����ȡ��������ġ��ٶȿ��������Ľ��滻����Ʒ����.pdf��
        appxInterstitialAdView = new BDInterstitialAd(this,
                "Ri966RBma15K6RDrLMGqLaYt", "mmoSXPO4RL4oIdzQvxjGnCyF");

        // ���ò��������Ϊ������
        appxInterstitialAdView
                .setAdListener(new BDInterstitialAd.InterstitialAdListener() {

                    @Override
                    public void onAdvertisementDataDidLoadFailure() {
                        Log.e(TAG, "load failure");
                    }

                    @Override
                    public void onAdvertisementDataDidLoadSuccess() {
                        Log.e(TAG, "load success");
                        showADs();
                    }

                    @Override
                    public void onAdvertisementViewDidClick() {
                        Log.e(TAG, "on click");
                    }

                    @Override
                    public void onAdvertisementViewDidHide() {
                        Log.e(TAG, "on hide");
                    }

                    @Override
                    public void onAdvertisementViewDidShow() {
                        Log.e(TAG, "on show");
                    }

                    @Override
                    public void onAdvertisementViewWillStartNewIntent() {
                        Log.e(TAG, "leave");
                    }

                });

        // ���ع��
        appxInterstitialAdView.loadAd();
    }

    private void initBannerAD() {
        // ���������ͼ
        // ����ʱ��ʹ����ȷ��ApiKey�͹��λID
        // �˴�ApiKey���ƹ�λID���ǲ����õ�
        // ������ʽ�ύӦ�õ�ʱ����ȷ�ϴ������Ѿ���Ϊ��Ӧ�ö�Ӧ��Key��ID
        // �����ȡ��������ġ��ٶȿ��������Ľ��滻����Ʒ����.pdf��
        bannerAdView = new BDBannerAd(this, "Ri966RBma15K6RDrLMGqLaYt",
                "KQOWeAmPgmpp6ytd4EWOiSR9");

        // ���ú����չʾ�ߴ磬�粻���ã�Ĭ��ΪSIZE_FLEXIBLE;
        bannerAdView.setAdSize(BDBannerAd.SIZE_320X50);

        // ���ú������Ϊ������
        bannerAdView.setAdListener(new BDBannerAd.BannerAdListener() {

            @Override
            public void onAdvertisementDataDidLoadFailure() {
                Log.e(TAG, "load failure");
            }

            @Override
            public void onAdvertisementDataDidLoadSuccess() {
                Log.e(TAG, "load success");
            }

            @Override
            public void onAdvertisementViewDidClick() {
                Log.e(TAG, "on click");
            }

            @Override
            public void onAdvertisementViewDidShow() {
                Log.e(TAG, "on show");
            }

            @Override
            public void onAdvertisementViewWillStartNewIntent() {
                Log.e(TAG, "leave app");
            }
        });

        // �����������
        appxBannerContainer = (RelativeLayout) findViewById(R.id.appx_banner_container);

        // ��ʾ�����ͼ
        appxBannerContainer.addView(bannerAdView);
    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.menu_bd, menu);
    // return true;
    // }
    //
    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    // // Handle action bar item clicks here. The action bar will
    // // automatically handle clicks on the Home/Up button, so long
    // // as you specify a parent activity in AndroidManifest.xml.
    // int id = item.getItemId();
    //
    // //noinspection SimplifiableIfStatement
    // if (id == R.id.action_settings) {
    // return true;
    // }
    //
    // return super.onOptionsItemSelected(item);
    // }
}
