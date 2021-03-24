package com.menu.task4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
        stopService(new Intent(this, AppsService.class));
        numberRunOtherAppsInt = sharedPref.getInt(getString(R.string.number_run_other_apps), 0);
        numberRunOtherApps.setText(numberRunOtherAppsInt + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, AppsService.class));
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