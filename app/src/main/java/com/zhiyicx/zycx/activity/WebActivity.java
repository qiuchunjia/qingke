package com.zhiyicx.zycx.activity;

import java.util.ArrayList;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.ListFragmentAdapter;
import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.fragment.WebAtomFragment;
import com.zhiyicx.zycx.fragment.WebCollectFragment;
import com.zhiyicx.zycx.fragment.WebCommentFragment;
import com.zhiyicx.zycx.fragment.WebListFragment;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboCreateActivity;
import com.zhiyicx.zycx.sociax.component.LoadingView;
import com.zhiyicx.zycx.widget.PagerSlidingTabStrip;

public class WebActivity extends BaseActivity {
    final private static String TAG = "WebActivity";
    private ViewPager mPager;
    private ArrayList<Fragment> mFragmentsList;
    private PagerSlidingTabStrip mTabs;

    private WebListFragment allListFragment;
    private WebAtomFragment atomFragment;
    private WebCommentFragment commentFragment;
    private WebCollectFragment collectFragment;
    LoadingView mLoadingView;
    private View mCustView = null;

    @Override
    public String setCenterTitle() {
        return "我的圈子";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.web_fragment;
    }

    @Override
    public void initView() {
        titleSetRightImage(R.drawable.chuangjianjingli);
        mPager = (ViewPager) findViewById(R.id.vPager);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.sliding_tabs);
        mLoadingView = (LoadingView) findViewById(LoadingView.ID);
        mLoadingView.show(mPager);
        InitViewPager();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        Title title = getTitleClass();
        title.iv_title_right1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApp.startActivity(WebActivity.this, WeiboCreateActivity.class,
                        null);
            }
        });
        // findViewById(R.id.btn_send).setOnClickListener(
        // new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // mApp.startActivity(WebActivity.this,
        // WeiboCreateActivity.class, null);
        // }
        // });
        // findViewById(R.id.btn_search).setOnClickListener(
        // new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // Intent intent = new Intent(WebActivity.this,
        // SearchActivity.class);
        // intent.putExtra("type", 0);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
        // | Intent.FLAG_ACTIVITY_NEW_TASK);
        // WebActivity.this.startActivity(intent);
        // Anim.in(WebActivity.this);
        // }
        // });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (allListFragment != null)
            allListFragment.doRefreshHeader();
    }

    private void InitViewPager() {
        mFragmentsList = new ArrayList<Fragment>();
        allListFragment = new WebListFragment();
        allListFragment
                .setFinishLoadListener(new BaseListFragment.FinishLoadListener() {
                    @Override
                    public void OnFinish() {
                        mLoadingView.hide(mPager);
                    }
                });
        atomFragment = new WebAtomFragment();
        commentFragment = new WebCommentFragment();
        collectFragment = new WebCollectFragment();
        mFragmentsList.add(allListFragment);
        mFragmentsList.add(atomFragment);
        mFragmentsList.add(commentFragment);
        mFragmentsList.add(collectFragment);

        String[] titles = new String[]{"首页", "@我", "评论", "收藏"};
        mPager.setAdapter(new ListFragmentAdapter(getSupportFragmentManager(),
                mFragmentsList, titles));
        mTabs.setViewPager(mPager);
        mTabs.setOnPageChangeListener(new MyOnPageChangeListener());
        setTabsValue();
        // mPager.setCurrentItem(0, false);
        // allListFragment.loadData(false);
    }

    private void setTabsValue() {
        mTabs.setShouldExpand(true);
        mTabs.setDividerColor(Color.TRANSPARENT);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mTabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        mTabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        mTabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        mTabs.setIndicatorColor(Color.parseColor("#45c01a"));
        mTabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        mTabs.setTabBackground(0);
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int arg0) {
            BaseListFragment fragment = (BaseListFragment) mFragmentsList
                    .get(arg0);
            // fragment.loadData(false);
            fragment.doRefreshHeader();
        }
    }

}
