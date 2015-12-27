package com.zhiyicx.zycx.activity;

import qcjlibrary.activity.MsgNotifyPraiseActivity;
import qcjlibrary.activity.RequestWayActivity;
import qcjlibrary.activity.SearchNewActivity;
import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.fragment.FragmentCaseIndex;
import qcjlibrary.fragment.FragmentExperience;
import qcjlibrary.fragment.FragmentIndex;
import qcjlibrary.fragment.FragmentMenu;
import qcjlibrary.fragment.FragmentRequestAnwer;
import qcjlibrary.fragment.FragmentZhixun;
import qcjlibrary.model.base.Model;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.appx.BDInterstitialAd;
import com.nineoldandroids.view.ViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.fragment.QClassFragment;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.net.HttpHelper;

public class HomeActivity extends BaseActivity {
    // private ZiXunFragment mZiXunFgmt; // 咨询fragment qcj
    private FragmentZhixun mZiXunFgmt; // 咨询fragment qcj
    private QClassFragment mQClassFgmt; // 轻课堂fragment qcj
    // private QuestionFragment mQustionFgmt;// 问答fragment qcj
    // private QiKanFragment mQiKanFgmt;// 期刊fragment qcj
    // private WebFragment mWebFgmt;// 微博fragment 这里主要是用的ts3.0来实现的 qcj
    private FragmentCaseIndex mCaseFgmt;
    private FragmentExperience mExpegmt;
    private FragmentRequestAnwer mAnwergmt;
    private FragmentIndex mDefaultFragment; // 新增加的页面
    public static final int index_Default = -1;
    public static final int index_zhixun = 0;
    public static final int index_qclass = 1;
    public static final int index_qustion = 2;
    public static final int index_qikan = 3;
    public static final int index_web = 4;
    private int mCurrentIndex = index_Default; // 当前所处的位置 默认为-1

    private RelativeLayout mZixunLayout, mClassLayout, mQuestionLayout,
            mQikanLayout, mWebLayout;
    private ImageView index_message, IB_home_bottom_class,
            IB_home_bottom_question, IB_home_bottom_qikan, IB_home_bottom_web;

    private BDInterstitialAd appxInterstitialAdView;
    private String TAG = "HomeActivity";
    private DrawerLayout mDrawer;
    private FragmentMenu mMenu;

    private Title mTitle; // 标题

