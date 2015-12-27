package com.zhiyicx.zycx.sociax.android;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.net.ImgDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.sociax.component.CustomTitle;
import com.zhiyicx.zycx.sociax.component.LoadingView;
import com.zhiyicx.zycx.sociax.component.RightIsButton;
import com.zhiyicx.zycx.sociax.unit.AsyncImageLoader;
import com.zhiyicx.zycx.sociax.unit.ImageUtil;
import com.zhiyicx.zycx.sociax.unit.ImageZoomView;
import com.zhiyicx.zycx.sociax.unit.SimpleZoomListener;
import com.zhiyicx.zycx.sociax.unit.ZoomState;

public class ThinksnsImageView extends ThinksnsAbscractActivity {
    private ImageZoomView imageZoomView;
    private ZoomControls zoomCtrl;
    private ZoomState mZoomState;
    private Button saveButton;
    private static Bitmap bitmap;
    private SimpleZoomListener mZoomListener;
    private String url;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private RelativeLayout rl_left_1;
    private TextView tv_title_right;
    private TextView tv_title;
    private static LoadingView loadingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateNoTitle(savedInstanceState);

        url = getIntentData().getString("url");
        imageZoomView = (ImageZoomView) findViewById(R.id.image_data);
        zoomCtrl = (ZoomControls) findViewById(R.id.zoomCtrl);

        NetComTools netComTools = NetComTools.getInstance(this);
        netComTools.getNetImage(url, new ImgDataListener() {
            @Override
            public void OnReceive(Bitmap bmp) {
                bitmap = bmp;
                imageZoomView.setImage(bitmap);
            }

            @Override
            public void OnError(String error) {

            }
        });
        // Drawable img = AsyncImageLoader.loadImageFromUrl(url);
        // bitmap = drawableToBitmap(img);
        // imageZoomView.setImage(bitmap);

        mZoomState = new ZoomState();
        mZoomListener = new SimpleZoomListener();
        mZoomListener.setZoomState(mZoomState);

        imageZoomView.setZoomState(mZoomState);
        imageZoomView.setOnTouchListener(mZoomListener);
        this.setZoomCtrls();
        imageZoomView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setFullScreen();
            }

        });
        /**** qcj添加title 并初始化 *********/
        rl_left_1 = (RelativeLayout) findViewById(R.id.rl_left_1);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("图片浏览");
        tv_title_right.setText("保存");
        rl_left_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title_right.setOnClickListener(getRightListener());
        // resetZoomState();
    }

    // private void resetZoomState() {
    // mZoomState.setPanX(0.5f);
    // mZoomState.setPanY(0.5f);
    //
    // final int mWidth = bitmap.getWidth();
    // final int vWidth= imageZoomView.getWidth();
    // mZoomState.setZoom(1f);
    // mZoomState.notifyObservers();
    //
    // }

    private void setFullScreen() {
        if (zoomCtrl != null) {
            if (zoomCtrl.getVisibility() == View.VISIBLE) {
                // zoomCtrl.setVisibility(View.GONE);
                zoomCtrl.hide(); // 有过度效果
            } else if (zoomCtrl.getVisibility() == View.GONE) {
                // zoomCtrl.setVisibility(View.VISIBLE);
                zoomCtrl.show();// 有过渡效果
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.imageshow;
    }

    @Override
    public String getTitleCenter() {
        return this.getString(R.string.imageshow);
    }

    @Override
    protected CustomTitle setCustomTitle() {
        return new RightIsButton(this, this.getString(R.string.imagesave));
    }

    @Override
    public int getRightRes() {
        return R.drawable.button_send;
    }

    @Override
    public OnClickListener getRightListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ImageUtil iu = new ImageUtil();
                String[] urlName = null;
                boolean result = false;
                urlName = url.split("/");
                try {
                    result = iu.saveImage(urlName[urlName.length - 1], bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (result) {
                    Toast.makeText(ThinksnsImageView.this, "保存成功",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ThinksnsImageView.this, "保存失败",
                            Toast.LENGTH_SHORT).show();
                }
            }

        };
    }

    private void setZoomCtrls() {
        zoomCtrl.setOnZoomInClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                float z = mZoomState.getZoom() + 0.25f;
                mZoomState.setZoom(z);
                mZoomState.notifyObservers();
            }
        });
        zoomCtrl.setOnZoomOutClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                float z = mZoomState.getZoom() - 0.25f;
                mZoomState.setZoom(z);
                mZoomState.notifyObservers();
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean isInTab() {
        return false;
    }

}
