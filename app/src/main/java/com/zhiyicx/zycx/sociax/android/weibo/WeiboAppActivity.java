package com.zhiyicx.zycx.sociax.android.weibo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.adapter.AtomAdapter;
import com.zhiyicx.zycx.sociax.adapter.CommentMyListAdapter;
import com.zhiyicx.zycx.sociax.adapter.CommentSendByMeAdapter;
import com.zhiyicx.zycx.sociax.adapter.FavoriteAdapter;
import com.zhiyicx.zycx.sociax.adapter.FollowListAdapter;
import com.zhiyicx.zycx.sociax.adapter.SearchWeiboListAdapter;
import com.zhiyicx.zycx.sociax.adapter.SociaxListAdapter;
import com.zhiyicx.zycx.sociax.adapter.UserListAdapter;
import com.zhiyicx.zycx.sociax.adapter.UserWeiboListAdapter;
import com.zhiyicx.zycx.sociax.adapter.WeiboListAdapter;
import com.zhiyicx.zycx.sociax.component.CommentMyList;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.EditCancel;
import com.zhiyicx.zycx.sociax.component.FollowList;
import com.zhiyicx.zycx.sociax.component.LeftAndRightTitle;
import com.zhiyicx.zycx.sociax.component.SearchUserList;
import com.zhiyicx.zycx.sociax.component.SearchWeiboList;
import com.zhiyicx.zycx.sociax.component.SociaxList;
import com.zhiyicx.zycx.sociax.component.WeiboList;
import com.zhiyicx.zycx.sociax.gimgutil.ImageFetcher;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;

