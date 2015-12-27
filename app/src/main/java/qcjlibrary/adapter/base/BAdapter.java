package qcjlibrary.adapter.base;

/***********************************************************************
 * Module:  BaseAdapter.java
 * Author:  qcj qq:260964739
 * Purpose: Defines the Class BaseAdapter
 ***********************************************************************/

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.cache.base.Cache;
import qcjlibrary.fragment.base.BaseFragment;
import qcjlibrary.listview.base.BaseListView;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.base.Model;
import qcjlibrary.request.base.Request;
import qcjlibrary.response.DataAnalyze;
import qcjlibrary.util.ToastUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhiyicx.zycx.sociax.android.Thinksns;

/**
 * adapter的基類，不要輕易修改這個類
 */
public abstract class BAdapter extends BaseAdapter {
    /**
     * 存入activity，必要时用来调用里面的东西
     */
    public BaseActivity mBaseActivity;
    /**
     * app全局应用
     */
    public Thinksns mApp;
    /**
     * 創建item需要传入的list
     */
    public List<Model> mList = new ArrayList<Model>();
    /**
     * 需要传入的fragment
     */
    private BaseFragment mBaseFragment;
    /** 缓存 */
    // private Cache mCache;
    /**
     * 需要刷新的条数
     */
    public final static int REFRESH_NEW = 1;
    public final static int REFRESH_HEADER = 2;
    public final static int REFRESH_FOOTER = 3;
    public final static int REQUEST_ITEM_COUNT = 20; // 请求item的数量
    public BaseListView mListView;
    public LayoutInflater mInflater;

    public BAdapter(BaseActivity activity, List<Model> list) {
        mBaseActivity = activity;
        mBaseActivity.setAdapter(this);
        mApp = (Thinksns) activity.getApplication();
        if (list != null)
            mList = list;
        mInflater = LayoutInflater.from(activity);
    }

    public BAdapter(BaseFragment fragment, List<Model> list) {
        this.mBaseFragment = fragment;
        mBaseActivity = (BaseActivity) mBaseFragment.getActivity();
        mInflater = LayoutInflater.from(mBaseActivity);
        mApp = (Thinksns) mBaseFragment.getActivity().getApplication();
        if (list != null)
            mList = list;
        mBaseFragment.setAdapter(this);
    }

    /**
     * 子类实现，用来第一次打开的时候获取新数据，当刷新到时候是调用refreshNew()
     */
    public abstract void refreshNew();

    /**
     * @param item  传递item到api用来刷新数据
     * @param count 获取刷新数据的多少 默認為20條，考虑的扩展性，可以修改它
     * @pdOid 上拉刷新數據
     */
    public abstract void refreshHeader(Model item, int count);

    /**
     * @param item  需要獲取的對象
     * @param count 數量
     * @pdOid 下拉加载更多
     */
    public abstract void refreshFooter(Model item, int count);

    /**
     * 真正的獲取數據，先查看是否存在缓存，如果存在就调用缓存的， 如果不存在就調用refreshnew（）獲取的數據加載到adapter里面
     */
    public void doRefreshNew() {
        // 先获取缓存
        // TODO
        if (mList != null && mList.size() == 0) { // 当为空的时候才请求，其它的都用doRefreshHeader()
            refreshNew();
        }
    }

    /**
     * 真正的刷新数据數據，即調用RefreshHeader() 獲取的數據加載到adapter里面
     */
    public void doRefreshHeader() {
        // TODO 这里要先检查网络是否有，如果没有的话 就return；
        if (mList == null)
            mList = new ArrayList<Model>();
        this.notifyDataSetChanged();
        if (mList.size() > 0) {
            refreshHeader(mList.get(0), REQUEST_ITEM_COUNT);
            // TODO
        } else {
            doRefreshNew();
        }

    }

    /**
     * 真正的獲取數據，即調用RefreshFooter() 獲取的數據加載到adapter里面
     */
    public void doRefreshFooter() {
        // TODO 这里要先检查网络是否有，如果没有的话 就return；
        if (mList == null)
            mList = new ArrayList<Model>();
        this.notifyDataSetChanged();
        if (!mList.isEmpty()) {
            // TODO
            refreshFooter(mList.get(mList.size() - 1), REQUEST_ITEM_COUNT);
        }

    }

    /**
     * @param list
     * @pdOid 下拉刷新后把数据加载到头部
     */
    public void addHeadList(List<Model> list) {
        if (list != null) {
            if (mList != null && list.size() > 0) {
                List<Model> cacheList = new ArrayList<Model>();
                if (mList.size() > 0) {
                    for (int i = 0; i < mList.size(); i++) {
                        Model model = mList.get(i);
                        cacheList.add(model);
                    }
                }
                mList.removeAll(mList); // 清除掉以前的list数据
                mList.addAll(list);
                mList.addAll(cacheList);
                // 加了数据后就要通知adapter 更新list
                this.notifyDataSetChanged();
            }
        }
        dismissTheProgress();
    }

    /**
     * 刷新头部的第二种方式
     * <p/>
     * 清理掉所有的内容，然后添加内容
     *
     * @param list
     */
    public void addHeadListWay2(List<Model> list) {
        if (list != null) {
            if (list.size() > 0) {
                if (mList == null) {
                    mList = new ArrayList<Model>();
                } else {
                    mList.removeAll(mList); // 清空所有的数据
                }
                Log.i("mlisttest", "mlist.size()=" + mList.size());
                mList.addAll(list);
                Log.i("mlisttest", "mlist.size()=" + mList.size());
                this.notifyDataSetChanged();
            }
        }
        dismissTheProgress();
    }

