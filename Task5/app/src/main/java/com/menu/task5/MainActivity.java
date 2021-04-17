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
        listView = findViewById(R.id.posts);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
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
            assert data != null;
            Post post = (Post) data.getExtras().getSerializable(AddPost.POST_RESULT);
            postAdapter.addPost(post);
        }
    }

}