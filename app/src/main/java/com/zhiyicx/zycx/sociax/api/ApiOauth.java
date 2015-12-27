package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.UserDataInvalidException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.User;

public interface ApiOauth {
    public static final String MOD_NAME = "Oauth";
    public static final String REQUEST_ENCRYP = "request_key";
    public static final String AUTHORIZE = "authorize";
    public static final String REGISTER = "Register";


    public User authorize(String uname, String password) throws ApiException,
            UserDataInvalidException, VerifyErrorException;

    public Api.Status requestEncrypKey() throws ApiException;

    public int register(Object data) throws ApiException;
}
