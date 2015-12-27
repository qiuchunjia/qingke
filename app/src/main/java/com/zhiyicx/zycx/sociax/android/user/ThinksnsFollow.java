package com.zhiyicx.zycx.sociax.android.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.adapter.FollowListAdapter;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.FollowList;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;

import android.os.Bundle;
import android.util.Log;

public class ThinksnsFollow extends ThinksnsAbscractActivity {
    private static final String TAG = "FollowList";
    private static User user;
    private static FollowList list;
    private static FollowListAdapter adapter;
    private static final int ADD_FOLLOWED = 0;
    private static final int DEL_FOLLOWED = 1;
    private static int userType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getIntentData().putBoolean("tab", false);
        // 获取list的布局对象
        list = (FollowList) findViewById(R.id.follow_list);
        Thinksns app = (Thinksns) ThinksnsFollow.this.getApplicationContext();
        try {
            if (getIntentData().getString("data") != null) {
                user = new User(new JSONObject(getIntentData()
                        .getString("data")));
            } else if (getIntentData().getInt("uid") != 0) {
                int uid = getIntentData().getInt("uid");
                ;
                user = app.getUserSql().getUser("uid=" + uid);
            } else {
                user = Thinksns.getMy();
            }
            Log.e("user", "user=" + user.toJSON());
        } catch (WeiboDataInvalidException e) {
            ThinksnsFollow.this.finish();
        } catch (JSONException e) {
            ThinksnsFollow.this.finish();
        } catch (DataInvalidException e) {
            ThinksnsFollow.this.finish();
        }
        // 获取数据源
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        userType = getIntentData().getInt("type");
        adapter = new FollowListAdapter(this, data, getIntentData().getInt(
                "type"), user);
        list.setAdapter(adapter, System.currentTimeMillis(), this);
        adapter.loadInitData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.follow;
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new LeftAndRightTitle(this);
    }

    @Override
    public String getTitleCenter() {
        // 修改关注和粉丝显示交叉
        userType = getIntentData().getInt("type");

        if (userType == 1) {
            if (getIntentData().getInt("title") != 0)
                return this.getString(getIntentData().getInt("title"));
            return this.getString(R.string.follow);
        } else {
            if (getIntentData().getInt("title") != 0)
                return this.getString(getIntentData().getInt("title"));
            return this.getString(R.string.followed);
        }
    }

    @Override
    public OnTouchListListener getListView() {
        return list;
    }

    @Override
    public void refreshHeader() {
        adapter.doRefreshHeader();
    }

    @Override
    public void refreshFooter() {
        adapter.doRefreshFooter();
    }

}
