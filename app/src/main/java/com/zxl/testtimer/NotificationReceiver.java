package com.zxl.testtimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @Description:
 * @Author: zxl
 * @Date: 21/9/16 PM3:07.
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utils.getInstance().isTopActivity(context)) {
            Log.e("NotificationReceiver", "onReceive" + "----isTopActivity");
        } else {
            Log.e("NotificationReceiver", "onReceive");
            Utils.getInstance().showNotication(context);
        }
        Utils.getInstance().addLocalNotication(context);
    }


}
