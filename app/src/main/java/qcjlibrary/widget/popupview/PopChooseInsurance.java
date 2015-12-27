package qcjlibrary.widget.popupview;

import com.zhiyicx.zycx.R;

import qcjlibrary.config.Config;
import qcjlibrary.model.ModelPop;
import qcjlibrary.widget.popupview.base.PopView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * author：qiuchunjia time：下午2:29:43 类描述：这个类是实现
 */

public class PopChooseInsurance extends PopView {
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private ModelPop mPopData;

    public PopChooseInsurance(Activity activity, Object object,
                              PopResultListener resultListener) {
        super(activity, object, resultListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_choose_insurance;
    }

    @Override
    public void initPopView() {
        tv_1 = (TextView) findViewbyId(R.id.tv_1);
        tv_2 = (TextView) findViewbyId(R.id.tv_2);
        tv_3 = (TextView) findViewbyId(R.id.tv_3);
        tv_4 = (TextView) findViewbyId(R.id.tv_4);
        tv_5 = (TextView) findViewbyId(R.id.tv_5);
        mPopData = new ModelPop();
        mPopData.setType(Config.TYPE_INSURANCE);
    }

    @Override
    public void initPopData(Object object) {

    }

    @Override
    public void setPopLisenter(PopResultListener listener) {
        tv_1.setOnClickListener(new MyOnClickListener(listener));
        tv_2.setOnClickListener(new MyOnClickListener(listener));
        tv_3.setOnClickListener(new MyOnClickListener(listener));
        tv_4.setOnClickListener(new MyOnClickListener(listener));
        tv_5.setOnClickListener(new MyOnClickListener(listener));

    }

    private class MyOnClickListener implements OnClickListener {
        private PopResultListener mListener;

        public MyOnClickListener(PopResultListener listener) {
            this.mListener = listener;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_1:
                    mPopData.setDataStr("城保");
                    mListener.onPopResult(mPopData);
                    mPopWindow.dismiss();
                    break;

                case R.id.tv_2:
                    mPopData.setDataStr("新农合");
                    mListener.onPopResult(mPopData);
                    mPopWindow.dismiss();
                    break;
                case R.id.tv_3:
                    mPopData.setDataStr("城乡医疗救助");
                    mListener.onPopResult(mPopData);
                    mPopWindow.dismiss();
                    break;
                case R.id.tv_4:
                    mPopData.setDataStr("商业保险");
                    mListener.onPopResult(mPopData);
                    mPopWindow.dismiss();
                    break;
                case R.id.tv_5:
                    mPopData.setDataStr("自费");
                    mListener.onPopResult(mPopData);
                    mPopWindow.dismiss();
                    break;
            }

        }
    }

}
