package com.zhiyicx.zycx.sociax.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyicx.zycx.sociax.adapter.AtContactAdapter.ViewHodler;
import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.RecentTopic;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

public class TopicAdapter extends SociaxListAdapter {

    private Context mContext;

    public TopicAdapter(ThinksnsAbscractActivity context,
                        ListData<SociaxItem> data) {
        super(context, data);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = View
                    .inflate(mContext, R.layout.topic_list_item, null);
            viewHodler.textView = (TextView) convertView
                    .findViewById(R.id.tv_name);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        RecentTopic reTopic = (RecentTopic) getItem(position);
        viewHodler.textView.setText("#" + reTopic.getName() + "#");

        return convertView;
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return null;
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return null;
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return thread.getApp().getUsers().getRecentTopic();
    }

}
