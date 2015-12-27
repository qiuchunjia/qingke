package qcjlibrary.adapter;

import java.util.List;

import qcjlibrary.fragment.FragmentZiXunList;
import qcjlibrary.model.ModelZiXunCategory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<ModelZiXunCategory> mList;

    public FragmentAdapter(FragmentManager fm, List<ModelZiXunCategory> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        if (mList != null) {
            FragmentZiXunList f = new FragmentZiXunList();
            Bundle bundle = new Bundle();
            bundle.putInt("type", mList.get(position).getFenlei_id());
            f.setArguments(bundle);
            return f;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mList != null) {
            return mList.get(position).getTitle().trim();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}