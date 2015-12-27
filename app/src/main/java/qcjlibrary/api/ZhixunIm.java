package qcjlibrary.api;

import qcjlibrary.model.ModelZiXunDetail;

import com.loopj.android.http.RequestParams;

/**
 * author：qiuchunjia time：上午11:21:14 类描述：这个类是实现
 */

public interface ZhixunIm {
    public static final String NEWS = "News";
    public static final String INDEX = "index";
    public static final String CID = "cid";
    public static final String LASTID = "lastid"; // 上拉加载，最下面资讯的id
    public static final String MAXID = "maxid";// 下拉刷新，最上面的资讯id

    /**
     * 获取咨询新闻
     *
     * @return
     */
    public RequestParams index();

    public RequestParams indexItem(ModelZiXunDetail detail);
}
