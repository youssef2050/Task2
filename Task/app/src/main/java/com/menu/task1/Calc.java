package com.menu.task1;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class Calc extends AppCompatActivity {
    private GridLayout OP_Adv;
    private int i = 0;
    private ImageView show;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        OP_Adv = findViewById(R.id.op_adv);
        OP_Adv.setVisibility(View.GONE);
        show = findViewById(R.id.imageView9);
        show.setImageDrawable(getDrawable(R.drawable.ic_up));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void show(View view) {
        if (i == 1) {
            OP_Adv.setVisibility(View.GONE);
            show.setImageDrawable(getDrawable(R.drawable.ic_down));
            i = 0;
        } else {
            OP_Adv.setVisibility(View.VISIBLE);
            show.setImageDrawable(getDrawable(R.drawable.ic_up));
            i = 1;
        }
    }
}