package com.zhiyicx.zycx.widget;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.unit.Anim;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDragDown implements OnTouchListener, OnGestureListener, OnTouchListListener {
    private GestureDetector mGestureDetector;
    private static final int CONTENT_TEXT_ID = 0;
    private static final int CONTENT_TIME_ID = 1;
    private static final int CONTENT_LAYOUT_ID = 2;
    private static final int CONTENT_IMAGE_ID = 3;
    private static final int FOOTER_IMAGE_ID = 3;
    private static final int OFFSET = -180;//-120;
    private static final int IMAGE_LEFT_MARGIN = 40;
    private static final int IMAGE_RIGHT_MARGIN = 40;
    private static final int BOTTON_MARIGN = 20;

    private static final int MIN_OFFSET = 0;

    private static final String TAG = "DragDown";

    private static boolean hasHeader = false;
    private long lastRefresh;

    private static boolean refreshing = false;
    private static boolean hasReverse = false;
    private static boolean hasTouch = false;

    private LinearLayout header;
    private LinearLayout footer;
    private LinearLayout headerContent;
    private LinearLayout footerContent;

    private static Animation anim;
    private static Animation anim_down;
    private Context context;

    private ListView view;
    private static boolean viewHasHeader = false;
    private static boolean viewHasFooter = false;

    public MyDragDown(Context context, ListView v) {
        mGestureDetector = new GestureDetector(context, this);
        this.context = context;
        this.view = v;
        footer = (LinearLayout) View.inflate(context, R.layout.more_item, null);
        setFooter(footer);
    }

    public View getHeaderView() {
        anim = AnimationUtils.loadAnimation(context, R.anim.reverse_up);
        anim_down = AnimationUtils.loadAnimation(context, R.anim.reverse_down);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        anim_down.setFillEnabled(true);
        anim_down.setFillAfter(true);

        LinearLayout.LayoutParams lpCenter = this.getLinearLayout();
        header = new LinearLayout(context);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_HORIZONTAL);

        // header.addView(child)

        headerContent = new LinearLayout(context);
        headerContent.setId(CONTENT_LAYOUT_ID);
        headerContent.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        lpText.gravity = Gravity.CENTER;

        TextView contentText = new TextView(context);
        contentText.setId(CONTENT_TEXT_ID);
        contentText.setText(R.string.please_up);
        TextView contentTime = new TextView(context);
        contentTime.setId(CONTENT_TIME_ID);
        headerContent.addView(contentText, lpText);
        headerContent.addView(contentTime, lpText);

        ImageView arrow = new ImageView(context);
        arrow.setId(CONTENT_IMAGE_ID);
        arrow.setBackgroundResource(R.drawable.arrow_down);
        LinearLayout.LayoutParams lpImage = this.getLinearLayout();
        // lpImage.leftMargin = IMAGE_LEFT_MARGIN;
        lpImage.rightMargin = IMAGE_RIGHT_MARGIN;
        lpImage.bottomMargin = BOTTON_MARIGN;
        header.addView(arrow, lpImage);
        header.addView(headerContent, lpCenter);
        this.setTime();
        viewHasHeader = true;
        header.setClickable(false);
        return header;
    }

    /**
     * 添加list底部view
     */
    public View getFooterView() {
        // TODO list底部view
        // if(footer != null)
        viewHasFooter = true;
        return getFooter();
    }

    @Override
    public View hideFooterView() {
        viewHasFooter = false;
        return getFooter();
    }

    @Override
    public View showFooterView() {
        return getFooter();
        // footer.setVisibility(View.VISIBLE);
    }

    private LinearLayout.LayoutParams getLinearLayout() {
        LinearLayout.LayoutParams result = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        result.gravity = Gravity.BOTTOM;
        result.topMargin = OFFSET;
        result.bottomMargin = BOTTON_MARIGN;
        return result;
    }

    public boolean canDrag() {
        return refreshing || hasHeader;
    }

    private void setTime() {
        TextView contentTime = (TextView) header.findViewById(CONTENT_TIME_ID);
        String time = new SimpleDateFormat("MM-dd HH:mm").format(new Date(this.lastRefresh));
        String text = context.getString(R.string.last_refresh) + time;
        contentTime.setText(text);
        contentTime.setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (null != headerContent) {
            LinearLayout.LayoutParams lpCenter = (LinearLayout.LayoutParams) headerContent
                    .getLayoutParams();
            if (!refreshing && event.getAction() == MotionEvent.ACTION_UP) {
                if (lpCenter.topMargin >= MIN_OFFSET) {
                    ((LoadListView) view).getListAdapter().doRefreshHeader();
                } else {
                    lpCenter.topMargin = OFFSET;
                    headerContent.setLayoutParams(lpCenter);
                }
                hasTouch = false;
            }
        }
        if (refreshing) {
            return true;
        }
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        hasHeader = true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        hasHeader = false;
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (this.checkCanScroll())
            return false;

        if (header == null) {
            return false;
        }
        TextView contentText = (TextView) header.findViewById(CONTENT_TEXT_ID);
        ImageView contentImage = (ImageView) header.findViewById(CONTENT_IMAGE_ID);

        if (headerContent == null) {
            return false;
        }
        LinearLayout.LayoutParams lpCenter = (LinearLayout.LayoutParams) headerContent.getLayoutParams();
        try {
            if (distanceY < 0 && e1.getY() >= e2.getY()) {
                int height = (int) Math.ceil(Math.abs((int) (e1.getY() - e2.getY())) * 0.5);
                lpCenter.topMargin = height - Math.abs(OFFSET);
                headerContent.setLayoutParams(lpCenter);
                hasTouch = true;
            } else {
                if (e2.getY() >= e1.getY()) {
                    int height = (int) Math.ceil(Math.abs((int) (e2.getY() - e1.getY())) * 0.5);
                    ;

                    lpCenter.topMargin = height + OFFSET;
                    headerContent.setLayoutParams(lpCenter);
                    hasTouch = true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        if (lpCenter.topMargin < 0) {
            if (hasReverse) {
                contentImage.startAnimation(anim_down);
                hasReverse = false;
            }
            contentText.setText(R.string.please_down);
            contentText.setPadding(0, 7, 0, 0);
        } else {
            if (!hasReverse) {
                contentImage.startAnimation(anim);
                hasReverse = true;
            }
            contentText.setText(R.string.please_up);
            contentText.setPadding(0, 7, 0, 0);
        }
        if (refreshing) {
            return true;
        }
        return hasTouch;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void headerShow() {
        LinearLayout.LayoutParams lpCenter = (LinearLayout.LayoutParams) headerContent.getLayoutParams();
        lpCenter.topMargin = 5;
        headerContent.setLayoutParams(lpCenter);
        refreshing = true;
    }

    @Override
    public void headerHiden() {
        try {
            LinearLayout.LayoutParams lpCenter = (LinearLayout.LayoutParams) headerContent
                    .getLayoutParams();

            Animation anima = AnimationUtils.loadAnimation(context, R.anim.push_up_out);
            // header.startAnimation(anima);

            lpCenter.topMargin = OFFSET;
            headerContent.setLayoutParams(lpCenter);
            refreshing = false;
            ImageView contentImage = (ImageView) header.findViewById(CONTENT_IMAGE_ID);
            LinearLayout.LayoutParams lpImage = (LinearLayout.LayoutParams) contentImage
                    .getLayoutParams();
            lpImage.bottomMargin = BOTTON_MARIGN;
            contentImage.setBackgroundResource(R.drawable.arrow_down);
        } catch (Exception ex) {
            return;
        }
    }

    @Override
    public void headerRefresh() {
        this.setTime();
        TextView contentText = (TextView) header.findViewById(CONTENT_TEXT_ID);
        ImageView contentImage = (ImageView) header.findViewById(CONTENT_IMAGE_ID);

        contentText.setText(R.string.refreshing);
        contentText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lpImage = (LinearLayout.LayoutParams) contentImage.getLayoutParams();
        lpImage.bottomMargin += 10;

        Anim.refresh(context, contentImage);
    }

    @Override
    public long getLastRefresh() {
        return lastRefresh;
    }

    @Override
    public void setLastRefresh(long lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    private boolean emptyList() {
        if (viewHasHeader && viewHasFooter)
            return view.getCount() == 2;
        return view.getCount() == 1;
    }

    @Override
    public void footerShow() {
        if (footerContent != null) {
            LinearLayout.LayoutParams lpCenter = (LinearLayout.LayoutParams) footerContent
                    .getLayoutParams();
            lpCenter.topMargin = 5;
            footerContent.setLayoutParams(lpCenter);
            ImageView contentImage = (ImageView) footer.findViewById(FOOTER_IMAGE_ID);

            Anim.refreshMiddle(context, contentImage);
        }
    }

    @Override
    public void footerHiden() {
        // TODO Auto-generated method stub
        try {
            LinearLayout.LayoutParams lpCenter = (LinearLayout.LayoutParams) footer.getLayoutParams();
            lpCenter.bottomMargin = OFFSET;
            footer.setLayoutParams(lpCenter);
            footer.setVisibility(View.GONE);
            refreshing = false;

        } catch (Exception ex) {
            return;
        }
    }

	/*
     * @Override public void footerHiden() {
	 * 
	 * this.getFooterView().setVisibility(View.GONE); Log.d(TAG,
	 * "footerHiden ...  "); if (footerContent != null) {
	 * LinearLayout.LayoutParams lpCenter =
	 * (android.widget.LinearLayout.LayoutParams) footerContent
	 * .getLayoutParams(); lpCenter.topMargin = OFFSET; if (headerContent !=
	 * null) { headerContent.setLayoutParams(lpCenter); } ImageView contentImage
	 * = (ImageView) footer .findViewById(FOOTER_IMAGE_ID);
	 * LinearLayout.LayoutParams lpImage =
	 * (android.widget.LinearLayout.LayoutParams) contentImage
	 * .getLayoutParams(); } }
	 */

    private boolean checkCanScroll() {
        boolean emptyList = emptyList();
        int firstVisiblePosition = view.getFirstVisiblePosition();
        // return refreshing || firstVisiblePosition != 0 || emptyList();
        return refreshing || firstVisiblePosition != 0;
    }

    public LinearLayout getFooter() {
        return footer;
    }

    public void setFooter(LinearLayout footer) {
        this.footer = footer;
    }

}
