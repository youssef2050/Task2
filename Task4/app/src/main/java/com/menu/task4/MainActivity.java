package com.menu.task4;

import android.app.AppOpsManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView numberStartApp, numberOnBackPress, numberRunOtherApps;
    private SharedPreferences sharedPref;
    private int numberStartAppInt;
    private int numberOnBackPressInt;
    private int numberRunOtherAppsInt;
    private SharedPreferences.Editor editor;
    public static final int ID_JOB = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberStartApp = findViewById(R.id.tv_number_start_app);
        numberOnBackPress = findViewById(R.id.tv_number_on_back_press);
        numberRunOtherApps = findViewById(R.id.tv_number_run_other_apps);
        sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);
        numberStartAppInt = sharedPref.getInt(getString(R.string.number_start_app), 0);
        numberOnBackPressInt = sharedPref.getInt(getString(R.string.number_on_back_press), 0);
        numberRunOtherAppsInt = sharedPref.getInt(getString(R.string.number_run_other_apps), 0);

        if (numberStartAppInt == 0) {
            alertDialog("رسالة ترحيبية", "مرحبًا بك في تطبيق Task 4😍😍");
        } else {
            alertDialog("نرحب بك مرة اخرى 🤩", "أهلًا بعودتك 😊");
        }
        numberStartAppInt++;
        editor = sharedPref.edit();
        editor.putInt(getString(R.string.number_start_app), numberStartAppInt);
        editor.apply();
        numberStartApp.setText(numberStartAppInt + " ");
        numberOnBackPress.setText(numberOnBackPressInt + "");
        numberRunOtherApps.setText(numberRunOtherAppsInt + "");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if(!hasPermission()){
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        Constant.MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
            if (!hasPermission()) {
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        Constant.MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        numberOnBackPressInt++;
        editor.putInt(getString(R.string.number_on_back_press), numberOnBackPressInt);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        numberRunOtherAppsInt = sharedPref.getInt(getString(R.string.number_run_other_apps), 0);
        numberRunOtherApps.setText(numberRunOtherAppsInt + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onStop() {
        super.onStop();
        ComponentName componentName = new ComponentName(getBaseContext(), AppsService.class);
        JobInfo jobInfo;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(ID_JOB, componentName)
                    .setPeriodic(200)
                    .build();
        } else {
            jobInfo = new JobInfo.Builder(ID_JOB, componentName)
                    .setMinimumLatency(200)
                    .build();
        }
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void alertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

}