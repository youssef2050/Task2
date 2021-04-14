package com.menu.task5;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class AddPost extends AppCompatActivity {

    public static final String POST_RESULT = "post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Post post = null;
        Intent intent = new Intent();
        intent.putExtra(POST_RESULT,  post);
        setResult(RESULT_OK);
        finish();
    }
}