package com.zhiyicx.zycx.sociax.component;

import android.content.Context;

import com.zhiyicx.zycx.R;

public class SmallDialog extends CustomerDialogNoTitle {
    public SmallDialog(Context context, String title) {
        super(context, com.zhiyicx.zycx.R.style.myDialog,
                R.layout.small_dailog, title);
    }

    public SmallDialog(Context context, String title, float margin) {
        super(context, com.zhiyicx.zycx.R.style.myDialog, R.layout.small_dailog, margin, title);
    }
}
