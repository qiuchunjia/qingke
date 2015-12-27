package qcjlibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午3:48:54 类描述：这个类是实现
 */

public class DeleteViewGroup extends LinearLayout {
    private LayoutInflater mInflater;
    private View mMainView;
    private View mAddView;
    private Scroller mScroller;

    @SuppressLint("NewApi")
    public DeleteViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(0, 0);
    }

    public DeleteViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(0, 0);

    }

    public DeleteViewGroup(Context context) {
        super(context);
        initView(0, 0);
        // TODO Auto-generated constructor stub
    }

    /**
     * 初始化view
     *
     * @param mainId    主要view
     * @param addViewId 右边尾部添加的view
     */
    private void initView(int mainId, int addViewId) {
        mScroller = new Scroller(getContext());
        mInflater = LayoutInflater.from(getContext());
        this.setOrientation(this.HORIZONTAL);
        this.setPadding(0, 10, 0, 10);
        mMainView = mInflater.inflate(R.layout.notify_notify_iem, this);
        // mAddView=mInflater.in
        Button button = new Button(getContext());
        button.setLayoutParams(new LinearLayout.LayoutParams(160,
                LayoutParams.MATCH_PARENT));
        button.setText("删除");
        button.setTextColor(0xffffffff);
        button.setBackgroundColor(0xff000000);
        mAddView = button;
        this.addView(button);
    }

    int lastX = 0;
    int lastY = 0;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getRawX();
        int currentY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = currentX;
                lastY = currentY;
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = currentX - lastX;
                int dy = currentY - lastY;
                this.scrollBy(-dx, 0);
                lastX = currentX;
                lastY = currentY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;

    }

}
