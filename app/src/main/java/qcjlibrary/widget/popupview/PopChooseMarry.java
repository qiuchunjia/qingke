package qcjlibrary.widget.popupview;

import qcjlibrary.config.Config;
import qcjlibrary.model.ModelPop;
import qcjlibrary.widget.popupview.base.PopView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午3:48:51 类描述：这个类是实现
 */

public class PopChooseMarry extends PopView {
    private TextView tv_praise;
    private TextView tv_cancle;

    public PopChooseMarry(Activity activity, Object object,
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
        tv_praise.setText("已婚");
        tv_cancle.setText("未婚");
    }

    @Override
    public void setPopLisenter(final PopResultListener listener) {
        final ModelPop modelPop = new ModelPop();
        modelPop.setType(Config.TYPE_MARRY);
        tv_praise.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                modelPop.setDataStr("1");
                listener.onPopResult(modelPop);
                mPopWindow.dismiss();
            }
        });
        tv_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                modelPop.setDataStr("0");
                listener.onPopResult(modelPop);
                mPopWindow.dismiss();
            }
        });
    }

}
