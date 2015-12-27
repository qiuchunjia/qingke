package com.zhiyicx.zycx.sociax.adapter;

import java.util.HashMap;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.api.ApiStatuses;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.SociaxUIUtils;
import com.zhiyicx.zycx.sociax.unit.TimeHelper;

import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 微博评论 Adapter
 */
public class CommentListAdapter extends SociaxListAdapter {

    private static TextView username;
    private static TextView content;
    private static TextView cTime;
    private static ImageView userhead;
    private static String TAG = "Comment";
    private static Weibo weibo;
    private static HashMap<String, Integer> buttonSet;

    public CommentListAdapter(ThinksnsAbscractActivity context, ListData<SociaxItem> list, Weibo weibo) {
        super(context, list);
        buttonSet = new HashMap<String, Integer>();
        CommentListAdapter.weibo = weibo;
    }

    public CommentListAdapter(ThinksnsAbscractActivity context, ListData<SociaxItem> data) {
        super(context, data);
    }

    @Override
    public Comment getFirst() {
        return (Comment) super.getFirst();
    }

    @Override
    public Comment getLast() {
        return (Comment) super.getLast();
    }

    @Override
    public void doRefreshHeader() {
        // addRightButtonAnimAfter();
        super.doRefreshHeader();
    }

    @Override
    public void refreshNewWeiboList() {
        // addRightButtonAnimAfter();
        super.refreshNewWeiboList();
    }

    @Override
    public Comment getItem(int position) {
        return (Comment) super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.comment_item, null);
        }
        userhead = (ImageView) convertView.findViewById(R.id.weibo_comment_iv);
        username = (TextView) convertView.findViewById(R.id.username);
        content = (TextView) convertView.findViewById(R.id.comment_content);
        cTime = (TextView) convertView.findViewById(R.id.comment_time);
        Comment data = this.getItem(position);
        if (weibo != null)
            try {
                convertView.setTag(weibo.toJSON());
                loadingHeader(data.getHeadUrl(), userhead);
                username.setText(data.getUname());
                content.setTag(data);
                SpannableString ss = new SpannableString(SociaxUIUtils.filterHtml(data.getContent()));
                SociaxUIUtils.highlightContent(context, ss);
                content.setText(ss);
                cTime.setText(TimeHelper.friendlyTimeFromeStringTime(data.getcTime()));
            } catch (Exception e) {
                cTime.setText(data.getcTime());
                Log.d(TAG, "commenlistadapter" + e.toString());
            }
        return convertView;
    }

    private void loadingHeader(String url, ImageView userheader) {
        if (url != null)
            dowloaderTask(url, userheader, BitmapDownloaderTask.Type.FACE);
    }

    protected void dowloaderTask(String url, ImageView image, BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj) throws VerifyErrorException, ApiException,
            ListAreEmptyException, DataInvalidException {
        return getApiStatuses().commentForWeiboHeaderTimeline(weibo, (Comment) obj, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj) throws VerifyErrorException, ApiException,
            ListAreEmptyException, DataInvalidException {
        return getApiStatuses().commentForWeiboFooterTimeline(weibo, (Comment) obj, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count) throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiStatuses().commentForWeiboTimeline(weibo, count);
    }

    private ApiStatuses getApiStatuses() {
        Thinksns app = thread.getApp();
        return app.getStatuses();
    }

}
