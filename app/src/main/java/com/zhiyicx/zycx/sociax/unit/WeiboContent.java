package com.zhiyicx.zycx.sociax.unit;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.android.weibo.WeiboContentList;
import com.zhiyicx.zycx.sociax.component.ImageBroder;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.exception.TimeIsOutFriendly;
import com.zhiyicx.zycx.sociax.modle.ImageAttach;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public class WeiboContent extends WeiboDataSet {
    private Activity activityContent;
    private static String temp;
    private ImageView image;

    private static final String FONT_SIZE = "font_size";
    private static final String FONT_SIZE_DEF = "14";

    public static int getFontSize(Context context) {
        return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
                context).getString(FONT_SIZE, FONT_SIZE_DEF));
    }

    public WeiboContent(ThinksnsAbscractActivity obj) {
        this.activityContent = obj;
    }

    /**
     * 附加微博正文内容
     */
    @Override
    public void appendWeiboData(Weibo weibo, View view) {
        WeiboDataItem weiboConententItem = new WeiboDataItem();
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.weibo_data);
        LinearLayout imageLayout = (LinearLayout) view.findViewById(R.id.image_layout);
        weiboConententItem.username = (TextView) view.findViewById(R.id.user_name);
        weiboConententItem.weiboCtime = (TextView) view.findViewById(R.id.weibo_ctime);
        weiboConententItem.weiboContent = (TextView) view.findViewById(R.id.weibo_content);
        weiboConententItem.header = (ImageView) view.findViewById(R.id.user_header);
        weiboConententItem.weiboFrom = (TextView) view.findViewById(R.id.weibo_from);
        layout.setTag(weibo);

        weiboConententItem.username.setTextSize(getFontSize(activityContent));
        weiboConententItem.weiboContent.setTextSize(getFontSize(activityContent));
        weiboConententItem.weiboCtime.setTextSize(getFontSize(activityContent) - 2);

        weiboConententItem.username.setText(weibo.getUsername());
        try {
            weiboConententItem.weiboFrom.setText(SociaxUIUtils.getFromString(weibo.getFrom().ordinal()));
            weiboConententItem.weiboCtime.setText(TimeHelper.friendlyTime(weibo.getTimestamp()));

        } catch (TimeIsOutFriendly e) {
            weiboConententItem.weiboCtime.setText(weibo.getCtime());
        }
        try {
            weiboConententItem.weiboContent.setText(dealWeiboContent(weibo.getContent(),
                    weiboConententItem.weiboContent));
        } catch (Exception ex) {
            weiboConententItem.weiboContent.setText(weibo.getContent());
        }

        setCommentCount(weibo, view);
        setTranspondCount(weibo, view);
        setCountLayout(weibo, view);

        addHeader(weibo, view, weiboConententItem.header);

        removeViews(layout);
        imageLayout.removeAllViews();

        if (!weibo.isNullForTranspond()) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            layout.addView(appendTranspond(weibo.getTranspond(), view), getContentIndex(), lp);
        }

        if (weibo.hasImage() && weibo.getAttachs() != null) {

            LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(getThumbWidth(), getThumbHeight());
            lpImage.gravity = Gravity.CENTER_HORIZONTAL;
            lpImage.setMargins(0, 0, 0, 5);
            if (weibo.getAttachs() != null)
                for (ImageAttach iAttach : weibo.getAttachs()) {

                    ImageBroder image = new ImageBroder(view.getContext());
                    image.setTag(weibo);
                    image.setId(IMAGE_VIEW);
                    image.setImageResource(R.drawable.bg_loading);
                    // 异步下载图片
                    dowloaderTask(iAttach.getMiddle(), image, getThumbType());
                    image.setOnClickListener(((ThinksnsAbscractActivity) activityContent).getImageFullScreen(iAttach
                            .getNormal()));
                    imageLayout.addView(image, lpImage);
                }
        }

        if (weibo.hasFile()) {
            LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                    getThumbHeight());
            lpImage.gravity = Gravity.CENTER_VERTICAL;

            if (weibo.getAttachs() != null) {
                TextView tx = new TextView(view.getContext());
                tx.setPadding(8, 8, 0, 8);
                tx.setGravity(Gravity.CENTER_VERTICAL);
                tx.setTextColor(view.getResources().getColor(R.color.main_link_color));
                tx.setCompoundDrawablesWithIntrinsicBounds(getDomLoadImg(weibo.getAttachs().get(0).getName()), 0, 0, 0);
                tx.setCompoundDrawablePadding(10);
                tx.setBackgroundResource(R.drawable.reviewboxbg);
                tx.setText(weibo.getAttachs().get(0).getName());
                imageLayout.addView(tx, lpImage);
            }
        }

        // TextView favorite = (TextView) view.findViewById(R.id.text_favorite);
        // if (weibo.isFavorited()) {
        // favorite.setCompoundDrawablesWithIntrinsicBounds(0,
        // R.drawable.favorited, 0, 0);
        // favorite.setText("取消收藏");
        // favorite.setTag(ThinksnsWeiboContent.FavoriteStatus.YES);
        // } else {
        // favorite.setCompoundDrawablesWithIntrinsicBounds(0,
        // R.drawable.weibo_app_collection_n, 0, 0);
        // favorite.setTag(ThinksnsWeiboContent.FavoriteStatus.NO);
        // favorite.setText("收藏");
        // }
    }

    /**
     * 附加微博正文包含的转发内容
     */
    @Override
    protected View appendTranspond(Weibo weibo, View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(view.getContext());
        layout.setLayoutParams(lp);
        layout.setBackgroundResource(R.drawable.reviewboxbg);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout imageLayout = (LinearLayout) view.findViewById(R.id.image_layout);
        imageLayout.setBackgroundResource(R.drawable.reviewboxbg);

        TextView content = new TextView(view.getContext());
        LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lpText.gravity = Gravity.CENTER;
        content.setTextSize(getFontSize(activityContent));
        content.setPadding(12, 8, 12, 0);
        content.setTextColor(view.getContext().getResources().getColor(R.color.font));
        content.setLineSpacing(4, 1);

        String patternStr = '@' + weibo.getUsername() + ": " + weibo.getContent();

        try {
            content.setText(dealWeiboContent(patternStr, content));
        } catch (Exception ex) {
            content.setText(weibo.getContent());
        }

        layout.addView(content, lpText);

        removeViews(layout);
        imageLayout.removeAllViews();

        if (weibo.hasImage() && weibo.getAttachs() != null) {

            LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(getThumbWidth(), getThumbHeight());
            lpImage.gravity = Gravity.CENTER_HORIZONTAL;
            lpImage.setMargins(0, 0, 0, 5);
            if (weibo.getAttachs() != null)
                for (ImageAttach iAttach : weibo.getAttachs()) {

                    ImageBroder image = new ImageBroder(view.getContext());
                    image.setTag(weibo);
                    image.setId(IMAGE_VIEW);
                    image.setImageResource(R.drawable.bg_loading);
                    dowloaderTask(iAttach.getMiddle(), image, getThumbType());
                    image.setOnClickListener(((ThinksnsAbscractActivity) activityContent).getImageFullScreen(iAttach
                            .getNormal()));
                    imageLayout.addView(image, lpImage);
                }
        }

        if (weibo.hasFile()) {
            LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    getThumbHeight());
            lpImage.gravity = Gravity.CENTER_VERTICAL;

            if (weibo.getAttachs() != null) {
                TextView tx = new TextView(view.getContext());
                tx.setPadding(8, 8, 0, 8);
                tx.setGravity(Gravity.CENTER_VERTICAL);
                tx.setTextColor(view.getResources().getColor(R.color.main_link_color));
                tx.setCompoundDrawablesWithIntrinsicBounds(getDomLoadImg(weibo.getAttachs().get(0).getName()), 0, 0, 0);
                tx.setCompoundDrawablePadding(10);
                tx.setBackgroundResource(R.drawable.reviewboxbg);
                tx.setText(weibo.getAttachs().get(0).getName());
                imageLayout.addView(tx, lpImage);
            }
        }
        layout.setId(TRANSPOND_LAYOUT);
        return layout;
    }

    @Override
    protected int getContentIndex() {
        return 1;
    }

    @Override
    protected void setCountLayout(Weibo weibo, View view) {

    }

    @Override
    protected void setTranspondCount(Weibo weibo, View view) {
        TextView transpondCount = (TextView) view.findViewById(R.id.text_trans_num);
        transpondCount.setTextSize(getFontSize(activityContent) - 2);
        transpondCount.setText(view.getContext().getString(R.string.transpond) + "(" + weibo.getTranspondCount() + ")");

    }

    @Override
    protected void setCommentCount(Weibo weibo, View view) {
        TextView transpondCount = (TextView) view.findViewById(R.id.text_comment_num);
        transpondCount.setTextSize(getFontSize(activityContent) - 2);
        transpondCount.setText(view.getContext().getString(R.string.comment) + "(" + weibo.getComment() + ")");
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER_HORIZONTAL;
    }

    @Override
    protected BitmapDownloaderTask.Type getThumbType() {
        return BitmapDownloaderTask.Type.MIDDLE_THUMB;
    }

    // Color.argb(255, 54, 92, 124)
    private SpannableStringBuilder dealWeiboContent(String weiboContent, TextView textView) {
        Pattern pattern = Pattern
                .compile("((https?)://([a-zA-Z0-9\\-.]+)((?:/[a-zA-Z0-9\\-._?,;'+\\&%$=~*!():@\\\\]*)+)?)|(#(.+?)#)|(@[\\u4e00-\\u9fa5\\w\\-]+)");

        // Pattern pattern = Pattern
        // .compile("((http://|https://){1}[\\w\\.\\-/:]+)|(#(.+?)#)|(@[\\u4e00-\\u9fa5\\w\\-]+)");
        temp = weiboContent;
        Matcher matcher = pattern.matcher(temp);
        List<String> list = new LinkedList<String>();
        while (matcher.find()) {
            if (!list.contains(matcher.group())) {
                temp = temp.replace(matcher.group(), "<a href=\"" + matcher.group() + "\">" + matcher.group() + "</a>");
            }
            list.add(matcher.group());
        }
        textView.setText(Html.fromHtml(temp));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = textView.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) textView.getText();
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();
            for (URLSpan url : urls) {
                style.setSpan(((WeiboContentList) activityContent).typeClick(url.getURL()), sp.getSpanStart(url),
                        sp.getSpanEnd(url), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            SociaxUIUtils.highlightContent(activityContent, style);
            return style;
        }
        return null;
    }

    @Override
    protected boolean hasThumbCache(Weibo weibo) {
        return false;
    }

    @Override
    public void appendWeiboData(Weibo weibo, View view, boolean isFirst) {
        WeiboDataItem weiboConententItem = new WeiboDataItem();
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.weibo_data);
        LinearLayout imageLayout = (LinearLayout) view.findViewById(R.id.image_layout);

        weiboConententItem.username = (TextView) view.findViewById(R.id.user_name);
        weiboConententItem.weiboCtime = (TextView) view.findViewById(R.id.weibo_ctime);
        weiboConententItem.weiboContent = (TextView) view.findViewById(R.id.weibo_content);
        weiboConententItem.header = (ImageView) view.findViewById(R.id.user_header);
        weiboConententItem.weiboFrom = (TextView) view.findViewById(R.id.weibo_from);
        layout.setTag(weibo);
        weiboConententItem.username.setTextSize(getFontSize(activityContent));
        weiboConententItem.weiboContent.setTextSize(getFontSize(activityContent));
        weiboConententItem.weiboCtime.setTextSize(getFontSize(activityContent) - 2);

        if (isFirst)
            weiboConententItem.username.setText(weibo.getUsername());
        try {
            weiboConententItem.weiboCtime.setText(TimeHelper.friendlyTime(weibo.getTimestamp()));
        } catch (TimeIsOutFriendly e) {
            weiboConententItem.weiboCtime.setText(weibo.getCtime());
        }

        try {
            weiboConententItem.weiboContent.setText(dealWeiboContent(weibo.getContent(),
                    weiboConententItem.weiboContent));
            // dealWeiboContent(weibo.getContent(),weiboConententItem.weiboContent);
            // setUrlSpans(weiboConententItem.weiboContent);
            weiboConententItem.weiboFrom.setText(SociaxUIUtils.getFromString(weibo.getFrom().ordinal()));
        } catch (Exception ex) {
            weiboConententItem.weiboContent.setText(weibo.getContent());
        }

        setCommentCount(weibo, view);
        setTranspondCount(weibo, view);
        setCountLayout(weibo, view);

        if (isFirst)
            addHeader(weibo, view, weiboConententItem.header);

        removeViews(layout);
        if (isFirst)
            imageLayout.removeAllViews();

        if (!weibo.isNullForTranspond()) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            layout.addView(appendTranspond(weibo.getTranspond(), view), getContentIndex(), lp);
        }
        /*
		 * if(weibo.hasImage()){ LinearLayout.LayoutParams lpImage = new
		 * LinearLayout.LayoutParams(getThumbWidth(),getThumbHeight());
		 * lpImage.gravity = Gravity.LEFT; lpImage.setMargins(0, 10, 0, 10);
		 * image = (ImageView)appendImage(weibo, view);
		 * image.setOnClickListener(
		 * ((ThinksnsWeiboContent)activityContent).getImageFullScreen
		 * (weibo.getThumbMiddleUrl()));
		 * layout.addView(image,getContentIndex(),lpImage); }
		 */
        if (isFirst)
            if (weibo.hasImage() && weibo.getAttachs() != null) {

                LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(getThumbWidth(), getThumbHeight());
                lpImage.gravity = Gravity.LEFT;
                lpImage.setMargins(0, 0, 0, 5);
                if (weibo.getAttachs() != null)
                    for (ImageAttach iAttach : weibo.getAttachs()) {

                        ImageBroder image = new ImageBroder(view.getContext());
                        image.setTag(weibo);
                        image.setId(IMAGE_VIEW);
                        dowloaderTask(iAttach.getMiddle(), image, getThumbType());

                        // image =(ImageView) appendImage(weibo, imageLayout);
                        image.setOnClickListener(((ThinksnsAbscractActivity) activityContent)
                                .getImageFullScreen(iAttach.getNormal()));
                        imageLayout.addView(image, lpImage);
                    }
            }
        if (isFirst)
            if (weibo.hasFile()) {
                LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                        getThumbHeight());
                lpImage.gravity = Gravity.CENTER_VERTICAL;
                // lpImage.setMargins(5,2,2,2);

                if (weibo.getAttachs() != null) {
                    TextView tx = new TextView(view.getContext());
                    tx.setPadding(8, 8, 0, 8);
                    tx.setGravity(Gravity.CENTER_VERTICAL);
                    tx.setTextColor(view.getResources().getColor(R.color.main_link_color));
                    tx.setCompoundDrawablesWithIntrinsicBounds(getDomLoadImg(weibo.getAttachs().get(0).getName()), 0,
                            0, 0);
                    tx.setCompoundDrawablePadding(10);
                    tx.setBackgroundResource(R.drawable.reviewboxbg);
                    tx.setText(weibo.getAttachs().get(0).getName());
                    imageLayout.addView(tx, lpImage);
                }
            }

        TextView favorite = (TextView) view.findViewById(R.id.text_favorite);
        if (weibo.isFavorited()) {
            // favorite.setImageResource(R.drawable.button_is_favorite);
            favorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favorited, 0, 0);
            favorite.setText("取消收藏");
            favorite.setTag(WeiboContentList.FavoriteStatus.YES);
        } else {
            // favorite.setImageResource(R.drawable.button_favorite);
            favorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.weibo_app_collection_n, 0, 0);
            favorite.setTag(WeiboContentList.FavoriteStatus.NO);
            favorite.setText("收藏");
        }
    }

    private int getDomLoadImg(String dType) {
        dType = dType.substring(dType.lastIndexOf('.') + 1, dType.length());

        if (dType.equals("pdf")) {
            return R.drawable.pdf_48;
        } else if (dType.equals("doc") || dType.equals("docx")) {
            return R.drawable.word_48;

        } else if (dType.equals("xls") || dType.equals("xlsx")) {
            return R.drawable.excel_48;
        } else if (dType.equals("ppt") || dType.equals("pptx")) {
            return R.drawable.ppt_48;
        } else if (dType.equals("png")) {
            return R.drawable.png_48;

        } else if (dType.equals("jpg")) {
            return R.drawable.jpg_48;

        } else if (dType.equals("txt")) {
            return R.drawable.txt_48;
        } else if (dType.equals("zip") || dType.equals("rar")) {
            return R.drawable.zip_48;

        } else {
            return R.drawable.attach;
        }
    }

}
