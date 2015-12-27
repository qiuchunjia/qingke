package com.zhiyicx.zycx.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.sociax.component.TSFaceView;
import com.zhiyicx.zycx.sociax.unit.SociaxUIUtils;
import com.zhiyicx.zycx.util.PreferenceUtil;
import com.zhiyicx.zycx.util.Utils;
import com.zhiyicx.zycx.widget.CustomLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Stack;

/**
 * Created by Administrator on 2015/1/6.
 */
public class QuestionDetailsActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "QuestionDetailsActivity";
    private int mQid = -1;
    private int mAuid = -1;
    private View mContentView;
    private ProgressBar mProgBar;
    private TextView mContent;
    private TextView mUser;
    private TextView mFlag;
    private TextView mTime;
    private TextView mType;
    private CustomLinearLayout mTopicLayout = null;
    private ListView mAnswerList;
    private ListView mOtherList;
    private EditText mEditAnswer;
    private JSONArray mAnswerArray = null;
    private JSONArray mOtherArray = null;
    private ImageView mFace = null;
    private TSFaceView mFaceView = null;

    private Button mPrevBtn = null;
    private Stack<Integer> mQuestionStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.question_details_layout);
        mQid = getIntent().getIntExtra("qid", -1);
        mContentView = findViewById(R.id.view_content);
        mProgBar = (ProgressBar) findViewById(R.id.progressBar);
        mContent = (TextView) findViewById(R.id.txt_content);
        mUser = (TextView) findViewById(R.id.txt_user);
        mTime = (TextView) findViewById(R.id.txt_time);
        mType = (TextView) findViewById(R.id.txt_type);
        mAnswerList = (ListView) findViewById(R.id.list_answer);
        mOtherList = (ListView) findViewById(R.id.list_other);
        mEditAnswer = (EditText) findViewById(R.id.edit_answer);
        mTopicLayout = (CustomLinearLayout) findViewById(R.id.top_layout);
        mPrevBtn = (Button) findViewById(R.id.btn_prev);
        mQuestionStack = new Stack<Integer>();
        initData(mQid);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_answer).setOnClickListener(this);
        mPrevBtn.setOnClickListener(this);
        mFace = (ImageView) findViewById(R.id.face);
        mFaceView = (TSFaceView) findViewById(R.id.face_view);
        setBottomClick();
        mEditAnswer.clearFocus();
        mFaceView.setFaceAdapter(mFaceAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_answer:
                String txt = mEditAnswer.getText().toString().trim();
                if (TextUtils.isEmpty(txt))
                    return;
                answerQuestion(Utils.getUTF8String(txt));
                break;
            case R.id.btn_prev:
                mQid = mQuestionStack.pop();
                initData(mQid);
                break;
        }
    }

    private void answerQuestion(String txt) {
        String url = MyConfig.QUESTION_ANSWER_URL + Utils.getTokenString(this) + "&qid=" + mQid + "&auid=" + mAuid + "&content=" + txt;
        NetComTools netComTools = NetComTools.getInstance(this);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "Question answer data:" + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        Utils.showToast(QuestionDetailsActivity.this, "回答成功!");
                        mEditAnswer.setText("");
                        initData(mQid);
                    } else {
                        String txt = jsonObject.optString("message");
                        if (!TextUtils.isEmpty(txt))
                            Utils.showToast(QuestionDetailsActivity.this, txt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question answer error, " + error);
            }
        });
        SociaxUIUtils.hideSoftKeyboard(this, mEditAnswer);
    }

    private void initData(int id) {
        String url = MyConfig.QUESTION_DETAILS_URL + Utils.getTokenString(this) + "&id=" + id;
        NetComTools netComTools = NetComTools.getInstance(this);
        mContentView.setVisibility(View.GONE);
        mProgBar.setVisibility(View.VISIBLE);
        if (mQuestionStack.isEmpty())
            mPrevBtn.setVisibility(View.GONE);
        else
            mPrevBtn.setVisibility(View.VISIBLE);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "Question Details data:" + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        mAnswerArray = jsonObject1.optJSONArray("answer");
                        mOtherArray = jsonObject1.optJSONArray("other_question");
                        JSONObject question = jsonObject1.optJSONObject("question");
                        JSONArray topic = jsonObject1.optJSONArray("topic_list");
                        mContent.setText(question.optString("question_content"));
                        mUser.setText(question.optString("user_name"));
                        mTime.setText(question.optString("time"));
                        mType.setText("癌症种类:" + question.optString("category"));
                        if (topic != null) {
                            setTopList(mTopicLayout, topic);
                        }
                        mAuid = question.optInt("uid");
                        mProgBar.setVisibility(View.GONE);
                        mContentView.setVisibility(View.VISIBLE);
                        initListView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question details error, " + error);
            }
        });
    }

    private void setTopList(CustomLinearLayout layout, JSONArray array) throws JSONException {
        layout.removeAllViews();
        TextView textView = new TextView(this);
        textView.setText("标签:");
        layout.addView(textView);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            int domain = jsonObject.optInt("domain", 0);
            String title = jsonObject.optString("title");
            textView = new TextView(this);
            textView.setClickable(true);
            if (domain != 0 && !TextUtils.isEmpty(title))
                textView.setOnClickListener(new TopOnClickListener(domain, title));
            textView.setTextColor(Color.parseColor("#1CBE9D"));
            textView.setBackgroundResource(R.drawable.textclick_background);
            textView.setText(title);
            layout.addView(textView);
        }
    }

    private class TopOnClickListener implements View.OnClickListener {
        private int mTid = 0;
        private String mTitle = null;

        private TopOnClickListener(int tid, String title) {
            mTid = tid;
            mTitle = title;
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Top TextView click!");
            QuestionActivity.show(QuestionDetailsActivity.this, mTid, true, mTitle);
        }
    }


    private void initListView() {
        mOtherList.setAdapter(new OtherListAdapter(this));
        mOtherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //mPrevBtn.setVisibility(View.VISIBLE);
                try {
                    mQuestionStack.push(mQid);
                    mQid = ((JSONObject) mOtherArray.get(i)).optInt("qid");
                    initData(mQid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mAnswerList.setAdapter(new AnswerListAdpter(this));

    }

    private boolean isOwnQuestion() {
        PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(this);
        return mAuid == preferenceUtil.getInt("uid", -1);
    }

    //设置最佳
    private void setBestQuestion(final View view, int qid, int aid, int set) {
        String url = MyConfig.QUESTION_BAST_URL + Utils.getTokenString(this) + "&qid=" + qid + "&aid=" + aid + "&type=" + set;
        NetComTools netComTools = NetComTools.getInstance(this);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                Log.d(TAG, "Set bast question data:" + jsonObject.toString());
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        view.setBackgroundResource(R.drawable.zuijiadaan);
                        Utils.showToast(QuestionDetailsActivity.this, "设置成功!");
                    } else {
                        String txt = jsonObject.optString("message");
                        if (!TextUtils.isEmpty(txt))
                            Utils.showToast(QuestionDetailsActivity.this, txt);
                    }
                } catch (Exception e) {
                    Utils.showToast(QuestionDetailsActivity.this, "网络错误!");
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Set best question error, " + error);
            }
        });
    }

    private class BastClickListener implements View.OnClickListener {
        int mAid = -1;
        int set = 1;

        private BastClickListener(int aid, int b) {
            mAid = aid;
            set = b;
        }

        @Override
        public void onClick(View view) {
            setBestQuestion(view, mQid, mAid, set);
        }
    }


    private class AnswerListAdpter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context mContext;

        class ViewHolder {
            TextView mName;
            TextView mTime;
            TextView mContent;
            ImageView mFlag;
            ImageView mPhoto;
        }

        private AnswerListAdpter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (mAnswerArray == null) {
                if (view == null) {
                    TextView textView = new TextView(mContext);
                    textView.setText("暂无回答内容！");
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setTextSize(15);
                    view = textView;
                }
                return view;
            }

            ViewHolder holder;
            if (view == null) {
                view = mInflater.inflate(R.layout.question_answer_item, null);
                holder = new ViewHolder();
                holder.mName = (TextView) view.findViewById(R.id.txt_name);
                holder.mContent = (TextView) view.findViewById(R.id.txt_content);
                holder.mTime = (TextView) view.findViewById(R.id.txt_time);
                holder.mPhoto = (ImageView) view.findViewById(R.id.img_photo);
                holder.mFlag = (ImageView) view.findViewById(R.id.txt_flag);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            try {
                JSONObject jsonObject = (JSONObject) mAnswerArray.get(i);
                holder.mName.setText(jsonObject.optString("user_name"));
                holder.mTime.setText(jsonObject.optString("time"));
                holder.mContent.setText(jsonObject.optString("answer_content"));
                int best = jsonObject.optInt("is_best");
                if (isOwnQuestion()) {
                    holder.mFlag.setVisibility(View.VISIBLE);
                    if (best == 0) {
                        holder.mFlag.setImageResource(R.drawable.caina);
                        holder.mFlag.setOnClickListener(new BastClickListener(jsonObject.optInt("answer_id"), 1));
                    } else {
                        holder.mFlag.setImageResource(R.drawable.zuijiadaan);
                    }

                } else {
                    if (best == 0)
                        holder.mFlag.setVisibility(View.GONE);
                    else {
                        holder.mFlag.setVisibility(View.VISIBLE);
                        holder.mFlag.setImageResource(R.drawable.zuijiadaan);
                        ;
                    }
                }
                NetComTools.getInstance(mContext).loadNetImage(holder.mPhoto,
                        jsonObject.optString("user_face"),
                        R.drawable.header, 144, 144);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return view;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public int getCount() {
            if (mAnswerArray == null)
                return 0;
            else
                return mAnswerArray.length();
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
    }

    private class OtherListAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context mContext;

        class ViewHolder {
            TextView mTitle;
            TextView mCnt;
        }

        private OtherListAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (mOtherArray == null)
                return 0;
            else
                return mOtherArray.length();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (mOtherArray == null) {
                if (view == null) {
                    TextView textView = new TextView(mContext);
                    textView.setText("暂无相关内容！");
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setTextSize(15);
                    view = textView;
                }
                return view;
            }

            ViewHolder holder;
            if (view == null) {
                view = mInflater.inflate(R.layout.question_other_item, null);
                holder = new ViewHolder();
                holder.mTitle = (TextView) view.findViewById(R.id.txt_title);
                holder.mCnt = (TextView) view.findViewById(R.id.txt_cnt);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            try {
                JSONObject jsonObject = (JSONObject) mOtherArray.get(i);
                holder.mTitle.setText(jsonObject.optString("title"));
                holder.mCnt.setText(jsonObject.optInt("view_count") + "人浏览过");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }
    }


    private void setBottomClick() {

        mFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mFaceView.getVisibility() == View.GONE) {
                    mFaceView.setVisibility(View.VISIBLE);
                    mFace.setImageResource(R.drawable.key_bar);
                    SociaxUIUtils.hideSoftKeyboard(QuestionDetailsActivity.this, mEditAnswer);
                } else if (mFaceView.getVisibility() == View.VISIBLE) {
                    mFaceView.setVisibility(View.GONE);
                    mFace.setImageResource(R.drawable.face_bar);
                    SociaxUIUtils.showSoftKeyborad(QuestionDetailsActivity.this, mEditAnswer);
                }
            }
        });

        mEditAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mFaceView.getVisibility() == View.VISIBLE) {
                    mFaceView.setVisibility(View.GONE);
                    mFace.setImageResource(R.drawable.key_bar);
                    SociaxUIUtils.showSoftKeyborad(QuestionDetailsActivity.this, mEditAnswer);
                }
            }
        });
    }

    private TSFaceView.FaceAdapter mFaceAdapter = new TSFaceView.FaceAdapter() {

        @Override
        public void doAction(int paramInt, String paramString) {
            // TODO Auto-generated method stub
            EditText localEditBlogView = mEditAnswer;
            int i = localEditBlogView.getSelectionStart();
            int j = localEditBlogView.getSelectionStart();
            String str1 = "[" + paramString + "]";
            String str2 = localEditBlogView.getText().toString();
            SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
            localSpannableStringBuilder.append(str2, 0, i);
            localSpannableStringBuilder.append(str1);
            localSpannableStringBuilder.append(str2, j, str2.length());
            SociaxUIUtils.highlightContent(QuestionDetailsActivity.this, localSpannableStringBuilder);
            localEditBlogView.setText(localSpannableStringBuilder,
                    TextView.BufferType.SPANNABLE);
            localEditBlogView.setSelection(i + str1.length());
            Log.v("Tag", localEditBlogView.getText().toString());
        }
    };

}
