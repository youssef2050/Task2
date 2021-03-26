package com.menu.task4;


import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.menu.task4.Public.paks;
import static com.menu.task4.Public.search;
import static com.menu.task4.Public.searchPackages;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AppsService extends JobService {
    private static final String TAG = "AppsService";
    private Context context;
    private boolean jobCancelled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(() -> {
            Log.d(TAG, "Job doBackgroundWork: ");
            String pak = getUsageStatsForegroundActivityName();
            if (pak != null) {
                if (!search(pak) && !searchPackages(pak))
                    paks.add(pak);
            }
            if (jobCancelled) {
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jobFinished(params, false);
        }).start();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private String getUsageStatsForegroundActivityName() {
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 1000 * 60;
        String topActivity = null;
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime);
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