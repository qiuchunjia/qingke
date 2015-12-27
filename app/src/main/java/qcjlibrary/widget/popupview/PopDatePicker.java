package qcjlibrary.widget.popupview;

import qcjlibrary.config.Config;
import qcjlibrary.model.ModelPop;
import qcjlibrary.widget.popupview.base.PopView;
import qcjlibrary.widget.wheelview.ArrayWheelAdapter;
import qcjlibrary.widget.wheelview.WheelView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午10:57:44 类描述：
 * <p/>
 * 这个类是实现时间选择器
 */

public class PopDatePicker extends PopView {
    private TextView tv_date_cancle;
    private TextView tv_date_sure;
    private WheelView wv_date_year;
    private WheelView wv_date_month;
    private WheelView wv_date_day;
    /***************************************/
    public static String[] mYear = new String[70];
    public static String[] mMonth = new String[12];
    public static String[] mDay = new String[31];

    private String mCurrentYear; // 当前的年
    private String mCurrentMonth;// 当前的月
    private String mCurrentDay;// 当前的日

    private String mDateType;

    public PopDatePicker(Activity activity, Object object,
                         PopResultListener resultListener) {
        super(activity, object, resultListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_date_picker;
    }

    @Override
    public void initPopView() {
        tv_date_cancle = (TextView) findViewbyId(R.id.tv_date_cancle);
        tv_date_sure = (TextView) findViewbyId(R.id.tv_date_sure);
        wv_date_year = (WheelView) findViewbyId(R.id.wv_date_year);
        wv_date_month = (WheelView) findViewbyId(R.id.wv_date_month);
        wv_date_day = (WheelView) findViewbyId(R.id.wv_date_day);
    }

    @Override
    public void initPopData(Object object) {
        genateYearMouthDay();
        wv_date_year.setVisibleItems(5);
        wv_date_year.setCyclic(true);
        wv_date_year.setAdapter(new ArrayWheelAdapter<String>(mYear));
        wv_date_month.setVisibleItems(5);
        wv_date_month.setCyclic(true);
        wv_date_month.setAdapter(new ArrayWheelAdapter<String>(mMonth));
        wv_date_day.setVisibleItems(5);
        wv_date_day.setCyclic(true);
        wv_date_day.setAdapter(new ArrayWheelAdapter<String>(mDay));
    }

    public void setType(String type) {
        mDateType = type;
    }

    @Override
    public void setPopLisenter(final PopResultListener listener) {
        tv_date_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        tv_date_sure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String pickTime = cacluteDate();
                ModelPop modelPop = new ModelPop();
                if (mDateType != null) {
                    modelPop.setType(mDateType);
                } else {
                    modelPop.setType(Config.TYPE_DATE);
                }
                modelPop.setDataStr(pickTime.trim());
                listener.onPopResult(modelPop);
                mPopWindow.dismiss();
            }
        });
    }

    /**
     * 生成年月日
     */
    private void genateYearMouthDay() {
        for (int i = 0; i < mYear.length; i++) {
            mYear[i] = "  " + (2015 - i) + "  ";
        }
        for (int i = 0; i < mMonth.length; i++) {
            mMonth[i] = "  " + ((1 + i)) + "  ";
        }
        for (int i = 0; i < mDay.length; i++) {
            mDay[i] = "  " + ((1 + i)) + "  ";
        }
    }

    /**
     * 计算日期
     *
     * @return
     */
    private String cacluteDate() {
        mCurrentYear = mYear[wv_date_year.getCurrentItem()];
        mCurrentYear = mCurrentYear.trim();
        mCurrentMonth = mMonth[wv_date_month.getCurrentItem()];
        mCurrentMonth = mCurrentMonth.trim();
        mCurrentDay = mDay[wv_date_day.getCurrentItem()];
        mCurrentDay = mCurrentDay.trim();
        return mCurrentYear + "年" + mCurrentMonth + "月" + mCurrentDay + "号";
    }

}
