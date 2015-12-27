package qcjlibrary.api;

import qcjlibrary.model.ModelExperience;
import qcjlibrary.model.ModelExperienceDetailItem1;
import qcjlibrary.model.ModelExperiencePostDetailItem;
import qcjlibrary.model.ModelExperienceSend;

import com.loopj.android.http.RequestParams;

/**
 * author：qiuchunjia time：下午2:33:45
 * <p/>
 * 类描述：这个类是实现经历的接口
 */

public interface ExperienceIm {
    // 接口需要的操作参数
    public static final String EXPERIENCE = "Experience";
    public static final String INDEX = "index";
    public static final String ADD_POST = "add_post";
    public static final String POST_DETAIL = "post_detail"; // 经历-主帖详情
    public static final String DETAIL = "detail";
    public static final String DOPRAISE = "doPraise"; // 点赞
    // 接口需要传的值的键
    public static final String WEIBA_ID = "weiba_id";
    public static final String PARENT_ID = "parent_id";
    public static final String TITLE = "title";
    public static final String POST_TIME = "post_time";
    public static final String BODY = "body";
    public static final String TAGS = "tags";
    public static final String ID = "id";

    /**
     * 经历-经历首页
     *
     * @return
     */
    public RequestParams index();

    public RequestParams addPost(ModelExperienceSend send);

    /**
     * 经历-小组详情
     *
     * @param experience
     * @return
     */
    public RequestParams detail(ModelExperience experience);

    /**
     * 经历-主帖详情
     *
     * @param send
     * @return
     */
    public RequestParams postDetail(ModelExperienceDetailItem1 item1);

    /**
     * 帖子点赞
     *
     * @param item1
     * @return
     */
    public RequestParams doPraise(ModelExperiencePostDetailItem item1);

}
