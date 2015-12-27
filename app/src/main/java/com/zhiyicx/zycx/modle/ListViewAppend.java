package com.zhiyicx.zycx.modle;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.Color;
import android.nfc.Tag;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.net.StringDataListener;
import com.zhiyicx.zycx.sociax.component.ImageBroder;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.exception.TimeIsOutFriendly;
import com.zhiyicx.zycx.sociax.gimgutil.ImageFetcher;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.SociaxUIUtils;
import com.zhiyicx.zycx.sociax.unit.TimeHelper;
import com.zhiyicx.zycx.sociax.unit.TypeNameUtil;
import com.zhiyicx.zycx.sociax.unit.WeiboDataItem;
import com.zhiyicx.zycx.sociax.unit.WeiboDataSet;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONObject;

/**
 * 列表微博内容填充类
 *
 * @author Povol
 */
public class ListViewAppend extends WeiboDataSet {
    private ImageView image;
    //private Activity activityContent;
    protected BaseListFragment mListContext = null;
    private Map<Integer, View> viewMap = new HashMap<Integer, View>();
    //private ImageFetcher mHeadImageFetcher, mContentImageFetcher;
    private NetComTools mNetComToools = null;
    private int mHeadImageSize;

    @Override
    protected int getContentIndex() {
        return CONTENT_INDEX;
    }

    public ListViewAppend(BaseListFragment obj) {
        mListContext = obj;
        mNetComToools = NetComTools.getInstance(mListContext.mContext);
        mHeadImageSize = mListContext.mContext.getResources().getDimensionPixelSize(R.dimen.header_width_hight);
    }

    @Override
    public void appendWeiboData(Weibo weibo, View view) {
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        TableLayout tableLayout = new TableLayout(mListContext.mContext);
        tableLayout.setLayoutParams(lp2);
        WeiboDataItem weiboDataItem = (WeiboDataItem) view.getTag();
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.weibo_data);
        LinearLayout imageLayout = (LinearLayout) view
                .findViewById(R.id.image_layout);
        LinearLayout tranLayout = (LinearLayout) view
                .findViewById(R.id.tran_layout);
        imageLayout.setVisibility(View.VISIBLE);
        tranLayout.setVisibility(View.GONE);
        layout.setTag(weibo);

