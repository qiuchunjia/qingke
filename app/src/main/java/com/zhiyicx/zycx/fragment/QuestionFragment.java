package com.zhiyicx.zycx.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.activity.PostQuestionActivity;
import com.zhiyicx.zycx.activity.QuestionActivity;
import com.zhiyicx.zycx.activity.SearchActivity;
import com.zhiyicx.zycx.sociax.unit.Anim;

public class QuestionFragment extends Fragment implements OnClickListener {
    private QuestionListFragment mQustionListFgmt = null;
    private QuestionCategoryFragment mCategoryFgmt = null;
    private Context mContext;
    private boolean mShowCategory = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionfragment, null);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
       /* FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if(mQustionListFgmt == null)
            mQustionListFgmt = QuestionListFragment.newInstance(0, false);
        transaction.add(R.id.list_view, mQustionListFgmt);
        transaction.show(mQustionListFgmt);
        transaction.commit();*/
    }

    private void initView(View view) {
        view.findViewById(R.id.txt_mores).setOnClickListener(this);
        view.findViewById(R.id.view_search).setOnClickListener(this);
        view.findViewById(R.id.view_cate).setOnClickListener(this);
        view.findViewById(R.id.view_myask).setOnClickListener(this);
        view.findViewById(R.id.view_myquestion).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 搜索问答
            case R.id.view_search:
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("type", 3);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                Anim.in((Activity) mContext);
                break;
            // 癌症分类
            case R.id.view_cate:
                // 显示癌症分类菜单
                showCancerClassification(!mShowCategory);
                break;

            // 我要提问
            case R.id.view_myask:
                Intent intent1 = new Intent(getActivity(), PostQuestionActivity.class);
                //intent.putExtra("id", id);
                //intent.putExtra("title", title);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent1);
                break;

            // 我的提问
            case R.id.view_myquestion:
                QuestionActivity.show(getActivity(), -1, false, "我的提问");
                break;
            case R.id.txt_mores:
                QuestionActivity.show(getActivity(), 0, false, "问题列表");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showCancerClassification(false);
    }

    // 显示癌症分类菜单
    private void showCancerClassification(boolean en) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (en) {
            if (mQustionListFgmt != null) {
                transaction.hide(mQustionListFgmt);
            }
            if (mCategoryFgmt == null) {
                mCategoryFgmt = new QuestionCategoryFragment();
                mCategoryFgmt.setSelectListener(new QuestionCategoryFragment.SelectListener() {
                    @Override
                    public void OnSelect(int id, String title) {
                        QuestionActivity.show(getActivity(), id, false, title);
                    }
                });
                transaction.add(R.id.list_view, mCategoryFgmt);
            }
            mShowCategory = true;
            transaction.show(mCategoryFgmt);
        } else {
            mShowCategory = false;
            if (mCategoryFgmt != null)
                transaction.hide(mCategoryFgmt);
            if (mQustionListFgmt == null) {
                mQustionListFgmt = QuestionListFragment.newInstance(0, false);
                transaction.add(R.id.list_view, mQustionListFgmt);
            }
            transaction.show(mQustionListFgmt);
        }
        transaction.commit();
    }

}
