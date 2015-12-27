package com.zhiyicx.zycx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/27.
 */
public class ListFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragmentList;
    // private Context mContext;
    private String[] mTitles;

    public ListFragmentAdapter(FragmentManager fm,
                               ArrayList<Fragment> fragmentArrayList, String[] titles) {
        super(fm);
        mFragmentList = fragmentArrayList;
        // mContext = context;
        mTitles = titles;
    }

    public void setFragmentList(ArrayList<Fragment> list) {
        mFragmentList = list;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
        View view = f.getView();
        if (view != null)
            container.addView(view);
        return f;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = mFragmentList.get(position).getView();
        if (view != null)
            container.removeView(view);
    }

}
