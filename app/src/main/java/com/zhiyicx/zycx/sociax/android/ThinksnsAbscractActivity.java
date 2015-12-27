package com.zhiyicx.zycx.sociax.android;

import java.util.List;


import com.zhiyicx.zycx.R;

import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;
import com.zhiyicx.zycx.sociax.modle.ApproveSite;
import com.zhiyicx.zycx.sociax.unit.Anim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;

public abstract class ThinksnsAbscractActivity extends Activity implements GestureDetector.OnGestureListener,
        View.OnTouchListener {
    public static final int TRANSPOND = 0;
    public static final int COMMENT = 1;
    public static final int REPLY_MESSAGE = 2;
    public static final int CREATE_MESSAGE = 3;
    protected CustomTitle title;
    protected Bundle data;
    protected static final String TIPS = "tips";
    protected View mBtton;
    public static final int MYWEIBO_DEL = 1212;
    private GestureDetector mGDetector;

    protected String ActivityTag = "";

    protected boolean isHasEmailList = false;
    protected List<String> emailList;
    public static boolean sendFlag = false;

    protected static final String TAG = "ThinksnsAbscractActivity";

    // 获取中间
    public abstract String getTitleCenter();

    // 获取左边资源
    public int getLeftRes() {
        return R.drawable.menu_back_img;
    }

    // 获取右边资源
    public int getRightRes() {
        return R.drawable.menu_home_img;
    }

    // 是否在底部tab中
    public boolean isInTab() {
        return false;
    }

    /**
     * 设置头部
     *
     * @return
     */
    protected abstract CustomTitle setCustomTitle();

    protected void onCreateNoTitle(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        initCreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityTag.equals("MainGridActivity") || ActivityTag.equals("MainTaskActivity")) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            initCreate();
        } else {
            this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            this.setTheme(R.style.titleTheme);
            // 加载布局
            initCreate();
            initTitle();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//		MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        this.paramDatas();
        super.onResume();
//		MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    protected void onCreateDefault(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCreate();
        initTitle();
    }

    // 初始化title
    protected void initTitle() {
        if (!this.isInTab()) {
            title = this.setCustomTitle();
        } else {
            title = this.setCustomTitle();
        }
    }

    /**
     * 设置布局
     */
    private void initCreate() {
        setContentView(this.getLayoutId());
        this.paramDatas();
        mGDetector = new GestureDetector(this);
        // Thinksns app = (Thinksns)this.getApplicationContext();
        // 加入栈
        // app.getActivityStack().addCache(this);
    }

    public Bundle getIntentData() {
        if (data != null)
            return data;
        data = new Bundle();
        return data;
    }

    // 看Intent数据中是否有TIPS，如果有,去除TIPS，用Toast显示TIPS
    protected void paramDatas() {
        data = this.getIntent().getExtras();
        if (data != null && data.containsKey(TIPS)) {
            String tips = data.getString(TIPS);
            data.remove(TIPS);
            Toast.makeText(this, tips, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    // 左部点击，加入栈
    public OnClickListener getLeftListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thinksns app = (Thinksns) ThinksnsAbscractActivity.this.getApplicationContext();
                ThinksnsAbscractActivity.this.finish();
                Anim.exit(ThinksnsAbscractActivity.this);
                Log.d(TAG, "left onclick ....");
            }
        };
    }

    // 右边点击，清除栈，进入home
    public OnClickListener getRightListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Thinksns app = (Thinksns) ThinksnsAbscractActivity.this.getApplicationContext();
				app.startActivity(ThinksnsAbscractActivity.this, MainGridActivity.class, null);
				Anim.exit(ThinksnsAbscractActivity.this);*/
            }
        };
    }

    @Override
    public void finish() {
        Thinksns app = (Thinksns) this.getApplicationContext();
        app.closeDb();
        super.finish();
    }

    public void refreshHeader() {
    }

    public void refreshFooter() {
    }

    /**
     * 数据list view隐藏 显示loading view
     */
    public OnTouchListListener getListView() {
        return null;
    }

    public View getOtherView() {
        return null;
    }

    public void updateView(View view, int state) {
    }

    public CustomTitle getCustomTitle() {
        return title;
    }

    public OnClickListener getImageFullScreen(final String url) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getIntentData().putString("url", url);
                // getIntentData().putBoolean("tab",
                // ThinksnsAbscractActivity.this.isInTab());
                Thinksns app = (Thinksns) ThinksnsAbscractActivity.this.getApplicationContext();
                // app.getActivityStack().startActivity(ThinksnsAbscractActivity.this,ThinksnsImageView.class,getIntentData());
                app.startActivity(ThinksnsAbscractActivity.this, ThinksnsImageView.class, getIntentData());
            }

        };
    }

    public int getSiteId() {
        Thinksns app = (Thinksns) this.getApplicationContext();
        ApproveSite as = Thinksns.getMySite();
        if (Thinksns.getMySite() == null) {
            return 0;
        } else {
            return Thinksns.getMySite().getSite_id();
        }
    }

    protected void dialog() {
        AlertDialog.Builder builder = new Builder(this);
        final Activity obj = this;

        builder.setTitle("提示");
        builder.setMessage("确认退出程序?");
        builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                obj.finish();
            }
        });
        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    // @Override
    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    // Thinksns app = (Thinksns)this.getApplicationContext();
    // if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 &&
    // app.getActivityStack().empty()){
    // dialog();
    // return false;
    // }
    // return true;
    // }

    // //////////////////////************************/////////////////////

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        System.err.println(" on touch  ... qqqq");
        // if ((e1.getX() - e2.getX() > 100.0F) && (Math.abs(velocityX) >
        // 200.0F)) {
        //
        // } else if ((e2.getX() - e1.getX() > 100.0F) && (Math.abs(velocityX) >
        // 200.0F)) {
        // this.finish();
        // }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGDetector.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ThinksnsAbscractActivity.this.finish();
            Anim.exit(ThinksnsAbscractActivity.this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
