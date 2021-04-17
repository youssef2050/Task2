package com.menu.task5;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddPost extends AppCompatActivity implements ItemListDialogFragment.click {
    public static final String POST_RESULT = "post";
    private EditText name, date, body, followers, following, posts;
    private Button save;
    private ImageView userImage;
    private FloatingActionButton addImage;
    private int image;
    private ItemListDialogFragment itemListDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        body = findViewById(R.id.body);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        posts = findViewById(R.id.post_numbers);
        save = findViewById(R.id.save);
        userImage = findViewById(R.id.user_img);
        addImage = findViewById(R.id.add_img);
        save.setOnClickListener(v -> {
            if (isOk()) {
                Post post = new Post(System.currentTimeMillis(),
                        name.getText().toString(),
                        body.getText().toString(),
                        date.getText().toString(),
                        Long.parseLong(followers.getText().toString()),
                        Long.parseLong(following.getText().toString()),
                        Long.parseLong(posts.getText().toString()),
                        getImage());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(POST_RESULT, post);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        addImage.setOnClickListener(v -> {
            itemListDialogFragment = ItemListDialogFragment.newInstance();
            itemListDialogFragment.show(getSupportFragmentManager(),
                    "itemListDialogFragment");
        });
    }

    private boolean isOk() {
        return name != null && !TextUtils.isEmpty(name.getText().toString()) &&
                date != null && !TextUtils.isEmpty(date.getText().toString()) &&
                body != null && !TextUtils.isEmpty(body.getText().toString()) &&
                following != null && !TextUtils.isEmpty(following.getText().toString()) &&
                followers != null && !TextUtils.isEmpty(followers.getText().toString()) &&
                posts != null && !TextUtils.isEmpty(posts.getText().toString());
    }

    private int getImage() {
        return image;
    }

    @Override
    public void onClick(int image) {
        userImage.setImageDrawable(getResources().getDrawable(image));
        this.image = image;
        itemListDialogFragment.dismiss();

    }
}