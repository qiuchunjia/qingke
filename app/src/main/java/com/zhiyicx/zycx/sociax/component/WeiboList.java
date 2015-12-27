package com.zhiyicx.zycx.sociax.component;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.adapter.WeiboListAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboContentList;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.Anim;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WeiboList extends SociaxList {

    private static final String TAG = "WeiboList";

    public WeiboList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiboList(Context context) {
        super(context);
    }

    @Override
    protected void onClick(View view, int position, long id) {

        if (view.getId() == R.id.footer_content) {
            ImageView iv = (ImageView) view.findViewById(R.id.anim_view);
            iv.setVisibility(View.VISIBLE);
            Anim.refresh(getContext(), iv);

            HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter) this
                    .getAdapter();
            WeiboListAdapter weiboLisAdapter = (WeiboListAdapter) headerAdapter
                    .getWrappedAdapter();
            weiboLisAdapter.animView = iv;
            weiboLisAdapter.doRefreshFooter();
        } else {
            Log.d(TAG, "get Weibolist item click position : " + position);
            LinearLayout layout = (LinearLayout) view
                    .findViewById(R.id.weibo_data);
            Weibo weiboData = (Weibo) layout.getTag();
            Bundle data = new Bundle();
            if (weiboData.getTempJsonString() != null) {
                data.putString("data", weiboData.getTempJsonString());
            } else {
                data.putString("data", weiboData.toJSON());
            }
            data.putInt("position", getLastPosition());
            data.putInt("this_position", position);
            Thinksns app = (Thinksns) getContext().getApplicationContext();

            app.startActivity(getActivityObj(), WeiboContentList.class, data);
            // app.startActivityForResult(getActivityObj(),
            // ThinksnsWeiboContent.class, data);
        }
    }

}
