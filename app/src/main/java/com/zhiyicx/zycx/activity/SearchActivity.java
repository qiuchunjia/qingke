package com.zhiyicx.zycx.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.fragment.QClassSearchFragment;
import com.zhiyicx.zycx.fragment.QuestionSearchFragment;
import com.zhiyicx.zycx.fragment.WebSearchFragment;
import com.zhiyicx.zycx.fragment.ZiXunSearchFragment;
import com.zhiyicx.zycx.sociax.unit.SociaxUIUtils;
import com.zhiyicx.zycx.util.Utils;

/**
 * Created by Administrator on 2015/1/17.
 */
public class SearchActivity extends FragmentActivity implements
        View.OnClickListener {

    private Button mSearchBtn = null;
    private ImageView mWeiboBtn, mKetangBtn, mZixunBtn, mWendaBtn;
    private EditText mKeyEdit = null;
    private int mIndex = 0;
    private WebSearchFragment webSearchFragment = null;
    private QClassSearchFragment qClassSearchFragment = null;
    private ZiXunSearchFragment ziXunSearchFragment = null;
    private QuestionSearchFragment questionSearchFragment = null;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_layout);
        fragmentManager = getSupportFragmentManager();

        mSearchBtn = (Button) findViewById(R.id.btn_search);
        mWeiboBtn = (ImageView) findViewById(R.id.btn_weibo);
        mKetangBtn = (ImageView) findViewById(R.id.btn_ketang);
        mZixunBtn = (ImageView) findViewById(R.id.btn_zixun);
        mWendaBtn = (ImageView) findViewById(R.id.btn_wenda);
        mKeyEdit = (EditText) findViewById(R.id.key_edit);

        mSearchBtn.setOnClickListener(this);
        mWeiboBtn.setOnClickListener(this);
        mKetangBtn.setOnClickListener(this);
        mZixunBtn.setOnClickListener(this);
        mWendaBtn.setOnClickListener(this);
        mIndex = getIntent().getIntExtra("type", 0);
        webSearchFragment = new WebSearchFragment();
        qClassSearchFragment = new QClassSearchFragment();
        ziXunSearchFragment = new ZiXunSearchFragment();
        questionSearchFragment = new QuestionSearchFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_layout, webSearchFragment);
        transaction.add(R.id.content_layout, qClassSearchFragment);
        transaction.add(R.id.content_layout, ziXunSearchFragment);
        transaction.add(R.id.content_layout, questionSearchFragment);
        transaction.commit();
        select(mIndex);
    }

    private void search(String key) {
        if (TextUtils.isEmpty(key))
            Utils.showToast(this, "请输入关键字!");
        SociaxUIUtils.hideSoftKeyboard(this, mKeyEdit);
        switch (mIndex) {
            case 0:
                webSearchFragment.doSearch(key);
                break;
            case 1:
                qClassSearchFragment.doSearch(key);
                break;
            case 2:
                ziXunSearchFragment.doSearch(key);
                break;
            case 3:
                questionSearchFragment.doSearch(key);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String key = mKeyEdit.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_search:
                search(key);
                break;
            case R.id.btn_weibo:
                select(0);
                if (!TextUtils.isEmpty(key))
                    webSearchFragment.doSearch(key);
                break;
            case R.id.btn_ketang:
                select(1);
                if (!TextUtils.isEmpty(key))
                    qClassSearchFragment.doSearch(key);
                break;
            case R.id.btn_zixun:
                select(2);
                if (!TextUtils.isEmpty(key))
                    ziXunSearchFragment.doSearch(key);
                break;
            case R.id.btn_wenda:
                select(3);
                if (!TextUtils.isEmpty(key))
                    questionSearchFragment.doSearch(key);
                break;
        }
    }

    private void select(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        mWeiboBtn.setImageResource(R.drawable.s_weibo);
        mKetangBtn.setImageResource(R.drawable.s_ketang);
        mZixunBtn.setImageResource(R.drawable.s_zixun);
        mWendaBtn.setImageResource(R.drawable.s_wenda);
        mIndex = i;
        switch (mIndex) {
            case 0:
                if (webSearchFragment == null) {
                    webSearchFragment = new WebSearchFragment();
                    transaction.add(R.id.content_layout, webSearchFragment);
                } else {
                    transaction.show(webSearchFragment);
                }
                mWeiboBtn.setImageResource(R.drawable.s_weibo_pressed);
                break;
            case 1:
                if (qClassSearchFragment == null) {
                    qClassSearchFragment = new QClassSearchFragment();
                    transaction.add(R.id.content_layout, qClassSearchFragment);
                } else {
                    transaction.show(qClassSearchFragment);
                }
                mKetangBtn.setImageResource(R.drawable.s_ketang_pressed);
                break;
            case 2:
                if (ziXunSearchFragment == null) {
                    ziXunSearchFragment = new ZiXunSearchFragment();
                    transaction.add(R.id.content_layout, ziXunSearchFragment);
                } else
                    transaction.show(ziXunSearchFragment);
                mZixunBtn.setImageResource(R.drawable.s_zixun_pressed);
                break;
            case 3:
                if (questionSearchFragment == null) {
                    questionSearchFragment = new QuestionSearchFragment();
                    transaction.add(R.id.content_layout, questionSearchFragment);
                } else
                    transaction.show(questionSearchFragment);
                mWendaBtn.setImageResource(R.drawable.s_wenda_pressed);
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (webSearchFragment != null) {
            transaction.hide(webSearchFragment);
        }
        if (qClassSearchFragment != null) {
            transaction.hide(qClassSearchFragment);
        }
        if (ziXunSearchFragment != null)
            transaction.hide(ziXunSearchFragment);

        if (questionSearchFragment != null)
            transaction.hide(questionSearchFragment);
    }
}
