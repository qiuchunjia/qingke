package com.zhiyicx.zycx.widget;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.adapter.SociaxListAdapter;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public abstract class SociaxList extends ListView implements
        OnTouchListListener {

    private static final String TAG = "SociaxList";
    public DragDown dragdown;

    private static int lastPosition;
    private static Activity activityObj;

    private SociaxListAdapter mListAdapter = null;

    protected abstract void onClick(View view, int position, long id);

    public SociaxList(Context context) {
        super(context);
        initSet(context);
    }

    public SociaxList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSet(context);
    }

    public void initSet(Context context) {
        setScrollbarFadingEnabled(true);
        // this.setCacheColorHint(Color.argb(0, 255, 255, 255));
        setCacheColorHint(0);
        // int color = context.getResources().getColor(R.color.line);
        setDivider(context.getResources().getDrawable(R.drawable.item_line));
        dragdown = new DragDown(context, this);
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
                SociaxList.this.onClick(view, position, id);
            }
        });
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    setLastPosition(SociaxList.this.getFirstVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
    }

    public void setAdapter(ListAdapter adapter, long lastTime, Activity obj) {
        mListAdapter = (SociaxListAdapter) adapter;
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
        SociaxList.lastPosition = lastPosition;
    }

    public SociaxListAdapter getListAdapter() {
        return mListAdapter;
    }
}
