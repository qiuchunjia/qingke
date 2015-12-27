package qcjlibrary.widget.popupview;

import qcjlibrary.model.ModelRequestAnswerComom;
import qcjlibrary.widget.popupview.base.PopView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午3:48:51 类描述：这个类是实现
 */

public class PopDealAnwer extends PopView {
    private TextView tv_praise;
    private TextView tv_cancle;
    private String mCurrentPos;

    public PopDealAnwer(Activity activity, Object object,
                        PopResultListener resultListener) {
        super(activity, object, resultListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_deal_anwer;
    }

    @Override
    public void initPopView() {
        tv_praise = (TextView) findViewbyId(R.id.tv_praise);
        tv_cancle = (TextView) findViewbyId(R.id.tv_cancle);
    }

    @Override
    public void initPopData(Object object) {
        if (object instanceof ModelRequestAnswerComom) {
            ModelRequestAnswerComom comom = (ModelRequestAnswerComom) object;
            if (comom.getIs_best().equals("0")) {
                tv_praise.setText("采纳为最佳答案");
                mCurrentPos = "1";
            }
            if (comom.getIs_best().equals("1")) {
                tv_praise.setText("取消最佳答案");
                mCurrentPos = "2";
            }
        }
    }

    @Override
    public void setPopLisenter(final PopResultListener listener) {
        tv_praise.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onPopResult(mCurrentPos);
                mPopWindow.dismiss();
            }
        });
        tv_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
    }

}
