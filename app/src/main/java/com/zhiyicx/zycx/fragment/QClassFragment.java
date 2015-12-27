package com.zhiyicx.zycx.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.ListFragmentAdapter;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.activity.SearchActivity;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.sociax.component.LoadingView;
import com.zhiyicx.zycx.sociax.unit.Anim;
import com.zhiyicx.zycx.util.Utils;
import com.zhiyicx.zycx.widget.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONObject;

public class QClassFragment extends Fragment implements View.OnClickListener {
    final private static String TAG = "QClassFragment";
    private ViewPager mPager;
    private PagerSlidingTabStrip mTabs;
    //private LinearLayout mProgress = null;
    private Activity mContext = null;
    private ArrayList<Fragment> mFragmentList = null;
    private QClassListFragment mCurrentfragment = null;
    private PopupWindow mMenu = null;
    private LinearLayout mBtnSel = null;
    private TextView mTxtStatus = null;
    private ImageView mArrow = null;
    private LoadingView mLoadingView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qclassfragment, container, false);
        //mProgress = (LinearLayout)view.findViewById(R.id.prog_layout);
        mLoadingView = (LoadingView) view.findViewById(LoadingView.ID);
        mPager = (ViewPager) view.findViewById(R.id.vPager);
        mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.sliding_tabs);
        mBtnSel = (LinearLayout) view.findViewById(R.id.btn_sel);
        mTxtStatus = (TextView) view.findViewById(R.id.txt_status);
        mArrow = (ImageView) view.findViewById(R.id.arrow_img);
        view.findViewById(R.id.btn_sel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("type", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                Anim.in(mContext);
            }
        });
        mLoadingView.show(mPager);
        mLoadingView.show(mTabs);
        initTabsData();
        return view;
    }

    private void showMenu(View v) {
        if (mMenu == null) {
            View view = mContext.getLayoutInflater().inflate(R.layout.popmenu, null);
            view.findViewById(R.id.txt_new).setOnClickListener(this);
            view.findViewById(R.id.txt_hot).setOnClickListener(this);
            view.findViewById(R.id.txt_my).setOnClickListener(this);
            mMenu = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        mMenu.setAnimationStyle(R.style.popwin_anim_style);
        mMenu.setFocusable(true);
        mMenu.setOutsideTouchable(true);
        mMenu.update();
        mMenu.setBackgroundDrawable(new BitmapDrawable());
        mMenu.showAsDropDown(v);
        mArrow.setImageResource(R.drawable.arrow_up);
        mMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mArrow.setImageResource(R.drawable.arrow_do);
            }
        });
    }

    private void changeFragmentStatus(int status) {
        if (mFragmentList == null || mFragmentList.isEmpty())
            return;
        QClassListFragment fragment;
        for (int i = 0; i < mFragmentList.size(); i++) {
            fragment = (QClassListFragment) mFragmentList.get(i);
            fragment.setStatus(status);
        }
        mCurrentfragment.loadNewData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_new:
                mTxtStatus.setText("最新");
                changeFragmentStatus(0);
                break;
            case R.id.txt_hot:
                mTxtStatus.setText("最热");
                changeFragmentStatus(1);
                break;
            case R.id.txt_my:
                mTxtStatus.setText("我的");
                changeFragmentStatus(2);
                break;
        }
        mMenu.dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    private void initTabsData() {
        String url = MyConfig.QCLASS_LIST_URL + Utils.getTokenString(mContext) + "&cid=3";
        NetComTools.getInstance(mContext).getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "QCLASS table Data:" + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = (JSONObject) jsonObject.get("data");
                        JSONArray categoryArray = data.getJSONArray("category");
                        initPager(categoryArray);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "QCLASS tables error, " + error);
            }
        });
    }

    private void initPager(JSONArray array) {
        String titles[] = new String[array.length() + 1];
        mFragmentList = new ArrayList<Fragment>();
        mCurrentfragment = QClassListFragment.newInstanse(0);
        mCurrentfragment.setFinishLoadListener(new BaseListFragment.FinishLoadListener() {
            @Override
            public void OnFinish() {
                mLoadingView.hide(mPager);
                mLoadingView.hide(mTabs);
            }
        });
        titles[0] = "推荐";
        mFragmentList.add(mCurrentfragment);
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) array.get(i);
                String title = jsonObject1.get("imooc_class_name").toString();
                int id = jsonObject1.getInt("class_id");
                titles[i + 1] = title;
                mFragmentList.add(QClassListFragment.newInstanse(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListFragmentAdapter adapter = new ListFragmentAdapter(
                QClassFragment.this.getChildFragmentManager(), mFragmentList, titles);
        mPager.setAdapter(adapter);
        mTabs.setOnPageChangeListener(new MyOnPageChangeListener());
        mTabs.setViewPager(mPager);
        setTabsValue();
        mPager.setCurrentItem(0, false);
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
                TypedValue.COMPLEX_UNIT_SP, 14, dm));
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
            if (mFragmentList != null && !mFragmentList.isEmpty()) {
                mCurrentfragment = (QClassListFragment) mFragmentList.get(arg0);
                mCurrentfragment.doRefreshHeader();
            }
        }
    }
}
