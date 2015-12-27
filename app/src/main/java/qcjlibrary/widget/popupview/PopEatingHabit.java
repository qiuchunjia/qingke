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
 * author：qiuchunjia time：下午3:01:15 类描述：这个类是实现
 */

public class PopEatingHabit extends PopView {
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;

    public PopEatingHabit(Activity activity, Object object,
                          PopResultListener resultListener) {
        super(activity, object, resultListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_eating_habits;
    }

    @Override
    public void initPopView() {
        tv_1 = (TextView) findViewbyId(R.id.tv_1);
        tv_2 = (TextView) findViewbyId(R.id.tv_2);
        tv_3 = (TextView) findViewbyId(R.id.tv_3);

    }

    @Override
    public void initPopData(Object object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPopLisenter(final PopResultListener listener) {
        final ModelPop modelPop = new ModelPop();
        modelPop.setType(Config.TYPE_EATING_HABIT);
        tv_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                modelPop.setDataStr("荤");
                listener.onPopResult(modelPop);
                mPopWindow.dismiss();
            }
        });
        tv_2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                modelPop.setDataStr("素");
                listener.onPopResult(modelPop);
                mPopWindow.dismiss();
            }
        });

        tv_3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                modelPop.setDataStr("荤素搭配");
                listener.onPopResult(modelPop);
                mPopWindow.dismiss();
            }
        });

    }

}
