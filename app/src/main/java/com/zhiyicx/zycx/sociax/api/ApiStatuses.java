package com.zhiyicx.zycx.sociax.api;

import java.io.File;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.UpdateException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.Follow;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public interface ApiStatuses {
    static final String MOD_NAME = "WeiboStatuses";
    static final String SHOW = "show";
    static final String PUBLIC_TIMELINE = "public_timeline";
    static final String FRIENDS_TIMELINE = "friends_timeline";
    static final String USER_TIMELINE = "user_timeline";
    static final String MENTION = "mentions_feed";
    static final String SEARCH = "weibo_search_weibo";
    static final String COMMENT_TIMELINE = "comments_timeline";
    static final String COMMENT_BY_ME = "comments_by_me";
    static final String COMMENT_RECEIVE_ME = "comments_to_me";
    static final String COMMENTS = "comments";
    static final String FOOLOWING = "following";
    static final String FOLLOWERS = "followers";
    static final String FOLLOWEACH = "friends";
    static final String SEARCH_USER = "weibo_search_user";
    static final String UPDATE = "update";
    static final String UPLOAD = "upload";
    static final String COMMENT = "comment";
    static final String REPOST = "repost";
    static final String DESTROY = "destroy";
    static final String COMMENT_DESTROY = "commentDestroy";
    static final String UN_READ = "unread";

    public Weibo show(int id) throws ApiException, WeiboDataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> publicTimeline(int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> publicHeaderTimeline(Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> publicFooterTimeline(Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> userTimeline(User user, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> userHeaderTimeline(User user, Weibo item,
                                                   int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> userFooterTimeline(User user, Weibo item,
                                                   int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> friendsTimeline(int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> friendsHeaderTimeline(Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> friendsFooterTimeline(Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> mentions(int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> mentionsHeader(Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> mentionsFooter(Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> search(String key, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> searchHeader(String key, Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> searchFooter(String key, Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<Comment> commentTimeline(int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<Comment> commentHeaderTimeline(Comment item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<Comment> commentFooterTimeline(Comment item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> commentMyTimeline(int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> commentMyHeaderTimeline(Comment item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> commentMyFooterTimeline(Comment item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> commentReceiveMyTimeline(int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> commentReceiveMyHeaderTimeline(Comment item,
                                                               int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> commentReceiveMyFooterTimeline(Comment item,
                                                               int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> commentForWeiboTimeline(Weibo item, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> commentForWeiboHeaderTimeline(Weibo item,
                                                              Comment comment, int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> commentForWeiboFooterTimeline(Weibo item,
                                                              Comment comment, int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> following(User user, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> followingHeader(User user, Follow firstUser,
                                                int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> followingFooter(User user, Follow lastUser,
                                                int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> followers(User user, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> followersHeader(User user, Follow firstUser,
                                                int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> followersFooter(User user, Follow lastUser,
                                                int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> followEach(User user, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> followEachHeader(User user, Follow firstUser,
                                                 int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> followEachFooter(User user, Follow lastUser,
                                                 int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> searchUser(String user, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> searchHeaderUser(String user, User firstUser,
                                                 int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> searchFooterUser(String user, User lastUser,
                                                 int count) throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public int update(Weibo weibo) throws ApiException, VerifyErrorException,
            UpdateException;

    public boolean upload(Weibo weibo, File file) throws ApiException,
            VerifyErrorException, UpdateException;

    // 转发微博
    public boolean repost(Weibo weibo, boolean comment) throws ApiException,
            VerifyErrorException, UpdateException, DataInvalidException;

    // 评论一条微博
    public boolean comment(Comment comment) throws ApiException,
            VerifyErrorException, UpdateException, DataInvalidException;

    public boolean destroyComment(Comment coment) throws ApiException,
            VerifyErrorException, DataInvalidException;

    public boolean destroyWeibo(Weibo weibo) throws ApiException,
            VerifyErrorException, DataInvalidException;

    public int unRead() throws ApiException, VerifyErrorException,
            DataInvalidException;
}