        weiboDataItem.username.setText(weibo.getUsername());
        try {
            weiboDataItem.weiboCtime.setText(TimeHelper.friendlyTime(weibo
                    .getTimestamp()));
            weiboDataItem.weiboFrom.setText(SociaxUIUtils.getFromString(weibo
                    .getFrom().ordinal()));
        } catch (TimeIsOutFriendly e) {
            weiboDataItem.weiboCtime.setText(weibo.getCtime());
        }
        try {
            Pattern pattern = Pattern
                    .compile("((https?)://([a-zA-Z0-9\\-.]+)((?:/[a-zA-Z0-9\\-._?,;'+\\&%$=~*!():@\\\\]*)+)?)|(#(.+?)#)|(@[\\u4e00-\\u9fa5\\w\\-]+)");

            //Pattern pattern = Pattern
            //.compile("((http://|https://){1}[\\w\\.\\-/:]+)|(#(.+?)#)|(@[\\u4e00-\\u9fa5\\w\\-]+)");

            String contetn = SociaxUIUtils.filterHtml(weibo.getContent());
            Matcher matcher = pattern.matcher(contetn);
            SpannableString spannableString = new SpannableString(
                    contetn);
            while (matcher.find()) {
                spannableString.setSpan(
                        new ForegroundColorSpan(Color.argb(255, 54, 92, 124)),
                        matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            SociaxUIUtils.highlightContent(mListContext.mContext, spannableString);
            weiboDataItem.weiboContent.setText(spannableString);
        } catch (Exception ex) {
            // weiboDataItem.weiboContent.setText(weibo.getContent().trim());
        }

        setCommentCount(weibo, view);
        setTranspondCount(weibo, view);
        // setCountLayout(weibo, view);
        // 添加头像
        // addHeader(weibo, view, weiboDataItem.header);
        /*
        if (getmHeadImageFetcher() != null) {
			getmHeadImageFetcher().loadImage(weibo.getUserface(),
					weiboDataItem.header);
		}
*/
        mNetComToools.loadNetImage(weiboDataItem.header, weibo.getUserface(), R.drawable.header, mHeadImageSize, mHeadImageSize);
        removeViews(layout);

        imageLayout.removeAllViews();

        if (!weibo.isNullForTranspond() || weibo.getTranspondId() > 0) {
            appendTranspond(weibo.getTranspond(), view);
        }

        if (weibo.hasImage() && weibo.getAttachs() != null) {
            tableLayout.setStretchAllColumns(false);
            // 生成10行，8列的表格
            for (int row = 0; row < weibo.getAttachs().size(); row = row + 2) {
                TableRow tableRow = new TableRow(mListContext.mContext);
                for (int col = row; col < row + 2
                        && col < weibo.getAttachs().size(); col++) {
                    ImageBroder image = new ImageBroder(view.getContext());
                    image.setTag(weibo);
                    image.setId(IMAGE_VIEW);
                    // image.setImageResource(R.drawable.bg_loading);
                    // dowloaderTask((weibo.getAttachs()).get(col).getSmall(),
                    // image, getThumbType());
                    /*
					if (getmContentImageFetcher() != null) {
						getmContentImageFetcher().loadImage(
								(weibo.getAttachs()).get(col).getSmall(),
								image, null);
					}
*/
                    mNetComToools.loadNetImage(image, (weibo.getAttachs()).get(col).getSmall(), R.drawable.bg_loading, 100, 100);
                    image.setOnClickListener(mListContext.getImageFullScreen((weibo.getAttachs()).get(col)
                            .getNormal()));
                    tableRow.addView(image);
                }
                // 新建的TableRow添加到TableLayout
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        if (weibo.hasFile()) {
            LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, getThumbHeight());
            lpImage.gravity = Gravity.CENTER_VERTICAL;

            if (weibo.getAttachs() != null) {
                TextView tx = new TextView(view.getContext());
                tx.setPadding(8, 8, 0, 8);
                tx.setGravity(Gravity.CENTER_VERTICAL);
                tx.setTextColor(view.getResources().getColor(
                        R.color.main_link_color));
                tx.setCompoundDrawablesWithIntrinsicBounds(
                        TypeNameUtil.getDomLoadImg(weibo.getAttachs().get(0)
                                .getName()), 0, 0, 0);
                tx.setCompoundDrawablePadding(10);
                tx.setBackgroundResource(R.drawable.reviewboxbg);
                tx.setText(weibo.getAttachs().get(0).getName());
                imageLayout.addView(tx, lpImage);
            }
        }
        imageLayout.addView(tableLayout);
        view.setTag(weiboDataItem);

    }

    /**
     * 转发内容
     */
    @Override
    public View appendTranspond(Weibo weibo, View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(view.getContext());
        layout.setLayoutParams(lp);

        TableLayout tableLayout = new TableLayout(mListContext.mContext);
        tableLayout.setLayoutParams(lp);

        layout.setBackgroundResource(R.drawable.reviewboxbg);
        LinearLayout tranLayout = (LinearLayout) view
                .findViewById(R.id.tran_layout);
        LinearLayout imageLayout = (LinearLayout) view
                .findViewById(R.id.image_layout);
        imageLayout.setVisibility(View.GONE);
        tranLayout.setVisibility(View.VISIBLE);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView content = new TextView(view.getContext());
        LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpText.gravity = Gravity.CENTER;
        content.setPadding(0, 0, 0, 10);

        // 设置名字
        // content.setText((weibo.getUsername() + weibo.getContent()),
        // TextView.BufferType.SPANNABLE);
        String patternStr = '@' + weibo.getUsername() + ": "
                + SociaxUIUtils.filterHtml(weibo.getContent());

        try {
            Pattern pattern = Pattern
                    .compile("((https?)://([a-zA-Z0-9\\-.]+)((?:/[a-zA-Z0-9\\-._?,;'+\\&%$=~*!():@\\\\]*)+)?)|(#(.+?)#)|(@[\\u4e00-\\u9fa5\\w\\-]+)");

            //Pattern pattern = Pattern
            //	.compile("((http://|https://){1}[\\w\\.\\-/:]+)|(#(.+?)#)|(@[\\u4e00-\\u9fa5\\w\\-]+)");
            Matcher matcher = pattern.matcher(patternStr);

            SpannableString spannableString = new SpannableString(patternStr);
            while (matcher.find()) {
                spannableString.setSpan(
                        new ForegroundColorSpan(Color.argb(255, 54, 92, 124)),
                        matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            SociaxUIUtils.highlightContent(mListContext.mContext, spannableString);
            content.setText(spannableString);
        } catch (Exception ex) {
            content.setText(weibo.getContent());
        }

        content.setTextColor(view.getContext().getResources()
                .getColor(R.color.tranFontColor));
        content.setTextSize(14);

        removeViews(layout);
        tranLayout.removeAllViews();

        tranLayout.addView(content, lpText);
        tranLayout.addView(tableLayout);

        if (weibo.hasImage() && weibo.getAttachs() != null) {
            LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(
                    getThumbWidth(), getThumbHeight());
            lpImage.setMargins(0, 0, 0, 5);

            tableLayout.setStretchAllColumns(false);
            // 生成10行，8列的表格
            for (int row = 0; row < weibo.getAttachs().size(); row = row + 2) {
                TableRow tableRow = new TableRow(mListContext.mContext);
                for (int col = row; col < row + 2
                        && col < weibo.getAttachs().size(); col++) {
                    ImageBroder image = new ImageBroder(view.getContext());
                    image.setTag(weibo);
                    image.setId(IMAGE_VIEW);
                    // image.setImageResource(R.drawable.bg_loading);
                    // dowloaderTask((weibo.getAttachs()).get(col).getSmall(),
                    // image, getThumbType());
					/*if (getmContentImageFetcher() != null) {
						getmContentImageFetcher().loadImage(
								(weibo.getAttachs()).get(col).getSmall(),
								image, null);
					}*/

                    mNetComToools.loadNetImage(image, (weibo.getAttachs()).get(col).getSmall(), R.drawable.bg_loading, 100, 100);
                    image.setOnClickListener(mListContext
                            .getImageFullScreen((weibo.getAttachs()).get(col)
                                    .getNormal()));
                    // tv用于显示
                    TextView tv = new TextView(mListContext.mContext);
                    tv.setText(col + "");
                    tableRow.addView(image);
                }
                // 新建的TableRow添加到TableLayout
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        if (weibo.hasFile()) {
            LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, getThumbHeight());
            lpImage.gravity = Gravity.CENTER_VERTICAL;
            if (weibo.getAttachs() != null) {
                TextView tx = new TextView(view.getContext());
                tx.setPadding(0, 0, 0, 0);
                tx.setGravity(Gravity.CENTER_VERTICAL);
                tx.setTextColor(view.getResources().getColor(
                        R.color.main_link_color));
                tx.setCompoundDrawablesWithIntrinsicBounds(
                        TypeNameUtil.getDomLoadImg(weibo.getAttachs().get(0)
                                .getName()), 0, 0, 0);
                tx.setCompoundDrawablePadding(10);
                tx.setBackgroundResource(R.drawable.reviewboxbg);
                tx.setText(weibo.getAttachs().get(0).getName());
                tranLayout.addView(tx, lpImage);
            }
        }

        layout.setId(TRANSPOND_LAYOUT);
        return layout;
    }

    @Override
    protected void setCountLayout(Weibo weibo, View view) {
        RelativeLayout countLayout = (RelativeLayout) view
                .findViewById(R.id.weibo_count_layout);
        if (weibo.isNullForComment() && weibo.isNullForTranspondCount()) {

            countLayout.setVisibility(View.GONE);
        } else {
            countLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setTranspondCount(Weibo weibo, View view) {
        TextView transpondCount = (TextView) view
                .findViewById(R.id.transpond_count);
        transpondCount.setText(view.getContext().getResources()
                .getString(R.string.transpond)
                + ":" + weibo.getTranspondCount() + "");

    }

    @Override
    protected void setCommentCount(Weibo weibo, View view) {
        TextView commentCount = (TextView) view
                .findViewById(R.id.comment_count);
        commentCount.setText(view.getContext().getString(R.string.comment)
                + ":" + weibo.getComment() + "");
    }


    @Override
    protected int getGravity() {
        return Gravity.LEFT;
    }

    @Override
    protected BitmapDownloaderTask.Type getThumbType() {
        return BitmapDownloaderTask.Type.THUMB;
    }

    @Override
    protected boolean hasThumbCache(Weibo weibo) {
        return false;
    }

    @Override
    public void appendWeiboData(Weibo weibo, View view, boolean isFirst) {

    }
/*
	public ImageFetcher getmHeadImageFetcher() {
		return mHeadImageFetcher;
	}

	public void setmHeadImageFetcher(ImageFetcher mHeadImageFetcher) {
		this.mHeadImageFetcher = mHeadImageFetcher;
	}

	public ImageFetcher getmContentImageFetcher() {
		return mContentImageFetcher;
	}

	public void setmContentImageFetcher(ImageFetcher mContentImageFetcher) {
		this.mContentImageFetcher = mContentImageFetcher;
	}*/

}
