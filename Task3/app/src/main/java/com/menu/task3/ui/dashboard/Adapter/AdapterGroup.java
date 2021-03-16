package com.menu.task3.ui.dashboard.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.menu.task3.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterGroup extends RecyclerView.Adapter<AdapterGroup.MyHolder> {
    private List<Group> data;
    private OnClickItem onClickItem;

    public AdapterGroup(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.id.setText(data.get(position).getId());
        holder.name.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getDescription());
        Drawable placeholder = holder.imageGroup.getContext().getResources().getDrawable(data.get(position).getImage());
        holder.imageGroup.setImageDrawable(placeholder);
    }

    @Override
    public int getItemCount() {
        if (data == null)
            data = new ArrayList<>();
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView id, name, description;
        private CardView item;
        private CircleImageView imageGroup;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            item = itemView.findViewById(R.id.item);
            imageGroup = itemView.findViewById(R.id.imageGroup);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem.onClick(data.get(getAdapterPosition()));
                }
            });
        }
    }

    public void setData(List<Group> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        notifyDataSetChanged();
    }

    public interface OnClickItem {
        void onClick(Group group);
    }
}
