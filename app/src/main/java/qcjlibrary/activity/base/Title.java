package qcjlibrary.activity.base;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午10:38:46
 * <p/>
 * 类描述：这个类是实现界面的title的里面的控件初始化之类的
 */

public class Title implements TitleInterface {
    private View mTitleView;
    private BaseActivity mActivity;
    // 初始化各个控件
    // 左边部分控件
    public RelativeLayout rl_left_1;// 左边只有一个图片和文字控件的 距离左10dp ，一般是用着返回的上一个界面的
    public ImageView iv_title_left;
    public TextView tv_title_left;

    public RelativeLayout rl_left_2; // 左边只有一个图片控件的 距离左10dp
    public ImageView iv_title_left2;
    // 中间部分控件
    public TextView tv_title; // 中间只有一个控件的时候
    // 中间有两个控件控件的时候 通常这两个控件是可以切换的
    public RelativeLayout rl_twoButton;
    public TextView tv_1;
    public TextView tv_2;
    public RelativeLayout rl_1_image; // 中间一个图片空间的情况
    public ImageView iv_1_choose;

    // 右边部分控件
    public TextView tv_title_right; // 距离右10dp
    public ImageView iv_title_right1;// 距离右10dp
    public ImageView iv_title_right2;// 距离右20dp
    public ImageView iv_title_right3;// 距离右40dp

    public Title(View titleView, BaseActivity activity) {
        this.mTitleView = titleView;
        this.mActivity = activity;
        initView();
    }

    private void initView() {
        if (mTitleView != null) {
            rl_left_1 = (RelativeLayout) mTitleView
                    .findViewById(R.id.rl_left_1);
            iv_title_left = (ImageView) mTitleView
                    .findViewById(R.id.iv_title_left);
            tv_title_left = (TextView) mTitleView
                    .findViewById(R.id.tv_title_left);

            rl_left_2 = (RelativeLayout) mTitleView
                    .findViewById(R.id.rl_left_2);
            iv_title_left2 = (ImageView) mTitleView
                    .findViewById(R.id.iv_title_left2);

            tv_title = (TextView) mTitleView.findViewById(R.id.tv_title);
            rl_twoButton = (RelativeLayout) mTitleView
                    .findViewById(R.id.rl_twoButton);
            tv_1 = (TextView) mTitleView.findViewById(R.id.tv_1);
            tv_2 = (TextView) mTitleView.findViewById(R.id.tv_2);

            tv_title_right = (TextView) mTitleView
                    .findViewById(R.id.tv_title_right);
            iv_title_right1 = (ImageView) mTitleView
                    .findViewById(R.id.iv_title_right1);
            iv_title_right2 = (ImageView) mTitleView
                    .findViewById(R.id.iv_title_right2);
            iv_title_right3 = (ImageView) mTitleView
                    .findViewById(R.id.iv_title_right3);
            rl_1_image = (RelativeLayout) mTitleView
                    .findViewById(R.id.rl_1_image);
            iv_1_choose = (ImageView) mTitleView.findViewById(R.id.iv_1_choose);
            Log.i("title", "init title end");
        }
    }

    @Override
    public Title getTitleClass() {
        return this;
    }

    /****************************************************************************/
    @Override
    public void titleSetLeftImage(int resouceId) {
        if (resouceId > 0) {
            iv_title_left2.setImageResource(resouceId);
        }

    }

    @Override
    public void titleSetLeftImageAndTxt(int resouceId, String str) {
        if (resouceId > 0 && str != null && !str.equals("")) {
            iv_title_left.setImageResource(resouceId);
            tv_title_left.setText(str);
        }
    }

    @Override
    public void titleOnBackPress() {
        if (mTitleView != null && mActivity != null) {
            rl_left_1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();

                }
            });
        }

    }

    @Override
    public void titleSlideMenu(final DrawerLayout drawerLayout) {
        if (mTitleView != null && mActivity != null && drawerLayout != null) {
            rl_left_2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    @Override
    public void titleSetCenterTitle(String title) {
        if (title != null && !title.equals("")) {
            tv_title.setText(title);
        }
    }

    @Override
    public void titleSetCenterTwoTitle(String title1, String title2) {
        if (title1 != null && !title1.equals("") && title2 != null
                && !title2.equals("")) {
            tv_title.setVisibility(View.VISIBLE);
            rl_twoButton.setVisibility(View.VISIBLE);
            tv_1.setText(title1);
            tv_2.setText(title2);
        }

    }

    @Override
    public void titleSetRightTitle(String rightTitle) {
        if (tv_title_right != null) {
            if (rightTitle != null && !rightTitle.equals("")) {
                tv_title_right.setVisibility(View.VISIBLE);
                tv_title_right.setText(rightTitle);
            }
        }

    }

    @Override
    public void titleSetRightImage(int resouceId) {
        if (resouceId > 0) {
            iv_title_right1.setVisibility(View.VISIBLE);
            iv_title_right1.setImageResource(resouceId);
        }
    }

}
