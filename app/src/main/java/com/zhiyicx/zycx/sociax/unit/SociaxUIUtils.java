package com.zhiyicx.zycx.sociax.unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhiyicx.zycx.sociax.component.TSFaceView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 用于SociaxUI的工具集合类
 *
 * @author Povol
 */
public class SociaxUIUtils {

    /**
     * 隐藏输入法
     *
     * @param paramContext
     * @param paramEditText
     */
    public static void hideSoftKeyboard(Context paramContext,
                                        EditText paramEditText) {
        ((InputMethodManager) paramContext.getSystemService("input_method"))
                .hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     *
     * @param paramContext
     * @param paramEditText
     */
    public static void showSoftKeyborad(Context paramContext,
                                        EditText paramEditText) {
        ((InputMethodManager) paramContext.getSystemService("input_method"))
                .showSoftInput(paramEditText, 0);
    }

    /**
     * 检测用户输入的Email格式
     *
     * @param mail
     * @return
     */
    public static boolean checkEmail(String mail) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mail);
        return m.find();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void setInputLimit(TextView tv, EditText et) {
        WordCount wordCount = new WordCount(et, tv);
        tv.setText(wordCount.getMaxCount() + "");
        et.addTextChangedListener(wordCount);
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 判断网络连接
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void highlightContent(Context paramContext,
                                        Spannable paramSpannable) {
        try {
            Matcher localMatcher = Pattern.compile("\\[(\\S+?)\\]").matcher(
                    paramSpannable);
            while (true) {
                if (!localMatcher.find())
                    return;
                int i = localMatcher.start();
                int j = localMatcher.end();
                String str = localMatcher.group(1);
                Integer localInteger = TSFaceView.facesKeyString.get(str);
                if ((localInteger.intValue() <= 0) || (localInteger == null))
                    continue;
                paramSpannable.setSpan(
                        new ImageSpan(paramContext, localInteger.intValue()),
                        i, j, 33);
            }
        } catch (Exception e) {
            Log.d("TSUtils", e.toString());
        }
    }

    public static String getFromString(int from) {
        String fromString = "来自网站";
        switch (from) {
            case 0:
                fromString = "来自网站";
                break;
            case 1:
                fromString = "来自手机网页版";
                break;
            case 2:
                fromString = "来自Android客户端";
                break;
            case 3:
                fromString = "来自iPhone客户端";
                break;
            case 4:
                fromString = "来自iPad客户端";
                break;
            case 5:
                fromString = "来自Windows.Phone客户端";
                break;
        }
        return fromString;
    }

    /**
     * 过滤 bom
     *
     * @param in
     * @return
     */
    public static String JSONFilterBom(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }

    /**
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     *
     * @param str
     * @return String
     */
    public static String filterHtml(String str) {
        String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    @SuppressLint("NewApi")
    public static void initTraffic(Context context) {
        if (Build.VERSION.SDK_INT < 8) {
            return;
        }
        PackageManager packageManager = context.getPackageManager();
        long receive = 0;
        long send = 0;
        try {
            ApplicationInfo applicationInfo = packageManager
                    .getApplicationInfo("com.zhiyicx.zycx.sociax.android",
                            ApplicationInfo.FLAG_SYSTEM);
            int uid = applicationInfo.uid;
            receive = TrafficStats.getUidRxBytes(uid);
            send = TrafficStats.getUidTxBytes(uid);
            // PrefsManager.putLong(context,
            // context.getString(R.string.traffic_send), send);
            // PrefsManager.putLong(context,
            // context.getString(R.string.traffic_receive), receive);
            System.err.println("receive " + receive);
            System.err.println("send " + send);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        Anim.in(context);
    }

    public static void startActivity(Activity context, Class<?> cls, Bundle data) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(data);
        //intent.putExtra("local", (String[])data);
        context.startActivity(intent);
        Anim.in(context);
    }

}
