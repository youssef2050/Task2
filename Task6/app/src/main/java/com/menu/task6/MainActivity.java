package com.menu.task6;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button red, green, blue;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);
        constraintLayout = findViewById(R.id.background);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_red) {
            red.setVisibility(View.VISIBLE);
            green.setVisibility(View.GONE);
            blue.setVisibility(View.GONE);
            constraintLayout.setBackgroundColor(Color.RED);
        }
        if (id == R.id.menu_green) {
            red.setVisibility(View.GONE);
            green.setVisibility(View.VISIBLE);
            blue.setVisibility(View.GONE);
            constraintLayout.setBackgroundColor(Color.GREEN);
        }
        if (id == R.id.menu_blue) {
            red.setVisibility(View.GONE);
            green.setVisibility(View.GONE);
            blue.setVisibility(View.VISIBLE);
            constraintLayout.setBackgroundColor(Color.BLUE);
        }
        if (id == R.id.menu_all) {
            red.setVisibility(View.VISIBLE);
            green.setVisibility(View.VISIBLE);
            blue.setVisibility(View.VISIBLE);
            constraintLayout.setBackgroundColor(0);
        }
        return super.onOptionsItemSelected(item);
    }
}