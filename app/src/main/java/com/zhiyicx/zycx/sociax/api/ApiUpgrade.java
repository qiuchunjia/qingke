package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.modle.VersionInfo;

public interface ApiUpgrade {
    public static final String MOD_NAME = "Upgrade";
    public static final String GET_VERSION = "getVersion";

    /**
     * 获取版本信息
     *
     * @return
     * @throws ApiException
     */
    public VersionInfo getVersion() throws ApiException;
}