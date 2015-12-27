package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.config.Config;
import qcjlibrary.model.ModelAddCase;
import qcjlibrary.model.ModelMeAddress;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelPop;
import qcjlibrary.util.ToastUtils;
import qcjlibrary.widget.popupview.PopChooseGender;
import qcjlibrary.widget.popupview.PopChooseInsurance;
import qcjlibrary.widget.popupview.PopChooseMarry;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午10:55:26 类描述：这个类是实现
 */

public class PatientInforActivity extends BaseActivity {
    private EditText et_name;
    private RelativeLayout rl_gender;
    private TextView tv_gender_name;
    private EditText et_age;
    private RelativeLayout rl_marry;
    private TextView tv_marry_name;
    private RelativeLayout rl_nation;
    private TextView tv_nation_name;
    private EditText et_job;
    private RelativeLayout rl_education;
    private TextView tv_education_name;
    private RelativeLayout rl_insurance;
    private TextView tv_insurance_name;
    private RelativeLayout rl_hometown;
    private TextView tv_hometown_name;
    private RelativeLayout rl_address;
    private TextView tv_address_name;
    private EditText et_heights;
    private EditText et_weights;

    @Override
    public String setCenterTitle() {
        return "患者信息";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patient_infor;
    }

    @Override
    public void initView() {
        titleSetRightTitle("保存");
        et_name = (EditText) findViewById(R.id.et_name);
        rl_gender = (RelativeLayout) findViewById(R.id.rl_gender);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_gender_name = (TextView) findViewById(R.id.tv_gender_name);
        et_age = (EditText) findViewById(R.id.et_age);
        rl_marry = (RelativeLayout) findViewById(R.id.rl_marry);
        tv_marry_name = (TextView) findViewById(R.id.tv_marry_name);
        rl_nation = (RelativeLayout) findViewById(R.id.rl_nation);
        tv_nation_name = (TextView) findViewById(R.id.tv_nation_name);
        et_job = (EditText) findViewById(R.id.et_job);
        rl_education = (RelativeLayout) findViewById(R.id.rl_education);
        tv_education_name = (TextView) findViewById(R.id.tv_education_name);
        rl_insurance = (RelativeLayout) findViewById(R.id.rl_insurance);
        tv_insurance_name = (TextView) findViewById(R.id.tv_insurance_name);
        rl_hometown = (RelativeLayout) findViewById(R.id.rl_hometown);
        tv_hometown_name = (TextView) findViewById(R.id.tv_hometown_name);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
        et_heights = (EditText) findViewById(R.id.et_heights);
        et_weights = (EditText) findViewById(R.id.et_weights);
    }

    @Override
    public void initData() {
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(this);

    }

