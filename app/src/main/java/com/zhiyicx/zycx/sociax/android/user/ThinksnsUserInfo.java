package com.zhiyicx.zycx.sociax.android.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.android.ThinksnsUserWeibo;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboSendActivity;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboCreateActivity;
import com.zhiyicx.zycx.sociax.adapter.FollowListAdapter;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.component.LoadingView;
import com.zhiyicx.zycx.sociax.component.MyTextView;
import com.zhiyicx.zycx.sociax.component.NumberButton;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.UserDataInvalidException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.unit.Anim;
import com.zhiyicx.zycx.sociax.unit.Compress;
import com.zhiyicx.zycx.sociax.unit.ImageUtil;

public class ThinksnsUserInfo extends ThinksnsAbscractActivity {
    private static NumberButton following; // 关注
    private static NumberButton follower;
    private static NumberButton weibos;
    private TextView username;
    private TextView userGen;
    private TextView userIntro;
    private TextView location;
    private TextView userTag;
    private TextView department;

    private TextView tel;
    private TextView userPhone;
    private TextView userEmail;

    private Button followButton;
    private Button editButton;
    private Button blackButton;

    private static final int DEFAULT = 0;
    private static final String DEFAULT_NULL = " ";
    private static final String TAG = "UserInfo";
    private static ResultHandler resultHandler;
    private static ImageView header;
    private static TextView blackList;
    private TextView contactBtn;
    private User userCache;
    private static ActivityHandler handler;
    private static boolean refreshing = false;
    private static Drawable oldDrawable;

    private LoadingView loadingView;

    public static enum FollowedStatus {
        YES, NO
    }

    public static enum BlackListStatus {
        YES, NO
    }

    private static int uid = 0;
    private static String uname = null;
    private LinearLayout linearLayout;
    private static String uName = "";
    private LinearLayout infoUtilLayout;
    private ScrollView utilScroll;

    private TextView handleText;
    private ImageView imgIsExpand;

    private LinearLayout layoutExpand;
    private LinearLayout expandHandler;
    private ProgressDialog prDialog;

    private LoadingView mLoadingView;
    private LinearLayout mLyUserInfoView;

    // /////////////////////////////////////////////////////
    private static final int ADD_FOLLOWED = 0;
    private static final int DEL_FOLLOWED = 1;
    private static final int LOAD_USER_INFO = 2;
    private static final int ADD_BLACKLIST = 3;
    private static final int DEL_BLACKLIST = 4;
    private static final int ADD_CONTACT = 5;
    private static final int DEL_CONTACT = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (this.isInTab()) {
            super.onCreateDefault(savedInstanceState);
        } else {
            super.onCreate(savedInstanceState);
        }

        mLoadingView = (LoadingView) findViewById(LoadingView.ID);
        mLyUserInfoView = (LinearLayout) findViewById(R.id.user_info_view);
        editButton = (Button) findViewById(R.id.button_edit);
        following = (NumberButton) findViewById(R.id.followers);
        follower = (NumberButton) findViewById(R.id.followeds);
        weibos = (NumberButton) findViewById(R.id.weibos);
        username = (TextView) findViewById(R.id.user_name);
        department = (TextView) findViewById(R.id.text_info_department);
        tel = (TextView) findViewById(R.id.text_info_phone);
        userPhone = (TextView) findViewById(R.id.text_info_phone);
        tel = (TextView) findViewById(R.id.text_info_tel);
        userEmail = (TextView) findViewById(R.id.user_info_email);
        userTag = (TextView) findViewById(R.id.text_info_tag);
        userGen = (TextView) findViewById(R.id.text_gen);
        userIntro = (TextView) findViewById(R.id.text_intro);

        followButton = (Button) findViewById(R.id.button_follow);
        // location = (TextView)findViewById(R.id.user_location);
        header = (ImageView) findViewById(R.id.userInfo_header);
        // linearLayout = (LinearLayout)findViewById(R.id.userinfo_util);
        blackList = (TextView) findViewById(R.id.black_list);
        blackButton = (Button) findViewById(R.id.button_black);
        contactBtn = (TextView) findViewById(R.id.add_contact);

