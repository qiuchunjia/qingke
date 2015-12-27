package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public interface ApiFavorites {
    public static final String MOD_NAME = "WeiboStatuses";
    public static final String INDEX = "favorite_feed";
    public static final String CREATE = "favorite_create";
    public static final String IS_FAVORITE = "isFavorite";
    public static final String DESTROY = "favorite_destroy";

    ListData<SociaxItem> index(int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    ListData<SociaxItem> indexHeader(Weibo weibo, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    ListData<SociaxItem> indexFooter(Weibo weibo, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    boolean create(Weibo weibo) throws ApiException, DataInvalidException,
            VerifyErrorException;

    boolean isFavorite(Weibo weibo) throws ApiException, DataInvalidException,
            VerifyErrorException;

    boolean destroy(Weibo weibo) throws ApiException, DataInvalidException,
            VerifyErrorException;
}