public class WeiboAppActivity extends ThinksnsAbscractActivity implements
        OnKeyListener, OnCheckedChangeListener {

    private static final String TAG = "WeiboAppActivity";

    private TextView weiboNew;
    private TextView weiboAtme;
    private TextView weiboComment;
    private TextView weiboSearch;
    private TextView weiboCollection;
    private TextView weiboFollower;
    private TextView weiboFollowing;
    private TextView weiboMy;
    private TextView weiboMyComment;
    private TextView weiboMore;

    private View menuView;
    private LinearLayout searchLayout;

    private SociaxList dataList;
    private SociaxListAdapter adapter;

    private WeiboListAdapter weiboAdapter;
    private WeiboList weibolist;

    private CommentMyList commentList;
    private CommentMyListAdapter commentAdapter;

    private WeiboList atomList;
    private AtomAdapter atomAdapter;

    // private FavoriteList favoriteList;
    private WeiboList favoriteList;
    private FavoriteAdapter favoriteAdapter;

    private FollowList followerList;
    private FollowListAdapter followerAdapter;

    private FollowList followingList;
    private FollowListAdapter followingAdapter;

    private WeiboListAdapter userWeiboAdapter;
    private WeiboList userWeiboList;

    //private CommentMyList commentSendList;
    private CommentSendByMeAdapter commentSendAdapter;

    private Thinksns app;
    private String clickType = "weiboNew";

    private HorizontalScrollView horiz;
    private LinearLayout lin;
    private ImageView scrollLeft;
    private ImageView scrollRight;

    private User userCache;

    // //////////********************search********************//////////////

    private static RadioButton searchWeibo;
    private static RadioButton searchUser;
    private static EditCancel edit;
    private static SociaxListAdapter searchAdapter;
    private static Type status;
    private static final int LISTVIEW_ID = 186;

    private static SearchWeiboList weiboList;
    private static SearchUserList userList;
    private static Button goForSearch;
    private static LinearLayout layout;

    // 缓存
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageFetcher mImageFetcher;

    private enum Type {
        WEIBO, USER
    }

    // ////////********************search********************//////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (Thinksns) this.getApplicationContext();

        initView(); // 初始化view
        setInitType(); // 设置默认选中
        serarchInit(); // 初始化搜索

        weiboNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickType = "weiboNew";
                boolean isRefresh = weiboNew.isSelected();
                setTextPressed(weiboNew);
                initTitle();

                if (weiboAdapter == null) {
                    loadWeibo(true);
                    Log.d(TAG, "weiboNew adapter is null ...");
                }
                adapter = weiboAdapter;
                Log.d(TAG, "weiboNew adapter is " + adapter);

                atomList.setVisibility(View.GONE);
                weibolist.setVisibility(View.VISIBLE);
                commentList.setVisibility(View.GONE);
                favoriteList.setVisibility(View.GONE);
                followerList.setVisibility(View.GONE);
                followingList.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                userWeiboList.setVisibility(View.GONE);
                //commentSendList.setVisibility(View.GONE);

                dataList = weibolist;
                if (isRefresh)
                    refreshHeader();
                showData();
            }
        });

        weiboAtme.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickType = "atme";
                boolean isRefresh = weiboAtme.isSelected();
                setTextPressed(weiboAtme);
                initTitle();

                if (atomAdapter == null) {
                    loadAtme(false);
                    Log.d(TAG, "atom adapter is null ...");
                }
                adapter = atomAdapter;
                Log.d(TAG, "atom adapter is " + adapter);

                atomList.setVisibility(View.VISIBLE);
                weibolist.setVisibility(View.GONE);
                commentList.setVisibility(View.GONE);
                favoriteList.setVisibility(View.GONE);
                followerList.setVisibility(View.GONE);
                followingList.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                userWeiboList.setVisibility(View.GONE);
                //commentSendList.setVisibility(View.GONE);

                dataList = atomList;
                if (isRefresh)
                    refreshHeader();
                showData();
                atomList.invalidate();
            }
        });

        weiboComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickType = "comment";
                boolean isRefresh = weiboComment.isSelected();
                setTextPressed(weiboComment);
                initTitle();

                if (commentAdapter == null) {
                    loadComment(false);
                    Log.d(TAG, "comment adapter is null ...");
                }
                adapter = commentAdapter;
                Log.d(TAG, "comment adapter is " + adapter);

                atomList.setVisibility(View.GONE);
                weibolist.setVisibility(View.GONE);
                commentList.setVisibility(View.VISIBLE);
                favoriteList.setVisibility(View.GONE);
                followerList.setVisibility(View.GONE);
                followingList.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                userWeiboList.setVisibility(View.GONE);
                //commentSendList.setVisibility(View.GONE);

                dataList = commentList;
                if (isRefresh)
                    refreshHeader();
                showData();
                commentList.invalidate();
            }
        });

        weiboSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickType = "search";
                setTextPressed(weiboSearch);
                initTitle();

                atomList.setVisibility(View.GONE);
                weibolist.setVisibility(View.GONE);
                commentList.setVisibility(View.GONE);
                favoriteList.setVisibility(View.GONE);
                followerList.setVisibility(View.GONE);
                followingList.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                userWeiboList.setVisibility(View.GONE);
                //commentSendList.setVisibility(View.GONE);
            }
        });

        weiboCollection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickType = "collection";
                boolean isRefresh = weiboCollection.isSelected();
                setTextPressed(weiboCollection);
                initTitle();

                if (favoriteAdapter == null) {
                    loadFavorite(false);
                    Log.d(TAG, "favorite adapter is null ...");
                }
                adapter = favoriteAdapter;
                Log.d(TAG, "favorite adapter is " + adapter);

                atomList.setVisibility(View.GONE);
                weibolist.setVisibility(View.GONE);
                commentList.setVisibility(View.GONE);
                favoriteList.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.GONE);
                followerList.setVisibility(View.GONE);
                followingList.setVisibility(View.GONE);
                userWeiboList.setVisibility(View.GONE);
                //commentSendList.setVisibility(View.GONE);

                favoriteList.invalidate();
                dataList = favoriteList;
                if (isRefresh)
                    refreshHeader();
                showData();

            }
        });

        weiboFollower.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickType = "follower";
                setTextPressed(weiboFollower);
                initTitle();

                if (followerAdapter == null) {
                    loadFollower();
                    Log.d(TAG, "favorite adapter is null ...");
                }
                adapter = followerAdapter;
                Log.d(TAG, "favorite adapter is " + adapter);

                atomList.setVisibility(View.GONE);
                weibolist.setVisibility(View.GONE);
                commentList.setVisibility(View.GONE);
                followerList.setVisibility(View.VISIBLE);
                followingList.setVisibility(View.GONE);
                favoriteList.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                userWeiboList.setVisibility(View.GONE);
                //commentSendList.setVisibility(View.GONE);

                followerList.invalidate();
                dataList = followerList;
                showData();

            }
        });

        weiboFollowing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                clickType = "following";
                setTextPressed(weiboFollowing);
                initTitle();

                if (followingAdapter == null) {
                    loadFollowing();
                    Log.d(TAG, "favorite adapter is null ...");
                }
                adapter = followingAdapter;
                Log.d(TAG, "favorite adapter is " + adapter);

                atomList.setVisibility(View.GONE);
                weibolist.setVisibility(View.GONE);
                commentList.setVisibility(View.GONE);
                favoriteList.setVisibility(View.GONE);
                followerList.setVisibility(View.GONE);
                followingList.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.GONE);
                userWeiboList.setVisibility(View.GONE);
                //commentSendList.setVisibility(View.GONE);

                followingList.invalidate();
                dataList = followingList;
                showData();

            }
        });

        weiboMy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                clickType = "myweibo";
                setTextPressed(weiboMy);
                initTitle();

                if (userWeiboAdapter == null) {
                    loadMyweibo();
                    Log.d(TAG, "favorite adapter is null ...");
                }
                adapter = userWeiboAdapter;
                Log.d(TAG, "userweibo adapter is " + adapter);

                atomList.setVisibility(View.GONE);
                weibolist.setVisibility(View.GONE);
                commentList.setVisibility(View.GONE);
                favoriteList.setVisibility(View.GONE);
                followerList.setVisibility(View.GONE);
                followingList.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                userWeiboList.setVisibility(View.VISIBLE);
                //commentSendList.setVisibility(View.GONE);

                userWeiboList.invalidate();
                dataList = userWeiboList;
                showData();

            }
        });

 /*
        weiboMyComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickType = "mycomment";
				setTextPressed(weiboMyComment);
				initTitle();

				if (commentSendAdapter == null) {
					loadCommentSend();
					Log.d(TAG, "comment send adapter is null ...");
				}
				adapter = commentSendAdapter;
				Log.d(TAG, "favorite adapter is " + adapter);

				atomList.setVisibility(View.GONE);
				weibolist.setVisibility(View.GONE);
				commentList.setVisibility(View.GONE);
				favoriteList.setVisibility(View.GONE);
				followerList.setVisibility(View.GONE);
				followingList.setVisibility(View.GONE);
				searchLayout.setVisibility(View.GONE);
				userWeiboList.setVisibility(View.GONE);
				commentSendList.setVisibility(View.VISIBLE);

				commentSendList.invalidate();
				dataList = commentSendList;
				showData();

			}
		});
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Thinksns.getDelIndex() > 0 && adapter instanceof WeiboListAdapter) {
            adapter.deleteItem(Thinksns.getDelIndex());
            adapter.notifyDataSetChanged();
            Thinksns.setDelIndex(0);
            Log.d(TAG,
                    "UserWeibo delete weibo id " + Thinksns.getDelIndex());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showData() {
        int position = 0;
        Bundle temp = null;
        if ((temp = getIntent().getExtras()) != null) {
            position = temp.getInt("position");
        }
        dataList.setSelectionFromTop(position, 20);
    }

    private void loadWeibo(boolean isLoadNew) {
        // 获取数据源
        Thinksns app = (Thinksns) this.getApplicationContext();
        ListData<SociaxItem> tempWeiboDatas = app.getWeiboSql().getWeiboList();
        ListData<SociaxItem> data = new ListData<SociaxItem>();

        //if (tempWeiboDatas.size() != 0 && !isLoadNew)
        if (false) {
            weiboAdapter = new WeiboListAdapter(this, tempWeiboDatas);
            weibolist.setAdapter(weiboAdapter, System.currentTimeMillis(), this);
            dataList = weibolist;
            weiboAdapter.loadInitData();
            adapter = weiboAdapter;
            showData();
            this.refreshHeader();
        } else {
            weiboAdapter = new WeiboListAdapter(this, data);
            weibolist.setAdapter(weiboAdapter, System.currentTimeMillis(), this);
            dataList = weibolist;
            weiboAdapter.loadInitData();
            adapter = weiboAdapter;
            showData();
        }
    }

    private void loadAtme(boolean isLoadNew) {
        // 获取atme数据
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        ListData<SociaxItem> tempAtMeDatas = app.getAtMeSql().getAtMeList();
        //if (tempAtMeDatas.size() != 0 && !isLoadNew)
        if (false) {
            atomAdapter = new AtomAdapter(this, tempAtMeDatas);
        } else {
            atomAdapter = new AtomAdapter(this, data);
        }
        atomList.setAdapter(atomAdapter, System.currentTimeMillis(), this);

        dataList = atomList;
        atomAdapter.loadInitData();
        adapter = atomAdapter;
        showData();
    }

    private void loadComment(boolean isLoadNew) {
        // 获取评论数据
        ListData<SociaxItem> data2 = new ListData<SociaxItem>();
        ListData<SociaxItem> tempAtMeDatas2 = app.getMyCommentSql()
                .getDBCommentList();
        //if (tempAtMeDatas2.size() != 0 && !isLoadNew)
        if (false) {
            commentAdapter = new CommentMyListAdapter(this, tempAtMeDatas2);
        } else {
            commentAdapter = new CommentMyListAdapter(this, data2);
        }
        commentList
                .setAdapter(commentAdapter, System.currentTimeMillis(), this);

        dataList = commentList;
        commentAdapter.loadInitData();
        adapter = commentAdapter;
        showData();
    }

    private void loadFavorite(boolean isLoadNew) {
        // 获取评论数据
        ListData<SociaxItem> tempAtMeDatas2 = app.getFavoritWeiboSql().getWeiboList();
        //if (tempAtMeDatas2.size() != 0 && !isLoadNew)
        if (false) {
            favoriteAdapter = new FavoriteAdapter(this, tempAtMeDatas2);
        } else {
            ListData<SociaxItem> data = new ListData<SociaxItem>();
            favoriteAdapter = new FavoriteAdapter(this, data);
        }
        favoriteList.setAdapter(favoriteAdapter, System.currentTimeMillis(),
                this);
        dataList = favoriteList;
        favoriteAdapter.loadInitData();
        adapter = favoriteAdapter;
        showData();
    }

    private void loadFollower() {
        // 获取评论数据
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        // userType = getIntentData().getInt("type");
        followerAdapter = new FollowListAdapter(this, data, 0,
                (Thinksns.getMy()));
        followerList.setAdapter(followerAdapter, System.currentTimeMillis(),
                this);

        dataList = followerList;
        followerAdapter.loadInitData();
        adapter = followerAdapter;
        showData();
    }

    private void loadFollowing() {
        // 获取评论数据
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        // userType = getIntentData().getInt("type");
        followingAdapter = new FollowListAdapter(this, data, 1,
                (Thinksns.getMy()));
        followingList.setAdapter(followingAdapter, System.currentTimeMillis(),
                this);

        dataList = followingList;
        followingAdapter.loadInitData();
        adapter = followingAdapter;
        showData();
    }

    private void loadMyweibo() {
        // 获取评论数据
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        userWeiboAdapter = new UserWeiboListAdapter(this, data, Thinksns
                .getMy().getUid());
        if (data.size() != 0) {
            userWeiboList.setAdapter(userWeiboAdapter, (long) userWeiboAdapter
                    .getFirst().getTimestamp() * 1000, this);
        } else {
            userWeiboList.setAdapter(userWeiboAdapter,
                    System.currentTimeMillis(), this);
        }

        dataList = userWeiboList;
        userWeiboAdapter.loadInitData();
        adapter = userWeiboAdapter;
        showData();
    }
/*
	private void loadCommentSend() {
		// 获取评论数据
		ListData<SociaxItem> data = new ListData<SociaxItem>();
		commentSendAdapter = new CommentSendByMeAdapter(this, data);
		commentSendList.setAdapter(commentSendAdapter,
				System.currentTimeMillis(), this);

		dataList = commentSendList;
		commentSendAdapter.loadInitData();
		adapter = commentSendAdapter;
		showData();
	}*/

    private void setInitType() {
        Intent intent = getIntent();
        String remindType = intent.getStringExtra("remindType");
        if (remindType == null) {
            clickType = "weiboNew";
            setTextPressed(weiboNew);
            initTitle();
            loadWeibo(false);
        } else if (remindType.equals("atme")) {
            clickType = "atme";
            setTextPressed(weiboAtme);
            atomList.setVisibility(View.VISIBLE);
            weibolist.setVisibility(View.GONE);
            initTitle();
            loadAtme(true);
        } else if (remindType.equals("comment")) {
            clickType = "comment";
            setTextPressed(weiboComment);
            atomList.setVisibility(View.GONE);
            weibolist.setVisibility(View.GONE);
            commentList.setVisibility(View.VISIBLE);
            initTitle();
            loadComment(true);
        }
    }

    private void initView() {

        // horiz = (HorizontalScrollView)
        // findViewById(R.id.weibo_tool_horizontal);
        scrollLeft = (ImageView) findViewById(R.id.scroll_left);
        scrollRight = (ImageView) findViewById(R.id.scroll_right);
        lin = (LinearLayout) findViewById(R.id.weibo_tool_layout);

        weiboNew = (TextView) findViewById(R.id.weibo_app_new);
        weiboAtme = (TextView) findViewById(R.id.weibo_app_atme);
        weiboComment = (TextView) findViewById(R.id.weibo_app_comment);
        weiboSearch = (TextView) findViewById(R.id.weibo_app_search);
        weiboCollection = (TextView) findViewById(R.id.weibo_app_collection);
        weiboFollower = (TextView) findViewById(R.id.weibo_app_follower);
        weiboFollowing = (TextView) findViewById(R.id.weibo_app_following);
        weiboMy = (TextView) findViewById(R.id.weibo_app_my);
        weiboMyComment = (TextView) findViewById(R.id.weibo_app_send_com);

        weiboNew.setTag("weibo_app_new_p");
        weiboAtme.setTag("weibo_app_atme_p");
        weiboComment.setTag("weibo_app_comment_p");
        weiboSearch.setTag("weibo_app_search_p");
        weiboCollection.setTag("weibo_app_collection_p");
        weiboFollower.setTag("weibo_follower_p");
        weiboFollowing.setTag("weibo_following_p");
        weiboMy.setTag("weibo_my_p");
        weiboMyComment.setTag("weibo_my_com_p");

        weibolist = (WeiboList) findViewById(R.id.weiboList_home);
        atomList = (WeiboList) findViewById(R.id.atom);
        commentList = (CommentMyList) findViewById(R.id.comment);
        favoriteList = (WeiboList) findViewById(R.id.favorite);
        followerList = (FollowList) findViewById(R.id.follower_list);
        followingList = (FollowList) findViewById(R.id.following_list);
        userWeiboList = (WeiboList) findViewById(R.id.user_weibo_list);
        //commentSendList = (CommentMyList) findViewById(R.id.comment_send_list);

        searchLayout = (LinearLayout) findViewById(R.id.search_layout);

        // horiz.setOnTouchListener(new OnTouchListener() {
        //
        // @Override
        // public boolean onTouch(View v, MotionEvent event) {
        //
        // int j = horiz.getWidth();
        // int k = horiz.getScrollX();
        // int m = lin.getWidth();
        //
        // scrollLeft.setVisibility(0);
        // scrollRight.setVisibility(0);
        // if (k < 20)
        // scrollLeft.setVisibility(View.INVISIBLE);
        // if (k + j > m-20)
        // scrollRight.setVisibility(4);
        //
        // return false;
        // }
        // });

    }

    // ////////////tab change //////

    private void setTextPressed(View view) {
        TextView[] viewArray = {weiboNew, weiboAtme, weiboComment,
                weiboSearch, weiboCollection, weiboFollower, weiboFollowing,
                weiboMy, weiboMyComment};
        for (TextView view2 : viewArray) {
            if (view2.equals(view)) {
                view2.setBackgroundResource(R.drawable.weibo_app_bar_p);
                setTextPressedImg(view2);
                view2.setTextColor(Color.WHITE);
                view2.setSelected(true);
                view2.invalidate();
            } else {
                view2.setBackgroundResource(R.drawable.weibo_app_bar_n);
                setTextUnPressedImg(view2);
                view2.setTextColor(getResources().getColor(
                        R.color.weibo_app_bar_text));
                view2.setSelected(false);
                view.invalidate();
            }
        }
    }

    private void setTextPressedImg(View view) {
        if (view.equals(weiboNew)) {
            weiboNew.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_new_p, 0, 0);
        } else if (view.equals(weiboAtme)) {
            weiboAtme.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_atme_p, 0, 0);
        } else if (view.equals(weiboComment)) {
            weiboComment.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_comment_p, 0, 0);
        } else if (view.equals(weiboSearch)) {
            weiboSearch.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_search_p, 0, 0);
        } else if (view.equals(weiboCollection)) {
            weiboCollection.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_collection_p, 0, 0);
        } else if (view.equals(weiboFollower)) {
            weiboFollower.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_follower_p, 0, 0);
        } else if (view.equals(weiboFollowing)) {
            weiboFollowing.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_following_p, 0, 0);
        } else if (view.equals(weiboMy)) {
            weiboMy.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_my_p, 0, 0);
        } else if (view.equals(weiboMyComment)) {
            weiboMyComment.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_my_com_p, 0, 0);
        }
    }

    private void setTextUnPressedImg(View view) {
        if (view.equals(weiboNew)) {
            weiboNew.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_new_n, 0, 0);
        } else if (view.equals(weiboAtme)) {
            weiboAtme.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_atme_n, 0, 0);
        } else if (view.equals(weiboComment)) {
            weiboComment.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_comment_n, 0, 0);
        } else if (view.equals(weiboSearch)) {
            weiboSearch.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_search_n, 0, 0);
        } else if (view.equals(weiboCollection)) {
            weiboCollection.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_app_collection_n, 0, 0);
        } else if (view.equals(weiboFollower)) {
            weiboFollower.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_follower_n, 0, 0);
        } else if (view.equals(weiboFollowing)) {
            weiboFollowing.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_following_n, 0, 0);
        } else if (view.equals(weiboMy)) {
            weiboMy.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_my_n, 0, 0);
        } else if (view.equals(weiboMyComment)) {
            weiboMyComment.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.weibo_my_com_n, 0, 0);
        }
    }

    // ////////////abscractActivity method //////

    @Override
    protected void initTitle() {
        this.title = setCustomTitle();

    }

    @Override
    public OnTouchListListener getListView() {
        return dataList;
    }

    // //////////// abscract method //////

    @Override
    public String getTitleCenter() {
        String centerText = "";
        if (clickType.equals("weiboNew")) {
            centerText = getString(R.string.all_action);
            super.getRightRes();
        } else if (clickType.equals("atme")) {
            centerText = getString(R.string.atme);
        } else if (clickType.equals("comment")) {
            centerText = getString(R.string.comment);
        } else if (clickType.equals("search")) {
            centerText = getString(R.string.search);
        } else if (clickType.equals("collection")) {
            centerText = getString(R.string.collection);
        } else if (clickType.equals("follower")) {
            centerText = getString(R.string.followed);
        } else if (clickType.equals("following")) {
            centerText = getString(R.string.follow);
        } else if (clickType.equals("myweibo")) {
            centerText = getString(R.string.weibo_more_my_weibo);
        } else if (clickType.equals("mycomment")) {
            centerText = getString(R.string.weibo_more_my_commend);
        } else if (clickType.equals("more")) {
            centerText = getString(R.string.more);
        }
        return centerText;
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new LeftAndRightTitle(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.weibo_app3;
    }

    @Override
    public OnClickListener getLeftListener() {
        return super.getRightListener();
    }

    @Override
    public int getLeftRes() {
        return R.drawable.menu_back_img;
    }

    @Override
    public OnClickListener getRightListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thinksns app = (Thinksns) WeiboAppActivity.this
                        .getApplicationContext();
                app.startActivity(WeiboAppActivity.this, WeiboCreateActivity.class, null);
            }
        };
    }

    @Override
    public int getRightRes() {
        return R.drawable.menu_creat_new_img;
    }

    // ////////// 头部和脚步刷新 //////////////////

    @Override
    public void refreshHeader() {
        if (adapter != null)
            adapter.doRefreshHeader();
        Log.d(TAG, "refreshHeader...." + adapter);
    }

    @Override
    public void refreshFooter() {
        adapter.doRefreshFooter();
    }

    // ////////// search //////////////////
    private void serarchInit() {
        searchWeibo = (RadioButton) findViewById(R.id.search_weibo);
        searchUser = (RadioButton) findViewById(R.id.search_user);
        edit = (EditCancel) findViewById(R.id.editCancel1);
        layout = (LinearLayout) findViewById(R.id.search_layout);
        goForSearch = (Button) findViewById(R.id.go_for_search);
        status = Type.WEIBO;
        searchWeibo.setTextColor(Color.WHITE);
        searchWeibo.setOnCheckedChangeListener(this);
        searchUser.setOnCheckedChangeListener(this);
        edit.setOnKeyListener(this);

        goForSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏输入法
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);

                doSearch();
            }
        });
    }

    private void doSearch() {
        View oldList = layout.findViewById(LISTVIEW_ID);

        if (oldList != null) {
            layout.removeView(oldList);
        }

        String key = edit.getText().toString().trim();
        if (key.length() <= 0) {
            Toast.makeText(getApplicationContext(), R.string.input_key,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        LayoutParams params = new LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.FILL_PARENT);
        ListData<SociaxItem> data = new ListData<SociaxItem>();
        Log.d(TAG, "searchkey" + edit.getText());
        switch (status) {
            case WEIBO:
                // 获取list的布局对象
                weiboList = new SearchWeiboList(WeiboAppActivity.this);
                weiboList.setId(LISTVIEW_ID);
                weiboList.setLayoutParams(params);
                layout.addView(weiboList);
                // 获取数据源
                adapter = new SearchWeiboListAdapter(WeiboAppActivity.this, data,
                        edit.getText());
                weiboList.setAdapter(adapter, System.currentTimeMillis(),
                        WeiboAppActivity.this);
                adapter.loadInitData();
                break;
            case USER:
                // 获取list的布局对象
                userList = new SearchUserList(WeiboAppActivity.this);
                userList.setId(LISTVIEW_ID);
                userList.setLayoutParams(params);
                layout.addView(userList);
                // 获取数据源
                adapter = new UserListAdapter(WeiboAppActivity.this, data,
                        edit.getText());
                userList.setAdapter(adapter, System.currentTimeMillis(),
                        WeiboAppActivity.this);
                adapter.loadInitData();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            String key = edit.getText().toString().trim();
            switch (buttonView.getId()) {
                case R.id.search_weibo:
                    status = Type.WEIBO;
                    searchWeibo.setTextColor(Color.WHITE);
                    searchUser.setTextColor(Color.BLACK);
                    break;
                case R.id.search_user:
                    status = Type.USER;
                    searchUser.setTextColor(Color.WHITE);
                    searchWeibo.setTextColor(Color.BLACK);
                    break;
            }
            if (key.length() > 0) {
                doSearch();
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event != null
                && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_ENVELOPE)
                && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_UP) {
            View oldList = layout.findViewById(LISTVIEW_ID);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
            if (oldList != null) {
                layout.removeView(oldList);
            }
            LayoutParams params = new LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            ListData<SociaxItem> data = new ListData<SociaxItem>();

            switch (status) {
                case WEIBO:
                    // 获取list的布局对象
                    weiboList = new SearchWeiboList(WeiboAppActivity.this);
                    weiboList.setId(LISTVIEW_ID);
                    weiboList.setLayoutParams(params);
                    layout.addView(weiboList);
                    // 获取数据源
                    adapter = new SearchWeiboListAdapter(this, data, edit.getText());
                    weiboList.setAdapter(adapter, System.currentTimeMillis(),
                            this.getParent());
                    adapter.loadInitData();
                    break;
                case USER:
                    // 获取list的布局对象
                    userList = new SearchUserList(WeiboAppActivity.this);
                    userList.setId(LISTVIEW_ID);
                    userList.setLayoutParams(params);
                    layout.addView(userList);
                    adapter = new UserListAdapter(this, data, edit.getText());
                    userList.setAdapter(adapter, System.currentTimeMillis(),
                            this.getParent());
                    adapter.loadInitData();
                    break;
            }
            return true;
        }
        return false;
    }

}
