package com.zhiyicx.zycx.sociax.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SearchUserList extends FavoriteList {

    public SearchUserList(Context context) {
        super(context);
    }

    public SearchUserList(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onClick(View view, int position, long id) {
        super.onClick(view, position, id);
    }

    @Override
    protected void initDrag(Context context) {
        return;
    }

    @Override
    protected void addHeaderView() {
        return;
    }

    @Override
    public void headerRefresh() {
        return;
    }

}