    @Override
    public void initListener() {
        rl_gender.setOnClickListener(this);
        rl_marry.setOnClickListener(this);
        rl_nation.setOnClickListener(this);
        rl_education.setOnClickListener(this);
        rl_insurance.setOnClickListener(this);
        rl_hometown.setOnClickListener(this);
        rl_address.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:
                setEditContent();
                if (checkTheContent()) {
                    ModelAddCase addCase = addDataToModel();
                    sendRequest(mApp.getMedRecordImpl().saveInfo(addCase),
                            ModelMsg.class, REQUEST_GET);
                }

                break;
            case R.id.rl_gender:
                PopChooseGender chooseGender = new PopChooseGender(this, null, this);
                chooseGender.showPop(rl_gender, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.rl_marry:
                PopChooseMarry chooseMarry = new PopChooseMarry(this, null, this);
                chooseMarry.showPop(rl_marry, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_nation:
                mApp.startActivityForResult_qcj(this, ChooseNationActivity.class,
                        null);
                break;
            case R.id.rl_education:
                mApp.startActivityForResult_qcj(this,
                        ChooseEducationActivity.class, null);
                break;
            case R.id.rl_insurance:
                PopChooseInsurance chooseInsurance = new PopChooseInsurance(this,
                        null, this);
                chooseInsurance.showPop(rl_insurance, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_hometown:
                ModelMeAddress hometown = new ModelMeAddress();
                hometown.setType(Config.TYPE_HOME);
                mApp.startActivityForResult_qcj(this,
                        MeChooseProvinceActivity.class,
                        sendDataToBundle(hometown, null));
                break;
            case R.id.rl_address:
                ModelMeAddress address = new ModelMeAddress();
                address.setType(Config.TYPE_ADDRESS);
                mApp.startActivityForResult_qcj(this,
                        MeChooseProvinceActivity.class,
                        sendDataToBundle(address, null));
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
            ModelPop popdata = (ModelPop) object;
            if (popdata.getType().equals(Config.TYPE_GENDER)) {
                if (popdata.getDataStr().equals("1")) {
                    tv_gender_name.setText("男");
                    sex = "0";
                } else {
                    tv_gender_name.setText("女");
                    sex = "1";
                }
            } else if (popdata.getType().equals(Config.TYPE_MARRY)) {
                if (popdata.getDataStr().equals("0")) {
                    tv_marry_name.setText("未婚");
                    marriage = "0";
                } else {
                    tv_marry_name.setText("已婚");
                    marriage = "1";
                }

            } else if (popdata.getType().equals(Config.TYPE_INSURANCE)) {
                tv_insurance_name.setText(popdata.getDataStr());
                insform = popdata.getDataStr();
            }
        }
        return super.onPopResult(object);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Object object = getReturnResultSeri(resultCode, data,
                Config.TYPE_ADDRESS);
        if (object instanceof ModelMeAddress) {
            ModelMeAddress address = (ModelMeAddress) object;
            if (address.getType().equals(Config.TYPE_HOME)) {
                tv_hometown_name.setText(address.getWholeAddress());
                natives = address.getWholeId();
            } else if (address.getType().equals(Config.TYPE_ADDRESS)) {
                tv_address_name.setText(address.getWholeAddress());
                domicile = address.getWholeId();
            }
        }
        Object object2 = getReturnResultSeri(resultCode, data,
                Config.TYPE_EDUCATION);
        if (object2 instanceof String) {
            String dataStr = (String) object2;
            tv_education_name.setText(dataStr);
            education = dataStr;
        }
        Object object3 = getReturnResultSeri(resultCode, data,
                Config.TYPE_NATION);
        if (object3 instanceof String) {
            String nationdata = (String) object3;
            nation = nationdata;
            tv_nation_name.setText(nationdata);
        }
    }

    /**************************************
     * 需要上传的参数集合
     ***********************/
    private String realname;
    private String sex;
    private String age;
    private String marriage;
    private String nation;
    private String profession;
    private String education;
    private String insform;
    private String natives;
    private String domicile;
    private String height;
    private String weight;

    /**
     * 设置输入框的内容
     */
    private void setEditContent() {
        realname = et_name.getText().toString();
        age = et_age.getText().toString();
        profession = et_job.getText().toString();
        height = et_heights.getText().toString();
        weight = et_weights.getText().toString();
    }

    /**
     * 检验数据是否存在空，用于需要上传的时候的判断
     *
     * @return
     */
    private boolean checkTheContent() {
        if (TextUtils.isEmpty(realname)) {
            ToastUtils.showToast("请输入名字");
            return false;
        }
        if (TextUtils.isEmpty(sex)) {
            ToastUtils.showToast("请选择性别");
            return false;
        }
        if (TextUtils.isEmpty(age)) {
            ToastUtils.showToast("请输入年龄");
            return false;
        }
        if (TextUtils.isEmpty(marriage)) {
            ToastUtils.showToast("请选择是否结婚");
            return false;
        }
        if (TextUtils.isEmpty(nation)) {
            ToastUtils.showToast("请选择民族");
            return false;
        }
        if (TextUtils.isEmpty(profession)) {
            ToastUtils.showToast("请输入职业");
            return false;
        }
        if (TextUtils.isEmpty(education)) {
            ToastUtils.showToast("请选择学历");
            return false;
        }
        if (TextUtils.isEmpty(insform)) {
            ToastUtils.showToast("请选择保险形式");
            return false;
        }
        if (TextUtils.isEmpty(natives)) {
            ToastUtils.showToast("请选择籍贯");
            return false;
        }
        if (TextUtils.isEmpty(domicile)) {
            ToastUtils.showToast("请选择居住地");
            return false;
        }

        if (TextUtils.isEmpty(height)) {
            ToastUtils.showToast("请输入身高");
            return false;
        }
        if (TextUtils.isEmpty(weight)) {
            ToastUtils.showToast("请输入体重");
            return false;
        }

        return true;
    }

    /**
     * 添加数据到model用于请求发送
     *
     * @return
     */
    private ModelAddCase addDataToModel() {
        ModelAddCase addCase = new ModelAddCase();
        addCase.setRealname(realname);
        addCase.setSex(sex);
        addCase.setAge(age);
        addCase.setMarriage(marriage);
        addCase.setNation(nation);
        addCase.setProfession(profession);
        addCase.setEducation(education);
        addCase.setInsform(insform);
        addCase.setNatives(natives);
        addCase.setDomicile(domicile);
        addCase.setHeight(height);
        addCase.setWeight(weight);
        return addCase;
    }
}
