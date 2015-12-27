package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.config.Config;
import qcjlibrary.model.ModelAddHistoryCase;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelPop;
import qcjlibrary.util.ToastUtils;
import qcjlibrary.widget.popupview.PopChooseDrink;
import qcjlibrary.widget.popupview.PopChooseSmoke;
import qcjlibrary.widget.popupview.PopChooseStopDrink;
import qcjlibrary.widget.popupview.PopChooseStopSmoke;
import qcjlibrary.widget.popupview.PopDatePicker;
import qcjlibrary.widget.popupview.PopEatingHabit;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午10:55:26 类描述：这个类是实现
 */

public class PatientHistoryActivity extends BaseActivity {
    private EditText et_name;
    private EditText et_allergy_name;
    private EditText et_single_name;
    private RelativeLayout rl_eat;
    private TextView tv_eat_name;
    private RelativeLayout rl_smoke;
    private TextView tv_smoke_name;
    private EditText et_smoke_year;
    private EditText et_smoke_gen;
    private RelativeLayout rl_stop_smoke;
    private TextView tv_stop_smoke_name;
    private RelativeLayout rl_stop_smoke_time;
    private TextView tv_stop_smoke_time_name;

    private RelativeLayout rl_drink;
    private TextView tv_drink_name;
    private EditText et_drink_year;
    private EditText et_drink_much;
    private RelativeLayout rl_stop_drink;
    private TextView tv_stop_drink_name;
    private EditText et_first;
    private RelativeLayout rl_last_time;
    private TextView tv_last_time_name;
    private EditText et_stop_yuejins;
    private RelativeLayout rl_children;
    private TextView tv_children_name;
    private EditText et_family_historys;

    private RelativeLayout rl_stop_drink_time;
    private TextView tv_stop_drink_name_time;

    @Override
    public String setCenterTitle() {
        return "既往史";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patient_history;
    }

    @Override
    public void initView() {
        titleSetRightTitle("保存");
        et_name = (EditText) findViewById(R.id.et_name);
        et_allergy_name = (EditText) findViewById(R.id.et_allergy_name);
        et_single_name = (EditText) findViewById(R.id.et_single_name);
        rl_eat = (RelativeLayout) findViewById(R.id.rl_eat);
        tv_eat_name = (TextView) findViewById(R.id.tv_eat_name);
        rl_smoke = (RelativeLayout) findViewById(R.id.rl_smoke);
        tv_smoke_name = (TextView) findViewById(R.id.tv_smoke_name);
        et_smoke_year = (EditText) findViewById(R.id.et_smoke_year);
        et_smoke_gen = (EditText) findViewById(R.id.et_smoke_gen);
        rl_stop_smoke = (RelativeLayout) findViewById(R.id.rl_stop_smoke);
        tv_stop_smoke_name = (TextView) findViewById(R.id.tv_stop_smoke_name);
        rl_stop_smoke_time = (RelativeLayout) findViewById(R.id.rl_stop_smoke_time);
        tv_stop_smoke_time_name = (TextView) findViewById(R.id.tv_stop_smoke_time_name);

        rl_drink = (RelativeLayout) findViewById(R.id.rl_drink);
        tv_drink_name = (TextView) findViewById(R.id.tv_drink_name);
        et_drink_year = (EditText) findViewById(R.id.et_drink_year);
        et_drink_much = (EditText) findViewById(R.id.et_drink_much);
        rl_stop_drink = (RelativeLayout) findViewById(R.id.rl_stop_drink);
        tv_stop_drink_name = (TextView) findViewById(R.id.tv_stop_drink_name);
        et_first = (EditText) findViewById(R.id.et_first);
        rl_last_time = (RelativeLayout) findViewById(R.id.rl_last_time);
        tv_last_time_name = (TextView) findViewById(R.id.tv_last_time_name);
        et_stop_yuejins = (EditText) findViewById(R.id.et_stop_yuejins);
        rl_children = (RelativeLayout) findViewById(R.id.rl_children);
        tv_children_name = (TextView) findViewById(R.id.tv_children_name);
        et_family_historys = (EditText) findViewById(R.id.et_family_historys);

        rl_stop_drink_time = (RelativeLayout) findViewById(R.id.rl_stop_drink_time);
        tv_stop_drink_name_time = (TextView) findViewById(R.id.tv_stop_drink_name_time);
    }

    @Override
    public void initData() {
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(this);

    }

