package com.menu.task4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "run background", Toast.LENGTH_SHORT).show();
    }
}