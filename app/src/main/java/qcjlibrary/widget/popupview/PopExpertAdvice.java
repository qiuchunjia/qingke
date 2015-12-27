package qcjlibrary.widget.popupview;

import qcjlibrary.util.UIUtils;
import qcjlibrary.widget.popupview.base.PopView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午11:22:10 类描述：这个类是实现
 */

public class PopExpertAdvice extends PopView {
    private RelativeLayout rl_sample;
    private LinearLayout ll_advice_content;
    private TextView tv_dianzhi;

    public PopExpertAdvice(Activity activity, Object object,
                           PopResultListener resultListener) {
        super(activity, object, resultListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_expert_advice;
    }

    @Override
    public void initPopView() {
        rl_sample = (RelativeLayout) findViewbyId(R.id.rl_sample);
        ll_advice_content = (LinearLayout) findViewbyId(R.id.ll_advice_content);
        tv_dianzhi = (TextView) findViewbyId(R.id.tv_dianzhi);
    }

    @Override
    public PopupWindow setPopWidthAndHeight(View dataView) {
        PopupWindow popupWindow = new PopupWindow(dataView,
                UIUtils.getWindowWidth(mActivity) / 6 * 5,
                LayoutParams.MATCH_PARENT);
        return popupWindow;
    }

    @Override
    public void initPopData(Object object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPopLisenter(PopResultListener listener) {
        rl_sample.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        tv_dianzhi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });

    }

}
