package com.menu.task5;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddPost extends AppCompatActivity {

    public static final String POST_RESULT = "post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Post post = new Post(10, "ddvdv", "", "", 1021, 105, 1524, R.drawable.ic_user);
        Intent intent = new Intent();
        intent.putExtra(POST_RESULT, post);
        setResult(RESULT_OK);
        finish();
    }
}