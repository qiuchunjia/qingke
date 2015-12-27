package com.zhiyicx.zycx.sociax.android.weibo;

import android.os.Bundle;

import com.zhiyicx.zycx.sociax.adapter.TopicAdapter;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.R.id;
import com.zhiyicx.zycx.R.layout;
import com.zhiyicx.zycx.R.string;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.component.TopicList;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

/**
 * 类说明：
 *
 * @version 1.0
 * @date Jan 18, 2013
 */
public class AtTopicActivity extends ThinksnsAbscractActivity {

    private TopicList mTopicList;
    private TopicAdapter mTopicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopicList = (TopicList) findViewById(R.id.topic_list);

        ListData<SociaxItem> data = new ListData<SociaxItem>();
        mTopicAdapter = new TopicAdapter(AtTopicActivity.this, data);
        mTopicList.setAdapter(mTopicAdapter, System.currentTimeMillis(),
                AtTopicActivity.this);
        mTopicAdapter.loadInitData();
    }

    @Override
    public OnTouchListListener getListView() {
        return mTopicList;
    }

    @Override
    public String getTitleCenter() {
        return getString(R.string.topic_title);
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new LeftAndRightTitle(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.topic_list;
    }

}
