package com.zhiyicx.zycx.sociax.adapter;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.db.MyCommentSqlHelper;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.TimeIsOutFriendly;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.NotifyCount;
import com.zhiyicx.zycx.sociax.modle.ReceiveComment;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.unit.ListViewAppend;
import com.zhiyicx.zycx.sociax.unit.SociaxUIUtils;
import com.zhiyicx.zycx.sociax.unit.TimeHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我收到的评论
 */
public class CommentMyListAdapter extends SociaxListAdapter {

    private static ListViewAppend append;
    private Context mContext;

    public ReceiveComment getFirst() {
        return (ReceiveComment) super.getFirst();
    }

    public ReceiveComment getLast() {
        return (ReceiveComment) super.getLast();
    }

    @Override
    public ReceiveComment getItem(int position) {
        // TODO Auto-generated method stub
        return (ReceiveComment) super.getItem(position);
    }

    public CommentMyListAdapter(ThinksnsAbscractActivity context, ListData<SociaxItem> data) {
        super(context, data);
        mContext = context;
        //initHeadImageFetcher();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentMyListItem commentMyListItem = null;
        if (convertView == null) {
            commentMyListItem = new CommentMyListItem();
            convertView = this.inflater.inflate(R.layout.comment_my_list, null);
            commentMyListItem.userheader = (ImageView) convertView.findViewById(R.id.user_comment_header);
            commentMyListItem.username = (TextView) convertView.findViewById(R.id.user_name);
            commentMyListItem.time = (TextView) convertView.findViewById(R.id.comment_ctime);
            commentMyListItem.content = (TextView) convertView.findViewById(R.id.comment_content);
            commentMyListItem.myComment = (TextView) convertView.findViewById(R.id.comment_receive_me);
            convertView.setTag(commentMyListItem);
        } else {
            commentMyListItem = (CommentMyListItem) convertView.getTag();
        }
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.weibo_data);
        // this.loadingHeader(position, commentMyListItem.userheader);
        ReceiveComment comment = this.getItem(position);

        //mHeadImageFetcher.loadImage(comment.getHeadUrl(), commentMyListItem.userheader);
        mNetComTools.loadNetImage(commentMyListItem.userheader, comment.getHeadUrl(), R.drawable.header, mHeadImageSize, mHeadImageSize);
        layout.setTag(comment.getStatus());
        commentMyListItem.username.setText(comment.getUname());
        try {
            commentMyListItem.time.setText(TimeHelper.friendlyTimeFromeStringTime(comment.getcTime()));
            System.out.println(" ctime" + comment.getcTime());
        } catch (Exception e) {
            commentMyListItem.time.setText(comment.getcTime());
        }
        SpannableString ss = new SpannableString(SociaxUIUtils.filterHtml(comment.getContent()));
        SociaxUIUtils.highlightContent(mContext, ss);
        commentMyListItem.content.setText(ss);

        if (comment.isNullForReplyComment()) {
            SpannableString ss1 = new SpannableString("回复我的微博:"
                    + SociaxUIUtils.filterHtml(comment.getStatus().getContent()));
            SociaxUIUtils.highlightContent(mContext, ss1);
            commentMyListItem.myComment.setText(ss1);
        } else {
            SpannableString ss2 = new SpannableString("回复我的评论:"
                    + SociaxUIUtils.filterHtml(comment.getReplyComment().getContent()));
            SociaxUIUtils.highlightContent(mContext, ss2);
            commentMyListItem.myComment.setText(ss2);
        }
        return convertView;
    }

    private User loadingHeader(int position, ImageView userheader) {
        User temp = getItem(position).getUser();
        userheader.setTag(temp);
        Thinksns app = (Thinksns) this.context.getApplicationContext();
        if (/* temp.hasHeader() && */app.isNetWorkOn()) {
            if (temp.isNullForHeaderCache()) {
                dowloaderTask(temp.getUserface(), userheader, BitmapDownloaderTask.Type.FACE);
            } else {
                Bitmap cache = temp.getHeader();
                if (cache == null) {
                    dowloaderTask(temp.getUserface(), userheader, BitmapDownloaderTask.Type.FACE);
                } else {
                    userheader.setImageBitmap(cache);
                }
            }
        }
        return temp;
    }

    protected void dowloaderTask(String url, ImageView image, BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj) throws VerifyErrorException, ApiException,
            ListAreEmptyException, DataInvalidException {

        if (obj == null) {
            return refreshNew(PAGE_COUNT);
        }

        this.getApiUsers().unsetNotificationCount(NotifyCount.Type.comment, getMyUid());

        ListData<SociaxItem> commentDatas = getApiStatuses().commentReceiveMyHeaderTimeline((ReceiveComment) obj,
                PAGE_COUNT);

        Thinksns app = (Thinksns) this.context.getApplicationContext();
        MyCommentSqlHelper mCommentSqlHelper = app.getMyCommentSql();

        if (commentDatas.size() > 0) {
            int dbSize = mCommentSqlHelper.getDBCommentSize();
            if (dbSize >= 20) {
                mCommentSqlHelper.deleteWeibo(commentDatas.size());
            } else if ((dbSize + commentDatas.size()) >= 20) {
                mCommentSqlHelper.deleteWeibo(dbSize + commentDatas.size() - 20);
            }
            for (int i = 0; i < commentDatas.size(); i++) {
                mCommentSqlHelper.addComment((ReceiveComment) commentDatas.get(i));
            }
        }

        return commentDatas;
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj) throws VerifyErrorException, ApiException,
            ListAreEmptyException, DataInvalidException {
        return getApiStatuses().commentReceiveMyFooterTimeline((Comment) obj, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count) throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {

        this.getApiUsers().unsetNotificationCount(NotifyCount.Type.comment, getMyUid());

        ListData<SociaxItem> commentDatas = getApiStatuses().commentReceiveMyTimeline(count);
        Thinksns app = (Thinksns) this.context.getApplicationContext();
        MyCommentSqlHelper mCommentSqlHelper = app.getMyCommentSql();

        if (commentDatas.size() > 0) {
            int dbSize = mCommentSqlHelper.getDBCommentSize();
            if (dbSize >= 20) {
                mCommentSqlHelper.deleteWeibo(commentDatas.size());
            } else if ((dbSize + commentDatas.size()) >= 20) {
                mCommentSqlHelper.deleteWeibo(dbSize + commentDatas.size() - 20);
            }
            for (int i = 0; i < commentDatas.size(); i++) {
                mCommentSqlHelper.addComment((ReceiveComment) commentDatas.get(i));
            }
        }

        return commentDatas;
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

    public class CommentMyListItem {
        ImageView userheader;
        TextView username;
        TextView time;
        TextView content;
        TextView myComment;
    }

}
