package com.menu.task5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends BaseAdapter {
    private List<Post> posts;
    private Context context;

    public PostAdapter(Context context) {
        this.posts = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return posts.get(position).getId();
    }

    public void addPost(Post post) {
        posts.add(post);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        }
        //TODO inflate for items in post_item
        TextView postFollowing = view.findViewById(R.id.post_following);
        TextView postFollowers = view.findViewById(R.id.post_followers);
        TextView posts = view.findViewById(R.id.post_num);
        TextView postName = view.findViewById(R.id.post_name);
        TextView postBody = view.findViewById(R.id.post_body);
        TextView postDate = view.findViewById(R.id.post_date);
        ImageView postImage = view.findViewById(R.id.post_img);

        //TODO get Post and create result in items
        Post post = getItem(position);
        postFollowing.setText(post.getFollowing() + "");
        postFollowers.setText(post.getFollowers() + "");
        posts.setText(post.getPosts() + "");
        postName.setText(post.getName());
        postBody.setText(post.getBody());
        postDate.setText(post.getDate());
        postImage.setImageDrawable(context.getResources().getDrawable(post.getImage()));

        //TODO return View object
        return view;
    }
}
