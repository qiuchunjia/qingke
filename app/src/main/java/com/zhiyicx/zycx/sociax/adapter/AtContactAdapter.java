package com.zhiyicx.zycx.sociax.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.gimgutil.AsyncImageLoader;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date Jan 18, 2013
 */
public class AtContactAdapter extends UserListAdapter {

    public Context mContext;
    public String mKey;

    public AtContactAdapter(ThinksnsAbscractActivity context,
                            ListData<SociaxItem> data) {
        super(context, data);
        mContext = context;
    }

    public AtContactAdapter(ThinksnsAbscractActivity context,
                            ListData<SociaxItem> data, String key) {
        super(context, data);
        mContext = context;
        mKey = key;
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        if (mKey != null) {
            return thread.getApp().getStatuses().searchUser(mKey, count);
        } else {
            return thread.getApp().getUsers().getRecentAt();
        }
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return thread.getApp().getStatuses()
                .searchFooterUser(mKey, (User) obj, PAGE_COUNT);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = View.inflate(mContext, R.layout.at_contact_list_itme,
                    null);
            viewHodler.imageView = (ImageView) convertView
                    .findViewById(R.id.iv_head);
            viewHodler.textView = (TextView) convertView
                    .findViewById(R.id.tv_name);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        User user = getItem(position);
        viewHodler.imageView.setImageDrawable(loadImage(user.getFace(),
                viewHodler.imageView));
        viewHodler.textView.setText(user.getUserName());

        return convertView;
    }

    private AsyncImageLoader asyncImageLoader3 = new AsyncImageLoader();

    private Drawable loadImage(final String url, final ImageView imageView) {
        Drawable cacheImage = asyncImageLoader3.loadDrawable(url,
                new AsyncImageLoader.ImageCallback() {
                    @Override
                    public void imageLoaded(Drawable imageDrawable) {
                        imageView.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public Drawable returnImageLoaded(Drawable imageDrawable) {
                        return imageDrawable;
                    }
                });
        if (cacheImage != null) {
            imageView.setImageDrawable(cacheImage);
        }
        return cacheImage;
    }

    static class ViewHodler {
        public ImageView imageView;
        public TextView textView;
    }
}
