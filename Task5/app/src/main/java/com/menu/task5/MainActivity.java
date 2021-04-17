package com.menu.task5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REQUEST_CODE = 100;
    private PostAdapter postAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        listView = findViewById(R.id.posts);
        postAdapter = new PostAdapter(this);
        listView.setAdapter(postAdapter);
        fab.setOnClickListener(view -> {
            startActivityForResult(new Intent(getBaseContext(), AddPost.class), ADD_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE & resultCode == RESULT_OK) {
            Post post = data.getParcelableExtra(AddPost.POST_RESULT);
            postAdapter.addPost(post);
        }
    }

}