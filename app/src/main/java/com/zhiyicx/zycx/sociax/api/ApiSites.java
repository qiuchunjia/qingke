package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.ApproveSite;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

public interface ApiSites {
    public static final String MOD_NAME = "Sitelist";
    public static final String GET_SITE_LIST = "getSiteList";
    public static final String GET_SITE_STATUS = "getSiteStatus";

    public ListData<SociaxItem> getSisteList() throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> newSisteList(int count) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public ListData<SociaxItem> getSisteListHeader(ApproveSite as, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public ListData<SociaxItem> getSisteListFooter(ApproveSite as, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;

    public boolean getSiteStatus(ApproveSite as) throws ApiException,
            ListAreEmptyException, DataInvalidException, VerifyErrorException;

    public boolean isSupport() throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    public boolean isSupportReg() throws ApiException, ListAreEmptyException,
            DataInvalidException, VerifyErrorException;

    ListData<SociaxItem> searchSisteList(String key, int count)
            throws ApiException, ListAreEmptyException, DataInvalidException,
            VerifyErrorException;
}
