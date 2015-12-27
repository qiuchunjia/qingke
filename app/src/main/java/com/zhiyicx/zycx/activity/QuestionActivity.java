package com.zhiyicx.zycx.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.fragment.QuestionListFragment;
import com.zhiyicx.zycx.sociax.unit.Anim;

/**
 * Created by Administrator on 2015/1/6.
 */
public class QuestionActivity extends FragmentActivity {


    private QuestionListFragment mQustionListFgmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.question_layout);
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView title = (TextView) findViewById(R.id.txt_title);
        String txt = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(txt))
            title.setText(txt);
        int type = getIntent().getIntExtra("id", 0);
        boolean topic = getIntent().getBooleanExtra("topic", false);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mQustionListFgmt = QuestionListFragment.newInstance(type, topic);
        transaction.add(R.id.list_layout, mQustionListFgmt);
        transaction.show(mQustionListFgmt);
        transaction.commit();
    }

    static public void show(Context context, int id, boolean topic, String title) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("topic", topic);
        intent.putExtra("title", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Anim.in((Activity) context);
    }
}