        infoUtilLayout = (LinearLayout) findViewById(R.id.info_util_layout);
        utilScroll = (ScrollView) findViewById(R.id.util_scroll);

        imgIsExpand = (ImageView) findViewById(R.id.img_is_expand);
        expandHandler = (LinearLayout) findViewById(R.id.expand_handler);
        handleText = (TextView) findViewById(R.id.handel_text);
        layoutExpand = (LinearLayout) findViewById(R.id.layout_expand);

        initView();

        mLoadingView.show(mLyUserInfoView);

        followButton.setClickable(false);
        weibos.setClickable(false);
        following.setClickable(false);
        follower.setClickable(false);

        expandHandler.setOnClickListener(new LayoutExpandListener());

        // userWeibo = (TextView) findViewById(R.id.firstweibo);
        setLayoutText();
        Thinksns app = (Thinksns) this.getApplicationContext();
        uid = Thinksns.getMy().getUid();
        uname = Thinksns.getMy().getUserName();
        // app.getUserSql().clear();
        // User temp = new User();
        resultHandler = new ResultHandler();
        Worker thread = new Worker(app, "Loading UserInfo");
        handler = new ActivityHandler(thread.getLooper(), this);

        try {
            userCache = app.getUserSql().getUser("uid=" + uid);
        } catch (UserDataInvalidException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (getIntentData().containsKey("uname")
                && getIntentData().getString("uname") != uname) {
            threadLoadingData();
        } else if (getIntentData().containsKey("uid")
                && getIntentData().getInt("uid") != uid) {
            threadLoadingData();
        } else if (userCache == null) {
            threadLoadingData();
        } else {
            this.setUserInfoView(userCache);
            threadLoadingData();
        }
        setOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.isInTab())
            super.initTitle();

        if (getIntentData().getString("type") != null
                && getIntentData().getString("type").equals("edit")) {
            editButton.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        int utilHeight = infoUtilLayout.getHeight();
        utilScroll.setPadding(0, 0, 0, utilHeight + 10);
    }

