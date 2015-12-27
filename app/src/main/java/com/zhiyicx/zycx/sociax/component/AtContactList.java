package com.zhiyicx.zycx.sociax.component;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.adapter.UserListAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.android.weibo.AtContactActivity;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboSendActivity;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.unit.Anim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date Jan 18, 2013
 */
public class AtContactList extends SociaxList {

    private Context mContext;

    public AtContactList(Context context) {
        super(context);
        mContext = context;
    }

    public AtContactList(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onClick(View view, int position, long id) {
        if (view.getId() == R.id.footer_content) {
            ImageView iv = (ImageView) view.findViewById(R.id.anim_view);
            iv.setVisibility(View.VISIBLE);
            Anim.refresh(getContext(), iv);

            HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter) this
                    .getAdapter();
            UserListAdapter weiboLisAdapter = (UserListAdapter) headerAdapter
                    .getWrappedAdapter();
            weiboLisAdapter.animView = iv;
            weiboLisAdapter.doRefreshFooter();
        } else if (getActivityObj().getClass() == AtContactActivity.class) {
            User user = (User) getItemAtPosition(position);
            Intent i = new Intent();
            Bundle b = new Bundle();
            b.putString("at_name", user.getUserName());
            i.putExtras(b);
            AtContactActivity at = (AtContactActivity) getActivityObj();
            at.setIntent(i);
            at.setResult(Activity.RESULT_OK, i);
            at.finish();
        } else {
            User user = (User) this.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putInt("to_uid", user.getUid());
            bundle.putInt("send_type", ThinksnsAbscractActivity.CREATE_MESSAGE);
            Thinksns app = (Thinksns) getContext().getApplicationContext();
            app.startActivity(getActivityObj(), WeiboSendActivity.class, bundle);
        }
    }

    @Override
    protected void addHeaderView() {
    }

}
