package com.menu.task5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ItemListDialogFragment extends BottomSheetDialogFragment {
    private click click;

    public static ItemListDialogFragment newInstance() {
        return new ItemListDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof click) {
            click = (ItemListDialogFragment.click) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.img_1);
        images.add(R.drawable.img_2);
        images.add(R.drawable.img_3);
        images.add(R.drawable.img_5);
        images.add(R.drawable.img_6);
        images.add(R.drawable.img_7);
        images.add(R.drawable.img_8);
        ItemAdapter itemAdapter = new ItemAdapter(images);
        recyclerView.setAdapter(itemAdapter);
    }


    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        private List<Integer> images;

        ItemAdapter(List<Integer> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.image.setImageDrawable(getContext().getResources().getDrawable(images.get(position)));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            final ImageView image;

            ViewHolder(LayoutInflater inflater, ViewGroup parent) {
                // TODO: Customize the item layout
                super(inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false));
                image = itemView.findViewById(R.id.images);
                image.setOnClickListener(v -> {
                    click.onClick(images.get(getAdapterPosition()));
                });
            }
        }
    }

    public interface click {
        void onClick(int image);
    }

}