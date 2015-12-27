package com.zhiyicx.zycx.sociax.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.user.ThinksnsUserInfo;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboSendActivity;
import com.zhiyicx.zycx.sociax.adapter.CommentListAdapter;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.component.CommentList;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.TimeIsOutFriendly;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.TimeHelper;
import com.zhiyicx.zycx.sociax.unit.WeiboContent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ThinksnsWeiboContent extends ThinksnsAbscractActivity {
    private static final String TAG = "WeiboContent";

    private static Weibo weibo;
    private Comment mComment;
    private static final int ADD_FAVORITE = 0;
    private static final int DEL_FAVORITE = 1;
    private static final int DEL_WEIBO = 2;
    private static final int REFRESH = 3;
    private static final int LOADCOMMENT = 4;
    private static TextView favorite;
    private static ResultHandler resultHandler;
    private static Worker thread;
    private static ActivityHandler handler;

    private static CommentList list;
    private static CommentListAdapter adapter;

    private TextView commentSeeAll;
    private ListData<SociaxItem> commentList;
    private ScrollView scrollView;

    public enum FavoriteStatus {
        YES, NO
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                .getApplicationContext();
        thread = new Worker(app, "delete opt");
        handler = new ActivityHandler(thread.getLooper(), app);
        resultHandler = new ResultHandler();

        commentSeeAll = (TextView) findViewById(R.id.comment_see_all);
        commentSeeAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getIntentData().putInt("send_type", COMMENT);
                Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                        .getApplicationContext();
                app.startActivity(ThinksnsWeiboContent.this,
                        ThinksnsWeiboComment.class, getIntentData());
            }
        });

        try {
            if (getIntentData().get("commenttype") != null) {
                weibo = new Weibo(new JSONObject(getIntentData().getString(
                        "data")), 1);
            } else {
                weibo = new Weibo(new JSONObject(getIntentData().getString(
                        "data")));
            }

            this.setWeiboContentData(weibo, true);

            this.setClickListener();

            // this.setButtomClickListener();
        } catch (WeiboDataInvalidException e) {
            Log.d(TAG, "wm " + e.toString());
            // app.getActivityStack().returnActivity(ThinksnsWeiboContent.this,getIntentData());
            ThinksnsWeiboContent.this.finish();
        } catch (JSONException e) {
            // app.getActivityStack().returnActivity(ThinksnsWeiboContent.this,getIntentData());
            ThinksnsWeiboContent.this.finish();
        }

        // 获取list的布局对象
        /*
		 * list = (CommentList) findViewById(R.id.comment_list);
		 * 
		 * // 获取数据源 ListData<SociaxItem> data = new ListData<SociaxItem>();
		 * adapter = new CommentListAdapter(this, data,weibo); if(data.size() !=
		 * 0){
		 * list.setAdapter(adapter,(long)adapter.getFirst().getTimestemp()*1000
		 * ,this); }else{
		 * list.setAdapter(adapter,System.currentTimeMillis(),this); }
		 * adapter.loadInitData();
		 */
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Message msg1 = handler.obtainMessage();
        msg1.obj = weibo;
        msg1.what = REFRESH;
        msg1.arg1 = 0;
        handler.sendMessage(msg1);
    }

    /*
     * 收藏、转发、评论按钮
     */
    private void setClickListener() {
        TextView transpond = (TextView) findViewById(R.id.text_trans);
        transpond.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntentData().putInt("send_type", TRANSPOND);
                Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                        .getApplicationContext();
                app.startActivity(ThinksnsWeiboContent.this,
                        WeiboSendActivity.class, getIntentData());
            }
        });

        TextView comment = (TextView) findViewById(R.id.text_comment);
        comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntentData().putInt("send_type", COMMENT);

                Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                        .getApplicationContext();
                app.startActivity(ThinksnsWeiboContent.this,
                        WeiboSendActivity.class, getIntentData());
            }
        });

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.userinfo);

        /*
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getIntentData().putInt("uid", weibo.getUid());
				Thinksns app = (Thinksns) ThinksnsWeiboContent.this
						.getApplicationContext();
				app.startActivity(ThinksnsWeiboContent.this,
						ThinksnsUserInfo.class, getIntentData());
			}
		});
        */

        favorite = (TextView) findViewById(R.id.text_favorite);
        favorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                        .getApplicationContext();
                thread = new Worker(app, "statuses weibo");
                handler = new ActivityHandler(thread.getLooper(), app);
                Message msg = handler.obtainMessage();
                msg.obj = weibo;
                switch ((FavoriteStatus) v.getTag()) {
                    case YES:
                        msg.what = DEL_FAVORITE;
                        favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                                R.drawable.weibo_app_collection_n, 0, 0);
                        favorite.setText("收藏");
                        break;
                    case NO:
                        msg.what = ADD_FAVORITE;
                        favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                                R.drawable.favorited, 0, 0);
                        favorite.setText("取消收藏");
                        break;
                }
                favorite.setClickable(false);
                handler.sendMessage(msg);
            }
        });

        TextView textMore = (TextView) findViewById(R.id.text_more);
        textMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });
    }

    // 增加内容
    private void setWeiboContentData(Weibo weibo, boolean isFirst) {
        WeiboContent helper = new WeiboContent(this);
        // WeiboDataItem weiboDataItem = new WeiboDataItem();
        helper.appendWeiboData(weibo,
                this.findViewById(R.id.weibo_content_layout), isFirst);
        if (!isFirst)
            getComments5(weibo);
    }

    @Override
    public OnTouchListListener getListView() {
        return list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.weibocontent;
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new LeftAndRightTitle(this);
    }

    @Override
    public String getTitleCenter() {
        return this.getString(R.string.weibo_content);
    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Thinksns.getMy().getUid() == weibo.getUid()) {
            menu.add(0, 0, 0, "删除");
        }
        menu.add(1, 1, 0, "刷新");
        menu.add(2, 2, 0, "取消");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                .getApplicationContext();
        switch (id) {
            case 0:
                AlertDialog.Builder builder = new Builder(this);
                final Activity obj = this;
                builder.setMessage("确定要删除此微博吗?");
                builder.setTitle("提示");
                builder.setPositiveButton("确认",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Message msg = handler.obtainMessage();
                                msg.obj = weibo;
                                msg.what = DEL_WEIBO;
                                handler.sendMessage(msg);
                            }
                        });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case 1:
                Message msg1 = handler.obtainMessage();
                msg1.obj = weibo;
                msg1.what = REFRESH;
                handler.sendMessage(msg1);
                break;
            case 2:
                closeOptionsMenu();
        }
        return true;
    }

    @Override
    public void closeOptionsMenu() {
        super.closeOptionsMenu();
    }

    private class ActivityHandler extends Handler {
        private Context context = null;

        public ActivityHandler(Looper looper, Context context) {
            super(looper);
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            boolean newData = false;
            Message mainMsg = new Message();
            mainMsg.what = ResultHandler.ERROR;
            Weibo newWeibo = new Weibo();
            Thinksns app = (Thinksns) this.context.getApplicationContext();
            Api.Favorites favorites = app.getFavorites();
            Api.StatusesApi status = app.getStatuses();
            try {
                switch (msg.what) {
                    case ADD_FAVORITE:
                        newData = favorites.create((Weibo) msg.obj);
                        break;
                    case DEL_FAVORITE:
                        newData = favorites.destroy((Weibo) msg.obj);
                        break;
                    case DEL_WEIBO:
                        newData = status.destroyWeibo((Weibo) msg.obj);
                        app.getWeiboSql().deleteWeiboById(
                                ((Weibo) msg.obj).getWeiboId());
                        getIntentData().putInt("delete_id",
                                ((Weibo) msg.obj).getWeiboId());
                        Log.d(TAG,
                                "ThinksnsWeiboContent delete weibo id "
                                        + ((Weibo) msg.obj).getWeiboId());
                        break;
                    case REFRESH:
                        newWeibo = status.show(((Weibo) msg.obj).getWeiboId());
                        mainMsg.arg2 = msg.arg1;
                        break;
                    case LOADCOMMENT:
                        try {
                            commentList = status.commentForWeiboTimeline(
                                    (Weibo) msg.obj, 6);
                        } catch (ListAreEmptyException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (msg.what == REFRESH) {
                    mainMsg.obj = newWeibo;
                } else if (msg.what == LOADCOMMENT) {
                    mainMsg.obj = commentList;
                } else {
                    mainMsg.obj = newData;
                }
                mainMsg.what = ResultHandler.SUCCESS;
                mainMsg.arg1 = msg.what;
            } catch (VerifyErrorException e) {
                mainMsg.obj = e.getMessage();
                Log.e(TAG, e.getMessage());
            } catch (ApiException e) {
                mainMsg.obj = e.getMessage();
                Log.e(TAG, e.getMessage());
            } catch (DataInvalidException e) {
                mainMsg.obj = e.getMessage();
                Log.e(TAG, e.getMessage());
            }
            resultHandler.sendMessage(mainMsg);
        }
    }

    private class ResultHandler extends Handler {
        private static final int SUCCESS = 0;
        private static final int ERROR = 1;

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            String info = "";
            boolean isShow = true;
            if (msg.what == SUCCESS) {
                Log.e("ms", "ms" + msg.arg1);
                switch (msg.arg1) {

                    case ADD_FAVORITE:
                        favorite.setTag(ThinksnsWeiboContent.FavoriteStatus.YES);
                        favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                                R.drawable.favorited, 0, 0);
                        favorite.setText("取消收藏");
                        info = "收藏成功";
                        break;
                    case DEL_FAVORITE:
                        favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                                R.drawable.weibo_app_collection_n, 0, 0);
                        favorite.setTag(ThinksnsWeiboContent.FavoriteStatus.NO);
                        favorite.setText("收藏");
                        info = "取消收藏成功";
                        break;
                    case DEL_WEIBO:
                        info = "删除成功";
                        break;
                    case REFRESH:
                        ThinksnsWeiboContent.this.setWeiboContentData(
                                (Weibo) msg.obj, false);
                        info = "刷新成功";
                        if (msg.arg2 == 0) {
                            isShow = false;
                        }
                        break;
                    case LOADCOMMENT:
                        showComments((ListData<SociaxItem>) (msg.obj));
                        isShow = false;
                        break;
                }
            } else {
                info = (String) msg.obj;
            }
            if (isShow) {
                Toast.makeText(ThinksnsWeiboContent.this, info,
                        Toast.LENGTH_SHORT).show();
            }
            if (msg.arg1 == DEL_WEIBO) {
                Thinksns.setDelIndex(getIntentData().getInt("this_position"));
                ThinksnsWeiboContent.this.finish();
            }
            favorite.setClickable(true);
        }
    }

    /*
     * 处理@、话题、链接点击
     */
    public ClickableSpan typeClick(final String value) {
        char type = value.charAt(0);
        switch (type) {
            case '@':
                return new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        // TODO Auto-generated method stub
                    /*
					String uname = "";
					uname = value.substring(1, value.length());
					getIntentData().putInt("uid", 0);
					getIntentData().putString("uname", uname);
					Thinksns app = (Thinksns) ThinksnsWeiboContent.this.getApplicationContext();
					app.startActivity(ThinksnsWeiboContent.this,ThinksnsUserInfo.class, getIntentData());
                    */
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.argb(255, 54, 92, 124));
                        ds.setUnderlineText(true);
                    }
                };
            case '#':
                return new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        // TODO Auto-generated method stub

                        getIntentData().putString("type", "joinTopic");
                        getIntentData().putString("topic", value);
                        Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                                .getApplicationContext();
					/*
					 * app.startActivity(ThinksnsWeiboContent.this,
					 * ThinksnsCreate.class, getIntentData());
					 */
                        return;
                        // app.startActivity(ThinksnsWeiboContent.this,
                        // ThinksnsTopicActivity.class, getIntentData());
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.argb(255, 54, 92, 124));
                        ds.setUnderlineText(true);
                    }
                };
            case 'h':
                return new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        // TODO Auto-generated method stub
                    /*
					Intent intent = new Intent();
					intent.putExtra("url", value);
					intent.setClass(ThinksnsWeiboContent.this,
							ThinksnsStartWeb.class);
					ThinksnsWeiboContent.this.startActivity(intent);
                    */
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(value);
                        intent.setData(content_url);
                        startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.argb(255, 54, 92, 124));
                        ds.setUnderlineText(true);
                    }
                };
        }
        return null;
    }

    /*
     * 展示大图
     */
    @Override
    public OnClickListener getImageFullScreen(final String url) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getIntentData().putString("url", url);
                Thinksns app = (Thinksns) ThinksnsWeiboContent.this
                        .getApplicationContext();
                app.startActivity(ThinksnsWeiboContent.this,
                        ThinksnsImageView.class, getIntentData());
            }

        };
    }

    @Override
    public OnClickListener getRightListener() {
        // TODO Auto-generated method stub
        System.out.println("content right click ...  ");
        // return super.getRightListener();
        return null;
    }

	/*
	 * @Override public OnClickListener getRightListener() { // TODO
	 * Auto-generated method stub return new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub Thinksns app = (Thinksns) ThinksnsWeiboContent.this
	 * .getApplicationContext(); thread = new Worker(app, "refresh opt");
	 * handler = new ActivityHandler(thread.getLooper(), app); Message msg1 =
	 * handler.obtainMessage(); msg1.obj = weibo; msg1.what = REFRESH;
	 * handler.sendMessage(msg1); Log.d(TAG, "weibo right refresh ...."); } }; }
	 */

    @Override
    public int getRightRes() {
        // TODO Auto-generated method stub
        return 0;
    }

    void getComments5(Weibo weibo) {
        Message msg = handler.obtainMessage();
        msg.what = LOADCOMMENT;
        msg.obj = weibo;
        handler.sendMessage(msg);
    }

    void showComments(ListData<SociaxItem> commentList) {
        LinearLayout commentLayout = (LinearLayout) findViewById(R.id.layout_comment);
        commentLayout.removeAllViews();
        if (commentList != null) {
            int i = 0;
            for (SociaxItem sociaxItem : commentList) {

                if (i > 4) {
                    commentSeeAll.setVisibility(View.VISIBLE);
                    break;
                }
                View view = new View(this);
                view = View.inflate(this, R.layout.comment_item1, null);
                ImageView userhead = (ImageView) view
                        .findViewById(R.id.weibo_comment_iv);
                TextView username = (TextView) view.findViewById(R.id.username);
                TextView content = (TextView) view
                        .findViewById(R.id.comment_content);
                TextView cTime = (TextView) view
                        .findViewById(R.id.comment_time);

                final Comment comment = (Comment) sociaxItem;
                loadingHeader(comment.getHeadUrl(), userhead);
                username.setText(comment.getUname());
                content.setText(comment.getContent());
                content.setTag(comment);
                try {
                    cTime.setText(TimeHelper.friendlyTime(comment
                            .getTimestemp()));
                } catch (TimeIsOutFriendly e) {
                    // TODO Auto-generated catch block
                    cTime.setText(TimeHelper.getStandardTimeWithDate(Integer
                            .valueOf(comment.getcTime())));
                    TimeHelper.getStandardTimeWithDate(Integer.valueOf(comment
                            .getcTime()));
                }

                TextView tv = new TextView(this);
                // tv.setText("text");
                commentLayout.addView(view);

                i++;

                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mComment = comment;
                        AlertDialog.Builder builder = new Builder(
                                ThinksnsWeiboContent.this);
                        CommentListener listener = new CommentListener();
                        if (Thinksns.getMy().getUid() != weibo.getUid()) {
                            builder.setItems(R.array.del_topts, listener)
                                    .setTitle("评论功能").setCancelable(true)
                                    .show();
                        } else {
                            builder.setItems(R.array.del_commentopts, listener)
                                    .setTitle("评论功能").setCancelable(true)
                                    .show();
                        }

                    }
                });
            }
        } else {
            commentSeeAll.setVisibility(View.GONE);
        }
    }

    private class CommentListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            Thinksns app = (Thinksns) getApplicationContext();
            Bundle data = new Bundle();
            switch (which) {
                case 0:
                /*
				data.putInt("uid", mComment.getUid());
				app.startActivity(ThinksnsWeiboContent.this,ThinksnsUserInfo.class, data);
                */
                    break;
                case 1:
                    data.putInt("send_type", ThinksnsAbscractActivity.COMMENT);
                    data.putInt("commentId", mComment.getCommentId());
                    data.putString("username", mComment.getUname());
                    data.putString("data", weibo.toJSON());
                    app.startActivity(ThinksnsWeiboContent.this,
                            WeiboSendActivity.class, data);
                    break;
                case 2:

                    // ((ThinksnsWeiboComment) CommentList.this.getActivityObj())
                    // .deleteComment(comment);
                    Message msg1 = handler.obtainMessage();
                    msg1.obj = weibo;
                    msg1.what = REFRESH;
                    msg1.arg1 = 0;
                    handler.sendMessage(msg1);
                    break;
            }
        }
    }

    private void loadingHeader(String url, ImageView userheader) {
        if (url != null)
            dowloaderTask(url, userheader, BitmapDownloaderTask.Type.THUMB);
    }

    protected void dowloaderTask(String url, ImageView image,
                                 BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }
}
