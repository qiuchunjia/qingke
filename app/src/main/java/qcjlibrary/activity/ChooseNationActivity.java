package qcjlibrary.activity;

import com.zhiyicx.zycx.R;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.StringAdapter;
import qcjlibrary.config.Config;
import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午3:19:06 类描述：这个类是实现
 */

public class ChooseNationActivity extends BaseActivity {
    private ListView mListView;
    private StringAdapter mAdapter;
    // 汉族1．壮族2．藏族3．裕固族4．彝族5．瑶族6．锡伯族7．乌孜别克族8．维吾尔族9．佤族10
    //
    // ．土家族11．土族12．塔塔尔族13．塔吉克族14．水族15．畲族16．撒拉族17．羌族18．普米
    //
    // 族19．怒族20．纳西族21．仫佬族22．苗族23．蒙古族24．门巴族25．毛南族26．满族27．珞
    // 巴族28．僳僳族29．黎族30．拉祜族31．柯尔克孜族32．景颇族33．京族34．基诺族35．回族
    //
    // 36．赫哲族37．哈萨克族38．哈尼族39．仡佬族40．高山族41．鄂温克族42．俄罗斯族43．鄂
    //
    // 伦春族44．独龙族45．东乡族46．侗族47．德昂族48．傣族49．达斡尔族50．朝鲜族51．布依
    //
    // 族52．布朗族53．保安族54．白族55．阿昌族
    private String[] mData = new String[]{"汉族", "壮族", "藏族", "裕固族", "彝族",
            "瑶族", "锡伯族", "乌孜别克族", "维吾尔族", "佤族", "土家族", "土族", "塔塔尔族", "塔吉克族",
            "水族", "畲族", "撒拉族", "羌族", "普米族", "怒族", "纳西族", "仫佬族", "苗族", "蒙古族",
            "门巴族", "毛南族", "满族", "珞巴族", "僳僳族", "黎族", "拉祜族", "柯尔克孜族", "景颇族",
            "京族", "基诺族", "回族", "赫哲族", "哈萨克族", "哈尼族", "仡佬族", "高山族", "鄂温克族",
            "俄罗斯族", "鄂伦春族", "独龙族", "东乡族", "侗族", "德昂族", "傣族", "达斡尔族", "朝鲜族",
            "布依族", "布朗族", "保安族", "白族", "阿昌族"};

    @Override
    public String setCenterTitle() {
        return "民族";
    }

    @Override
    public void initIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.listview_layout;
    }

    @Override
    public void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new StringAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String dataStr = (String) parent.getItemAtPosition(position);
                setReturnResultSeri(dataStr, Config.TYPE_NATION);
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initListener() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}
