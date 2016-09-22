package com.zxl.testtimer;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;

/**
 * @Description:
 * @Author: zxl
 * @Date: 21/9/16 PM2:49.
 */
public class Utils {
    private static volatile Utils mInstance = null;
    //    public static final int alarm_time = 2 * 24 * 60 * 60;
    public static final int alarm_time = 5;

    private Utils() {
    }

    public static Utils getInstance() {
        Utils instance = mInstance;
        if (instance == null) {
            synchronized (Utils.class) {
                instance = mInstance;
                if (instance == null) {
                    instance = new Utils();
                    mInstance = instance;
                }
            }
        }
        return instance;
    }

    public void showNotication(Context mContext) {
        //1首先是拿到NotificationManager这个管理类实例，
        NotificationManager mNotifiManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(mContext, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder1 = new Notification.Builder(mContext);
        builder1.setSmallIcon(R.mipmap.ic_launcher); //设置图标
        builder1.setTicker("2048");
        builder1.setContentTitle("2048"); //设置标题
        builder1.setContentText(mContext.getString(R.string.notificationTips)); //消息内容
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(true);//打开程序后图标消失
        builder1.setContentIntent(pendingIntent);
        Notification notification1 = builder1.getNotification();
        mNotifiManager.notify(124, notification1); // 通过通知管理器发送通知
    }

    /**
     * time：倒时时（秒）
     */
    public void addLocalNotication(Context context) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND, alarm_time);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setClass(context, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    }

    protected boolean isTopActivity(Context activity) {
        String packageName = activity.getPackageName();
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            //应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
