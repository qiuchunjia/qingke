package qcjlibrary.widget.popupview;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.widget.popupview.base.PopView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午3:39:10 类描述：这个类是实现
 */

public class PopUploadIcon extends PopView {
    private Button btn_openTheCamera;
    private Button btn_openTheGallery;
    private Button btn_cancle;
    private BaseActivity baseActivity;

    public PopUploadIcon(Activity activity, Object object,
                         PopResultListener resultListener) {
        super(activity, object, resultListener);
        if (activity instanceof BaseActivity) {
            baseActivity = (BaseActivity) activity;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_upload_icon;
    }

    @Override
    public void initPopView() {
        btn_openTheCamera = (Button) findViewbyId(R.id.btn_openTheCamera);
        btn_openTheGallery = (Button) findViewbyId(R.id.btn_openTheGallery);
        btn_cancle = (Button) findViewbyId(R.id.btn_cancle);

    }

    @Override
    public void initPopData(Object object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPopLisenter(PopResultListener listener) {
        btn_openTheCamera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (baseActivity != null) {
                    baseActivity.openTheCamera();
                }
                mPopWindow.dismiss();
            }
        });
        btn_openTheGallery.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (baseActivity != null) {
                    baseActivity.openTheGalley();
                }
                mPopWindow.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
    }

}
