package qcjlibrary.fragment;

import java.util.List;

import qcjlibrary.adapter.FragmentAdapter;
import qcjlibrary.api.api;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.ModelZiXun;
import qcjlibrary.model.ModelZiXunCategory;
import qcjlibrary.widget.viewpagerindicator.TabPageIndicator;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午10:23:36 类描述：这个类是实现
 */

public class FragmentZhixun extends BaseFragment {
    private TabPageIndicator tabpagerIndicator;
    private ViewPager vPager;

    @Override
    public void initIntentData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.zixunfragment;
    }

    @Override
    public void initView() {
        tabpagerIndicator = (TabPageIndicator) findViewById(R.id.tabpagerIndicator);
        vPager = (ViewPager) findViewById(R.id.vPager);
        sendRequest(new api.ZhiXunImpl().index(), ModelZiXun.class, 0);
    }

    @Override
    public Object onResponceSuccess(String str, Class class1) {
        Object object = super.onResponceSuccess(str, class1);
        if (object instanceof ModelZiXun) {
            Object result = ((ModelZiXun) object).getFenlei();
            FragmentAdapter fadapter = new FragmentAdapter(
                    getChildFragmentManager(),
                    (List<ModelZiXunCategory>) result);
            vPager.setAdapter(fadapter);
            tabpagerIndicator.setViewPager(vPager);
            tabpagerIndicator.setVisibility(View.VISIBLE);
        }
        return object;
    }

    @Override
    public void initListener() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}
