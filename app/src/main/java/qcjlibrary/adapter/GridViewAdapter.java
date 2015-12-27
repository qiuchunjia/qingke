package qcjlibrary.adapter;

import java.util.List;

import com.zhiyicx.zycx.R;

import qcjlibrary.model.ModelCancerCategory;
import qcjlibrary.model.base.Model;
import qcjlibrary.util.DisplayUtils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * author：qiuchunjia time：上午11:18:15 类描述：这个类是实现 gridview 的子view的装载
 */

public class GridViewAdapter extends BaseAdapter {
    private List<Model> mList;
    private Context mContext;

    public GridViewAdapter(Context context, List<Model> models) {
        this.mList = models;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mList != null) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Model model = mList.get(position);
        convertView = judgeTheCreateView(model);
        return convertView;
    }

    /**
     * 根据model类型判断需要生成的view类型
     *
     * @param model
     * @return
     */
    private View judgeTheCreateView(Model model) {
        if (model instanceof ModelCancerCategory) {
            ModelCancerCategory category = (ModelCancerCategory) model;
            TextView textView = new TextView(mContext);
            textView.setText(category.getTitle());
            textView.setTextColor(mContext.getResources().getColor(R.color.text_black));
            textView.setBackgroundResource(R.color.main_white_pure_color);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, DisplayUtils.dp2px(mContext, 10), 0,
                    DisplayUtils.dp2px(mContext, 10));
            return textView;
        }
        return null;
    }
}
