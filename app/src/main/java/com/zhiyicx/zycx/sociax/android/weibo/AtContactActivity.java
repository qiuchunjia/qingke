package com.zhiyicx.zycx.sociax.android.weibo;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.zhiyicx.zycx.sociax.adapter.AtContactAdapter;
import com.zhiyicx.zycx.sociax.adapter.UserListAdapter;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.R.id;
import com.zhiyicx.zycx.R.layout;
import com.zhiyicx.zycx.R.string;
import com.zhiyicx.zycx.sociax.component.AtContactList;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.EditCancel;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

/**
 * 类说明：
 *
 * @version 1.0
 * @date Jan 18, 2013
 */
public class AtContactActivity extends ThinksnsAbscractActivity implements
        OnKeyListener, OnCheckedChangeListener {

    private AtContactList seUserList;
    private UserListAdapter usListAdapter;
    private EditCancel edit;
    private LinearLayout layout;
    private Button goForSearch;

    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getIntent().getStringExtra("press_button");
        init();
        loadNewData(null);

    }

    private void init() {
        seUserList = (AtContactList) findViewById(R.id.find_userList);
        edit = (EditCancel) findViewById(R.id.editCancel1);
        layout = (LinearLayout) findViewById(R.id.search_layout);
        goForSearch = (Button) findViewById(R.id.go_for_search);

        edit.setOnKeyListener(this);

        goForSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏输入法
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);

                doSearch();
            }
        });
    }

    private void doSearch() {

        String key = edit.getText().toString().trim();
        if (key.length() <= 0) {
            Toast.makeText(getApplicationContext(), R.string.input_key,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        loadNewData(key);
    }

    private void loadNewData(String key) {
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        usListAdapter = new AtContactAdapter(AtContactActivity.this, data, key);
        seUserList.setAdapter(usListAdapter, System.currentTimeMillis(),
                AtContactActivity.this);
        usListAdapter.loadInitData();
    }

    // /////////************ system method
    // ********//////////////////////////////
    @Override
    public String getTitleCenter() {
        return getString(R.string.recent);
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new LeftAndRightTitle(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.at_contact_list;
    }

    @Override
    public OnTouchListListener getListView() {
        return seUserList;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event != null
                && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_ENVELOPE)
                && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_UP) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
            doSearch();
            return true;
        }
        return false;
    }

}
