package qcjlibrary.activity.base;

import android.support.v4.widget.DrawerLayout;

/**
 * author：qiuchunjia time：
 * <p/>
 * 上午11:45:33
 * <p/>
 * 这个接口主要是用来实现改变title布局里面小布局的某些值的，以及《《常用》》的点击事件
 * <p/>
 * 其它不常用的就简单粗暴的获取并实现就ok了
 */

public interface TitleInterface {
    public Title getTitleClass();

    /**
     * 点击title左边返回上一个界面
     */
    public void titleOnBackPress();

    /**
     * 点击左边实现滑动菜单
     */
    public void titleSlideMenu(DrawerLayout drawerLayout);

    /**
     * 设置左边的一个控件的布局图片
     *
     * @param resouceId
     */
    public void titleSetLeftImage(int resouceId);

    /**
     * 设置title左边两个控件的布局的资源id，和文字
     *
     * @param resouceId
     * @param str
     */
    public void titleSetLeftImageAndTxt(int resouceId, String str);

    /**
     * 设置title
     *
     * @param title
     */
    public void titleSetCenterTitle(String title);

    /**
     * 设置中间布局的两个title
     *
     * @param title1
     * @param title2
     */
    public void titleSetCenterTwoTitle(String title1, String title2);

    /**
     * 设置右边的文字
     *
     * @param rightTitle
     */
    public void titleSetRightTitle(String rightTitle);

    /**
     * 设置右边部分图片
     *
     * @param resouceId
     */
    public void titleSetRightImage(int resouceId);

}
