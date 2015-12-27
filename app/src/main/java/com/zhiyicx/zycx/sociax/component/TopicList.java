package com.zhiyicx.zycx.sociax.component;

import com.zhiyicx.zycx.sociax.adapter.UserListAdapter;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.weibo.AtTopicActivity;
import com.zhiyicx.zycx.sociax.modle.RecentTopic;
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
public class TopicList extends SociaxList {

    private Context mContext;

    public TopicList(Context context) {
        super(context);
        mContext = context;
    }

    public TopicList(Context context, AttributeSet attrs) {
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
        } else {
            RecentTopic reTopic = (RecentTopic) getItemAtPosition(position);
            Intent i = new Intent();
            Bundle b = new Bundle();
            b.putString("recent_topic", reTopic.getName());
            i.putExtras(b);
            AtTopicActivity at = (AtTopicActivity) getActivityObj();
            at.setIntent(i);
            at.setResult(Activity.RESULT_OK, i);
            at.finish();
        }
    }

    @Override
    protected void addHeaderView() {
    }

}
