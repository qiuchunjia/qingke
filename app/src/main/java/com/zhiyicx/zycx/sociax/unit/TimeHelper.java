package com.zhiyicx.zycx.sociax.unit;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zhiyicx.zycx.sociax.exception.TimeIsOutFriendly;

public class TimeHelper {

    public static String friendlyTime(String timestamp) {
        try {
            return friendlyTime(Integer.valueOf(timestamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (TimeIsOutFriendly e) {
            e.printStackTrace();
        }
        return "刚刚";

    }

    public static String friendlyTime(int timestamp) throws TimeIsOutFriendly {

        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数

        long toZero = currentSeconds / (24 * 60 * 60) * (24 * 60 * 60);
        long todayGap = currentSeconds - toZero;

        String timeStr = null;
        if (timeGap > 24 * 60 * 60 || timeGap > todayGap) {// 1天以上
            // timeStr = timeGap/(24*60*60)+"天前";
            timeStr = getStandardTimeWithDate(timestamp);
        } else if (timeGap > 60 * 60 && timeGap < todayGap) {// 1小时-24小时
            timeStr = "今天  " + getStandardTimeWithHour(timestamp);
        } else if (timeGap > 60 && timeGap < 3600) {// 1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else if (timeGap > 0 && timeGap < 60) {// 1秒钟-59秒钟
            timeStr = "刚刚";
        } else {
            throw new TimeIsOutFriendly();
        }
        return timeStr;
    }

    public static String friendlyTimeFromeStringTime(String timeTemp) throws Exception {

        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - getTimeInt(timeTemp);// 与现在时间相差秒数

        long toZero = currentSeconds / (24 * 60 * 60) * (24 * 60 * 60);
        long todayGap = currentSeconds - toZero;

        String timeStr = null;
        if (timeGap > 24 * 60 * 60 || timeGap > todayGap) {// 1天以上
            // timeStr = timeGap/(24*60*60)+"天前";
            timeStr = getStandardTimeWithDate(getTimeInt(timeTemp));
        } else if (timeGap > 60 * 60 && timeGap < todayGap) {// 1小时-24小时
            timeStr = "今天  " + getStandardTimeWithHour(getTimeInt(timeTemp));
        } else if (timeGap > 60 && timeGap < 3600) {// 1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else if (timeGap > 0 && timeGap < 60) {// 1秒钟-59秒钟
            timeStr = "刚刚";
        } else {
            throw new TimeIsOutFriendly();
        }
        return timeStr;
    }

    public static String getStandardTimeWithYeay(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timestamp * 1000);
        return sdf.format(date);
    }

    public static String getStandardTimeWithDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(timestamp * 1000);
        return sdf.format(date);
    }

    public static String getStandardTimeWithHour(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(timestamp * 1000);
        return sdf.format(date);
    }

    public static long getTimeInt(String timeTemp) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = sdf.parse(timeTemp);
        return date.getTime() / 1000;

    }
}
