package qcjlibrary.activity;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.model.ModelFoodIdDetail;
import qcjlibrary.model.ModelFoodIdDetailInfo;
import qcjlibrary.model.ModelFoodSearch0;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午4:17:17 类描述：这个类是实现
 */

public class FoodSingleDetailActivity extends BaseActivity {
    private TextView tv_food_name;
    private TextView tv_food_category;
    private ImageView iv_food_icon;
    private TextView tv_food_main_value;
    private TextView tv_function_value;
    private RelativeLayout rl_funtion;
    private TextView tv_category_value;
    private TextView tv_food_way_value;
    private TextView tv_flag;
    private TextView tv_flag2;
    private ImageView iv_relate1;
    private TextView tv_relate1;
    private ImageView iv_relate2;
    private TextView tv_relate2;
    private ImageView iv_relate3;
    private TextView tv_relate3;
    private ImageView iv_relate4;
    private TextView tv_relate4;

    private ModelFoodSearch0 mFoodData;
    private ModelFoodIdDetail mDetail;

    @Override
    public String setCenterTitle() {
        return "玉米";
    }

    @Override
    public void initIntent() {
        mFoodData = (ModelFoodSearch0) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_category_single_detail;
    }

    @Override
    public void initView() {
        tv_food_name = (TextView) findViewById(R.id.tv_food_name);
        tv_food_category = (TextView) findViewById(R.id.tv_food_category);
        iv_food_icon = (ImageView) findViewById(R.id.iv_food_icon);
        tv_food_main_value = (TextView) findViewById(R.id.tv_food_main_value);
        tv_function_value = (TextView) findViewById(R.id.tv_function_value);
        rl_funtion = (RelativeLayout) findViewById(R.id.rl_funtion);
        tv_category_value = (TextView) findViewById(R.id.tv_category_value);
        tv_food_way_value = (TextView) findViewById(R.id.tv_food_way_value);
        tv_flag = (TextView) findViewById(R.id.tv_flag);
        tv_flag2 = (TextView) findViewById(R.id.tv_flag2);
        iv_relate1 = (ImageView) findViewById(R.id.iv_relate1);
        tv_relate1 = (TextView) findViewById(R.id.tv_relate1);
        iv_relate2 = (ImageView) findViewById(R.id.iv_relate2);
        tv_relate2 = (TextView) findViewById(R.id.tv_relate2);
        iv_relate3 = (ImageView) findViewById(R.id.iv_relate3);
        tv_relate3 = (TextView) findViewById(R.id.tv_relate3);
        iv_relate4 = (ImageView) findViewById(R.id.iv_relate4);
        tv_relate4 = (TextView) findViewById(R.id.tv_relate4);

    }

    @Override
    public void initData() {
        sendRequest(mApp.getFoodImpl().food_detail(mFoodData),
                ModelFoodIdDetail.class, REQUEST_GET);

    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelFoodIdDetail) {
            mDetail = (ModelFoodIdDetail) object;
            addInfoToView(mDetail.getInfo());
            addRelateToView(mDetail.getFoodRel());
        }
        return object;
    }

    /**
     * 添加相关的图片
     *
     * @param foodRel
     */
    private void addRelateToView(List<ModelFoodIdDetailInfo> foodRel) {
        if (foodRel != null) {
            for (int i = 0; i < foodRel.size(); i++) {
                ModelFoodIdDetailInfo detailInfo = foodRel.get(i);
                if (i == 0) {
                    mApp.displayImage(detailInfo.getImgSrc(), iv_relate1);
                    tv_relate1.setText(detailInfo.getFood_name());
                } else if (i == 1) {
                    mApp.displayImage(detailInfo.getImgSrc(), iv_relate2);
                    tv_relate2.setText(detailInfo.getFood_name());
                } else if (i == 2) {
                    mApp.displayImage(detailInfo.getImgSrc(), iv_relate3);
                    tv_relate3.setText(detailInfo.getFood_name());
                } else if (i == 3) {
                    mApp.displayImage(detailInfo.getImgSrc(), iv_relate4);
                    tv_relate4.setText(detailInfo.getFood_name());
                }
            }
        }
    }

    /**
     * 把基本信息添加到上面
     *
     * @param info
     */
    private void addInfoToView(ModelFoodIdDetailInfo info) {
        if (info != null) {
            tv_food_name.setText(info.getFood_name());
            mApp.displayImage(info.getImgSrc(), iv_food_icon);
            tv_food_main_value.setText(info.getFood_anticancer());
            tv_function_value.setText(info.getFood_tumor());
            // TODO 添加圆形的控件

            tv_category_value.setText(info.getFood_forcancer());
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

}
