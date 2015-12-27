package com.zhiyicx.zycx.sociax.component;

import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;

import android.app.Activity;

import android.view.View;

public class LeftAndRightTitle extends CustomTitle {
    private int leftButtonResource;
    private int rightButtonResource;

    public LeftAndRightTitle(Activity context) {
        super(context, ((ThinksnsAbscractActivity) context).isInTab());
        ThinksnsAbscractActivity activity = (ThinksnsAbscractActivity) context;

        leftButtonResource = activity.getLeftRes();
        rightButtonResource = activity.getRightRes();
        this.setListenerLeft(activity.getLeftListener());
        this.setListenerRight(activity.getRightListener());
        this.setView(activity.getTitleCenter(), TITLE_HAVE_ENDS);
    }

    public LeftAndRightTitle(Activity context, View layout) {
        super(context, ((ThinksnsAbscractActivity) context).isInTab());
        ThinksnsAbscractActivity activity = (ThinksnsAbscractActivity) context;

        leftButtonResource = activity.getLeftRes();
        rightButtonResource = activity.getRightRes();
        this.setListenerLeft(activity.getLeftListener());
        this.setListenerRight(activity.getRightListener());
        this.setView(layout);
    }

    @Override
    public int getRightResource() {
        return rightButtonResource;
    }

    @Override
    public int getLeftResource() {
        return leftButtonResource;
    }

}
