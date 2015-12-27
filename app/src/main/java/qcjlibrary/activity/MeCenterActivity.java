package qcjlibrary.activity;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.model.base.Model;
import qcjlibrary.widget.RoundImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.activity.GuideActivity;
import com.zhiyicx.zycx.activity.WebActivity;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.unit.Anim;

/**
 * author：qiuchunjia time：下午5:41:04 类描述：这个类是实现
 */

public class MeCenterActivity extends BaseActivity {
    private RelativeLayout rl_user;
    private RoundImageView riv_user_icon;
    private RelativeLayout rl_mycase;
    private RelativeLayout rl_question;
    private RelativeLayout rl_app;
    private RelativeLayout rl_cycle;
    private RelativeLayout rl_periodical;
    private Button btn_quit;

    @Override
    public String setCenterTitle() {

        return "个人中心";
    }

    @Override
    public void initIntent() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_me_center;
    }

    @Override
    public void initView() {
        rl_user = (RelativeLayout) findViewById(R.id.rl_user);
        riv_user_icon = (RoundImageView) findViewById(R.id.riv_user_icon);
        rl_mycase = (RelativeLayout) findViewById(R.id.rl_mycase);
        rl_question = (RelativeLayout) findViewById(R.id.rl_question);
        rl_app = (RelativeLayout) findViewById(R.id.rl_app);
        rl_cycle = (RelativeLayout) findViewById(R.id.rl_cycle);
        rl_periodical = (RelativeLayout) findViewById(R.id.rl_periodical);
        btn_quit = (Button) findViewById(R.id.btn_quit);

    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        rl_user.setOnClickListener(this);
        rl_mycase.setOnClickListener(this);
        rl_question.setOnClickListener(this);
        rl_app.setOnClickListener(this);
        rl_cycle.setOnClickListener(this);
        rl_periodical.setOnClickListener(this);
        btn_quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user:
                // TODO
                mApp.startActivity_qcj(this, MeCenterBasicActivity.class,
                        sendDataToBundle(new Model(), null));
                break;

            case R.id.rl_mycase:
                mApp.startActivity_qcj(this, PatientMeActivity.class,
                        sendDataToBundle(new Model(), null));
                break;
            case R.id.rl_question:
                mApp.startActivity_qcj(this, ExpertRequestActivity.class,
                        sendDataToBundle(new Model(), null));
                break;
            case R.id.rl_app:
                mApp.startActivity_qcj(this, MeAplicationActivity.class,
                        sendDataToBundle(new Model(), null));
                break;
            case R.id.rl_cycle:
                mApp.startActivity_qcj(this, WebActivity.class,
                        sendDataToBundle(new Model(), null));
                break;
            case R.id.rl_periodical:
                mApp.startActivity_qcj(this, MePerioActivity.class,
                        sendDataToBundle(new Model(), null));
                break;
            case R.id.btn_quit:
                quitLogin();
                break;
        }

    }

    /**
     * 退出登录
     */
    public void quitLogin() {
        final Activity obj = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(obj);
        builder.setMessage("确定要注销此帐户吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Thinksns app = (Thinksns) obj.getApplicationContext();
                        app.getUserSql().clear();
                        // Thinksns.exitApp();
                        Intent intent = new Intent(obj, GuideActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        obj.startActivity(intent);
                        Anim.in(obj);
                        obj.finish();
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
