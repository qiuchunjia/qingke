package qcjlibrary.widget.popupview;

import qcjlibrary.widget.popupview.base.PopView;
import qcjlibrary.widget.popupview.base.PopView.PopResultListener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午3:48:51 类描述：这个类是实现
 */

public class PopSizeChoose extends PopView {
    private TextView tv_a;
    private TextView tv_text;
    private TextView tv_big;
    private TextView tv_small;
    private ImageView iv_cancle;

    public PopSizeChoose(Activity activity, Object object,
                         PopResultListener resultListener) {
        super(activity, object, resultListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_zixun_textsize;
    }

    @Override
    public void initPopView() {
        tv_a = (TextView) findViewbyId(R.id.tv_a);
        tv_text = (TextView) findViewbyId(R.id.tv_text);
        tv_big = (TextView) findViewbyId(R.id.tv_big);
        tv_small = (TextView) findViewbyId(R.id.tv_small);
        iv_cancle = (ImageView) findViewbyId(R.id.iv_cancle);
    }

    @Override
    public void initPopData(Object object) {

    }

    @Override
    public PopupWindow setPopWidthAndHeight(View dataView) {
        return setPopWidthAndHeight2(dataView);
    }

    @Override
    public void setPopLisenter(PopResultListener listener) {
        iv_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
    }

}
