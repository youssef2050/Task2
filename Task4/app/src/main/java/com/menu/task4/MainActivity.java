package com.menu.task4;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView numberStartApp, numberOnBackPress, numberRunOtherApps;
    private SharedPreferences sharedPref;
    private int numberStartAppInt;
    private int numberOnBackPressInt;
    private int numberRunOtherAppsInt;
    private SharedPreferences.Editor editor;
    private AppsService mService;
    private boolean mBound = false;

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
            Toast.makeText(this, "مرحبًا بك في تطبيق Task 4 ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "أهلًا بعودتك", Toast.LENGTH_SHORT).show();
        }
        numberStartAppInt++;
        editor = sharedPref.edit();
        editor.putInt(getString(R.string.number_start_app), numberStartAppInt);
        editor.apply();
        numberStartApp.setText(numberStartAppInt + " ");
        numberOnBackPress.setText(numberOnBackPressInt + "");
        numberRunOtherApps.setText(numberRunOtherAppsInt + "");
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
        Intent intent = new Intent(this, AppsService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            numberRunOtherAppsInt = sharedPref.getInt(getString(R.string.number_run_other_apps), 0);
            String s = mService.getForegroundApp(this);
            if (!s.equals(getPackageName())) {
                numberRunOtherAppsInt++;
            }
            editor.putInt(getString(R.string.number_run_other_apps), numberRunOtherAppsInt);
            editor.apply();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        numberRunOtherAppsInt = sharedPref.getInt(getString(R.string.number_run_other_apps), 0);
        numberRunOtherApps.setText(numberRunOtherAppsInt + "");
        unbindService(connection);
        mBound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private final ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            AppsService.LocalBinder binder = (AppsService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}