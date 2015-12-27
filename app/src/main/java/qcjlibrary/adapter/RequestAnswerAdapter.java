package qcjlibrary.adapter;

import java.util.List;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.adapter.base.ViewHolder;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.model.ModelRequest;
import qcjlibrary.model.ModelRequestFlag;
import qcjlibrary.model.ModelRequestItem;
import qcjlibrary.model.ModelRequestMyAsk;
import qcjlibrary.model.ModelRequestSearch;
import qcjlibrary.model.base.Model;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：下午5:06:10
 * <p/>
 * 类描述：这个类是实现专家提问列表
 */

public class RequestAnswerAdapter extends BAdapter {
    private Model mRequestData;

    public RequestAnswerAdapter(BaseActivity activity, Model data) {
        super(activity, null);
        this.mRequestData = data;
    }

    public RequestAnswerAdapter(BaseActivity activity, List<Model> list,
                                Model data) {
        super(activity, list);
        this.mRequestData = data;
    }

    public RequestAnswerAdapter(BaseFragment fragment, Model data) {
        super(fragment, null);
        this.mRequestData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_request_answer, null);
            initView(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindDataToView(holder, position);
        return convertView;
    }

    private void bindDataToView(ViewHolder holder, int position) {
        if (holder != null) {
            ModelRequestItem modelRequestItem = (ModelRequestItem) mList
                    .get(position);
            if (modelRequestItem != null) {
                holder.tv_title.setText(modelRequestItem.getQuestion_content());
                holder.iv_a.setVisibility(View.GONE);
                holder.tv_answer.setVisibility(View.GONE);
                holder.tv_advice.setVisibility(View.GONE);
                holder.tv_expert_answer.setVisibility(View.GONE);
                if (modelRequestItem.getAnswercontent() != null
                        && !modelRequestItem.getAnswercontent().equals("")) {
                    holder.iv_a.setVisibility(View.VISIBLE);
                    holder.tv_answer.setVisibility(View.VISIBLE);
                    holder.tv_answer.setText(modelRequestItem.getAnswername()
                            + ":" + modelRequestItem.getAnswercontent());
                }
                if (modelRequestItem.getIs_recommend() != null) {
                    if (modelRequestItem.getIs_recommend().equals("1")) {
                        holder.tv_advice.setVisibility(View.VISIBLE);
                    }
                }
                if (modelRequestItem.getIs_expert() != null) {
                    if (modelRequestItem.getIs_expert().equals("1")) {
                        holder.tv_expert_answer.setVisibility(View.VISIBLE);
                        holder.tv_expert_answer.setText("专家建议："
                                + modelRequestItem.getBest_answer());
                    }
                }
                holder.tv_date.setText(modelRequestItem.getTime());
                holder.tv_num.setText(modelRequestItem.getAnswer_count());
            }
        }
    }

    /**
     * 初始化布局
     *
     * @param convertView
     * @param holder
     */
    private void initView(View convertView, ViewHolder holder) {
        if (convertView != null && holder != null) {
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.tv_advice = (TextView) convertView
                    .findViewById(R.id.tv_advice);
            holder.iv_a = (ImageView) convertView.findViewById(R.id.iv_a);
            holder.tv_answer = (TextView) convertView
                    .findViewById(R.id.tv_answer);
            holder.tv_expert_answer = (TextView) convertView
                    .findViewById(R.id.tv_expert_answer);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);

        }
    }

    @Override
    public void refreshNew() {
        if (mRequestData instanceof ModelRequestSearch) {
            ModelRequestSearch search = (ModelRequestSearch) mRequestData;
            // 这个用于搜索
            sendRequest(mApp.getRequestImpl().search(search),
                    ModelRequest.class, 0, REFRESH_NEW);
        } else if (mRequestData instanceof ModelRequestItem) {
            ModelRequestItem item = (ModelRequestItem) mRequestData;
            // 这个接口用于首页
            Log.i("anwer", item.toString() + "");
            sendRequest(mApp.getRequestImpl().index(item), ModelRequest.class,
                    0, REFRESH_NEW);
        } else if (mRequestData instanceof ModelRequestFlag) {
            // 这个接口用于标签
            ModelRequestFlag flag = (ModelRequestFlag) mRequestData;
            sendRequest(mApp.getRequestImpl().topicQuestion(flag),
                    ModelRequest.class, 0, REFRESH_NEW);
        } else if (mRequestData instanceof ModelRequestMyAsk) {
            sendRequest(mApp.getRequestImpl().myAsk(), ModelRequest.class, 0,
                    REFRESH_NEW);
        }
    }

    @Override
    public void refreshHeader(Model item, int count) {
        ModelRequestItem requestItem = (ModelRequestItem) item;
        requestItem.setLastid(requestItem.getQuestion_id());
        Log.i("params", "refreshHeader" + requestItem.getLastid() + "  dfadfds");
        sendRequest(mApp.getRequestImpl().index(requestItem),
                ModelRequest.class, 0, REFRESH_HEADER);
    }

    @Override
    public void refreshFooter(Model item, int count) {
        ModelRequestItem requestItem = (ModelRequestItem) item;
        requestItem.setMaxid(requestItem.getQuestion_id());
        sendRequest(mApp.getRequestImpl().index(requestItem),
                ModelRequest.class, 0, REFRESH_FOOTER);
    }

    @Override
    public int getTheCacheType() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getReallyList(Object object, Class type2) {
        if (object instanceof ModelRequest) {
            ModelRequest request = (ModelRequest) object;
            return request.getList();
        }
        return null;
    }

}
