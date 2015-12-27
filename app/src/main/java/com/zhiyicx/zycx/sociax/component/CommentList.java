package com.zhiyicx.zycx.sociax.component;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.adapter.CommentListAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.android.user.ThinksnsUserInfo;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboSendActivity;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboContentList;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.Anim;

/**
 * 微博评论列表
 */
public class CommentList extends SociaxList {
    private static View v;
    private static final int SHOW_USER = 0;
    private static final int REPLY_COMMENT = 1;
    private static final int DEL_COMMENT = 2;
    private static Comment comment;
    private static String weibo;
    private static ThinksnsAbscractActivity context;

    public CommentList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentList(ThinksnsAbscractActivity context) {
        super(context);
        CommentList.context = context;
    }

    // 点击单条评论，进入发表评论
    @Override
    protected void onClick(View view, int position, long id) {
        Thinksns app = (Thinksns) getContext().getApplicationContext();
        // Bundle data = new Bundle();
        // data.putInt("send_type", ThinksnsWeiboComment.COMMENT);
        // Comment comment =
        // (Comment)view.findViewById(R.id.comment_content).getTag();
        if (view.getId() == R.id.footer_content) {
            ImageView iv = (ImageView) view.findViewById(R.id.anim_view);
            iv.setVisibility(View.VISIBLE);
            Anim.refresh(getContext(), iv);

            HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter) this
                    .getAdapter();
            CommentListAdapter comenlAdapter = (CommentListAdapter) headerAdapter
                    .getWrappedAdapter();
            comenlAdapter.animView = iv;
            comenlAdapter.doRefreshFooter();
            return;
        }
        /*
        weibo = (String) view.getTag();
		Weibo wb = null;
		try {
			wb = new Weibo(new JSONObject(weibo));
		} catch (WeiboDataInvalidException e) {
			e.printStackTrace();
			return;
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}

		AlertDialog.Builder builder = new Builder(view.getContext());
		CommentListener listener = new CommentListener();
		comment = (Comment) this.getItemAtPosition(position);
		if (Thinksns.getMy().getUid() != comment.getUid()) {
			builder.setItems(R.array.del_topts, listener).setTitle("评论功能")
					.setCancelable(true).show();
		} else {
			builder.setItems(R.array.del_commentopts, listener)
					.setTitle("评论功能").setCancelable(true).show();
		}
        */
    }

    private class CommentListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Thinksns app = (Thinksns) getContext().getApplicationContext();
            Bundle data = new Bundle();
            switch (which) {
                case SHOW_USER:
                /*
				data.putInt("uid", comment.getUid());
				app.startActivity(getActivityObj(), ThinksnsUserInfo.class,
						data);
                */
                    break;
                case REPLY_COMMENT:
                    data.putInt("send_type", ThinksnsAbscractActivity.COMMENT);
                    data.putInt("commentId", comment.getCommentId());
                    data.putString("username", comment.getUname());
                    data.putString("data", weibo);
                    app.startActivity(getActivityObj(), WeiboSendActivity.class,
                            data);
                    break;
                case DEL_COMMENT:
                    ((WeiboContentList) getActivityObj()).deleteComment(comment);
                    break;
            }
        }
    }

    @Override
    public void addHeaderView(View v) {
        super.addHeaderView(v);
    }

    @Override
    protected void addHeaderView() {
    }

    @Override
    protected void addFooterView() {
    }

}
