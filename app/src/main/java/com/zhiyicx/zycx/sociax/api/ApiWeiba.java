package com.zhiyicx.zycx.sociax.api;

import java.util.List;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.modle.CommentPost;
import com.zhiyicx.zycx.sociax.modle.Posts;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weiba;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date Nov 19, 2012
 */
public interface ApiWeiba {

    public static String MOD_NAME = "Weiba"; // MOD 名称
    // act 名称
    public static String GET_WEIBAS = "get_weibas"; // 获取微吧列表
    public static String CREATE = "create"; // 关注微吧
    public static String DESTROY = "destroy"; // 取消关注微吧
    public static String CREATE_POST = "create_post"; // 发布帖子
    public static String GET_POSTS = "get_posts"; // 获取帖子列表
    public static String POST_DETAIL = "post_detail"; // 获取贴子详细信息
    public static String POST_FAVORITE = "post_favorite"; // 收藏
    public static String POST_UNFAVORITE = "post_unfavorite"; // 取消收藏
    public static String COMMENT_LIST = "comment_list";
    public static String COMMENT_POST = "comment_post"; // 评论帖子
    public static String REPLY_COMMENT = "reply_comment"; // 回复评论
    public static String DELETE_COMMENT = "delete_comment"; // 删除评论
    public static String FOLLOWING_POSTS = "following_posts"; // 我关注的帖子
    public static String FAVORITE_POSTS = "favorite_list"; // 我收藏的帖子
    public static String POSTEDS = "posteds"; // 我发布的帖子
    public static String COMMENTEDS = "commenteds"; // 我评论的帖子
    public static String SEARCH_WEIBA = "search_weiba"; // 搜索微吧
    public static String SEARCH_POST = "search_post"; // 搜索帖子

    // 获取微吧列表
    public List<SociaxItem> getWeibas() throws ApiException;

    public List<SociaxItem> getWeibasHeader(Weiba weiba, int page, int count)
            throws ApiException;

    public List<SociaxItem> getWeibasFooter(Weiba weiba, int page, int count)
            throws ApiException;

    public boolean create(int weibaId) throws ApiException;

    public boolean destroy(int weibaId) throws ApiException;

    // 发布帖子
    public boolean cretePost(Posts posts) throws ApiException;

    // 获取帖子列表
    public List<SociaxItem> getPosts(int weibaId) throws ApiException;

    public List<SociaxItem> getPostsHeader(int weibaId, int page, int count)
            throws ApiException;

    public List<SociaxItem> getPostsFooter(int weibaId, int page, int count)
            throws ApiException;

    public Posts postDetail(int postsId) throws ApiException;

    // 获取评论列表
    public List<SociaxItem> getCommentList(int postId) throws ApiException;

    public List<SociaxItem> getCommentListHeader(int postId, int page, int count)
            throws ApiException;

    public List<SociaxItem> getCommentListFooter(int postId, int page, int count)
            throws ApiException;

    public boolean commentPost(CommentPost cPost) throws ApiException;

    public boolean favoritePost(int postId) throws ApiException;

    public boolean unfavoritePost(int postId) throws ApiException;

    public boolean replyComment(CommentPost cPost) throws ApiException;

    public boolean deleteComment() throws ApiException;

    // 我关注的
    public List<SociaxItem> followingPosts() throws ApiException;

    public List<SociaxItem> followingPostsHeader(int page, int count)
            throws ApiException;

    public List<SociaxItem> followingPostsFooter(int page, int count)
            throws ApiException;

    // 我发布的
    public List<SociaxItem> posteds(int uid) throws ApiException;

    public List<SociaxItem> postedsHeader(int page, int count)
            throws ApiException;

    public List<SociaxItem> postedsFooter(int page, int count)
            throws ApiException;

    // 我评论的
    public List<SociaxItem> commenteds(int uid) throws ApiException;

    public List<SociaxItem> commentedsHeader(int page, int count)
            throws ApiException;

    public List<SociaxItem> commentedsFooter(int page, int count)
            throws ApiException;

    // 我收藏的
    public List<SociaxItem> favoritePostsList(int uid) throws ApiException;

    public List<SociaxItem> favoritePostsListHeader(int page, int count)
            throws ApiException;

    public List<SociaxItem> favoritePostsListFooter(int page, int count)
            throws ApiException;

    public List<SociaxItem> searchWeiba(String key) throws ApiException;

    public List<SociaxItem> searchWeibaHeader(String key, int page, int count)
            throws ApiException;

    public List<SociaxItem> searchWeibaFooter(String key, int page, int count)
            throws ApiException;

    public List<SociaxItem> searchPost(String key) throws ApiException;

    public List<SociaxItem> searchPostHeader(String key, int page, int count)
            throws ApiException;

    public List<SociaxItem> searchPostFooter(String key, int page, int count)
            throws ApiException;
}
