package com.menu.task4;


import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class AppsService extends Service {
    public static final String TAG = "AppsService";
    private Context context = null;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;
    List<String> stalkList;

    @Override
    public void onCreate() {
        super.onCreate();
        stalkList = new ArrayList<>();
        context = getApplicationContext();
        sharedPreference = getSharedPreferences(context.getString(R.string.preference_file_key), MODE_PRIVATE); /* @prmay*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                final List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
                for (int i = 0; i < services.size(); i++) {
                    if (!stalkList.contains(services.get(i).baseActivity.getPackageName())) {
                        stalkList.add(services.get(i).baseActivity.getPackageName());
                    }
                }

                List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
                for (int i = 0; i < procInfos.size(); i++) {

                    ArrayList<String> runningPkgs = new ArrayList<String>(Arrays.asList(procInfos.get(i).pkgList));

                    Collection diff = subtractSets(runningPkgs, stalkList);

                    if (diff != null) {
                        stalkList.removeAll(diff);
                    }
                }


            }
        }, 20000, 6000);  // every 6 seconds


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private ActivityManager.RunningAppProcessInfo getForegroundApp() {
        ActivityManager.RunningAppProcessInfo result = null, info = null;

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> l = activityManager.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
        while (i.hasNext()) {
            info = i.next();
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && !isRunningService(info.processName)) {
                result = info;
                break;
            }
        }
        return result;
    }

    private boolean isRunningService(String processName) {
        if (processName == null)
            return false;

        ActivityManager.RunningServiceInfo service;

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> l = activityManager.getRunningServices(9999);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            service = i.next();
            if (service.process.equals(processName))
                return true;
        }
        return false;
    }

    private boolean isRunningApp(String processName) {
        if (processName == null)
            return false;

        ActivityManager.RunningAppProcessInfo app;

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> l = activityManager.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
        while (i.hasNext()) {
            app = i.next();
            if (app.processName.equals(processName) && app.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE)
                return true;
        }
        return false;
    }


    private boolean checkifThisIsActive(ActivityManager.RunningAppProcessInfo target) {
        boolean result = false;
        ActivityManager.RunningTaskInfo info;

        if (target == null)
            return false;

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> l = activityManager.getRunningTasks(9999);
        Iterator<ActivityManager.RunningTaskInfo> i = l.iterator();

        while (i.hasNext()) {
            info = i.next();
            if (info.baseActivity.getPackageName().equals(target.processName)) {
                result = true;
                break;
            }
        }

        return result;
    }


    // what is in b that is not in a ?
    public static Collection subtractSets(Collection a, Collection b) {
        Collection result = new ArrayList(b);
        result.removeAll(a);
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        int numbers = sharedPreference.getInt(context.getString(R.string.number_run_other_apps), 0);
        editor = sharedPreference.edit();
        editor.putInt(context.getString(R.string.number_run_other_apps), numbers + stalkList.size());
        editor.apply();
        editor.commit();
    }
}