package qcjlibrary.broadcast;

import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.content.Intent;

import qcjlibrary.broadcast.base.BaseBroadCast;
import qcjlibrary.util.ToastUtils;

public class AlarmBroadCastReciever extends BaseBroadCast {


    @Override
    public void onReceive(Context context, Intent intent) {
        String mAction = intent.getAction();
        //开机广播
        if (mAction.equals(Intent.ACTION_BOOT_COMPLETED)) {
        }
        if (mAction.equals("alarm.alert.short")) {
            Log.d("Cathy", "short alert: 吃药");
            //发送通知
            sendNotification(context);
            //播放短提示音,由于service只能开启一次，所有需要先stop
            context.stopService(new Intent("alert_music"));
            context.startService(new Intent("alert_music"));
        }
    }

    private void sendNotification(Context context) {
        //推送？
    }

}
