package com.zhiyicx.zycx.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.ListFragmentAdapter;
import com.zhiyicx.zycx.activity.HomeActivity;
import com.zhiyicx.zycx.activity.SearchActivity;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboCreateActivity;
import com.zhiyicx.zycx.sociax.component.LoadingView;
import com.zhiyicx.zycx.sociax.unit.Anim;
import com.zhiyicx.zycx.widget.PagerSlidingTabStrip;

public class WebFragment extends Fragment {
    final private static String TAG = "WebFragment";
    private ViewPager mPager;
    private ArrayList<Fragment> mFragmentsList;
    private PagerSlidingTabStrip mTabs;
    private HomeActivity mContext = null;

    private WebListFragment allListFragment;
    private WebAtomFragment atomFragment;
    private WebCommentFragment commentFragment;
    private WebCollectFragment collectFragment;
    LoadingView mLoadingView;
    private View mCustView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mCustView == null)
            mCustView = inflater.inflate(R.layout.web_fragment, null);
        mPager = (ViewPager) mCustView.findViewById(R.id.vPager);
        mTabs = (PagerSlidingTabStrip) mCustView.findViewById(R.id.sliding_tabs);
        mLoadingView = (LoadingView) mCustView.findViewById(LoadingView.ID);
        mCustView.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thinksns app = (Thinksns) mContext.getApplicationContext();
                app.startActivity(mContext, WeiboCreateActivity.class, null);
            }
        });
        mCustView.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("type", 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                Anim.in(mContext);
            }
        });
        mLoadingView.show(mPager);
        InitViewPager();
        return mCustView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (HomeActivity) activity;
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
        allListFragment.setFinishLoadListener(new BaseListFragment.FinishLoadListener() {
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
        mPager.setAdapter(new ListFragmentAdapter(getChildFragmentManager(), mFragmentsList, titles));
        mTabs.setViewPager(mPager);
        mTabs.setOnPageChangeListener(new MyOnPageChangeListener());
        setTabsValue();
        //mPager.setCurrentItem(0, false);
        //allListFragment.loadData(false);
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
            BaseListFragment fragment = (BaseListFragment) mFragmentsList.get(arg0);
            //fragment.loadData(false);
            fragment.doRefreshHeader();
        }
    }

}
