package qcjlibrary.listview.base.xlistview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * @author markmjw
 * @date 2013-10-08
 */
public class XListView extends ListView implements OnScrollListener {
    // private static final String TAG = "XListView";

    private final static int SCROLL_BACK_HEADER = 0;
    private final static int SCROLL_BACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400;

    // when pull up >= 50px
    private final static int PULL_LOAD_MORE_DELTA = 50;

    // support iOS like pull
    private final static float OFFSET_RADIO = 1.8f;

    private float mLastY = -1;

    // used for scroll back
    private Scroller mScroller;
    // user's scroll listener
    private OnScrollListener mScrollListener;
    // for mScroller, scroll back from header or footer.
    private int mScrollBack;

    // the interface to trigger refresh and load more.
    private IXListViewListener mListener;

    private XHeaderView mHeader;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderContent;
    private TextView mHeaderTime;
    private int mHeaderHeight;

    private LinearLayout mFooterLayout;
    private XFooterView mFooterView;
    private boolean mIsFooterReady = false; // 底部是否准备好

    private boolean mEnablePullRefresh = true;// 能否下拉刷新
    private boolean mPullRefreshing = false;// 是否在刷新

    private boolean mEnablePullLoad = true; // 能否加载更多
    private boolean mEnableAutoLoad = false;// 能否自动加载
    private boolean mPullLoading = false; // 是否在加载更多

    // listview的总的item数量，这个主要是用于程序检测能否上拉加载更多（起了一个判断作用）
    private int mTotalItemCount;

    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    @SuppressLint("NewApi")
    private void initWithContext(Context context) {
        // 去掉下拉时，头部或者底部，过渡阴影 qcj 添加
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);

        // 初始化 header view
        mHeader = new XHeaderView(context);
        mHeaderContent = (RelativeLayout) mHeader
                .findViewById(R.id.header_content);
        mHeaderTime = (TextView) mHeader.findViewById(R.id.header_hint_time);
        addHeaderView(mHeader);

        // 初始化 footer view
        mFooterView = new XFooterView(context);
        mFooterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mFooterLayout.addView(mFooterView, params);

        // init header height
        ViewTreeObserver observer = mHeader.getViewTreeObserver();
        if (null != observer) {
            observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    mHeaderHeight = mHeaderContent.getHeight();
                    ViewTreeObserver observer = getViewTreeObserver();

                    if (null != observer) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            observer.removeGlobalOnLayoutListener(this);
                        } else {
                            observer.removeOnGlobalLayoutListener(this);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // 保证listview的尾部只能有一个尾部
        if (!mIsFooterReady) {
            mIsFooterReady = true;
            addFooterView(mFooterLayout);
        }
        super.setAdapter(adapter);
    }

    /**
     * 设置是否能可以下拉刷新，共外部调用选择
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        // 如果设置不可以的话，就隐藏头部
        mHeaderContent.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置是否能可以底部加载更多，共外部调用选择
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;

        if (!mEnablePullLoad) {
            mFooterView.setBottomMargin(0);
            mFooterView.hide();
            mFooterView.setPadding(0, 0, 0, 0);
            mFooterView.setOnClickListener(null);

        } else {
            mPullLoading = false;
            mFooterView.setPadding(0, 0, 0, 0);
            mFooterView.show();
            mFooterView.setState(XFooterView.STATE_NORMAL);
            // 设置监听器，让不仅上拉加载更多，点击也会加载更多
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * 设置当移动到listview底部的时候是否可以自动加载更多
     *
     * @param enable
     */
    public void setAutoLoadEnable(boolean enable) {
        mEnableAutoLoad = enable;
    }

    /**
     * 停止刷新, 重置header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * 停止加载更多, 重置 footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(XFooterView.STATE_NORMAL);
        }
    }

    /**
     * 设置最后的 refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTime.setText(time);
    }

    /**
     * 设置监听器 listener.
     *
     * @param listener
     */
    public void setXListViewListener(IXListViewListener listener) {
        mListener = listener;
    }

