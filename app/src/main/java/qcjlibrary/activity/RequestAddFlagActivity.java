package qcjlibrary.activity;

import java.util.ArrayList;
import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.activity.base.Title;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelRequestAsk;
import qcjlibrary.model.ModelRequestFlag;
import qcjlibrary.model.ModelRequestItem;
import qcjlibrary.model.base.Model;
import qcjlibrary.response.DataAnalyze;
import qcjlibrary.util.ToastUtils;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午1:56:07 类描述：这个类是实现
 */

public class RequestAddFlagActivity extends BaseActivity {
    private LinearLayout ll_add_flag;
    private EditText et_content;
    private TextView tv_add;
    private List<ModelRequestFlag> mFlags = new ArrayList<ModelRequestFlag>();
    private String mAddFlags = "";
    private ModelRequestAsk mAsk;

    @Override
    public String setCenterTitle() {
        return "添加标签";
    }

    @Override
    public void initIntent() {
        mAsk = (ModelRequestAsk) getDataFromIntent(getIntent(), null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_add_flag;
    }

    @Override
    public void initView() {
        ll_add_flag = (LinearLayout) findViewById(R.id.ll_add_flag);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_add = (TextView) findViewById(R.id.tv_add);
        titleSetRightTitle("提交");
    }

    private boolean isCommit = false;

    @Override
    public void initData() {
        sendRequest(mApp.getRequestImpl().getTopic(mAsk),
                ModelRequestFlag.class, REQUEST_GET);
        Title title = getTitleClass();
        title.tv_title_right.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getFlag();
                mAsk.setTopics(mAddFlags);
                ToastUtils.showToast("正在提交");
                sendRequest(mApp.getRequestImpl().addQuestion(mAsk),
                        ModelRequestAsk.class, REQUEST_GET);
                isCommit = true;
            }
        });
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        if (isCommit) {
            isCommit = false;
            Object object = DataAnalyze.parseDataByGson(str, class1);
            if (object instanceof ModelRequestAsk) {
                ModelRequestAsk ask = (ModelRequestAsk) object;
                mAsk.setQid(ask.getQid());
                mApp.startActivity_qcj(RequestAddFlagActivity.this,
                        RequestSendTopicCommitedActivity.class,
                        sendDataToBundle(mAsk, null));
            }
            judgeTheMsg(object);
            return object;
        }
        Object object = DataAnalyze.parseData(str, class1);
        if (object instanceof List<?>) {
            mFlags = (List<ModelRequestFlag>) object;
            for (int i = 0; i < mFlags.size(); i++) {
                mFlags.get(i).setChoose(true); // 默认为选中
            }
            addDataToView(mFlags);
        }
        return object;
    }

    @Override
    public void initListener() {
        tv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                String content = et_content.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    ModelRequestFlag flag = new ModelRequestFlag();
                    flag.setTitle(content);
                    if (mFlags != null) {
                        mFlags.add(flag);
                        addModelToView(flag);
                    }
                }
                break;

        }
    }

    /**
     * 添加数据到界面
     *
     * @param list
     */
    private void addDataToView(List<ModelRequestFlag> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ModelRequestFlag flag = list.get(i);
                addModelToView(flag);
            }
        }
    }

    /**
     * 添加单个model到view
     *
     * @param i
     * @param category
     */
    private void addModelToView(ModelRequestFlag flag) {
        View view = mInflater.inflate(R.layout.item_request_add_flag, null);
        TextView tv_flag_name = (TextView) view.findViewById(R.id.tv_flag_name);
        ImageView iv_choose = (ImageView) view.findViewById(R.id.iv_choose);
        tv_flag_name.setText(flag.getTitle());
        iv_choose.setTag(flag);
        countTheChooseAndDisplay(iv_choose);
        iv_choose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    ImageView imageView = (ImageView) v;
                    countTheChooseAndDisplay(imageView);
                }
            }
        });
        ll_add_flag.addView(view);
    }

    /**
     * 统计被选中的癌症并显示出来
     *
     * @param imageView
     */
    private void countTheChooseAndDisplay(ImageView imageView) {
        if (imageView != null) {
            ModelRequestFlag flag = (ModelRequestFlag) imageView.getTag();
            if (flag.isChoose()) {
                imageView.setImageResource(R.drawable.weixuanzhong02);
                flag.setChoose(false);
            } else {
                int count = 0; // 计算选择的癌症的个数
                for (int i = 0; i < mFlags.size(); i++) {
                    if (mFlags.get(i).isChoose()) {
                        count++;
                    }
                    if (count >= 3) {
                        ToastUtils.showToast("最多只能选三个标签");
                        return;
                    }
                }
                imageView.setImageResource(R.drawable.xuanzhong02);
                flag.setChoose(true);
            }
        }
    }

    /**
     * 获取癌症分类 用逗号隔开
     */
    private boolean getFlag() {
        if (mFlags != null) {
            for (int i = 0; i < mFlags.size(); i++) {
                if (mFlags.get(i).isChoose()) {
                    mAddFlags = mAddFlags + mFlags.get(i).getTitle() + ",";
                }
            }
            if (mAddFlags != null) {
                mAddFlags = mAddFlags.substring(0, mAddFlags.length() - 1);
                return true;
            }
        }
        return false;
    }

}
