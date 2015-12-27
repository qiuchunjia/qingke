package com.zhiyicx.zycx.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.zhiyicx.zycx.LoginActivity;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.net.HttpHelper;
import com.zhiyicx.zycx.sociax.unit.Anim;
import com.zhiyicx.zycx.util.PreferenceUtil;

/**
 * @Author: fyang
 * @Date: 14-1-20
 */

public class GuideActivity extends Activity {

    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private static ThinksnsAbscractActivity activity;
    private static Worker initThread = null;
    protected static ActivityHandler handler = null;
    protected static int INIT_OK = 0;
    private PreferenceUtil preferenceUtil = null;

    private ArrayList<View> pageViews;

    private ViewPager mViewPager;

    private View.OnClickListener mCloseListenser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            preferenceUtil.saveBoolean("first_launch", false);
            Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            GuideActivity.this.startActivity(intent);
            Anim.in(GuideActivity.this);
            GuideActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        //getActionBar().hide();
        preferenceUtil = PreferenceUtil.getInstance(this);
        initApp();
        HttpHelper.setContext(getApplicationContext());
/*
        if(!Build.FINGERPRINT.contains("Meizu"))
        {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/

        /*mViewPager.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(pageViews.get(2).equals(v))
                {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });*/
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
                Thinksns app = (Thinksns) GuideActivity.this.getApplicationContext();
                app.initApi();
                Intent intent;
                if (app.HasLoginUser()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    intent = new Intent(GuideActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    GuideActivity.this.startActivity(intent);
                    initThread.quit();
                    Anim.in(GuideActivity.this);
                    GuideActivity.this.finish();
                } else {
                    Bundle data = new Bundle();
                    try {
                        Api.Status status = app.initOauth();
                        if (status == Api.Status.RESULT_ERROR) {
                            data.putBoolean("status", false);
                            data.putString("message",
                                    GuideActivity.this.getResources().getString(R.string.request_key_error));
                        } else {
                            data.putBoolean("status", true);
                        }
                    } catch (ApiException e1) {
                        data.putBoolean("status", false);
                        data.putString("message", e1.getMessage());
                    }

                    if (!preferenceUtil.getBoolean("first_launch", true)) {
                        intent = new Intent(GuideActivity.this, LoginActivity.class);
                        intent.putExtras(data);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        GuideActivity.this.startActivity(intent);
                        initThread.quit();
                        Anim.in(GuideActivity.this);
                        GuideActivity.this.finish();
                    } else {
                        GuideActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                LayoutInflater inflater = getLayoutInflater();
                                pageViews = new ArrayList<View>();
                                pageViews.add(inflater.inflate(R.layout.guide_page1, null));
                                pageViews.add(inflater.inflate(R.layout.guide_page2, null));
                                pageViews.add(inflater.inflate(R.layout.guide_page3, null));
                                mViewPager = (ViewPager) findViewById(R.id.guide_pages);
                                mViewPager.setAdapter(new GuidePageAdapter(GuideActivity.this, pageViews, mCloseListenser));
                                findViewById(R.id.welcome_layout).setVisibility(View.GONE);
                            }
                        });
                        initThread.quit();
                    }
                }
            }
        }
    }
}


class GuidePageAdapter extends PagerAdapter {

    private ArrayList<View> mPages;
    private View.OnClickListener mListener;
    private AssetFileDescriptor mDescripter;
    private Context mContext;

    public GuidePageAdapter(Context ctx, ArrayList<View> pages, View.OnClickListener listener) {
        super();
        mPages = pages;
        mListener = listener;
        mContext = ctx;
    }

    //销毁position位置的界面
    @Override
    public void destroyItem(View v, int position, Object arg2) {
        // TODO Auto-generated method stub
        ((ViewPager) v).removeView(mPages.get(position));
    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mPages.size();
    }

    @Override
    public Object instantiateItem(View v, int position) {
        // TODO Auto-generated method stub
        ((ViewPager) v).addView(mPages.get(position));
        ImageButton btn;
        //SVideoView sVideoView;
        if (position == 0) {
            btn = (ImageButton) v.findViewById(R.id.btn_close_guide1);
        } else if (position == 1) {
            btn = (ImageButton) v.findViewById(R.id.btn_close_guide2);
        } else
            btn = (ImageButton) v.findViewById(R.id.btn_close_guide3);
        btn.setOnClickListener(mListener);
        return mPages.get(position);
    }

    @Override
    public boolean isViewFromObject(View v, Object arg1) {
        // TODO Auto-generated method stub
        return v == arg1;
    }

    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return super.getItemPosition(object);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

}
