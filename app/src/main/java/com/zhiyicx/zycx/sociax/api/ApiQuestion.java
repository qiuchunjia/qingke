package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.Question;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

public interface ApiQuestion {

    static final String MOD_NAME = "Support";
    // 获取热门问题列表
    public static final String GET_HOT_QUESTION = "get_hot_question";
    // 搜索问题
    public static final String SEARCH_QUESTION = "search_question";
    // 获取分类
    public static final String GET_CATEGORY = "get_category";
    // 搜索分类
    public static final String SEARCH_CATEGORY = "search_category";
    // 根据分类获取问题
    public static final String GET_QUESTION_BY_CATEGORY = "get_question_by_category";
    // 根据分类获取问题
    public static final String SHOW_QUESTION = "show";
    // 根据问题的评论
    public static final String GET_COMMENTS = "get_comments";

    /**
     * 获取热门问题列表
     *
     * @return
     * @throws ApiException
     */
    ListData<SociaxItem> getHotQuestion() throws ApiException;

    /**
     * 根据关键字获取问题
     *
     * @param key
     * @return
     * @throws ApiException
     */
    ListData<SociaxItem> searchQuestion(String key) throws ApiException;

    /**
     * 获取所有分类包含子分类
     *
     * @return
     * @throws ApiException
     */
    ListData<SociaxItem> getCategory() throws ApiException;

    /**
     * 根据关键字获取分类
     *
     * @param key
     * @return
     * @throws ApiException
     */
    ListData<SociaxItem> searchCategory(String key) throws ApiException;

    /**
     * 根据分类获取问题
     *
     * @param key
     * @return
     * @throws ApiException
     */
    ListData<SociaxItem> getQuestionByCate(int cateId) throws ApiException;

    Question questionShow(int quesId) throws ApiException;
}
