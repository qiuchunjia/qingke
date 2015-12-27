package qcjlibrary.activity;

import java.util.ArrayList;
import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.model.ModelExperienceSend;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.util.DateUtil;
import qcjlibrary.util.ToastUtils;
import qcjlibrary.util.localImageHelper.LocalImageManager;
import qcjlibrary.widget.popupview.PopDatePicker;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午2:34:26 类描述：这个类是实现
 */

public class ExperienceSendActivity extends BaseActivity {

    private EditText et_title;
    private TextView tv_choosedate;
    private EditText et_content;
    private RelativeLayout rl_choose;
    private LinearLayout ll_ScrollView;

    private ModelExperienceSend mSendData;
    private LocalImageManager mImageManager;

    @Override
    public String setCenterTitle() {
        return "发布经历";
    }

    @Override
    public void initIntent() {
        mSendData = (ModelExperienceSend) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_experience;
    }

    @Override
    public void initView() {
        titleSetRightTitle("发布");
        et_title = (EditText) findViewById(R.id.et_title);
        tv_choosedate = (TextView) findViewById(R.id.tv_choosedate);
        et_content = (EditText) findViewById(R.id.et_content);
        rl_choose = (RelativeLayout) findViewById(R.id.rl_choose);
        ll_ScrollView = (LinearLayout) findViewById(R.id.ll_ScrollView);
        mImageManager = LocalImageManager.from(mApp);
        addImageToHsv(null, ADDPHOTO);
    }

    @Override
    public void initData() {
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(this);

    }

    @Override
    public void initListener() {
        tv_choosedate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();
                String date = tv_choosedate.getText().toString();
                if (judgeTheSend(title, date, content)) {
                    mSendData.setTitle(title);
                    mSendData.setBody(content);
                    mSendData.setPost_time(DateUtil.dateToStr(date));
                    mSendData.setPhotoUrls(mPhotoList);
                    sendRequest(mApp.getExperienceImpl().addPost(mSendData),
                            ModelMsg.class, REQUEST_GET);
                }
                break;
            case R.id.tv_choosedate:
                PopDatePicker datePicker = new PopDatePicker(this, null, this);
                datePicker.showPop(tv_choosedate, Gravity.BOTTOM, 0, 0);
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
        tv_choosedate.setText(object.toString());
        return super.onPopResult(object);
    }

    /**
     * 判断内容是否为空
     *
     * @param title
     * @param content
     * @return
     */
    private boolean judgeTheSend(String title, String date, String content) {
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showToast("标题不能为空");
            return false;
        }
        if (TextUtils.isEmpty(date)) {
            ToastUtils.showToast("日历不能为空");
            return false;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast("内容不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Object object = getReturnResultSeri(resultCode, data, null);
        if (object instanceof List<?>) {
            mPhotoList = (ArrayList<String>) object;
            if (mPhotoList != null) {
                for (String str : mPhotoList) {
                    if (ll_ScrollView.getChildCount() > 6) {
                        ToastUtils.showToast("最多只能选六张！");
                        return;
                    }
                    addImageToHsv(str, PHOTO);
                    // TODO 这里还需要把bitmap获取出来
                }
            }
        }
    }

    /**
     * 添加图片到下面布局中
     */
    private final int ADDPHOTO = 0;
    private final int PHOTO = 1;
    private ArrayList<String> mPhotoList;

    private void addImageToHsv(String path, int type) {
        View itemView = mInflater.inflate(R.layout.hsv_img_item, null);
        ImageView big_image = (ImageView) itemView.findViewById(R.id.big_image);
        ImageView delete_image = (ImageView) itemView
                .findViewById(R.id.delete_image);
        if (type == PHOTO) {
            if (path != null) {
                mImageManager.displayImage(big_image, path,
                        R.drawable.default_image_small, 100, 100);
                delete_image.setTag(itemView);
                ll_ScrollView.addView(itemView);
                changeThePosition(ll_ScrollView, itemView);
                delete_image.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        View view = (View) v.getTag();
                        ll_ScrollView.removeView(view);
                    }
                });
            }
        } else if (type == ADDPHOTO) {
            big_image.setBackgroundResource(R.drawable.add);
            itemView.setTag("tag");
            delete_image.setVisibility(View.GONE);
            ll_ScrollView.addView(itemView);
            big_image.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mApp.startActivityForResult_qcj(
                            ExperienceSendActivity.this,
                            LocalImagListActivity.class, null);
                }
            });
        }
    }

    /**
     * 交换位置
     *
     * @param parent   父布局
     * @param itemView
     */
    private void changeThePosition(LinearLayout parent, View itemView) {
        int sum = parent.getChildCount();
        for (int i = 0; i < sum; i++) {
            View view = parent.getChildAt(i);
            String str = (String) view.getTag();
            if (str != null && str == "tag") {
                parent.removeView(view);
                parent.addView(view);
            }
        }
    }
}
