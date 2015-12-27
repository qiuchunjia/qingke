package com.zhiyicx.zycx.sociax.unit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.component.ImageBroder;
import com.zhiyicx.zycx.sociax.concurrent.BitmapDownloaderTask;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ReceiveComment;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public abstract class WeiboDataSet {
    protected static final int TRANSPOND_LAYOUT = 111;
    protected static final int IMAGE_VIEW = 222;

    protected static final int IMAGE_LAYOUT = 333;
    protected static final int CONTENT_INDEX = 2;

    private static enum PATTERN {
        AT, TOPIC, URL
    }

    private ImageBroder imageBorder;
    private Object weibo;
    private Bitmap bitmap;

    // public abstract void appendWeiboData(Weibo weibo,View view,WeiboDataItem
    // weiboDataItem);
    public abstract void appendWeiboData(Weibo weibo, View view);

    public abstract void appendWeiboData(Weibo weibo, View view, boolean isFirst);

    protected static Activity activityObj;

    protected abstract int getContentIndex();

    protected abstract void setCountLayout(Weibo weibo, View view);

    protected abstract void setTranspondCount(Weibo weibo, View view);

    protected abstract void setCommentCount(Weibo weibo, View view);

    protected abstract int getGravity();

    protected abstract BitmapDownloaderTask.Type getThumbType();

    protected abstract boolean hasThumbCache(Weibo weibo);

	/*
     * protected abstract String getThumbUrl(Weibo weibo);
	 * 
	 * protected abstract Bitmap getThumbCache(Weibo weibo);
	 */

    protected int getThumbWidth() {
        // TODO Auto-generated method stub
        return LayoutParams.WRAP_CONTENT;
    }

    protected int getThumbHeight() {
        // TODO Auto-generated method stub
        return LayoutParams.WRAP_CONTENT;
    }

    // 头像
    protected void addHeader(Weibo weibo, View view, ImageView header) {
        Thinksns app = (Thinksns) view.getContext().getApplicationContext();
        // header = (ImageView)view.findViewById(R.id.user_header);
        header.setTag(weibo);
        header.setImageDrawable(view.getContext().getResources()
                .getDrawable(R.drawable.header));

        if (/* weibo.hasHeader()&& */app.isNetWorkOn()) {
            //String headerUrl = weibo.getUserface();
            /*if(weibo instanceof Comment)
            {
                ReceiveComment receiveComment = (Comment)weibo;
                headerUrl = receiveComment.getHeadUrl();
            }*/
            if (weibo.isNullForHeaderCache()) {

                dowloaderTask(weibo.getUserface(), header,
                        BitmapDownloaderTask.Type.FACE);
            } else {
                Bitmap cache = weibo.getHeader();
                if (cache == null) {
                    dowloaderTask(weibo.getUserface(), header,
                            BitmapDownloaderTask.Type.FACE);
                } else {
                    header.setImageBitmap(cache);
                }
            }
        }
    }

    final protected void removeViews(LinearLayout layout) {
        ImageBroder image = (ImageBroder) layout.findViewById(IMAGE_VIEW);
        LinearLayout transpond = (LinearLayout) layout
                .findViewById(TRANSPOND_LAYOUT);
        if (image != null) {
            layout.removeViewInLayout(image);
        }

        if (transpond != null) {
            layout.removeViewInLayout(transpond);
        }
    }

    protected abstract View appendTranspond(Weibo weibo, View view);

    final protected View appendImage(Weibo weibo, View view) {
        ImageBroder image = new ImageBroder(view.getContext());
        image.setTag(weibo);
        image.setId(IMAGE_VIEW);

        if (weibo.getAttachs() != null) {
            LinearLayout ly = creatImageLayout(view);
            dowloaderTask(weibo.getAttachs().get(0).getSmall(), image,
                    getThumbType());

        }

		/*
		 * System.out.println(weibo.getAttachs().get(0).getSmall()+ "image ");
		 * dowloaderTask(weibo.getAttachs().get(0).getSmall(),
		 * image,getThumbType());
		 */

		/*
		 * if(hasThumbCache(weibo)){ dowloaderTask(getThumbUrl(weibo),
		 * image,getThumbType()); }else{ Bitmap cache = getThumbCache(weibo);
		 * if(cache == null){ dowloaderTask(getThumbUrl(weibo),
		 * image,getThumbType()); }else{ image.setImageBitmap(cache); } }
		 */
        return image;
    }

    final protected void dowloaderTask(String url, ImageView image,
                                       BitmapDownloaderTask.Type type) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(image, type);
        task.execute(url);
    }

    protected LinearLayout creatImageLayout(View view) {
        LinearLayout imageLayout = new LinearLayout(view.getContext());
        imageLayout.setId(IMAGE_LAYOUT);
        return imageLayout;

    }
}
