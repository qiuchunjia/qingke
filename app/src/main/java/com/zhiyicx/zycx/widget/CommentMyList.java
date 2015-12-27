package com.zhiyicx.zycx.widget;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.CommentMyListAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboContentList;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboSendActivity;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.Anim;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CommentMyList extends SociaxList {
    private static final int SHOW_WEIBO = 0;
    private static final int SHOW_USER = 1;
    private static final int REPLY_COMMENT = 2;
    private static View v;
    private Comment comment;

    public CommentMyList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentMyList(Context context) {
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
            CommentMyListAdapter commentMyListAdapter = (CommentMyListAdapter) headerAdapter
                    .getWrappedAdapter();
            commentMyListAdapter.animView = iv;
            commentMyListAdapter.doRefreshFooter();
            return;
        }

        /*
        AlertDialog.Builder builder = new Builder(view.getContext());
		CommentListener listener = new CommentListener();
		// comment = new Comment();
		comment = (Comment) this.getItemAtPosition(position);
		v = view;
		builder.setItems(R.array.commentopts, listener).setTitle("评论功能")
				.setCancelable(true).show();
        */
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.weibo_data);
        Weibo weiboData = (Weibo) layout.getTag();
        Bundle data = new Bundle();
        data.putInt("weiboId", weiboData.getWeiboId());
        if (weiboData.getTempJsonString() != null) {
            data.putString("data", weiboData.getTempJsonString());
        } else {
            data.putString("data", weiboData.toJSON());
        }
        //data.putString("data", weiboData.toJSON());
        data.putString("commenttype", "receivecomment");
        data.putInt("position", getLastPosition());
        data.putInt("this_position", position);
        Thinksns app = (Thinksns) getContext().getApplicationContext();
        app.startActivity(getActivityObj(), WeiboContentList.class, data);
    }

    private class CommentListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Thinksns app = (Thinksns) getContext().getApplicationContext();
            Bundle data = new Bundle();
            switch (which) {
                case SHOW_WEIBO:
                    LinearLayout layout = (LinearLayout) v.findViewById(R.id.weibo_data);
                    Weibo weiboData = (Weibo) layout.getTag();
                    data.putInt("weiboId", weiboData.getWeiboId());
                    data.putString("data", weiboData.toJSON());
                    data.putString("commenttype", "receivecomment");
                    data.putInt("position", getLastPosition());
                    app.startActivity(getActivityObj(), WeiboContentList.class,
                            data);
                    break;
                case SHOW_USER:
                    // ImageView userheader =
                    // (ImageView)v.findViewById(R.id.user_header);
                    // User user = (User)userheader.getTag();
                /*
				data.putInt("uid", comment.getUid());
				app.startActivity(getActivityObj(), ThinksnsUserInfo.class,
						data);
                */
                    break;
                case REPLY_COMMENT:
                    data.putInt("send_type", ThinksnsAbscractActivity.COMMENT);
                    // Comment comment = (Comment)v.getTag();
                    data.putInt("commentId", comment.getCommentId());
                    data.putString("commenttype", "receivecomment");
                    data.putString("username", comment.getUname());
                    data.putString("data", comment.getStatus().toJSON());
                    app.startActivity(getActivityObj(), WeiboSendActivity.class,
                            data);
                    break;
            }
        }

    }
}