    /**
     * 自动刷新.
     */
    public void autoRefresh() {
        mHeader.setVisibleHeight(mHeaderHeight);

        if (mEnablePullRefresh && !mPullRefreshing) {
            // update the arrow image not refreshing
            if (mHeader.getVisibleHeight() > mHeaderHeight) {
                mHeader.setState(XHeaderView.STATE_READY);
            } else {
                mHeader.setState(XHeaderView.STATE_NORMAL);
            }
        }

        mPullRefreshing = true;
        mHeader.setState(XHeaderView.STATE_REFRESHING);
        refresh();
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener listener = (OnXScrollListener) mScrollListener;
            listener.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeader.setVisibleHeight((int) delta + mHeader.getVisibleHeight());

        if (mEnablePullRefresh && !mPullRefreshing) {
            // update the arrow image unrefreshing
            if (mHeader.getVisibleHeight() > mHeaderHeight) {
                mHeader.setState(XHeaderView.STATE_READY);
            } else {
                mHeader.setState(XHeaderView.STATE_NORMAL);
            }
        }

        // scroll to top each time
        setSelection(0);
    }

    private void resetHeaderHeight() {
        int height = mHeader.getVisibleHeight();
        if (height == 0)
            return;

        // 刷新中，但是头部没有完全显示出来，这个时候不需要干什么事情，（这种情况发现在，正在刷新的时候，然后又故意向上滑动）
        if (mPullRefreshing && height <= mHeaderHeight)
            return;

        int finalHeight = 0;
        // is refreshing, just scroll back to show all the header.
        // 当触发到刷新的临界点，然后继续下拉的时候，松开手就弹到头部的高端
        if (mPullRefreshing && height > mHeaderHeight) {
            finalHeight = mHeaderHeight;
        }

        mScrollBack = SCROLL_BACK_HEADER;
        // 这就是我们看到的弹到原位置的感觉（在这里其实可以实现一弹一弹的感觉）
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);

        // 调用这个方法请求重绘界面 这个方法又会触发 computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;

        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) {
                // height enough to invoke load more.
                mFooterView.setState(XFooterView.STATE_READY);
            } else {
                mFooterView.setState(XFooterView.STATE_NORMAL);
            }
        }

        mFooterView.setBottomMargin(height);

    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();

        if (bottomMargin > 0) {
            mScrollBack = SCROLL_BACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    /**
     * 修复了listview里面item很少的时候，下拉刷新会同时加载更多的bug
     */
    private void startLoadMore() {
        if (!mPullRefreshing) {
            mPullLoading = true;
            mFooterView.setState(XFooterView.STATE_LOADING);
            loadMore();
        }
    }

    /**
     * 设置底部消失 qcj添加
     */
    public void setFooterGone() {
        mFooterLayout.setVisibility(View.GONE);
        mFooterView.setVisibility(View.GONE);
    }

    public void setFooterVisable() {
        mFooterLayout.setVisibility(View.VISIBLE);
        mFooterView.setVisibility(View.VISIBLE);
    }

    // 核心方法
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();

                if (getFirstVisiblePosition() == 0
                        && (mHeader.getVisibleHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;

            default:
                // reset 其实本质上就是松开手后执行的操作
                mLastY = -1;
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh
                            && mHeader.getVisibleHeight() > mHeaderHeight) {
                        mPullRefreshing = true;
                        mHeader.setState(XHeaderView.STATE_REFRESHING);
                        refresh();
                    }

                    resetHeaderHeight();

                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLL_BACK_HEADER) {
                mHeader.setVisibleHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }

            postInvalidate();
            invokeOnScrolling();
        }

        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }

        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (mEnableAutoLoad && getLastVisiblePosition() == getCount() - 1) {
                startLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    private void refresh() {
        if (mEnablePullRefresh && null != mListener) {
            mListener.onRefresh();
        }
    }

    private void loadMore() {
        if (mEnablePullLoad && null != mListener) {
            mListener.onLoadMore();
        }
    }

    /**
     * You can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     * <p/>
     * 自定义OnScrollListener，暂时用不到
     */
    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    /**
     * 实现这个接口就可以刷新或者加载更多
     */
    public interface IXListViewListener {
        public void onRefresh();

        public void onLoadMore();
    }
}
