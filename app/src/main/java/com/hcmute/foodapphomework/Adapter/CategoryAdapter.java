package com.hcmute.foodapphomework.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.foodapphomework.Domain.CategoryDomain;
import com.hcmute.foodapphomework.Listener.OnCategoryClickListener;
import com.hcmute.foodapphomework.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> categoriesList;
    private OnCategoryClickListener listener;
    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(ArrayList<CategoryDomain> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDomain cgrItem = categoriesList.get(position);
        holder.tvCategoryName.setText(cgrItem.getName());
        Glide.with(holder.itemView.getContext())
                .load(cgrItem.getImages())
                .into(holder.ivCategoryPic);

        holder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCategoryClick(cgrItem.getId(), cgrItem.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoryPic;
        TextView tvCategoryName;
        ConstraintLayout layoutCategory;
        int id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryPic = itemView.findViewById(R.id.iv_cgr_pic);
            tvCategoryName = itemView.findViewById(R.id.tv_cgr_name);
            layoutCategory = itemView.findViewById(R.id.layout_category);
        }
    }
}
