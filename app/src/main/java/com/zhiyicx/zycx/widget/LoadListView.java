package com.zhiyicx.zycx.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.LoadListAdapter;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.unit.Anim;

public class LoadListView extends ListView implements
        OnTouchListListener {

    private static final String TAG = "LoadListView";
    public MyDragDown dragdown;

    private static int lastPosition;
    private static Activity activityObj;

    private LoadListAdapter mListAdapter = null;
    private OnItemListener mOnItemListener = null;

    public interface OnItemListener {
        public abstract void onClick(View view, int position, long id);
    }

    public void setOnItemListener(OnItemListener listener) {
        mOnItemListener = listener;
    }


    public LoadListView(Context context) {
        super(context);
        initSet(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSet(context);
    }

    public void initSet(Context context) {
        setScrollbarFadingEnabled(true);
        setCacheColorHint(0);
        setDivider(context.getResources().getDrawable(R.drawable.item_line));
        dragdown = new MyDragDown(context, this);
        setOnTouchListener(dragdown);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        addHeaderView(dragdown.getHeaderView());
        //addFooterView(dragdown.getFooterView());
        super.setAdapter(adapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (dragdown.canDrag())
                    return;
                if (view.getId() == R.id.footer_content) {
                    ImageView iv = (ImageView) view.findViewById(R.id.anim_view);
                    iv.setVisibility(View.VISIBLE);
                    Anim.refresh(activityObj, iv);
                    //HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter)LoadListView.this.getListAdapter();
                    LoadListAdapter LisAdapter = LoadListView.this.getListAdapter();
                    LisAdapter.animView = iv;
                    LisAdapter.doRefreshFooter();
                } else {
                    if (mOnItemListener != null)
                        mOnItemListener.onClick(view, position, id);
                }

            }
        });
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    setLastPosition(LoadListView.this.getFirstVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
    }

    public void setAdapter(ListAdapter adapter, long lastTime, Activity obj) {
        mListAdapter = (LoadListAdapter) adapter;
        activityObj = obj;
        dragdown.setLastRefresh(lastTime);
        setAdapter(adapter);
    }

    @Override
    public void headerShow() {
        dragdown.headerShow();
    }

    @Override
    public void headerHiden() {
        dragdown.headerHiden();
    }

    @Override
    public void headerRefresh() {
        dragdown.headerRefresh();
    }

    @Override
    public long getLastRefresh() {
        return dragdown.getLastRefresh();
    }

    @Override
    public void setLastRefresh(long lastRefresh) {
        dragdown.setLastRefresh(lastRefresh);
    }

    @Override
    public void footerShow() {
        dragdown.footerShow();
    }

    @Override
    public void footerHiden() {
        dragdown.footerHiden();
        Log.d(TAG, "remove footer view ... ");
    }

    @Override
    public View hideFooterView() {
        super.removeFooterView(dragdown.getFooter());
        //removeFooterView(this);
        return null;
        // dragdown.hideFooterView();
    }

    @Override
    public View showFooterView() {
        //dragdown.showFooterView();
        if (getFooterViewsCount() == 0)
            addFooterView(dragdown.getFooterView());
        return null;
    }

    public static Activity getActivityObj() {
        return activityObj;
    }

    public static int getLastPosition() {
        return lastPosition;
    }

    private static void setLastPosition(int lastPosition) {
        LoadListView.lastPosition = lastPosition;
    }

    public LoadListAdapter getListAdapter() {
        return mListAdapter;
    }
}
