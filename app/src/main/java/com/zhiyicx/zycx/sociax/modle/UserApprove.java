package com.zhiyicx.zycx.sociax.modle;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date Jan 17, 2013
 */
public class UserApprove extends SociaxItem {

    public static enum ApproveTpye {
        个人认证, 公司认证;
    }

    private boolean isPerApp; // 是否个人认证
    private boolean isComApp; // 是否公司认证
    private boolean isTS; // 达人

    public UserApprove() {
    }

    public UserApprove(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("user_group").equals("[]"))
                return;
            JSONObject approveDate = jsonObject.getJSONObject("user_group");
            for (Iterator<?> iterator = approveDate.keys(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                JSONObject temp = approveDate.getJSONObject(key);
                String appInfo = temp.getString("user_group_name");
                if (appInfo.equals("个人认证")) {
                    setPerApp(true);
                } else if (appInfo.equals("公司认证")) {
                    setComApp(true);
                } else if (appInfo.equals("达人用户")) {
                    setTS(true);
                }
            }
        } catch (Exception e) {
            try {
                JSONArray approveDate = jsonObject.getJSONArray("user_group");
                for (int i = 0; i < approveDate.length(); i++) {
                    JSONObject temp = (JSONObject) approveDate.get(i);
                    String appInfo = temp.getString("user_group_name");
                    if (appInfo.equals("个人认证")) {
                        setPerApp(true);
                    } else if (appInfo.equals("公司认证")) {
                        setComApp(true);
                    } else if (appInfo.equals("达人用户")) {
                        setTS(true);
                    }
                }
            } catch (Exception e2) {
                Log.e(this.getClass().toString(), e.toString());
            }
        }

        // for (int i = 0; i < approveDate.length(); i++) {
        // JSONObject temp = (JSONObject) approveDate.get(i);
        // String appInfo = temp.getString("user_group_name");
        // if (appInfo.equals("个人认证")) {
        // setPerApp(true);
        // }else if (appInfo.equals("公司认证")) {
        // setComApp(true);
        // }
        // }
    }

    public boolean isPerApp() {
        return isPerApp;
    }

    public void setPerApp(boolean isPerApp) {
        this.isPerApp = isPerApp;
    }

    public boolean isComApp() {
        return isComApp;
    }

    public void setComApp(boolean isComApp) {
        this.isComApp = isComApp;
    }

    public boolean isTS() {
        return isTS;
    }

    public void setTS(boolean isTS) {
        this.isTS = isTS;
    }

    @Override
    public boolean checkValid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getUserface() {
        // TODO Auto-generated method stub
        return null;
    }

}
