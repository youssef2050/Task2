package com.menu.task4;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import java.util.List;
import java.util.Random;

public class AppsService extends Service {
    private final IBinder binder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    public AppsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        AppsService getService() {
            // Return this instance of LocalService so clients can call public methods
            return AppsService.this;
        }
    }

    UsageStatsManager usageStatsManager;

    public int getRandomNumber(int number) {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
            if (runningTaskInfos != null && !runningTaskInfos.isEmpty()) {
                System.out.println(runningTaskInfos.get(0).topActivity.getPackageName());

            } else {
                long endTime = System.currentTimeMillis();
                long beginTime = 10000;
                String result = "";
                UsageEvents.Event event = new UsageEvents.Event();
                UsageEvents usageEvents = usageStatsManager.queryEvents(beginTime, endTime);
                while (usageEvents.hasNextEvent()) {
                    usageEvents.getNextEvent(event);
                    if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND && !event.getPackageName().equals(getPackageName())) {
                        System.out.println("package : " + event.getPackageName());
                        return number++;
                    }
                }
            }
        }
        return 0;
    }

    public String getForegroundApp(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
        String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();
        PackageManager pm = context.getPackageManager();
        PackageInfo foregroundAppPackageInfo = null;
        try {
            foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String foregroundApp = null;
        if (foregroundAppPackageInfo != null)
            foregroundApp = foregroundAppPackageInfo.applicationInfo.packageName;
        return foregroundApp;
    }
}