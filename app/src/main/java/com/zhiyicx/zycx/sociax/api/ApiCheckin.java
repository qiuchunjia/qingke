package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date 2013-2-6
 */
public interface ApiCheckin {

    public static final String MOD_NAME = "Checkin";

    public static final String CHECKIN = "checkin";

    public static final String GET_CHECK_INFO = "get_check_info";

    public Object checkIn() throws ApiException;

    public Object getCheckInfo() throws ApiException;

}
