package com.zhiyicx.zycx.sociax.unit;import com.zhiyicx.zycx.R;import android.app.Activity;import android.widget.EditText;public class TopicHelper {    private Activity activity;    private String strDefaultTopic = "";    private EditText editText;    public TopicHelper(Activity paramEditorActivity) {        this.activity = paramEditorActivity;        this.strDefaultTopic = this.activity                .getString(R.string.input_text_topic);    }    public TopicHelper(Activity paramEditorActivity, EditText editText) {        this.activity = paramEditorActivity;        this.editText = editText;        this.strDefaultTopic = this.activity                .getString(R.string.input_text_topic);    }    public void insertTopicTips() {        EditText localEditBlogView = this.editText;        int i = localEditBlogView.getSelectionStart();        int j = localEditBlogView.getSelectionEnd();        localEditBlogView.getText().replace(i, j, this.strDefaultTopic);        localEditBlogView.setSelection(i + 1,                -1 + (i + this.strDefaultTopic.length()));    }}