package qcjlibrary.widget.ads;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhiyicx.zycx.R;

/**
 * 广告栏
 *
 * @author qcj
 */
public class MyADView extends LinearLayout {

    private String TAG = "MyADView";

    // 标志 Message
    private static final int MESSAGE_WHAT = 1;
    private int delay = 1000 * 2;
    // 当前页数索引
    private int currentIndex = 0;

    // 装载图片的 ViewPager
    private ViewPager mViewPager;

    // viewpager 的适配器
    private PagerAdapter mAdapter;

    // 数据源，根据项目改变 Model 类型
    private List<MyADViewModel> data;

    private Context mContext;

    // 圆点父布局
    private LinearLayout ll_point_container;

    // 圆点 ImagaView 数组
    private ImageView[] iv_points;

    public int mRealCount = 0;

    /**
     * 滑动切换动画类型
     *
     * @author Administrator
     */
    public interface transFormerMode {

        int DepthPageTransformer = 1;
        int ZoomOutPageTransformer = 2;

    }

    public MyADView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;

        // 重要，注意 inflater() 方法第二个参数不为 null
        LayoutInflater.from(context).inflate(R.layout.ads_layout_item, this,
                true);
        mViewPager = (ViewPager) findViewById(R.id.vp_ad);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
    }

    /**
     * 设置页面切换的时间间隔
     *
     * @param delay 切换延迟
     */
    public void setTransformerDelay(int delay) {
        this.delay = delay;
    }

    /**
     * 设置广告栏的高度
     *
     * @param height 高度
     */
    public void setHeight(int height) {
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height);
        mViewPager.setLayoutParams(mParams);
    }

    /**
     * 设置 viewpager 的切换过程动画持续时间（自动切换）
     *
     * @param duration 持续时间
     * @throws Exception
     */
    public void setTransformDuration(int duration) throws Exception {

        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    mViewPager.getContext(), new AccelerateInterpolator());
            field.set(mViewPager, scroller);
            scroller.setmDuration(duration);
        } catch (Exception e) {
            throw new Exception("尝试修改 viewpager 动画持续时间出错");
        }

    }

    /**
     * 设置广告位的广告数据
     *
     * @param data 数据源
     * @throws Exception
     */
    public void setData(List<MyADViewModel> data) throws Exception {
        if (data == null || data.size() == 0) {
            throw new Exception("广告位数据源不能设置为空");
        }
        mRealCount = data.size();
        // 设置数据
        if (this.data != null || mAdapter != null) {
            this.data.clear();
            this.data.addAll(data);
            mAdapter.notifyDataSetChanged();
        } else {
            // 适配数据
            this.data = data;
            mAdapter = new MyADAdapter(mContext, data);
            mViewPager.setAdapter(mAdapter);
        }

        // 产生圆点
        createPoints(mRealCount, ll_point_container, mContext, null);

        // 设置选中第一项
        selectPoint(0);

        // 开始旋转
        mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, delay);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                currentIndex = arg0;
                selectPoint(arg0);
                mHandler.removeMessages(MESSAGE_WHAT);
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, delay);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    /**
     * 设置第几个圆点被选中
     *
     * @param index
     * 被选中圆圈的索引
     */
    private int lastIndex = -1;

    private void selectPoint(int index) {

        if (index > lastIndex) {
            Log.i("ads", index + "");
            // 先重置
            for (ImageView image : iv_points) {
                image.setImageResource(R.drawable.ic_dot_normal);
            }
            // 设置选中的圆圈
            iv_points[index % mRealCount]
                    .setImageResource(R.drawable.ic_dot_focused);
            lastIndex = index;
        }

    }

    /**
     * 根据设置的数据生成相应的圆点
     *
     * @param size               数据条数
     * @param ll_point_container 圆点所有布局
     * @param mContext           context
     */
    private void createPoints(int size, LinearLayout ll_point_container,
                              Context mContext, LinearLayout.LayoutParams params) {
        if (size == 0 || ll_point_container == null || mContext == null) {
            return;
        }

        if (params == null) {
            params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
        }

        ll_point_container.removeAllViews();
        iv_points = new ImageView[size];

        for (int i = 0; i < size; i++) {
            iv_points[i] = new ImageView(mContext);
            iv_points[i].setImageResource(R.drawable.ic_dot_normal);
            iv_points[i].setLayoutParams(params);
            ll_point_container.addView(iv_points[i]);
        }

    }

    /**
     * ViewPager 适配器
     *
     * @author 曹立该
     */
    class MyADAdapter extends PagerAdapter {

        private Context context;
        private List<MyADViewModel> datas;
        // 图片加载 options ,根据实际更改
        DisplayImageOptions options;

        public MyADAdapter(Context context, List<MyADViewModel> datas) {
            if (datas != null) {
                this.context = context;
                this.datas = datas;
                mRealCount = datas.size();
                options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.default_image_small)// 加载中显示的图片
                        .showImageOnFail(R.drawable.default_image_small)// 加载失败显示的图片
                        .cacheInMemory(true)// 缓存保存在内存中
                        .cacheOnDisk(true)// 缓存保存在硬盘中
                        .build();
            }
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0.equals(arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 根据实际需要更改此方法实现
            View mView = LayoutInflater.from(context).inflate(
                    R.layout.ads_item, null);
            ImageView iv_pic = (ImageView) mView.findViewById(R.id.iv_pic);
            String imageurl = datas.get(position % mRealCount).getImgUrl();
            int resouceID = datas.get(position % mRealCount).getResouceId();
            if (imageurl != null && imageurl.length() > 0) {
                ImageLoader.getInstance().displayImage(imageurl, iv_pic,
                        options);
            } else if (resouceID > 0) {
                iv_pic.setImageResource(resouceID);
            }
            ((ViewPager) container).addView(mView, 0);
            return mView;
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MESSAGE_WHAT:
                    mHandler.removeMessages(MESSAGE_WHAT);
                    if (currentIndex < Integer.MAX_VALUE) {
                        currentIndex++;
                        Log.i("ads++", currentIndex + "");
                        mViewPager.setCurrentItem(currentIndex);
                    }
                    break;

                default:
                    break;
            }
            mHandler.removeMessages(MESSAGE_WHAT);
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, delay);
        }

    };
}
