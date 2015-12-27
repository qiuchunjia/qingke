package com.zhiyicx.zycx.sociax.android;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.adapter.UserWeiboListAdapter;
import com.zhiyicx.zycx.sociax.adapter.WeiboListAdapter;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.component.WeiboList;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

import android.os.Bundle;
import android.util.Log;

public class ThinksnsUserWeibo extends ThinksnsAbscractActivity {
    private static final String TAG = "Home";

    private static WeiboListAdapter adapter;

    private static WeiboList list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取list的布局对象
        list = (WeiboList) findViewById(R.id.weiboList_home);

        // 获取数据源
        Thinksns app = (Thinksns) this.getApplicationContext();
        Api.StatusesApi api = app.getStatuses();
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        adapter = new UserWeiboListAdapter(this, data, getIntentData().getInt(
                "uid"));
        if (data.size() != 0) {
            list.setAdapter(adapter,
                    (long) adapter.getFirst().getTimestamp() * 1000, this);
        } else {
            list.setAdapter(adapter, System.currentTimeMillis(), this);
        }
        adapter.loadInitData();
        int position = 0;
        Bundle temp = null;
        if ((temp = getIntent().getExtras()) != null) {
            position = temp.getInt("position");
        }
        // list.setSelection(position);
        list.setSelectionFromTop(position, 20);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (Thinksns.getDelIndex() > 0) {
            adapter.deleteItem(Thinksns.getDelIndex());
            adapter.notifyDataSetChanged();
            Thinksns.setDelIndex(0);
            Log.d(TAG,
                    "UserWeibo delete weibo id " + Thinksns.getDelIndex());
        }
    }

    @Override
    public void refreshHeader() {
        adapter.doRefreshHeader();
    }

    @Override
    public void refreshFooter() {
        adapter.doRefreshFooter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.userweibo;
    }

    @Override
    public OnTouchListListener getListView() {
        return list;
    }

    /*
     * @Override public OnClickListener getRightListener() { // TODO
     * Auto-generated method stub return new OnClickListener() {
     *
     * @Override public void onClick(View v) {
     * ThinksnsUserWeibo.this.refreshHeader(); } }; }
     */
    @Override
    protected CustomTitle setCustomTitle() {
        return new LeftAndRightTitle(this);
    }

    @Override
    public String getTitleCenter() {
        if (getIntentData().getInt("title") != 0)
            return this.getString(getIntentData().getInt("title"));
        return getIntentData().getString("uname");
    }

}
