package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

public interface ApiDocument {

    static final String MOD_NAME = "Document";
    // 获取文档分类列表
    public static final String CATEGORY_LIST = "getDocumentCategoryList";
    // 获取所有文档列表
    public static final String ALL_DOCUMENTLIST = "getAllDocumentList";
    // 获取我的文档人列表
    public static final String MY_DOCUMEN_LIST = "getMyDocumenList";

    ListData<SociaxItem> getDocumentCategoryList() throws ApiException;

    ListData<SociaxItem> getDocumentList() throws ApiException;

}
