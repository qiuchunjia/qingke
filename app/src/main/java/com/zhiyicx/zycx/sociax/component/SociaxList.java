package com.zhiyicx.zycx.sociax.component;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.unit.DragDown;

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

    public SociaxList(Context context) {
        super(context);
        this.initSet(context);
    }

    public SociaxList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initSet(context);
    }

    public void initSet(Context context) {
        this.setScrollbarFadingEnabled(true);
        // this.setCacheColorHint(Color.argb(0, 255, 255, 255));
        this.setCacheColorHint(0);
        // int color = context.getResources().getColor(R.color.line);
        this.setDivider(context.getResources().getDrawable(R.drawable.item_line));
        dragdown = new DragDown(context, this);
        this.initDrag(context);
    }

    protected void initDrag(Context context) {
        this.setOnTouchListener(dragdown);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        this.addHeaderView();
        this.addFooterView();
        super.setAdapter(adapter);
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (dragdown.canDrag())
                    return;
                SociaxList.this.onClick(view, position, id);
            }
        });
        this.setOnScrollListener(new OnScrollListener() {
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

    protected void addHeaderView() {
        super.addHeaderView(dragdown.getHeaderView());
    }

    protected void addFooterView() {
        super.addFooterView(dragdown.getFooterView());
    }

    @Override
    public boolean removeFooterView(View v) {
        return super.removeFooterView(dragdown.getFooter());
    }

    protected abstract void onClick(View view, int position, long id);

    public void setAdapter(ListAdapter adapter, long lastTime, Activity obj) {
        setActivityObj(obj);
        this.setLastRefresh(lastTime);
        this.setAdapter(adapter);
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
        removeFooterView(this);
        return null;
        // dragdown.hideFooterView();
    }

    @Override
    public View showFooterView() {
        dragdown.showFooterView();
        return null;
    }

    /**
     * Activity
     *
     * @return
     */
    public static Activity getActivityObj() {
        return activityObj;
    }

    private static void setActivityObj(Activity activityObj) {
        SociaxList.activityObj = activityObj;
    }

    public static int getLastPosition() {
        return lastPosition;
    }

    private static void setLastPosition(int lastPosition) {
        SociaxList.lastPosition = lastPosition;
    }
}
