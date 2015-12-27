package com.zhiyicx.zycx.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * FragmentPagerAdapter
 *
 * @author Mr . H
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;

    }

    public int getCount() {
        return list.size();
    }

    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }
}
