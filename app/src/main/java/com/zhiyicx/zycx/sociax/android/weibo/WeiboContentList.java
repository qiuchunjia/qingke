package com.zhiyicx.zycx.sociax.android.weibo;

import org.json.JSONObject;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.adapter.CommentListAdapter;
import com.zhiyicx.zycx.sociax.adapter.GroupWeiboComListAdapter;
import com.zhiyicx.zycx.sociax.adapter.SociaxListAdapter;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.android.ThinksnsImageView;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.component.CommentList;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.WeiboContent;
import com.zhiyicx.zycx.util.Utils;

public class WeiboContentList extends ThinksnsAbscractActivity {
    private static final String TAG = "WeiboContent";

    private static Weibo weibo;
    private Comment mComment;
    private static final int ADD_FAVORITE = 0;
    private static final int DEL_FAVORITE = 1;
    private static final int DEL_WEIBO = 2;
    private static final int REFRESH = 3;
    private static final int LOADCOMMENT = 4;
    private final static int DEL_COMMENT = 5;
    private static TextView favorite;
    private static ResultHandler resultHandler;
    private static Worker thread;
    private static ActivityHandler handler;

    private CommentList list;
    private SociaxListAdapter adapter;

    private View headView;
    /*********
     * qcj添加
     *******/
    private RelativeLayout rl_left_1;
    private TextView tv_title;

    /*********
     * qcj添加
     *******/

    public enum FavoriteStatus {
        YES, NO
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        Thinksns app = (Thinksns) WeiboContentList.this.getApplicationContext();
        thread = new Worker(app, "delete opt");
        handler = new ActivityHandler(thread.getLooper(), app);
        resultHandler = new ResultHandler();

        try {
            if (getIntentData().get("commenttype") != null) {
                if (/*
                     * "receivecomment".equals(getIntentData().getString(
					 * "commenttype"))
					 */false) {
                    mComment = new Comment(new JSONObject(getIntentData()
                            .getString("data")));
                } else
                    weibo = new Weibo(new JSONObject(getIntentData().getString(
                            "data")), 1);
            } else {
                weibo = new Weibo(new JSONObject(getIntentData().getString(
                        "data")));
            }
        } catch (Exception e) {
            Log.d(TAG, " WeiboContentList oncreat wm " + e.toString());
            WeiboContentList.this.finish();
        }
        // 获取list的布局对象
        list = (CommentList) findViewById(R.id.weibo_content_comment_list);
        headView = LayoutInflater.from(this).inflate(
                R.layout.weibo_content_header, null);
        list.addHeaderView(headView);

        this.setWeiboContentData(weibo);

        setFavoritState(weibo);
        this.setClickListener();

