package com.zhiyicx.zycx.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/31.
 */
public class ListPagerAdapter extends PagerAdapter {

    private String[] mTitles = null;
    private ArrayList<View> mViews = null;

    public ListPagerAdapter(String[] titles, ArrayList<View> views) {
        mTitles = titles;
        mViews = views;
    }

    /*public ListPagerAdapter(String[] titles, ArrayList<ZiXunView> views) {
        mTitles = titles;
        //mViews = views;
        mViews = new ArrayList<View>();
        for (int i = 0; i< views.size(); i++)
        {
            mViews.add(views.get(i).getView());
        }
    }*/


    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}
