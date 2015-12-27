package com.zhiyicx.zycx.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.fragment.BaseListFragment;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.api.ApiUsers;
import com.zhiyicx.zycx.sociax.component.LoadingView;
import com.zhiyicx.zycx.sociax.concurrent.Worker;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.gimgutil.ImageCache.ImageCacheParams;
import com.zhiyicx.zycx.sociax.gimgutil.ImageFetcher;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.Posts;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.unit.Anim;

public abstract class SociaxListAdapter extends BaseAdapter {
    protected static final String TAG = "SociaxListAdapter";
    protected ListData<SociaxItem> list;
    protected Activity context;
    protected LayoutInflater inflater;
    public static final int LIST_FIRST_POSITION = 0;
    protected static View refresh;
    protected static Worker thread;
    protected ActivityHandler handler;
    protected ResultHandler resultHander;
    protected static String Type;
    public static final int REFRESH_HEADER = 0;
    public static final int REFRESH_FOOTER = 1;
    public static final int REFRESH_NEW = 2;
    public static final int SEARCH_NEW = 3;
    public static final int UPDATA_LIST = 4;
    public static final int UPDATA_LIST_ID = 5;
    public static final int UPDATA_LIST_TYPE = 6;
    public static final int SEARCH_NEW_BY_ID = 7;
    public static final int PAGE_COUNT = 20;
    private static LoadingView loadingView;

    public static final int FAV_STATE = 8;

    protected boolean isSelectButtom;

    public boolean hasRefreshFootData;
    public boolean isHideFootToast = false;
    public boolean isShowToast = true; // 是否显示提示
    public boolean isCleanAllData = false; // 是否清楚list中所有数据

    public int lastNum;
    public String isRefreshActivity;
    public ImageView animView;

    //public ImageFetcher mHeadImageFetcher, mContentImageFetcher;
    public NetComTools mNetComTools;
    public int mHeadImageSize;
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private static final String CONTET_IMAGE_CACHE_DIR = "cthumbs";

    protected BaseListFragment mListContext = null;

    public SociaxListAdapter(BaseListFragment ctx, ListData<SociaxItem> list) {
        this.list = list;
        mListContext = ctx;
        this.context = ctx.mContext;
        this.inflater = LayoutInflater.from(context);
        SociaxListAdapter.thread = new Worker((Thinksns) context.getApplicationContext(), Type + " Refresh");
        handler = new ActivityHandler(thread.getLooper(), context);
        resultHander = new ResultHandler();
        loadingView = (LoadingView) ctx.getCustView().findViewById(LoadingView.ID);
        mHeadImageSize = context.getResources().getDimensionPixelSize(R.dimen.header_width_hight);
        mNetComTools = NetComTools.getInstance(context);
    }

    /**
     * List列表头部刷新
     *
     * @param obj
     * @return
     * @throws VerifyErrorException
     * @throws ApiException
     * @throws ListAreEmptyException
     * @throws DataInvalidException
     */
    public abstract ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException;

    /**
     * List列表更多刷新
     *
     * @param obj
     * @return
     * @throws VerifyErrorException
     * @throws ApiException
     * @throws ListAreEmptyException
     * @throws DataInvalidException
     */
    public abstract ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException;

    /**
     * List列表刷新
     *
     * @param count
     * @return
     * @throws VerifyErrorException
     * @throws ApiException
     * @throws ListAreEmptyException
     * @throws DataInvalidException
     */
    public abstract ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException;

