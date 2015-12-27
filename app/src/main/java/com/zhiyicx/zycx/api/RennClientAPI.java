package com.zhiyicx.zycx.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.renn.rennsdk.AccessToken;
import com.renn.rennsdk.RennExecutor;
import com.renn.rennsdk.RennService;
import com.renn.rennsdk.oauth.RenRenOAuth;
import com.renn.rennsdk.oauth.ValueStorage;

public class RennClientAPI {
    private String apiKey;
    private String secretKey;
    private String scope;
    private String tokenType;
    private AccessToken accessToken;
    private String mUid;
    private static RennClientAPI mInstance;
    private ValueStorage valueStorage;
    private RenRenOAuth mRenRenOAuth;
    public static final String ACCESSTOKEN = "rr_renn_accessToken";
    public static final String REFRESHTOKEN = "rr_renn_refreshToken";
    public static final String TOKENTYPE = "rr_renn_tokenType";
    public static final String MACKEY = "rr_renn_macKey";
    public static final String MACALGORITHM = "rr_renn_macAlgorithm";
    public static final String ACCESSSCOPE = "rr_renn_accessScope";
    public static final String EXPIRESIN = "rr_renn_expiresIn";
    public static final String REQUESTTIME = "rr_renn_requestTime";
    public static final String UID = "rr_renn_uid";

    public RennClientAPI(Context context) {
        this.mRenRenOAuth = RenRenOAuth.getInstance(context);
        this.valueStorage = ValueStorage.getInstance(context);
        if (isLogin()) {
            this.accessToken = new AccessToken();
            this.accessToken.type = this.valueStorage.getType("rr_renn_tokenType");
            this.accessToken.accessToken = this.valueStorage.getString("rr_renn_accessToken");
            this.accessToken.refreshToken = this.valueStorage.getString("rr_renn_refreshToken");
            this.accessToken.macKey = this.valueStorage.getString("rr_renn_macKey");
            this.accessToken.macAlgorithm = this.valueStorage.getString("rr_renn_macAlgorithm");
            this.accessToken.accessScope = this.valueStorage.getString("rr_renn_accessScope");
            this.accessToken.expiresIn = this.valueStorage.getLong("rr_renn_expiresIn").longValue();
            this.accessToken.requestTime = this.valueStorage.getLong("rr_renn_requestTime").longValue();
            this.mUid = this.valueStorage.getString("rr_renn_uid");
        }
    }

    public void init(String appId, String apiKey, String secretKey) {
        if ((appId == null) || (apiKey == null) || (secretKey == null)) {
            throw new IllegalArgumentException("arguments in setClientInfo can not be NULL");
        }
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public static synchronized RennClientAPI getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RennClientAPI(context);
        }
        return mInstance;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mRenRenOAuth != null) {
            return this.mRenRenOAuth.onActivityResult(requestCode, resultCode, data);
        }
        return false;
    }

    public void setLoginListener(com.renn.rennsdk.RennClient.LoginListener loginListener) {
        if (this.mRenRenOAuth != null)
            this.mRenRenOAuth.setLoginListener((com.renn.rennsdk.RennClient.LoginListener) loginListener);
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return this.scope;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public Long getUid() {
        try {
            return Long.valueOf(Long.parseLong(this.mUid));
        } catch (Exception e) {
        }
        return null;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void login(Activity activity) {
        if (this.mRenRenOAuth != null) {
            this.mRenRenOAuth.apiKey = this.apiKey;
            this.mRenRenOAuth.secretKey = this.secretKey;
            this.mRenRenOAuth.scope = this.scope;
            this.mRenRenOAuth.tokenType = this.tokenType;
            this.mRenRenOAuth.login(activity);
        }
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(this.valueStorage.getString("rr_renn_accessToken"));
    }

    public void logout() {
        this.valueStorage.remove("rr_renn_accessToken");
        this.valueStorage.remove("rr_renn_tokenType");
        this.valueStorage.remove("rr_renn_macKey");
        this.valueStorage.remove("rr_renn_macAlgorithm");
        this.valueStorage.remove("rr_renn_accessScope");
        this.valueStorage.remove("rr_renn_expiresIn");
        this.valueStorage.remove("rr_renn_requestTime");
        this.valueStorage.remove("rr_renn_uid");
        this.accessToken = null;
    }

    public boolean isAuthorizeExpired() {
        if (this.accessToken != null) {
            if (this.accessToken.type == AccessToken.Type.Bearer) {
                long expireTime = this.accessToken.requestTime + this.accessToken.expiresIn * 1000L;
                long currentTime = System.currentTimeMillis();
                return currentTime > expireTime;
            }
            return false;
        }

        return true;
    }

    public boolean isAuthorizeValid() {
        return (isLogin()) && (!isAuthorizeExpired());
    }

    public RennService getRennService() {
        RennExecutor executor = new RennExecutor();
        return new RennService(executor, this.accessToken);
    }

    public static abstract interface LoginListener {
        public abstract void onLoginSuccess();

        public abstract void onLoginCanceled();
    }
}