    /**
     * 展示用户信息
     *
     * @param user
     */
    private void setUserInfoView(User user) {
        initTitle();

        ThinksnsUserInfo.this.setUserInfoButton();
        follower.setCount(user.getFollowersCount());
        following.setCount(user.getFollowedCount());
        weibos.setCount(user.getWeiboCount());

        username.setText(user.getUserName());
        userGen.setText("性别：" + user.getSex());
        department.setText("地区：" + user.getLocation());
        userPhone.setText("手机：" + user.getUserPhone());
        tel.setText("座机：" + user.getTel());
        userEmail.setText("邮箱：" + user.getUserEmail());
        userTag.setText("标签：" + user.getUserTag());
        userIntro.setText("简介：" + user.getIntro());
        uName = user.getUserName();
        followButton.setVisibility(View.GONE);
        if (user.getIsMyContact() == 0) {
            contactBtn.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.menu_add_contact_img, 0, 0);
            contactBtn.setTag(0);
        } else {
            contactBtn.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.menu_del_contact_img, 0, 0);
            contactBtn.setTag(1);
        }

        // TODO 添加一些扩展字段
        List<String[]> temp = user.getOtherFiled();
        if (temp != null) {
            layoutExpand.removeAllViews();
            for (String[] strings : temp) {
                MyTextView tv = new MyTextView(this);
                if (strings[0] != null && !(strings[0].equals("null"))) {
                    tv.setText(strings[0] + " : " + strings[1]);
                    layoutExpand.addView(tv);
                }
            }
        }
        loadHeader(user);
        buttonClickable(true);
    }

    private void buttonClickable(boolean clickable) {
        refreshing = !clickable;

        if (this.isInTab()) {
            if (refreshing) {
                // 设置动画
                Anim.refresh(this, this.getCustomTitle().getRight(),
                        R.drawable.spinner_black_60);
                oldDrawable = this.getCustomTitle().getRight().getBackground();
            } else {
                // 取消动画
                this.getCustomTitle().getRight().clearAnimation();
                this.getCustomTitle().getRight()
                        .setBackgroundResource(R.drawable.button_refresh);
            }
        }

        if (clickable) {
            Thinksns app = (Thinksns) ThinksnsUserInfo.this
                    .getApplicationContext();

            if ((getIntentData().containsKey("uid")
                    && getIntentData().getInt("uid") != Thinksns.getMy()
                    .getUid() && getIntentData().getInt("uid") != 0)
                    || (getIntentData().containsKey("uname")
                    && !getIntentData().getString("uname").equals(
                    Thinksns.getMy().getUserName()) && getIntentData()
                    .getString("uname") != null)) {
                followButton.setVisibility(View.VISIBLE);
                infoUtilLayout.setVisibility(View.VISIBLE);
            }

        } else {
            followButton.setVisibility(View.GONE);
        }
        followButton.setClickable(clickable);
        weibos.setClickable(clickable);
        following.setClickable(clickable);
        follower.setClickable(clickable);
        /*
		 * if (userWeibo.getVisibility() == View.VISIBLE) {
		 * userWeibo.setClickable(clickable); }
		 */
    }

    private void setOnClick() {
        /**
         * 关注
         */
        following.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntentData().putInt("type", FollowListAdapter.FOLLOWING);
                getIntentData().putString("data", userCache.toJSON());
                if (userCache.getUserJson() != null) {
                    getIntentData().putString("data", userCache.getUserJson());
                } else {
                    Log.e("user", "user" + userCache.toJSON());
                    getIntentData().putString("data", userCache.toJSON());
                }

                Thinksns app = (Thinksns) ThinksnsUserInfo.this
                        .getApplicationContext();
                app.startActivity(getTabActivity(), ThinksnsFollow.class,
                        getIntentData());
            }
        });
        // 粉丝
        follower.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntentData().putInt("type", FollowListAdapter.FOLLOWER);
                if (userCache.getUserJson() != null) {
                    getIntentData().putString("data", userCache.getUserJson());
                } else {
                    getIntentData().putString("data", userCache.toJSON());
                }
                Thinksns app = (Thinksns) ThinksnsUserInfo.this
                        .getApplicationContext();
                app.startActivity(getTabActivity(), ThinksnsFollow.class,
                        getIntentData());
            }

        });

        followButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                v.setClickable(false);
                Message msg = handler.obtainMessage();
                if ((ThinksnsUserInfo.FollowedStatus) v.getTag() == ThinksnsUserInfo.FollowedStatus.YES) {
                    msg.what = DEL_FOLLOWED;
                } else {
                    msg.what = ADD_FOLLOWED;
                }
                User user = new User();
                user.setUid(userCache.getUid());
                msg.obj = user;
                handler.sendMessage(msg);
            }
        });

        weibos.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getIntentData().putInt("uid", userCache.getUid());
                getIntentData().putString("uname", userCache.getUserName());
                Thinksns app = (Thinksns) ThinksnsUserInfo.this
                        .getApplicationContext();
                app.startActivity(getTabActivity(), ThinksnsUserWeibo.class,
                        getIntentData());
            }
        });

        // 编辑
        editButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ThinksnsUserInfo.this)
                        .setTitle("选择头像")
                        .setItems(R.array.camera, new headImageChangeListener())
                        .show();
            }
        });

        userPhone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String phone = userPhone.getText().toString().substring(2);
                if (phone.length() <= 1)
                    return;
                new AlertDialog.Builder(ThinksnsUserInfo.this).setTitle("选择操作")
                        .setItems(R.array.phone, new phoneNumListener()).show();
                System.err.println("phone phone " + phone.length());
            }
        });
        tel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String phone = tel.getText().toString().substring(2);
                if (phone.length() <= 1)
                    return;
                new AlertDialog.Builder(ThinksnsUserInfo.this).setTitle("选择操作")
                        .setItems(R.array.phone, new telNumListener()).show();
                System.err.println("phone phone " + phone.length());
            }
        });

        userEmail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String email = userEmail.getText().toString().substring(3);
                try {
                    // Intent intent = new Intent(Intent.ACTION_SEND);
                    Uri uri = Uri.parse("mailto:" + email);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    // 开始呼叫
                    startActivity(intent);
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getApplicationContext(), "请检查系统是否可以发送邮件",
                            Toast.LENGTH_SHORT).show();
                }
                // new
                // AlertDialog.Builder(ThinksnsUserInfo.this).setTitle("选择操作")
                // .setItems(R.array.phone, new phoneNumListener()).show();
            }
        });
    }

    /**
     * 设置 tool bar 按钮事件
     */
    private void setUserInfoButton() {

        TextView atHe = (TextView) findViewById(R.id.at_he);
        atHe.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String at = "@" + userCache.getUserName() + " ";
                getIntentData().putString("type", "joinTopic");
                getIntentData().putString("topic", at);
                Thinksns app = (Thinksns) ThinksnsUserInfo.this
                        .getApplicationContext();
                app.startActivity(ThinksnsUserInfo.this,
                        WeiboCreateActivity.class, getIntentData());
                Anim.in(ThinksnsUserInfo.this);
            }

        });

        TextView sendMessage = (TextView) findViewById(R.id.send_chat);

        sendMessage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getIntentData().putInt("to_uid", userCache.getUid());
                getIntentData().putInt("send_type",
                        ThinksnsAbscractActivity.CREATE_MESSAGE);
                Thinksns app = (Thinksns) ThinksnsUserInfo.this
                        .getApplicationContext();
                app.startActivity(ThinksnsUserInfo.this,
                        WeiboSendActivity.class, getIntentData());
                Anim.in(ThinksnsUserInfo.this);
            }

        });

        contactBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = handler.obtainMessage();
                if ((Integer) contactBtn.getTag() == 0) {
                    msg.what = ADD_CONTACT;
                } else if ((Integer) contactBtn.getTag() == 1) {
                    msg.what = DEL_CONTACT;
                }
                msg.obj = userCache;
                handler.sendMessage(msg);
            }

        });

        blackList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = handler.obtainMessage();
                if ((ThinksnsUserInfo.BlackListStatus) v.getTag() == ThinksnsUserInfo.BlackListStatus.YES) {
                    msg.what = DEL_BLACKLIST;
                } else {
                    msg.what = ADD_BLACKLIST;
                }
                msg.obj = userCache;
                handler.sendMessage(msg);
            }
        });
    }

    // TODO 展开更多
    class LayoutExpandListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (layoutExpand.getVisibility() == View.GONE) {
                layoutExpand.setVisibility(View.VISIBLE);
                handleText.setText(R.string.see_less);
                imgIsExpand.setImageResource(R.drawable.expand);
            } else {
                handleText.setText(R.string.see_more);
                layoutExpand.setVisibility(View.GONE);
                imgIsExpand.setImageResource(R.drawable.un_expand);
            }
        }
    }

    class userEmailListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            try {

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    @Override
    public OnClickListener getRightListener() {
        // TODO Auto-generated method stub
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                threadLoadingData();
            }
        };
    }

    @Override
    public OnClickListener getLeftListener() {
        if (this.isInTab()) {
            return new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thinksns app = (Thinksns) ThinksnsUserInfo.this
                            .getApplicationContext();
                    app.startActivity(getTabActivity(),
                            WeiboCreateActivity.class, null);
                    Anim.exit(getTabActivity());
                }
            };
        } else {
            return super.getLeftListener();
        }
    }

    @Override
    public int getRightRes() {
        return R.drawable.menu_refresh_img;
    }

    @Override
    protected void initTitle() {
        this.title = setCustomTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.userinfo;
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new LeftAndRightTitle(this);
    }

    @Override
    public boolean isInTab() {
        return this.getIntent().getBooleanExtra("tab", false);
    }

    @Override
    public String getTitleCenter() {
        if (getIntentData().getString("type") != null
                && getIntentData().getString("type").equals("edit")) {
            uName = "我";
        } else {
            uName = "他";
        }
        return uName + getString(R.string.deinfo);
    }

    private void setLayoutText() {
        following.setText(R.string.follow);
        follower.setText(R.string.followed);
        weibos.setText(R.string.weibo);

        following.setCount(DEFAULT);
        follower.setCount(DEFAULT);
        weibos.setCount(DEFAULT);

        username.setText(DEFAULT_NULL);
        // location.setText(DEFAULT_NULL);

    }

    private void threadLoadingData() {
        if (refreshing) {
            Toast.makeText(this, R.string.re_load, Toast.LENGTH_LONG).show();
            return;
        }

        buttonClickable(false);
        resultHandler = new ResultHandler();
        Thinksns app = (Thinksns) this.getApplicationContext();
        Worker thread = new Worker(app, "Loading UserInfo");
        handler = new ActivityHandler(thread.getLooper(), this);
        Message msg = handler.obtainMessage();
        msg.what = LOAD_USER_INFO;
        User user = new User();
        user.setUid(getIntentData().containsKey("uid") ? getIntentData()
                .getInt("uid") : Thinksns.getMy().getUid());
        if (getIntentData().getString("uname") != null) {
            user.setUserName(getIntentData().containsKey("uname") ? getIntentData()
                    .getString("uname") : Thinksns.getMy().getUserName());
        }
        msg.obj = user;
        handler.sendMessage(msg);
    }

	/* ///////////////////////// Handler */// /////////////////////////////

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
            Thinksns app = (Thinksns) this.context.getApplicationContext();
            Api.Friendships friendships = app.getFriendships();
            Api.STContacts stContacts = app.getContact();
            Api.Users userApi = app.getUsers();

            try {
                switch (msg.what) {
                    case ADD_FOLLOWED:
                        newData = friendships.create((User) msg.obj);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = newData;
                        mainMsg.arg1 = msg.what;
                        break;
                    case DEL_FOLLOWED:
                        newData = friendships.destroy((User) msg.obj);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = newData;
                        mainMsg.arg1 = msg.what;
                        break;
                    case ADD_CONTACT:
                        newData = stContacts.contacterCreate((User) msg.obj);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = newData;
                        mainMsg.arg1 = msg.what;
                        break;
                    case DEL_CONTACT:
                        newData = stContacts.contacterDestroy((User) msg.obj);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = newData;
                        mainMsg.arg1 = msg.what;
                        break;
                    case LOAD_USER_INFO:
                        User user = userApi.show((User) msg.obj);
                        if (user.getUid() == Thinksns.getMy().getUid()) {
                            // 添加认证信息
                            user.setToken(Thinksns.getMy().getToken());
                            user.setSecretToken(Thinksns.getMy().getSecretToken());
                            app.getUserSql().updateUser(user);
                        }
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = user;
                        mainMsg.arg1 = msg.what;
                        break;

                    case ADD_BLACKLIST:
                        newData = friendships.addBlackList((User) msg.obj);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = newData;
                        mainMsg.arg1 = msg.what;
                        break;
                    case DEL_BLACKLIST:
                        newData = friendships.delBlackList((User) msg.obj);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = newData;
                        mainMsg.arg1 = msg.what;
                        break;

                    case UPLOAD_FACE:
                        boolean result = userApi.uploadFace((Bitmap) msg.obj,
                                new File(changeListener.getImagePath()));
                        User user2 = userApi.show(userCache);
                        int i = app.getUserSql().updateUserFace(user2);

                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = result;
                        resultHandler.resultUser = user2;
                        mainMsg.arg1 = msg.what;
                        mainMsg.arg2 = i;

                        break;
                }
            } catch (VerifyErrorException e) {
                mainMsg.obj = e.getMessage();
                refreshing = false;
                Log.e(TAG, e.getMessage());
            } catch (ApiException e) {
                Log.d(TAG, " wm " + e.toString());
                mainMsg.obj = e.getMessage();
                refreshing = false;
                Log.e(TAG, e.getMessage());
            } catch (DataInvalidException e) {
                mainMsg.obj = e.getMessage();
                refreshing = false;
                // Log.e(TAG, e.getMessage());
            }
            resultHandler.sendMessage(mainMsg);
        }
    }

    private class ResultHandler extends Handler {
        private static final int SUCCESS = 0;
        private static final int ERROR = 1;

        private User resultUser = null;

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            String info = "";
            if (msg.what == SUCCESS) {
                switch (msg.arg1) {
                    case ADD_FOLLOWED:
                        followButton.setTag(ThinksnsUserInfo.FollowedStatus.YES);
                        followButton
                                .setBackgroundResource(R.drawable.button_is_follow);
                        followButton.setText(R.string.delfollow);
                        info = "关注成功";
                        Toast.makeText(ThinksnsUserInfo.this, info,
                                Toast.LENGTH_SHORT).show();
                        followButton.setClickable(true);
                        break;
                    case DEL_FOLLOWED:
                        followButton.setTag(ThinksnsUserInfo.FollowedStatus.NO);
                        followButton
                                .setBackgroundResource(R.drawable.button_follow);
                        followButton.setText(R.string.addfollow);
                        info = "取消关注成功";
                        Toast.makeText(ThinksnsUserInfo.this, info,
                                Toast.LENGTH_SHORT).show();
                        followButton.setClickable(true);
                        break;
                    case ADD_CONTACT:
                        if ((Boolean) msg.obj) {
                            info = "收藏成功";
                            contactBtn.setCompoundDrawablesWithIntrinsicBounds(0,
                                    R.drawable.menu_del_contact_img, 0, 0);
                            contactBtn.setTag(1);
                            contactBtn.setText(R.string.del_contact_list);
                        } else {
                            info = "操作失败";
                            // contactBtn.setCompoundDrawablesWithIntrinsicBounds(0,
                            // R.drawable.menu_add_contact_img, 0, 0);
                            // contactBtn.setTag(0);
                        }
                        Toast.makeText(ThinksnsUserInfo.this, info,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case DEL_CONTACT:
                        if ((Boolean) msg.obj) {
                            info = "取消收藏成功";
                            contactBtn.setCompoundDrawablesWithIntrinsicBounds(0,
                                    R.drawable.menu_add_contact_img, 0, 0);
                            contactBtn.setTag(0);
                            contactBtn.setText(R.string.add_contact_list);
                        } else {
                            info = "操作失败";
                            // contactBtn.setCompoundDrawablesWithIntrinsicBounds(0,
                            // R.drawable.menu_add_contact_img, 0, 0);
                            // contactBtn.setTag(0);
                        }
                        Toast.makeText(ThinksnsUserInfo.this, info,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case LOAD_USER_INFO:
                        User user = (User) msg.obj;
                        userCache = user;

                        setUserInfoView(user);

                        if (user.isFollowed()) {
                            followButton
                                    .setTag(ThinksnsUserInfo.FollowedStatus.YES);
                            followButton
                                    .setBackgroundResource(R.drawable.button_is_follow);
                            followButton.setText(R.string.delfollow);
                        } else {
                            followButton.setTag(ThinksnsUserInfo.FollowedStatus.NO);
                            followButton
                                    .setBackgroundResource(R.drawable.button_follow);
                            followButton.setText(R.string.addfollow);
                        }

                        if (user.getIsInBlackList()) {
                            followButton.setVisibility(View.GONE);
                        }
                        mLoadingView.hide(mLyUserInfoView);
                        break;
                    case ADD_BLACKLIST:
                        blackList.setTag(ThinksnsUserInfo.BlackListStatus.YES);
                        blackList.setText("解除黑名单");
                        blackList.setCompoundDrawablesWithIntrinsicBounds(0,
                                R.drawable.userinfo_blacklist, 0, 0);
                        info = "已加入黑名单";
                        Toast.makeText(ThinksnsUserInfo.this, info,
                                Toast.LENGTH_SHORT).show();

                        followButton.setVisibility(View.GONE);
                        blackButton.setVisibility(View.VISIBLE);
                        break;
                    case DEL_BLACKLIST:
                        blackList.setTag(ThinksnsUserInfo.BlackListStatus.NO);
                        blackList.setText("加入黑名单");
                        blackList.setCompoundDrawablesWithIntrinsicBounds(0,
                                (R.drawable.menu_black_img), 0, 0);
                        info = "已从黑名单取消";
                        Toast.makeText(ThinksnsUserInfo.this, info,
                                Toast.LENGTH_SHORT).show();
                        followButton
                                .setBackgroundResource(R.drawable.button_follow);
                        followButton.setText(R.string.addfollow);
                        followButton.setVisibility(View.VISIBLE);

                        blackButton.setVisibility(View.GONE);
                        break;
                    case UPLOAD_FACE:
                        loadingView.setVisibility(View.GONE);
                        boolean result = (Boolean) msg.obj;
                        if (result && msg.arg2 > 0) {
                            if (resultUser != null) {
                                loadHeader(resultUser);
                                info = "上传成功";
                            }
                        } else {
                            info = "上传失败";
                        }
                        Toast.makeText(ThinksnsUserInfo.this, info,
                                Toast.LENGTH_SHORT).show();
                        prDialog.dismiss();
                }
            } else {
                info = (String) msg.obj;
                Toast.makeText(ThinksnsUserInfo.this, info, Toast.LENGTH_SHORT)
                        .show();
                followButton.setClickable(false);
            }

        }
    }

    public void loadHeader(User user) {
        header.setTag(user);
        Thinksns app = (Thinksns) ThinksnsUserInfo.this.getApplicationContext();
        if (/* user.hasHeader()&& */app.isNetWorkOn()) {
            if (user.isNullForHeaderCache()) {
                dowloaderTask(user.getUserface(), header,
                        BitmapDownloaderTask.Type.FACE);
            } else {
                Bitmap cache = user.getHeader();
                if (cache == null) {
                    dowloaderTask(user.getUserface(), header,
                            BitmapDownloaderTask.Type.FACE);
                } else {
                    header.setImageBitmap(cache);
                }
            }
        }
    }

    final protected void dowloaderTask(String url, ImageView image,
                                       BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }

    protected Activity getTabActivity() {
        return this;
    }

    class phoneNumListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String phone = userPhone.getText().toString().substring(3).trim();
            switch (which) {
                case 0:
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("smsto:" + phone));
                    sendIntent.putExtra("address", phone);
				/*
				 * sendIntent.putExtra("sms_body", "");
				 * sendIntent.setType("vnd.android-dir/mms-sms");
				 * startActivity(sendIntent);
				 */
                    startActivity(sendIntent);
                    break;
                case 1:
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"
                            + phone));
                    // 开始呼叫
                    startActivity(intent);
                    break;
                default:
                    dialog.dismiss();
            }
        }
    }

    class telNumListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String phone = tel.getText().toString().substring(3).trim();
            switch (which) {
                case 0:
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("smsto:" + phone));
                    sendIntent.putExtra("address", phone);
				/*
				 * sendIntent.putExtra("sms_body", "");
				 * sendIntent.setType("vnd.android-dir/mms-sms");
				 * startActivity(sendIntent);
				 */
                    startActivity(sendIntent);
                    break;
                case 1:
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"
                            + phone));
                    // 开始呼叫
                    startActivity(intent);
                    break;
                default:
                    dialog.dismiss();
            }
        }
    }

    private void startProgressDialog() {
        if (prDialog == null) {
            prDialog = new ProgressDialog(this);
            prDialog.setMessage("正在上传...");
        }
        prDialog.show();
    }

    // ///////////////////********** 相片处理 **************************///////////

    private static final int LOCATION = 1;
    private static final int CAMERA = 0;
    private static final int UPLOAD_FACE = 11;

    private headImageChangeListener changeListener;
    private static final int IO_BUFFER_SIZE = 4 * 1024;
    private boolean hasImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Bitmap btp = null;
            switch (requestCode) {
                case CAMERA:
                    try {
                        startPhotoZoom(Uri.fromFile(new File(changeListener
                                .getImagePath())));
                    } catch (Exception e) {
                        Log.e(TAG, "file saving..." + e.toString());
                    }
                    break;
                case LOCATION:
                    btp = checkImage(data);
                    startPhotoZoom(data.getData());
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            btp = extras.getParcelable("data");
                            Log.d(TAG, "sava cut ....");
                            Message msg = handler.obtainMessage();
                            msg.what = UPLOAD_FACE;
                            msg.arg1 = UPLOAD_FACE;
                            msg.obj = btp;
                            loadingView = (LoadingView) findViewById(LoadingView.ID);
                            startProgressDialog();
                            handler.sendMessage(msg);
                        }
                    } else {
                        Log.d(TAG, "data is null  .... ");
                    }
                    break;
            }
            if (btp != null) {
                this.hasImage = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        // TODO 裁剪图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private Bitmap checkImage(Intent data) {
        if (changeListener == null)
            changeListener = new headImageChangeListener();
        Bitmap bitmap = null;
        try {
            Uri originalUri = data.getData();
            String path = getRealPathFromURI(originalUri);

            path = path.substring(path.indexOf("/sdcard"), path.length());
            Log.d(TAG, "imagePath" + path);
            bitmap = Compress.compressPicToBitmap(new File(path));
            if (bitmap != null) {
                changeListener.setImagePath(path);
            }

        } catch (Exception e) {
            Log.e("checkImage", e.getMessage());
        } finally {
            return bitmap;
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        String result = contentUri.toString();
        String[] proj =
                {MediaColumns.DATA};
        cursor = managedQuery(contentUri, proj, null, null, null);
        if (cursor == null)
            throw new NullPointerException("reader file field");
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            result = cursor.getString(column_index);
            try {
                // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
                if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                    cursor.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "error:" + e);
            }
        }
        return result;
    }

    /**
     * 照片来源
     */
    class headImageChangeListener implements DialogInterface.OnClickListener {
        private String imagePath = "";

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    cameraImage();
                    break;
                case 1:
                    locationImage();
                    break;
                default:
                    dialog.dismiss();
            }
        }

        private void locationImage() {
            Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
            getImage.addCategory(Intent.CATEGORY_OPENABLE);
            getImage.setType("image/*");
            startActivityForResult(Intent.createChooser(getImage, "选择照片"), 1);

        }

        // 获取相机拍摄图片
        private void cameraImage() {
            if (!ImageUtil.isHasSdcard()) {
                // Toast.makeText(this.ThinksnsCreate,"" ,T );//.show();
                Toast.makeText(ThinksnsUserInfo.this, "使用相机前先插入SD卡",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (changeListener == null)
                changeListener = new headImageChangeListener();
            // 启动相机
            Intent myIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            String picName = System.currentTimeMillis() + ".jpg";
            try {
                String path = ImageUtil.saveFilePaht(picName);
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                changeListener.setImagePath(path);
                myIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "file saving...");
            }
            startActivityForResult(myIntent, 0);
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }

}
