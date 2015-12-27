package com.zhiyicx.zycx.sociax.android.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.ApiGroup;
import com.zhiyicx.zycx.sociax.api.ApiMessage;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.LoadingView;
import com.zhiyicx.zycx.sociax.component.RightIsButton;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.UpdateException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.SociaxUIUtils;
import com.zhiyicx.zycx.sociax.unit.WordCount;

public class WeiboSendActivity extends ThinksnsAbscractActivity {
    private static Weibo weibo;
    private static EditText edit;
    private static CheckBox checkBox;
    private static Worker thread;
    private static Handler handler;
    private static LoadingView loadingView;
    private static int replyCommentId = -1;
    private RelativeLayout rl_left_1;
    private TextView tv_title_right;
    private TextView tv_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        edit = (EditText) findViewById(R.id.send_content);
        checkBox = (CheckBox) findViewById(R.id.isComment);
        loadingView = (LoadingView) findViewById(LoadingView.ID);

        this.setTextForCheckBox(getIntentData().getInt("send_type"));

        if (this.getIntentData().containsKey("commentId")) {
            replyCommentId = this.getIntentData().getInt("commentId");
            edit.setText("回复@" + getIntentData().getString("username") + ":");
            edit.setSelection(edit.getText().length());
        } else {
            replyCommentId = -1;
        }
        if (getIntentData().getInt("send_type") != REPLY_MESSAGE
                && getIntentData().getInt("send_type") != CREATE_MESSAGE) {
            try {
                if (getIntentData().get("commenttype") != null) {
                    weibo = new Weibo(new JSONObject(getIntentData().getString(
                            "data")), 1);
                } else {
                    weibo = new Weibo(new JSONObject(getIntentData().getString(
                            "data")));
                }
            } catch (WeiboDataInvalidException e) {
                Log.d(TAG, "ThinksnsSend ---> wm " + e.toString());
                WeiboSendActivity.this.finish();
            } catch (JSONException e) {
                WeiboSendActivity.this.finish();
            }
        }
        this.setInputLimit(getIntentData().getInt("send_type"));

