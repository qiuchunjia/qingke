package com.zhiyicx.zycx.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.android.ThinksnsImageView;
import com.zhiyicx.zycx.sociax.listener.OnTouchListListener;

/**
 * Created by Administrator on 2014/12/27.
 */
public abstract class BaseListFragment extends Fragment {

    public Activity mContext = null;
    public View mCustView = null;
    public FinishLoadListener mFinishListener = null;

    public abstract interface FinishLoadListener {
        public abstract void OnFinish();
    }

    public void setFinishLoadListener(FinishLoadListener listener) {
        mFinishListener = listener;
    }

    public View getCustView() {
        return mCustView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    /**
     * 数据list view隐藏 显示loading view
     */
    public abstract OnTouchListListener getListView();

    public abstract void doRefreshHeader();

    public abstract void loadData(boolean isLoadNew);

    public View.OnClickListener getImageFullScreen(final String url) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                Thinksns app = (Thinksns) mContext.getApplicationContext();
                app.startActivity(mContext, ThinksnsImageView.class, bundle);
            }

        };
    }

    public View getOtherView() {
        return null;
    }

    public void updateView(View view, int state) {
    }
}
