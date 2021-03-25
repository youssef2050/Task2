package com.menu.task4;


import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.menu.task4.Public.paks;
import static com.menu.task4.Public.search;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AppsService extends JobService {
    public static final String TAG = "AppsService";
    private Context context = null;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharedPreference = getSharedPreferences(context.getString(R.string.preference_file_key), MODE_PRIVATE); /* @prmay*/
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onStartJob(JobParameters params) {

        String pak = getUsageStatsForegroundActivityName();
        System.out.println(pak);
        if (pak != null) {
            if (!search(pak))
                paks.add(pak);
            System.out.println(paks);
        }
        jobFinished(params, true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        int numbers = sharedPreference.getInt(context.getString(R.string.number_run_other_apps), 0);
        editor = sharedPreference.edit();
        editor.putInt(context.getString(R.string.number_run_other_apps), numbers + paks.size());
        editor.apply();
        editor.commit();
        return true;
    }

    private String ApplicationInForeground(final Context context) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            final ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return topActivity.getPackageName();
            }
        }
        return null;
    }
    public  String ApplicationInForeground() {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
        for (final ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (final String activeProcess : processInfo.pkgList) {
                    if (!activeProcess.equals(context.getPackageName())) {
                        return activeProcess;
                    }
                }
            }
        }
        return null;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private String getUsageStatsForegroundActivityName() {


        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 1000 * 60;

        // result
        String topActivity = null;

        // We get usage stats for the last minute
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime);

        // Sort the stats by the last time used
        if (stats != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : stats) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                topActivity = mySortedMap.get(mySortedMap.lastKey()).getPackageName();

            }
        }
        if (topActivity != null)
            return topActivity;
        else
            return Constant.ACTIVITY_NOT_FOUND;

    }
}