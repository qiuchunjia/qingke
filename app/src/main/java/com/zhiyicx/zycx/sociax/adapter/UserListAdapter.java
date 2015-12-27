package com.zhiyicx.zycx.sociax.adapter;

import java.util.HashMap;
import java.util.Map;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.android.user.ThinksnsUserInfo;
import com.zhiyicx.zycx.sociax.api.Api;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.api.ApiUsers;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.unit.ListViewAppend;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserListAdapter extends SociaxListAdapter {
    private static String TAG = "FollowList";
    public static final int FOLLOWER = 0;
    public static final int FOLLOWED = 1;

    private static ImageView userheader;
    private static TextView username;
    private static TextView lastWeibo;
    private Button followButton;
    private static ListViewAppend append;
    private static String key;
    private ThinksnsAbscractActivity context;
    private static final int ADD_FOLLOWED = 0;
    private static final int DEL_FOLLOWED = 1;
    private ResultHandler resultHandler;
    private static ActivityHandler handler;
    private Map<Integer, View> viewMap = new HashMap<Integer, View>();

    @Override
    public User getFirst() {
        return (User) super.getFirst();
    }

    @Override
    public User getLast() {
        return (User) super.getLast();
    }

    @Override
    public User getItem(int position) {
        return (User) super.getItem(position);
    }

    public UserListAdapter(ThinksnsAbscractActivity context,
                           ListData<SociaxItem> data) {
        super(context, data);
        this.context = context;
        Worker worker = new Worker((Thinksns) context.getApplicationContext(),
                "User");
        handler = new ActivityHandler(worker.getLooper(), context);
    }

    public UserListAdapter(ThinksnsAbscractActivity context,
                           ListData<SociaxItem> data, String key) {
        this(context, data);
        UserListAdapter.key = key;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("UserListAdapter", position + "");
        UserView userView;
        View rowView = this.viewMap.get(position);
        resultHandler = new ResultHandler();
        /*
		 * if(position == this.list.size()-1 && refresh.isClickable()){
		 * doRefreshFooter(); }
		 */
        if (rowView == null) {
            User tempUser = this.getItem(position);
            userView = new UserView();
            rowView = this.inflater.inflate(R.layout.follow_item, null);
            userView.userHeader = (ImageView) rowView
                    .findViewById(R.id.user_header);
            userView.username = (TextView) rowView.findViewById(R.id.username);
            userView.department = (TextView) rowView
                    .findViewById(R.id.department);
            userView.followButton = (Button) rowView
                    .findViewById(R.id.follow_button);
            rowView.setTag(userView);
            userView.username.setText(tempUser.getUserName());
            userView.department.setText(tempUser.getDepartment());
            this.loadingHeader(tempUser, userView.userHeader);
            final int uid = tempUser.getUid();
            final int nowPosition = position;
            if (tempUser.isFollowed()) {
                userView.followButton
                        .setBackgroundResource(R.drawable.minus_follow_list);
                userView.followButton.setText(R.string.delfollow);
                userView.followButton
                        .setTag(ThinksnsUserInfo.FollowedStatus.YES);
            } else {
                userView.followButton
                        .setBackgroundResource(R.drawable.plus_follow_list);
                userView.followButton.setText(R.string.addfollow);
                userView.followButton
                        .setTag(ThinksnsUserInfo.FollowedStatus.NO);
            }
            userView.followButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getMyUid() == uid) {
                        Toast.makeText(UserListAdapter.this.context, "你不能关注自己",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Message msg = handler.obtainMessage();
                    msg.arg1 = uid;
                    msg.arg2 = nowPosition;
                    if ((ThinksnsUserInfo.FollowedStatus) v.getTag() == ThinksnsUserInfo.FollowedStatus.YES) {
                        msg.what = DEL_FOLLOWED;
                    } else {
                        msg.what = ADD_FOLLOWED;
                    }
                    msg.obj = v;
                    handler.sendMessage(msg);
                }
            });
            viewMap.put(position, rowView);
        } else {
            userView = (UserView) rowView.getTag();
        }
        return rowView;
    }

    private void loadingHeader(User user, ImageView imageView) {
        imageView.setTag(user);
        Thinksns app = (Thinksns) this.context.getApplicationContext();
        if (/* user.hasHeader()&& */app.isNetWorkOn()) {
            if (user.isNullForHeaderCache()) {
                dowloaderTask(user.getUserface(), imageView,
                        BitmapDownloaderTask.Type.FACE);
            } else {
                Bitmap cache = user.getHeader();
                if (cache == null) {
                    dowloaderTask(user.getUserface(), imageView,
                            BitmapDownloaderTask.Type.FACE);
                } else {
                    imageView.setImageBitmap(cache);
                }
            }
        }
    }

    final protected void dowloaderTask(String url, ImageView image,
                                       BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().searchHeaderUser(key, (User) obj, PAGE_COUNT);
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
        return getApiUser().getUserFollower();
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

    private ApiUsers getApiUser() {
        Thinksns app = thread.getApp();
        return app.getUsers();
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
            Thinksns app = (Thinksns) this.context.getApplicationContext();
            Api.Friendships friendships = app.getFriendships();
            User user = new User();
            user.setUid(msg.arg1);

            try {
                switch (msg.what) {
                    case ADD_FOLLOWED:
                        newData = friendships.create(user);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = msg.obj;
                        mainMsg.arg1 = msg.what;
                        break;
                    case DEL_FOLLOWED:
                        newData = friendships.destroy(user);
                        mainMsg.what = ResultHandler.SUCCESS;
                        mainMsg.obj = msg.obj;
                        mainMsg.arg1 = msg.what;
                        break;
                }
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
            if (msg.what == SUCCESS) {
                Button button = (Button) msg.obj;
                switch (msg.arg1) {
                    case ADD_FOLLOWED:
                        button.setTag(ThinksnsUserInfo.FollowedStatus.YES);
                        button.setBackgroundResource(R.drawable.minus_follow_list);
                        button.setText(R.string.delfollow);
                        info = "关注成功";
                        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        button.setClickable(true);
                        break;
                    case DEL_FOLLOWED:
                        button.setTag(ThinksnsUserInfo.FollowedStatus.NO);
                        button.setBackgroundResource(R.drawable.plus_follow_list);
                        button.setText(R.string.addfollow);
                        info = "取消关注成功";
                        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        button.setClickable(true);
                        break;
                }
            } else {
                info = (String) msg.obj;
            }

        }
    }

    public class UserView {
        TextView username;
        TextView department;
        ImageView userHeader;
        Button followButton;

    }
}