        /**** qcj添加title 并初始化 *********/
        rl_left_1 = (RelativeLayout) findViewById(R.id.rl_left_1);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("评论");
        tv_title_right.setText("发送");
        rl_left_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title_right.setOnClickListener(getRightListener());
    }

    @Override
    public void finish() {
        if (edit != null)
            SociaxUIUtils.hideSoftKeyboard(getApplicationContext(), edit);
        super.finish();
    }

    private void setInputLimit(int type) {

        TextView overWordCount = (TextView) findViewById(R.id.overWordCount);

        if (type != REPLY_MESSAGE || type != CREATE_MESSAGE) {
            if (type == TRANSPOND) {
                String tran = "";
                if (weibo.getTranspond() != null) {
                    tran = "//@" + weibo.getUsername() + ":"
                            + weibo.getContent();
                }
                WordCount wordCount = new WordCount(edit, overWordCount, tran);
                edit.addTextChangedListener(wordCount);
            } else {
                WordCount wordCount = new WordCount(edit, overWordCount);
                overWordCount.setText(wordCount.getMaxCount() + "");
                edit.addTextChangedListener(wordCount);
            }
        } else {
            overWordCount.setVisibility(View.GONE);
        }
    }

    private void setTextForCheckBox(int type) {
        switch (type) {
            case TRANSPOND:
                checkBox.setText(R.string.transpond_checkbox);
                break;
            case COMMENT:
                checkBox.setText(R.string.comment_checkbox);
                break;
            case REPLY_MESSAGE:
                checkBox.setVisibility(View.GONE);
            case CREATE_MESSAGE:
                checkBox.setVisibility(View.GONE);
                break;
        }
    }

    private final class ActivityHandler extends Handler {
        @SuppressWarnings("unused")
        private Context context = null;

        public ActivityHandler(Looper looper, Context context) {
            super(looper);
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingView.show(edit);

            // 获取数据
            Thinksns app = thread.getApp();
            ApiStatuses statuses = app.getStatuses();
            ApiMessage message = app.getMessages();
            ApiGroup groupStatuses = app.getGroupApi();

            try {
                switch (msg.what) {
                    case ThinksnsAbscractActivity.COMMENT:
                        if (edit.getText().length() == 0) {
                            loadingView.error("评论不能为空", edit);
                            loadingView.hide(edit);
                        } else {
                            String editContent = edit.getText().toString();

                            Comment comment = new Comment();
                            comment.setContent(editContent);
                            comment.setStatus(weibo);
                            comment.setType(checkBox.isChecked() ? Comment.Type.WEIBO
                                    : Comment.Type.COMMENT);

                            if (replyCommentId != -1) {
                                Comment recomment = new Comment();
                                recomment.setCommentId(replyCommentId);
                                comment.setReplyComment(recomment);
                            }
                            if (getIntentData().containsKey("app")) {
                                if (groupStatuses.commentStatuses(comment)) {
                                    loadingView.error("评论成功", edit);
                                    WeiboSendActivity.this.finish();
                                } else {
                                    loadingView.error("评论失败", edit);
                                    loadingView.hide(edit);
                                }
                            } else {
                                if (statuses.comment(comment)) {
                                    loadingView.error("评论成功", edit);
                                    WeiboSendActivity.this.finish();
                                } else {
                                    loadingView.error("评论失败", edit);
                                    loadingView.hide(edit);
                                }
                            }
                        }
                        break;
                    // 微博转发
                    case ThinksnsAbscractActivity.TRANSPOND:
                        String editContent = edit.getText().toString().trim()
                                .length() > 0 ? edit.getText().toString().trim()
                                : "转发微博";

                        Weibo newWeibo = new Weibo();
                        newWeibo.setContent(editContent);
                        newWeibo.setTranspond(weibo);
                        if (getIntentData().containsKey("app")) {
                            if (groupStatuses.repostStatuses(newWeibo,
                                    checkBox.isChecked())) {
                                loadingView.error("分享成功", edit);
                                Log.d(TAG, "weibo transpond success...");
                                WeiboSendActivity.this.finish();
                            } else {
                                loadingView.error("分享失败", edit);
                                loadingView.hide(edit);
                                Log.d(TAG, "weibo transpond fail ...");
                            }
                        } else {
                            if (statuses.repost(newWeibo, checkBox.isChecked())) {
                                loadingView.error("分享成功", edit);
                                Log.d(TAG, "weibo transpond success...");
                                WeiboSendActivity.this.finish();
                            } else {
                                loadingView.error("分享失败", edit);
                                loadingView.hide(edit);
                                Log.d(TAG, "weibo transpond fail ...");
                            }
                        }
                        break;
                    case ThinksnsAbscractActivity.REPLY_MESSAGE:
                        com.zhiyicx.zycx.sociax.modle.Message replyMessage = new com.zhiyicx.zycx.sociax.modle.Message();
                        replyMessage.setListId(getIntentData().getInt("messageId"));
                        replyMessage.setContent(edit.getText().toString());
                        // messageObj.setSourceMessage(replyMessage);

                        if (message.reply(replyMessage)) {
                            getIntentData().putString(TIPS, "回复消息成功");
                            WeiboSendActivity.this.finish();
                        } else {
                            loadingView.error("回复消息失败", edit);
                        }
                        break;
                    case ThinksnsAbscractActivity.CREATE_MESSAGE:
                        com.zhiyicx.zycx.sociax.modle.Message createMessage = new com.zhiyicx.zycx.sociax.modle.Message();
                        createMessage.setToUid(getIntentData().getInt("to_uid"));
                        String content = edit.getText().toString().trim();
                        if (content.length() < 1) {
                            loadingView.error("请输入内容");
                            loadingView.hide(edit);
                            return;
                        } else if (content.length() > MyConfig.weiboLenght) {
                            loadingView.error(getString(R.string.word_limit));
                            loadingView.hide(edit);
                            return;
                        }
                        createMessage.setContent(content);
                        createMessage.setTitle("new message");
                        Log.e("uid",
                                "getIntentData" + getIntentData().getInt("to_uid"));
                        Log.e("content", "content" + edit.getText().toString());
                        if (message.createNew(createMessage)) {
                            loadingView.error("发送成功", edit);
                            getIntentData().putString(TIPS, "发送成功");
                            WeiboSendActivity.this.finish();
                        } else {
                            loadingView.error("发送失败", edit);
                        }
                        break;
                }

            } catch (VerifyErrorException e) {
                // clearSendingButtonAnim(getCustomTitle().getRight());
                loadingView.error(e.getMessage(), edit);
            } catch (ApiException e) {
                // clearSendingButtonAnim(getCustomTitle().getRight());
                loadingView.error(e.getMessage(), edit);
            } catch (UpdateException e) {
                // clearSendingButtonAnim(getCustomTitle().getRight());
                loadingView.error(e.getMessage(), edit);
            } catch (DataInvalidException e) {
                // clearSendingButtonAnim(getCustomTitle().getRight());
                loadingView.error(e.getMessage(), edit);
            }
            thread.quit();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.transpond;
    }

    @Override
    public OnClickListener getRightListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                // sendingButtonAnim(v);
                Thinksns app = (Thinksns) WeiboSendActivity.this
                        .getApplicationContext();
                thread = new Worker(app, "Publish data");
                handler = new ActivityHandler(thread.getLooper(),
                        WeiboSendActivity.this);
                Message msg = handler.obtainMessage(getIntentData().getInt(
                        "send_type"));
                handler.sendMessage(msg);
            }
        };
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new RightIsButton(this, this.getString(R.string.sendMessage));
    }

    @Override
    public int getRightRes() {
        // TODO Auto-generated method stub
        return R.drawable.button_send;
    }

    @Override
    public String getTitleCenter() {
        switch (getIntentData().getInt("send_type")) {
            case TRANSPOND:
                return this.getString(R.string.transpond);
            case COMMENT:
                return this.getString(R.string.comment);
            case REPLY_MESSAGE:
                return this.getString(R.string.private_letter);
            case CREATE_MESSAGE:
                return this.getString(R.string.private_letter);
        }
        return null;
    }
}