    public ListData<SociaxItem> searchNew(String key)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return null;
    }

    ;

    public ListData<SociaxItem> searchNew(int key) throws ApiException {
        return null;
    }

    ;

    public ListData<SociaxItem> refreshNew(int count, String key)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return null;
    }

    public ListData<SociaxItem> refreshNew(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return null;
    }

    public Object refresState(int key) throws ApiException {
        return null;
    }

    public Context getContext() {
        return this.context;
    }

    public SociaxItem getFirst() {
        return this.list.size() == 0 ? null : this.list
                .get(LIST_FIRST_POSITION);
    }

    public SociaxItem getLast() {
        return this.list.get(this.list.size() - 1);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public SociaxItem getItem(int position) {
        return this.list.get(position);
    }

    /**
     * 头部追加信息
     *
     * @param list
     */
    public void addHeader(ListData<SociaxItem> list) {
        if (null != list) {
            if (list.size() == 20) {
                this.list.clear();
                this.list.addAll(list);
                // 修改适配器绑定的数组后
                this.notifyDataSetChanged();
                mListContext.getListView().showFooterView();
                //Toast.makeText(context, com.zhiyicx.zycx.R.string.refresh_success, Toast.LENGTH_SHORT).show();
            } else if (list.size() == 0) {
                if (this.list.size() > 20) {
                    ListData<SociaxItem> tempList = new ListData<SociaxItem>();
                    for (int i = 0; i < 20 - list.size(); i++) {
                        tempList.add(this.list.get(i));
                    }
                    this.list.clear();
                    this.list.addAll(tempList);
                }
                this.notifyDataSetChanged();
                //Toast.makeText(context, com.zhiyicx.zycx.R.string.refresh_error, Toast.LENGTH_SHORT).show();
            } else {
                ListData<SociaxItem> tempList = new ListData<SociaxItem>();
                for (int i = 0; i < 20 - list.size() && i < this.list.size(); i++) {
                    tempList.add(this.list.get(i));
                }
                this.list.clear();
                for (int i = 1; i <= list.size(); i++) {
                    this.list.add(0, list.get(list.size() - i));
                }
                this.list.addAll(this.list.size(), tempList);
                // 修改适配器绑定的数组后
                this.notifyDataSetChanged();

                //Toast.makeText(context, com.zhiyicx.zycx.R.string.refresh_success, Toast.LENGTH_SHORT).show();
            }

            if (this.list.size() == 0 && isShowToast) {
                Toast.makeText(context, "暂无信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 删除Item
     *
     * @param position
     */
    public void deleteItem(int position) {
        if (list.size() > 0)
            this.list.remove(position - 1);
        this.notifyDataSetChanged();
    }

    /**
     * 底部追加信息
     *
     * @param list
     */
    public void addFooter(ListData<SociaxItem> list) {
        if (null != list) {
            if (list.size() > 0) {
                hasRefreshFootData = true;
                this.list.addAll(list);
                lastNum = this.list.size();
                this.notifyDataSetChanged();
            }
        }
        if (list == null || list.size() == 0 || list.size() < 20) {
            mListContext.getListView().hideFooterView();
        } else
            mListContext.getListView().showFooterView();

        /*
        if (this.list.size() == 0 && isShowToast) {
			Toast.makeText(context, "暂无信息", Toast.LENGTH_SHORT).show();
		}
        */
    }

    public void changeListData(ListData<SociaxItem> list) {
        if (null != list) {
            if (list.size() > 0) {
                hasRefreshFootData = true;
                this.list = list;
                lastNum = this.list.size();
                this.notifyDataSetChanged();
            }
        }
        if (list == null || list.size() == 0 || list.size() < 20) {
            mListContext.getListView().hideFooterView();
        }

        if (this.list.size() == 0 && isShowToast) {
            Toast.makeText(context, "暂无信息", Toast.LENGTH_SHORT).show();
        }

    }

    public void changeListDataNew(ListData<SociaxItem> list) {
        if (null != list) {
            if (list.size() > 0) {
                hasRefreshFootData = true;
                this.list = list;
                lastNum = this.list.size();
                this.notifyDataSetChanged();

            } else {
                this.list.clear();
                this.notifyDataSetChanged();
            }
        }

        if (list == null || list.size() == 0 || list.size() < 20) {
            mListContext.getListView().hideFooterView();
        } else {
            mListContext.getListView().showFooterView();
        }

        if (this.list.size() == 0) {
            Toast.makeText(context, "暂无相关内容", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 头部刷新
     */
    public void doRefreshHeader() {
        if (!((Thinksns) this.context.getApplicationContext()).isNetWorkOn()) {
            Toast.makeText(context, "网络设置不正确，请设置网络", Toast.LENGTH_SHORT).show();
            mListContext.getListView().headerHiden();
            return;
        }

        SociaxListAdapter.thread = new Worker((Thinksns) context.getApplicationContext(), Type + " Refresh");
        handler = new ActivityHandler(thread.getLooper(), context);
        resultHander = new ResultHandler();
        mListContext.getListView().headerRefresh();
        mListContext.getListView().headerShow();
        Message msg = handler.obtainMessage();

        msg.obj = this.getFirst();
        msg.what = REFRESH_HEADER;
        Log.d(TAG, "doRefreshHeader .....");
        handler.sendMessage(msg);
    }

    public void doRefreshFooter() {
        if (!((Thinksns) this.context.getApplicationContext()).isNetWorkOn()) {
            Toast.makeText(context, "网络设置不正确，请设置网络", Toast.LENGTH_SHORT).show();
            return;
        }
        SociaxListAdapter.thread = new Worker((Thinksns) context.getApplicationContext(), Type + " Refresh");
        handler = new ActivityHandler(thread.getLooper(), context);
        resultHander = new ResultHandler();
        mListContext.getListView().footerShow();
        if (this.list.size() == 0) {
            return;
        }
        Message msg = handler.obtainMessage();
        msg.obj = this.getLast();
        msg.what = REFRESH_FOOTER;
        handler.sendMessage(msg);
    }

    protected void cacheHeaderPageCount() {
        ListData<SociaxItem> cache = new ListData<SociaxItem>();
        for (int i = 0; i < PAGE_COUNT; i++) {
            cache.add(0, this.list.get(i));
        }
        Thinksns.setLastWeiboList(cache);
    }

    public void refreshNewWeiboList() {

        Message msg = handler.obtainMessage();
        msg.what = REFRESH_NEW;
        handler.sendMessage(msg);
    }

    public void doUpdataList() {
        Message msg = handler.obtainMessage();
        msg.what = UPDATA_LIST;
        handler.sendMessage(msg);
    }

    public void doUpdataList(String type) {
        if (type.equals("taskCate")) {

            if (loadingView != null)
                loadingView.show((View) mListContext.getListView());
            if (mListContext.getOtherView() != null) {
                loadingView.show(mListContext.getOtherView());
            }
        }
        Message msg = handler.obtainMessage();
        msg.what = UPDATA_LIST;
        handler.sendMessage(msg);
    }

    public void doUpdataListById() {
        Message msg = handler.obtainMessage();
        msg.what = UPDATA_LIST_ID;
        handler.sendMessage(msg);
    }

    public void doUpdataListByType(SociaxItem sociaxItem) {

        if (loadingView != null)
            loadingView.show((View) mListContext.getListView());
        if (mListContext.getOtherView() != null) {
            loadingView.show(mListContext.getOtherView());
        }

        Message msg = handler.obtainMessage();
        msg.obj = sociaxItem;
        msg.what = UPDATA_LIST_ID;
        handler.sendMessage(msg);
    }

    public void doSearchNew(String key) {
        Message msg = handler.obtainMessage();
        msg.what = SEARCH_NEW;
        msg.obj = key;
        handler.sendMessage(msg);
    }

    public void doSearchNewById(int key) {
        Message msg = handler.obtainMessage();
        msg.what = SEARCH_NEW_BY_ID;
        msg.arg1 = key;
        handler.sendMessage(msg);
    }

    public void updateState(int key) {
        Message msg = handler.obtainMessage();
        msg.what = FAV_STATE;
        msg.arg1 = key;
        handler.sendMessage(msg);
    }

    private class ActivityHandler extends Handler {
        private Context context = null;

        public ActivityHandler(Looper looper, Context context) {
            super(looper);
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ListData<SociaxItem> newData = null;
            Message mainMsg = new Message();
            mainMsg.what = ResultHandler.ERROR;
            try {
                switch (msg.what) {
                    case REFRESH_HEADER:
                        newData = refreshHeader((SociaxItem) msg.obj);
                        Log.d(TAG, "refresh header ....");
                        break;
                    case REFRESH_FOOTER:
                        newData = refreshFooter((SociaxItem) msg.obj);
                        Log.d(TAG, "refresh footer ....");
                        break;
                    case REFRESH_NEW:
                        Log.d(TAG, "refresh new  ....");
                        newData = refreshNew(PAGE_COUNT);
                        break;
                    case SEARCH_NEW:
                        Log.d(TAG, "seache new  ....");
                        newData = searchNew((String) msg.obj);
                        break;
                    case SEARCH_NEW_BY_ID:
                        Log.d(TAG, "seache new  ....");
                        newData = searchNew(msg.arg1);
                        break;
                    case UPDATA_LIST:
                        Log.d(TAG, "updata list  ....");
                        newData = refreshNew(PAGE_COUNT);
                        break;
                    case UPDATA_LIST_ID:
                        Log.d(TAG, "updata list  ....");
                        newData = refreshNew((SociaxItem) msg.obj);
                        break;
                    case UPDATA_LIST_TYPE:
                        Log.d(TAG, "updata list  ....");
                        newData = refreshNew((SociaxItem) msg.obj);
                        break;
                    case FAV_STATE:
                        mainMsg.arg2 = ((Posts) (refresState(mId))).getFavorite();
                        break;
                }
                mainMsg.what = ResultHandler.SUCCESS;
                mainMsg.obj = newData;
                mainMsg.arg1 = msg.what;
            } catch (VerifyErrorException e) {
                Log.d("SociaxListAdapter class ", e.toString());
                mainMsg.obj = e.getMessage();
            } catch (ApiException e) {
                Log.d("SociaxListAdapter class ", e.toString());
                mainMsg.what = 2;
                mainMsg.obj = e.getMessage();
            } catch (ListAreEmptyException e) {
                Log.d("SociaxListAdapter class ", e.toString());
                mainMsg.obj = e.getMessage();
            } catch (DataInvalidException e) {
                Log.d("SociaxListAdapter class ", e.toString());
                mainMsg.obj = e.getMessage();
            }
            resultHander.sendMessage(mainMsg);
        }
    }

    @SuppressLint("HandlerLeak")
    private class ResultHandler extends Handler {
        private static final int SUCCESS = 0;
        private static final int ERROR = 1;

        public ResultHandler() {
        }

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            mListContext.getListView().setLastRefresh(System.currentTimeMillis());
            if (msg.what == SUCCESS) {
                switch (msg.arg1) {
                    case REFRESH_NEW:
                        addFooter((ListData<SociaxItem>) msg.obj);
                        Log.d(TAG, "refresh new load ....");
                        break;
                    case REFRESH_HEADER:
                        addHeader((ListData<SociaxItem>) msg.obj);
                        mListContext.getListView().headerHiden();
                        Log.d(TAG, "refresh header load ....");
                        break;
                    case REFRESH_FOOTER:
                        addFooter((ListData<SociaxItem>) msg.obj);
                        //mListContext.getListView().footerHiden();
                        Log.d(TAG, "refresh heiden load ....");
                        break;
                    case SEARCH_NEW:
                        changeListDataNew((ListData<SociaxItem>) msg.obj);
                        //mListContext.getListView().footerHiden();
                        Log.d(TAG, "refresh heiden load ....");
                        break;
                    case SEARCH_NEW_BY_ID:
                        changeListDataNew((ListData<SociaxItem>) msg.obj);
                        mListContext.getListView().footerHiden();
                        Log.d(TAG, "refresh heiden load ....");
                        break;
                    case UPDATA_LIST:
                        changeListData((ListData<SociaxItem>) msg.obj);
                        mListContext.getListView().footerHiden();
                        Log.d(TAG, "refresh heiden load ....");
                        break;
                    case UPDATA_LIST_ID:
                        changeListDataNew((ListData<SociaxItem>) msg.obj);
                        mListContext.getListView().footerHiden();
                        Log.d(TAG, "refresh heiden load ....");
                        break;
                    case UPDATA_LIST_TYPE:
                        changeListDataNew((ListData<SociaxItem>) msg.obj);
                        mListContext.getListView().footerHiden();
                        Log.d(TAG, "refresh heiden load ....");
                        break;
                    case FAV_STATE:
                        mListContext.updateView(mUpdateView, msg.arg2);
                        break;
                }
            } else {
                //if (!isHideFootToast)
                //	Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
                mListContext.getListView().headerHiden();
                mListContext.getListView().footerHiden();
                if (msg.arg1 == SEARCH_NEW)
                    Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
            Anim.cleanAnim(animView); // 清除动画
            if (loadingView != null)
                loadingView.hide((View) mListContext.getListView());
            if (mListContext.getOtherView() != null) {
                loadingView.hide(mListContext.getOtherView());
            }
            if (mListContext.mFinishListener != null)
                mListContext.mFinishListener.OnFinish();
        }
    }

    /**
     * 加载数据
     */
    public void loadInitData() {
        if (!((Thinksns) this.context.getApplicationContext()).isNetWorkOn()) {
            Toast.makeText(context, R.string.net_fail, Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.getCount() == 0) {
            ListData<SociaxItem> cache = Thinksns.getLastWeiboList();
            if (cache != null) {
                this.addHeader(cache);
            } else {

                if (loadingView != null)
                    loadingView.show((View) mListContext.getListView());
                if (mListContext.getOtherView() != null) {
                    loadingView.show(mListContext.getOtherView());
                }

                refreshNewWeiboList();
            }
        }
    }

    public void loadHomeInitData() {
        if (!((Thinksns) this.context.getApplicationContext()).isNetWorkOn()) {
            Toast.makeText(context, R.string.net_fail, Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.getCount() == 0) {
            ListData<SociaxItem> cache = Thinksns.getLastWeiboList();
            if (cache != null) {
                this.addHeader(cache);
            } else {
                refreshNewWeiboList();
            }
        }
    }


    /**
     * 加载数据
     */
    public void loadSearchData(String key) {
        if (!((Thinksns) this.context.getApplicationContext()).isNetWorkOn()) {
            Toast.makeText(context, R.string.net_fail, Toast.LENGTH_SHORT).show();
            return;
        }

        if (loadingView != null)
            loadingView.show((View) mListContext.getListView());
        if (mListContext.getOtherView() != null) {
            loadingView.show(mListContext.getOtherView());
        }
        doSearchNew(key);
    }

    private int mId;
    private int mState;
    private View mUpdateView;

    public void loadInitData(View updateView, int id, int state) {
        if (!((Thinksns) this.context.getApplicationContext()).isNetWorkOn()) {
            Toast.makeText(context, R.string.net_fail, Toast.LENGTH_SHORT).show();
            return;
        }
        mId = id;
        mState = state;
        mUpdateView = updateView;
        if (this.getCount() == 0) {
            ListData<SociaxItem> cache = Thinksns.getLastWeiboList();
            if (cache != null) {
                this.addHeader(cache);
            } else {
                if (loadingView != null)
                    loadingView.show((View) mListContext.getListView());
                if (mListContext.getOtherView() != null) {
                    loadingView.show(mListContext.getOtherView());
                }
                refreshNewWeiboList();
                updateState(mId);
            }
        }
    }

    public int getMyUid() {
        Thinksns app = thread.getApp();
        return Thinksns.getMy().getUid();
    }

    public ApiUsers getApiUsers() {
        Thinksns app = thread.getApp();
        return app.getUsers();
    }

    public int getMySite() {
        Thinksns app = thread.getApp();
        if (Thinksns.getMySite() == null) {
            return 0;
        } else {
            return Thinksns.getMySite().getSite_id();
        }
    }
}