        if (getIntentData().getString("app") != null
                && getIntentData().getString("app").equals("group")) {
            // 获取数据源
            ListData<SociaxItem> data = new ListData<SociaxItem>();
            adapter = new GroupWeiboComListAdapter(this, data, weibo);
            list.setAdapter(adapter, System.currentTimeMillis(), this);
            // adapter.loadInitData();
            TextView favorite = (TextView) findViewById(R.id.text_favorite);
            favorite.setVisibility(View.GONE);
        } else {
            ListData<SociaxItem> data = new ListData<SociaxItem>();
            adapter = new CommentListAdapter(this, data, weibo);
            list.setAdapter(adapter, System.currentTimeMillis(), this);
            // adapter.loadInitData();

            /*******************************/
            /**** qcj添加title 并初始化 *********/
            rl_left_1 = (RelativeLayout) findViewById(R.id.rl_left_1);
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_title.setText("微博正文");
            rl_left_1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            /**** qcj添加title end *********/

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message msg1 = handler.obtainMessage();
        msg1.obj = weibo;
        msg1.what = REFRESH;
        msg1.arg1 = 0;
        // handler.sendMessage(msg1);
        adapter.clearList();
        adapter.loadInitData();
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
                Thinksns app = (Thinksns) WeiboContentList.this
                        .getApplicationContext();
                app.startActivity(WeiboContentList.this,
                        WeiboSendActivity.class, getIntentData());
            }
        });

        TextView comment = (TextView) findViewById(R.id.text_comment);
        comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntentData().putInt("send_type", COMMENT);

                Thinksns app = (Thinksns) WeiboContentList.this
                        .getApplicationContext();
                app.startActivity(WeiboContentList.this,
                        WeiboSendActivity.class, getIntentData());
            }
        });

        RelativeLayout layout = (RelativeLayout) headView
                .findViewById(R.id.userinfo);
		/*
		 * layout.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { getIntentData().putInt("uid",
		 * weibo.getUid()); Thinksns app = (Thinksns) WeiboContentList.this
		 * .getApplicationContext(); app.startActivity(WeiboContentList.this,
		 * ThinksnsUserInfo.class, getIntentData()); } });
		 */

        favorite = (TextView) findViewById(R.id.text_favorite);
        favorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thinksns app = (Thinksns) WeiboContentList.this
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

        TextView textDelete = (TextView) findViewById(R.id.text_delete);
        textDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity obj = WeiboContentList.this;
                AlertDialog.Builder builder = new Builder(obj);
                builder.setMessage("确定要删除此微博吗?");
                builder.setTitle("提示");
                builder.setPositiveButton("确认",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
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
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        if (weibo.getUid() != Utils.getUid(this))
            textDelete.setEnabled(false);
        else
            textDelete.setEnabled(true);
    }

    // 增加内容
    private void setWeiboContentData(Weibo weibo) {
        WeiboContent helper = new WeiboContent(this);
        helper.appendWeiboData(weibo,
                headView.findViewById(R.id.weibo_content_layout));
    }

    @Override
    public OnTouchListListener getListView() {
        return list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.weibocontentlist;
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
        final Thinksns app = (Thinksns) WeiboContentList.this
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
                        Log.d(TAG, "ThinksnsWeiboContent delete weibo id "
                                + ((Weibo) msg.obj).getWeiboId());
                        break;
                    case REFRESH:
                        newWeibo = status.show(((Weibo) msg.obj).getWeiboId());
                        mainMsg.arg2 = msg.arg1;
                        break;
                    case LOADCOMMENT:
                        // try {
                        // // commentList = status.commentForWeiboTimeline(
                        // // (Weibo) msg.obj, 6);
                        // } catch (ListAreEmptyException e) {
                        // e.printStackTrace();
                        // }
                        break;
                    case DEL_COMMENT:
                        newData = status.destroyComment((Comment) msg.obj);
                        break;
                }
                if (msg.what == REFRESH) {
                    mainMsg.obj = newWeibo;
                } else if (msg.what == LOADCOMMENT) {
                    // mainMsg.obj = commentList;
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
                        favorite.setTag(WeiboContentList.FavoriteStatus.YES);
                        favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                                R.drawable.favorited, 0, 0);
                        favorite.setText("取消收藏");
                        info = "收藏成功";
                        break;
                    case DEL_FAVORITE:
                        favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                                R.drawable.weibo_app_collection_n, 0, 0);
                        favorite.setTag(WeiboContentList.FavoriteStatus.NO);
                        favorite.setText("收藏");
                        info = "取消收藏成功";
                        break;
                    case DEL_WEIBO:
                        info = "删除成功";
                        break;
                    case REFRESH:
                        setWeiboContentData((Weibo) msg.obj);
                        setFavoritState((Weibo) msg.obj);
                        info = "刷新成功";
                        if (msg.arg2 == 0) {
                            isShow = false;
                        }
                        break;
                    case LOADCOMMENT:
                        // showComments((ListData<SociaxItem>) (msg.obj));
                        isShow = false;
                        break;
                    case DEL_COMMENT:
                        info = "删除成功";
                        // isShow = false;
                        break;
                }
            } else {
                info = (String) msg.obj;
            }
            if (isShow) {
                Toast.makeText(WeiboContentList.this, info, Toast.LENGTH_SHORT)
                        .show();
            }
            if (msg.arg1 == DEL_WEIBO) {
                Thinksns.setDelIndex(getIntentData().getInt("this_position"));
                WeiboContentList.this.finish();
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
					/*
					 * String uname = ""; uname = value.substring(1,
					 * value.length()); getIntentData().putInt("uid", 0);
					 * getIntentData().putString("uname", uname); Thinksns app =
					 * (Thinksns) WeiboContentList.this
					 * .getApplicationContext();
					 * app.startActivity(WeiboContentList.this,
					 * ThinksnsUserInfo.class, getIntentData());
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

					/*
					 * getIntentData().putString("type", "joinTopic");
					 * getIntentData().putString("topic", value); Thinksns app =
					 * (Thinksns) WeiboContentList.this
					 * .getApplicationContext();
					 */
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
					/*
					 * Intent intent = new Intent(); intent.putExtra("url",
					 * value); intent.setClass(WeiboContentList.this,
					 * ThinksnsStartWeb.class);
					 * WeiboContentList.this.startActivity(intent);
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
                Thinksns app = (Thinksns) WeiboContentList.this
                        .getApplicationContext();
                app.startActivity(WeiboContentList.this,
                        ThinksnsImageView.class, getIntentData());
            }

        };
    }

    @Override
    public OnClickListener getRightListener() {
        return null;
    }

    @Override
    public int getRightRes() {
        return 0;
    }

    private void setFavoritState(Weibo weibo) {
        TextView favorite = (TextView) findViewById(R.id.text_favorite);
        if (weibo.isFavorited()) {
            favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.favorited, 0, 0);
            favorite.setText("取消收藏");
            favorite.setTag(WeiboContentList.FavoriteStatus.YES);
        } else {
            favorite.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_collection_n, 0, 0);
            favorite.setTag(WeiboContentList.FavoriteStatus.NO);
            favorite.setText("收藏");
        }
    }

    protected void dowloaderTask(String url, ImageView image,
                                 BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }

    @Override
    public void refreshHeader() {
        adapter.doRefreshHeader();
    }

    @Override
    public void refreshFooter() {
        adapter.doRefreshFooter();
    }

    public void deleteComment(final Comment comment) {
        AlertDialog.Builder builder = new Builder(this);
        final Activity obj = this;
        builder.setMessage("确定要删除此评论吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Message msg = handler.obtainMessage();
                        msg.obj = comment;
                        msg.what = DEL_COMMENT;
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
    }
}