    /**
     * 清空头部，并且添加一个个没有数据的model站位，这个用于一级页面的刷新
     *
     * @param list
     */
    public void addHeadListWay3(List<Model> list) {

        if (list != null) {
            if (list.size() > 0) {
                if (mList == null) {
                    mList = new ArrayList<Model>();
                } else {
                    mList.removeAll(mList); // 清空所有的数据
                }
                Model model = new Model();
                mList.add(model);
                mList.addAll(list);
                this.notifyDataSetChanged();
            }
        }
        dismissTheProgress();
    }

    /**
     * 把下拉的进度条去掉
     */
    public void dismissTheProgress() {
        if (mListView != null) {
            mListView.onLoad();
        }
    }

    /**
     * @param list
     * @pdOid 把数据加载到底部
     */
    public void addFooterList(List<Model> list) {
        // judgeSuccessRefreshFooter(list);
        if (mList != null && list != null) {
            mList.addAll(list);
            // 加了数据后就要通知adapter 更新list
            this.notifyDataSetChanged();
        }
        dismissTheProgress();
    }

    /**
     * 判断底部是否刷新成功，如果不成功就设置为2
     *
     * @param list
     */
    public void judgeSuccessRefreshFooter(List<Model> list) {
        if (list == null) {
        }
    }

    /**
     * 獲取緩存，通常是調用mapp里面的緩存
     */
    private Cache getCache() {
        return null;
    }

    /**
     * @param cacheType 获取缓存的类型，因为每一个下拉框的item是不一样的，所以必须要标明获取哪一种缓存 獲取緩存類型
     */
    public abstract int getTheCacheType();

    /**
     * 获取list 的第一个item
     */
    public Model getFirstItem() {
        if (mList.size() > 0) {
            return mList.get(0);
        }
        return null;
    }

    /**
     * 获取list的最有一个item
     */
    public Model getLastItem() {
        if (mList.size() > 0) {
            return mList.get(mList.size() - 1);
        }
        return null;
    }

    /**
     * 刪掉所以的list，估計這個用不到，不過先寫好，萬一要用呢
     */
    public void deleteAlltheItem() {
        if (mList.size() > 0) {
            mList.removeAll(mList);
            this.notifyDataSetChanged();
        }
    }

    /**
     * @return 返回mlist最后一个modelitem
     */
    public Model getLastPositionItem() {
        if (mList != null && !mList.isEmpty()) {
            return mList.get(mList.size() - 1);
        }
        return null;
    }

    /**
     * @return 返回mlist第一个modelitem
     */
    public Model getFirstPositionItem() {
        if (mList != null && !mList.isEmpty()) {
            return mList.get(0);
        }
        return null;
    }

    // /**
    // * 第一次刷新数据（就是打開listview 獲取的数据）先獲取緩存中的數據，如果存在就加載出來，否則就去網絡上獲取數據
    // */
    // private List<Model> firstRefreshData() {
    // mCache = getCache();
    // if (mCache != null) {
    // // return mCache.getTheData(0);
    // }
    // // TODO 这里要先检查网络是否有，如果没有的话 就return；
    // List<Model> list = refreshNew();
    // if (list == null)
    // list = new ArrayList<Model>();
    // return list;
    // }

    public void setListView(BaseListView listView) {
        this.mListView = listView;
    }

    // ------------------------------------实现baseadapter必须实现的方法-------------------------------------------------------
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Model> getList() {
        return mList;
    }

    /************************************
     * 网络请求传递，以及返回数据解析
     ***************************************/
    private Request mRequst;
    public static final int REQUEST_GET = 0;
    public static final int REQUEST_POST = 1;

    public void sendRequest(RequestParams params,
                            Class<? extends Model> modeltype, int requsetType, int RefreshType) {
        if (params != null && modeltype != null) {
            if (mRequst == null) {
                mRequst = Request.getSingleRequest();
            }
            if (requsetType == 0) {
                mRequst.get(mApp.getHostUrl(), params,
                        new MyAsyncHttpResponseHandler(modeltype, RefreshType));
            } else {
                mRequst.post(mApp.getHostUrl(), params,
                        new MyAsyncHttpResponseHandler(modeltype, RefreshType));
            }
        } else {
            // TODO 专门用来测试
            List<Model> list = new ArrayList<Model>();
            list.add(new Model());
            list.add(new Model());
            list.add(new Model());
            list.add(new Model());
            list.add(new Model());
            addHeadList(list);
        }

    }

    private class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        private Class type;
        private int RefreshType;

        public MyAsyncHttpResponseHandler(Class ResultType, int RefreshType) {
            this.type = ResultType;
            this.RefreshType = RefreshType;
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            ToastUtils.showToast("请求异常");
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            if (arg2 != null) {
                String result = new String(arg2);
                if (result != null) {
                    Object object = onResponceSuccess(result, type);
                    if (object != null) {
                        if (object instanceof ModelMsg) {
                            ToastUtils.showToast(((ModelMsg) object)
                                    .getMessage() + "");
                        } else {
                            Object objectResult = getReallyList(object, type);
                            if (objectResult instanceof List<?>) {
                                List<Model> list = (List<Model>) objectResult;
                                if (RefreshType == REFRESH_NEW
                                        || RefreshType == REFRESH_HEADER) {
                                    addHeadList(list);
                                } else {
                                    addFooterList(list);
                                }
                            }
                        }
                    } else {
                        ToastUtils.showToast("加载数据失败");
                    }
                }
            }
        }
    }

    public Object onResponceSuccess(String str, Class class1) {
        return DataAnalyze.parseDataByGson(str, class1);
    }

    public abstract Object getReallyList(Object object, Class type2);
}