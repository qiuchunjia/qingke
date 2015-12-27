package com.zhiyicx.zycx.sociax.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.TimeIsOutFriendly;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.ReceiveComment;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.unit.TimeHelper;

/**
 * 我发出的评论
 */
public class CommentSendByMeAdapter extends CommentMyListAdapter {

    public CommentSendByMeAdapter(ThinksnsAbscractActivity context,
                                  ListData<SociaxItem> list) {
        super(context, list);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentMyListItem commentMyListItem = null;
        if (convertView == null) {
            commentMyListItem = new CommentMyListItem();
            convertView = this.inflater.inflate(
                    R.layout.comment_send_by_my_item, null);
            commentMyListItem.userheader = (ImageView) convertView
                    .findViewById(R.id.user_comment_header);
            commentMyListItem.username = (TextView) convertView
                    .findViewById(R.id.user_name);
            commentMyListItem.time = (TextView) convertView
                    .findViewById(R.id.comment_ctime);
            commentMyListItem.content = (TextView) convertView
                    .findViewById(R.id.comment_content);
            commentMyListItem.myComment = (TextView) convertView
                    .findViewById(R.id.comment_receive_me);
            convertView.setTag(commentMyListItem);
        } else {
            commentMyListItem = (CommentMyListItem) convertView.getTag();
        }
        LinearLayout layout = (LinearLayout) convertView
                .findViewById(R.id.weibo_data);

        ReceiveComment comment = this.getItem(position);

        this.loadingHeader(comment.getHeadUrl(), commentMyListItem.userheader);

        layout.setTag(comment.getStatus());
        commentMyListItem.username.setText(comment.getUname());
        try {
            commentMyListItem.time.setText(TimeHelper.friendlyTime(comment
                    .getTimestemp()));
        } catch (TimeIsOutFriendly e) {
            commentMyListItem.time.setText(TimeHelper
                    .getStandardTimeWithDate(comment.getTimestemp()));
        }

        commentMyListItem.content.setText(comment.getContent());
        if (comment.isNullForReplyComment()) {
            commentMyListItem.myComment.setText("回复"
                    + comment.getStatus().getUsername() + "的微博:"
                    + comment.getStatus().getContent());
        } else {
            commentMyListItem.myComment.setText("回复我的评论:"
                    + comment.getReplyComment().getContent());
        }
        return convertView;
    }

	/*
     * CommentMyListItem commentMyListItem = new CommentMyListItem();
	 * if(convertView ==null){ convertView =
	 * this.inflater.inflate(R.layout.comment_my_list, null); } boolean
	 * canRefresh = false; if(refresh == null){ canRefresh = true; }else{
	 * canRefresh = refresh.isClickable(); } if(position == this.list.size()-1
	 * && canRefresh){ //doRefreshFooter(); }
	 */

    private void loadingHeader(String url, ImageView userheader) {
        if (url != null)
            dowloaderTask(url, userheader, BitmapDownloaderTask.Type.FACE);
    }

    @Override
    protected void dowloaderTask(String url, ImageView image,
                                 BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }

    // /////////////*****************************///
    // /////////////////////////////////////////
    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        // TODO Auto-generated method stub
        return getApiStatuses().commentMyHeaderTimeline((Comment) obj,
                PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        // TODO Auto-generated method stub
        return getApiStatuses().commentMyFooterTimeline((Comment) obj,
                PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        Log.d("TAG", "load new comment send by me ....");
        return getApiStatuses().commentMyTimeline(count);
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

    // /////////////******************************////////////////////////////////////////////
}