    @Override
    public void initListener() {
        rl_eat.setOnClickListener(this);
        rl_smoke.setOnClickListener(this);
        rl_stop_smoke.setOnClickListener(this);
        rl_stop_smoke_time.setOnClickListener(this);
        rl_drink.setOnClickListener(this);
        rl_stop_drink.setOnClickListener(this);
        rl_last_time.setOnClickListener(this);
        rl_children.setOnClickListener(this);
        rl_stop_drink_time.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:
                setEditContent();
                if (checkTheContent()) {
                    ModelAddHistoryCase historyCase = addDataToModel();
                    Log.i("addHistory", historyCase.toString());
                    sendRequest(mApp.getMedRecordImpl().saveHistory(historyCase),
                            ModelMsg.class, REQUEST_GET);
                }
                break;

            case R.id.rl_eat:
                PopEatingHabit habit = new PopEatingHabit(this, null, this);
                habit.showPop(rl_eat, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.rl_smoke:
                PopChooseSmoke chooseSmoke = new PopChooseSmoke(this, null, this);
                chooseSmoke.showPop(rl_smoke, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_stop_smoke:
                PopChooseStopSmoke chooseStopSmoke = new PopChooseStopSmoke(this,
                        null, this);
                chooseStopSmoke.showPop(rl_stop_smoke, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_stop_smoke_time:
                PopDatePicker datePicker = new PopDatePicker(this, null, this);
                datePicker.setType(Config.TYPE_STOP_SMOKE_TIME);
                datePicker.showPop(rl_stop_smoke_time, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_drink:
                PopChooseDrink chooseDrink = new PopChooseDrink(this, null, this);
                chooseDrink.showPop(rl_drink, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.rl_stop_drink:
                PopChooseStopDrink stopDrink = new PopChooseStopDrink(this, null,
                        this);
                stopDrink.showPop(rl_stop_drink, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_stop_drink_time:
                PopDatePicker stopDrinktime = new PopDatePicker(this, null, this);
                stopDrinktime.setType(Config.TYPE_STOP_DRINK_TIME);
                stopDrinktime.showPop(rl_stop_drink, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.rl_last_time:
                PopDatePicker lastTime = new PopDatePicker(this, null, this);
                lastTime.setType(Config.TYPE_LAST_TIME);
                lastTime.showPop(rl_last_time, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_children:
                // TODO
                break;

        }

    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (judgeTheMsg(object)) {
            onBackPressed();
        }
        return object;
    }

    @Override
    public Object onPopResult(Object object) {
        if (object instanceof ModelPop) {
            ModelPop modelPop = (ModelPop) object;
            if (modelPop.getType().equals(Config.TYPE_EATING_HABIT)) {
                eating_habit = modelPop.getDataStr();
                tv_eat_name.setText(eating_habit);
            } else if (modelPop.getType().equals(Config.TYPE_SMOKE)) {
                if (modelPop.getDataStr().equals("0")) {
                    smoke = "0";
                    tv_smoke_name.setText("抽烟");
                } else {
                    smoke = "1";
                    tv_smoke_name.setText("不抽烟");
                }

            } else if (modelPop.getType().equals(Config.TYPE_STOP_SMOKE)) {
                if (modelPop.getDataStr().equals("0")) {
                    stop_smoke = "0";
                    tv_stop_smoke_name.setText("未戒烟");
                } else {
                    stop_smoke = "1";
                    tv_stop_smoke_name.setText("已戒烟");
                }

            } else if (modelPop.getType().equals(Config.TYPE_STOP_SMOKE_TIME)) {
                tv_stop_smoke_time_name.setText(modelPop.getDataStr());
                stop_smoke_time = modelPop.getDataStr();

            } else if (modelPop.getType().equals(Config.TYPE_DRINK)) {
                if (modelPop.getDataStr().equals("0")) {
                    drink = "0";
                    tv_drink_name.setText("不饮酒");
                } else {
                    drink = "1";
                    tv_drink_name.setText("饮酒");
                }

            } else if (modelPop.getType().equals(Config.TYPE_STOP_DRINK)) {
                if (modelPop.getDataStr().equals("0")) {
                    stop_drink = "0";
                    tv_stop_drink_name.setText("未戒酒");
                } else {
                    stop_drink = "1";
                    tv_stop_drink_name.setText("已戒酒");
                }

            } else if (modelPop.getType().equals(Config.TYPE_STOP_DRINK_TIME)) {
                stop_drink_time = modelPop.getDataStr();
                tv_stop_drink_name_time.setText(stop_drink_time);
            } else if (modelPop.getType().equals(Config.TYPE_LAST_TIME)) {
                menarche_etime = modelPop.getDataStr();
                tv_last_time_name.setText(menarche_etime);
            }

        }
        return super.onPopResult(object);
    }

    /************************
     * 添加既往史需要的数据
     **********************************/
    private String med_history; // 既往病史
    private String allergy_history; // 过敏史
    private String per_history; // 个人史
    private String eating_habit;// 饮食习惯
    private String smoke;// 抽烟 0不抽 1抽
    private String smoke_age;// 抽烟年龄
    private String smoke_time;// 每日抽烟根数
    private String stop_smoke;// 否戒烟 0-未戒 1-已戒
    private String stop_smoke_time;// 戒烟时间
    private String drink;// 是否饮酒 0不饮酒 1饮酒
    private String drink_age;// 饮酒年龄
    private String drink_consumption;// 饮酒量
    private String stop_drink;// 是否戒酒 0-未戒 1-已戒
    private String stop_drink_time;// 戒酒时间
    private String menarche_age;// 初潮年纪
    private String menarche_etime;// 末次月经时间
    private String amenorrhoea_age;// 闭经年龄
    private String childs = "一子一女";// 子女
    private String family_history;// 家族史

    /**
     * 设置输入框的内容
     */
    private void setEditContent() {
        med_history = et_name.getText().toString();
        allergy_history = et_allergy_name.getText().toString();
        per_history = et_single_name.getText().toString();

        smoke_age = et_smoke_year.getText().toString();
        smoke_time = et_smoke_gen.getText().toString();
        drink_age = et_drink_year.getText().toString();
        drink_consumption = et_drink_much.getText().toString();
        menarche_age = et_first.getText().toString();
        amenorrhoea_age = et_stop_yuejins.getText().toString();
        family_history = et_family_historys.getText().toString();

    }

    /**
     * 检验数据是否存在空，用于需要上传的时候的判断
     *
     * @return
     */
    private boolean checkTheContent() {
        if (TextUtils.isEmpty(med_history)) {
            ToastUtils.showToast("请输入既往病史");
            return false;
        }
        if (TextUtils.isEmpty(allergy_history)) {
            ToastUtils.showToast("请输入过敏史");
            return false;
        }
        if (TextUtils.isEmpty(per_history)) {
            ToastUtils.showToast("请输入个人史");
            return false;
        }
        if (TextUtils.isEmpty(eating_habit)) {
            ToastUtils.showToast("请选择饮食习惯");
            return false;
        }
        if (TextUtils.isEmpty(smoke)) {
            ToastUtils.showToast("请选择是否抽烟");
            return false;
        }
        if (TextUtils.isEmpty(smoke_age)) {
            ToastUtils.showToast("请输入抽烟年龄");
            return false;
        }
        if (TextUtils.isEmpty(stop_smoke)) {
            ToastUtils.showToast("请选择是否戒烟");
            return false;
        }
        if (TextUtils.isEmpty(stop_smoke_time)) {
            ToastUtils.showToast("选择戒烟年龄");
            return false;
        }
        if (TextUtils.isEmpty(drink)) {
            ToastUtils.showToast("请选择是否饮酒");
            return false;
        }
        if (TextUtils.isEmpty(drink_age)) {
            ToastUtils.showToast("请输入饮酒年龄");
            return false;
        }
        if (TextUtils.isEmpty(drink_consumption)) {
            ToastUtils.showToast("请输入饮酒量");
            return false;
        }
        if (TextUtils.isEmpty(stop_drink)) {
            ToastUtils.showToast("请选择是否戒酒");
            return false;
        }
        if (TextUtils.isEmpty(stop_drink_time)) {
            ToastUtils.showToast("请选择戒酒时间");
            return false;
        }
        if (TextUtils.isEmpty(menarche_age)) {
            ToastUtils.showToast("请输入初潮年纪");
            return false;
        }
        if (TextUtils.isEmpty(menarche_etime)) {
            ToastUtils.showToast("请选择末次月经时间");
            return false;
        }
        if (TextUtils.isEmpty(amenorrhoea_age)) {
            ToastUtils.showToast("请输入闭经年龄");
            return false;
        }
        if (TextUtils.isEmpty(childs)) {
            ToastUtils.showToast("请选择子女情况");
            return false;
        }
        if (TextUtils.isEmpty(family_history)) {
            ToastUtils.showToast("请输入家族史");
            return false;
        }
        return true;
    }

    /**
     * 添加数据到model用于请求发送
     *
     * @return
     */
    private ModelAddHistoryCase addDataToModel() {
        ModelAddHistoryCase HistoryCase = new ModelAddHistoryCase();
        HistoryCase.setMed_history(med_history);
        HistoryCase.setAllergy_history(allergy_history);
        HistoryCase.setPer_history(per_history);
        HistoryCase.setEating_habit(eating_habit);
        HistoryCase.setSmoke(smoke);
        HistoryCase.setSmoke_age(smoke_age);
        HistoryCase.setSmoke_time(smoke_time);
        HistoryCase.setStop_smoke(stop_smoke);
        HistoryCase.setStop_smoke_time(stop_smoke_time);
        HistoryCase.setDrink(drink);
        HistoryCase.setDrink_age(drink_age);
        HistoryCase.setDrink_consumption(drink_consumption);
        HistoryCase.setStop_drink(stop_drink);
        HistoryCase.setStop_drink_time(stop_drink_time);
        HistoryCase.setMenarche_age(menarche_age);
        HistoryCase.setMenarche_etime(menarche_etime);
        HistoryCase.setAmenorrhoea_age(amenorrhoea_age);
        HistoryCase.setChilds(childs);
        HistoryCase.setFamily_history(family_history);
        return HistoryCase;
    }
}