    @Override
    public void initSet() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mApp = (Thinksns) getApplication();
        mApp.setActivity(this);
        mInflater = LayoutInflater.from(getApplicationContext());
        setContentView(R.layout.comom_layout_drawer);
        initParentView();
        // 把内容和title结合
        combineTheLayout();
        initEvents();
        initIntent();
        initView();
        initData();
        initListener();
    }

    private void initEvents() {
        mTitle = getTitleClass();
        if (mTitle != null) {
            mTitle.rl_left_1.setVisibility(View.GONE);
            mTitle.rl_left_2.setVisibility(View.VISIBLE);
        }
        titleSlideMenu(mDrawer);
        mDrawer.setScrimColor(Color.TRANSPARENT); // 去掉滑动的时候阴影
        mDrawer.setDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawer.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }
        });
    }

    /**
     * 初始化父布局，这个本来是父类来实现的，但是由于要更改策略，不得不重新
     */
    private void initParentView() {
        mDrawer = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mLayout = (RelativeLayout) findViewById(R.id.rl_layout);
        mTitlell = (LinearLayout) mLayout.findViewById(R.id.ll_Title);
        mContentll = (FrameLayout) mLayout.findViewById(R.id.ll_content);
        mBottomll = (LinearLayout) mLayout.findViewById(R.id.ll_bottom);

    }

    @Override
    public String setCenterTitle() {
        return "咨询";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mZixunLayout = (RelativeLayout) findViewById(R.id.zixun_layout);
        mClassLayout = (RelativeLayout) findViewById(R.id.class_layout);
        mQikanLayout = (RelativeLayout) findViewById(R.id.qikan_layout);
        mQuestionLayout = (RelativeLayout) findViewById(R.id.question_layout);
        mWebLayout = (RelativeLayout) findViewById(R.id.weibo_layout);

        index_message = (ImageView) findViewById(R.id.index_message);
        IB_home_bottom_class = (ImageView) findViewById(R.id.IB_home_bottom_class);
        IB_home_bottom_question = (ImageView) findViewById(R.id.IB_home_bottom_question);
        IB_home_bottom_qikan = (ImageView) findViewById(R.id.IB_home_bottom_qikan);
        IB_home_bottom_web = (ImageView) findViewById(R.id.IB_home_bottom_web);
    }

    @Override
    public void initData() {
        HttpHelper.setContext(getApplicationContext());
        setTabSelection(mCurrentIndex);
        // 曹立该添加，百度广告，点击 Tab 时第二项时弹出广告
        initBDAD();
    }

    @Override
    public void initListener() {
        mZixunLayout.setOnClickListener(this);
        mClassLayout.setOnClickListener(this);
        mQikanLayout.setOnClickListener(this);
        mQuestionLayout.setOnClickListener(this);
        mWebLayout.setOnClickListener(this);

    }

    /*
     * 曹立该添加，初始化百度广告
     */
    private void initBDAD() {
        // 创建广告视图
        // 发布时请使用正确的ApiKey和广告位ID
        // 此处ApiKey和推广位ID均是测试用的
        // 您在正式提交应用的时候，请确认代码中已经更换为您应用对应的Key和ID
        // 具体获取方法请查阅《百度开发者中心交叉换量产品介绍.pdf》
        appxInterstitialAdView = new BDInterstitialAd(this,
                "T8A7nrKyOEkzFzGqA5zeBABq", "6qI0TX8NSv8Enq74iuNRy0X2");

        // 设置插屏广告行为监听器
        appxInterstitialAdView
                .setAdListener(new BDInterstitialAd.InterstitialAdListener() {

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

        // 加载广告
        appxInterstitialAdView.loadAd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.zixun_layout:
                setTabSelection(index_zhixun);
                index_message.setImageResource(R.drawable.zixun_press);
                break;
            case R.id.class_layout:
                setTabSelection(index_qclass);
                IB_home_bottom_class.setImageResource(R.drawable.qingketang_press);
                // 展示插屏广告前先请先检查下广告是否加载完毕
                // if (appxInterstitialAdView.isLoaded()) {
                // appxInterstitialAdView.showAd();
                // } else {
                // Log.i(TAG, "AppX Interstitial Ad is not ready");
                // appxInterstitialAdView.loadAd();
                // }
                // startActivity(new Intent(HomeActivity.this, BDActivity.class));
                break;
            case R.id.question_layout:
                setTabSelection(index_qustion);
                break;
            case R.id.qikan_layout:
                setTabSelection(index_qikan);
                IB_home_bottom_qikan.setImageResource(R.drawable.jingli_press);
                break;
            case R.id.weibo_layout:
                setTabSelection(index_web);
                IB_home_bottom_web.setImageResource(R.drawable.bingli_press);
                // Intent intent = new Intent(this, WeiboAppActivity.class);
                // startActivity(intent);
                // Anim.in(activity);
                break;
        }
    }

    public void setTabSelection(int index) {

        // 开启一个Fragment事务
        FragmentTransaction transaction = mFManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        setChangeTitle(index);
        resetBottomImage();
        switch (index) {
            case index_Default:
                if (mDefaultFragment == null) {
                    mDefaultFragment = new FragmentIndex();
                    transaction.add(R.id.content, mDefaultFragment);
                } else {
                    transaction.show(mDefaultFragment);
                }
                break;
            case index_zhixun:
                if (mZiXunFgmt == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mZiXunFgmt = new FragmentZhixun();
                    transaction.add(R.id.content, mZiXunFgmt);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mZiXunFgmt);
                }
                // mZixunLayout.setBackgroundResource(R.drawable.foot_pressed);
                break;
            case index_qclass:
                if (mQClassFgmt == null) {
                    mQClassFgmt = new QClassFragment();
                    transaction.add(R.id.content, mQClassFgmt);
                } else {
                    transaction.show(mQClassFgmt);
                }
                // mClassLayout.setBackgroundResource(R.drawable.foot_pressed);
                break;
            case index_qustion:
                if (mAnwergmt == null) {
                    mAnwergmt = new FragmentRequestAnwer();
                    transaction.add(R.id.content, mAnwergmt);
                } else {
                    transaction.show(mAnwergmt);
                }
                // if (mQustionFgmt == null) {
                // mQustionFgmt = new QuestionFragment();
                // transaction.add(R.id.content, mQustionFgmt);
                // } else {
                // transaction.show(mQustionFgmt);
                // }
                // mQuestionLayout.setBackgroundResource(R.drawable.foot_pressed);
                break;
            case index_qikan:
                if (mExpegmt == null) {
                    mExpegmt = new FragmentExperience();
                    transaction.add(R.id.content, mExpegmt);
                } else {
                    transaction.show(mExpegmt);
                }
                // if (mQiKanFgmt == null) {
                // mQiKanFgmt = new QiKanFragment();
                // transaction.add(R.id.content, mQiKanFgmt);
                // } else {
                // transaction.show(mQiKanFgmt);
                // }
                // mQikanLayout.setBackgroundResource(R.drawable.foot_pressed);
                break;
            case index_web:
                if (mCaseFgmt == null) {
                    mCaseFgmt = new FragmentCaseIndex();
                    transaction.add(R.id.content, mCaseFgmt);
                } else {
                    transaction.show(mCaseFgmt);
                }
                // if (mWebFgmt == null) {
                // mWebFgmt = new WebFragment();
                // transaction.add(R.id.content, mWebFgmt);
                // } else {
                // transaction.show(mWebFgmt);
                // }
                // mWebLayout.setBackgroundResource(R.drawable.foot_pressed);
                break;
        }
        transaction.commit();
    }

    /**
     * 根据点击的fragment，设置可变的title，以及title右边需要实现的功能
     *
     * @param index
     */

    private void setChangeTitle(int index) {
        mTitle.iv_title_right1.setVisibility(View.GONE);
        switch (index) {
            case index_Default:
                titleSetCenterTitle("癌友帮");
                mTitle.iv_title_right1.setVisibility(View.VISIBLE);
                mTitle.iv_title_right1.setImageResource(R.drawable.index);
                mTitle.iv_title_right1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mApp.startActivity_qcj(HomeActivity.this,
                                MsgNotifyPraiseActivity.class,
                                sendDataToBundle(new Model(), null));
                    }
                });
                break;
            case index_zhixun:
                titleSetCenterTitle("资讯");
                mTitle.iv_title_right1.setVisibility(View.VISIBLE);
                mTitle.iv_title_right1.setImageResource(R.drawable.searchwhite);
                mTitle.iv_title_right1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mApp.startActivity_qcj(HomeActivity.this,
                                SearchNewActivity.class,
                                sendDataToBundle(new Model(), null));
                    }
                });
                break;
            case index_qclass:
                titleSetCenterTitle("最新");
                mTitle.iv_title_right1.setVisibility(View.VISIBLE);
                mTitle.iv_title_right1.setImageResource(R.drawable.searchwhite);
                mTitle.iv_title_right1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mApp.startActivity_qcj(HomeActivity.this,
                                SearchNewActivity.class,
                                sendDataToBundle(new Model(), null));
                    }
                });
                break;
            case index_qustion:
                titleSetCenterTitle("问答");
                mTitle.iv_title_right1.setVisibility(View.VISIBLE);
                mTitle.iv_title_right1
                        .setImageResource(R.drawable.chuangjianjingli);
                mTitle.iv_title_right1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mApp.startActivity_qcj(HomeActivity.this,
                                RequestWayActivity.class,
                                sendDataToBundle(new Model(), null));
                    }
                });
                break;
            case index_qikan:
                titleSetCenterTitle("经历");
                break;
            case index_web:
                titleSetCenterTitle("病例");
                mTitle.iv_title_right1.setVisibility(View.VISIBLE);
                mTitle.iv_title_right1.setImageResource(R.drawable.searchwhite);
                mTitle.iv_title_right1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mApp.startActivity_qcj(HomeActivity.this,
                                SearchNewActivity.class,
                                sendDataToBundle(new Model(), null));
                    }
                });
                break;
        }

    }

    /**
     * 重置底部image
     */
    private void resetBottomImage() {
        index_message.setImageResource(R.drawable.zixun);
        IB_home_bottom_class.setImageResource(R.drawable.qingketang);
        IB_home_bottom_qikan.setImageResource(R.drawable.jingli);
        IB_home_bottom_web.setImageResource(R.drawable.bingli);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mZiXunFgmt != null) {
            transaction.hide(mZiXunFgmt);
        }
        if (mQClassFgmt != null) {
            transaction.hide(mQClassFgmt);
        }
        if (mAnwergmt != null) {
            transaction.hide(mAnwergmt);
        }
        // if (mQustionFgmt != null) {
        // transaction.hide(mQustionFgmt);
        // }
        if (mExpegmt != null) {
            transaction.hide(mExpegmt);
        }
        // if (mQiKanFgmt != null) {
        // transaction.hide(mQiKanFgmt);
        // }
        if (mCaseFgmt != null)
            transaction.hide(mCaseFgmt);
        // if (mWebFgmt != null)
        // transaction.hide(mWebFgmt);
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
