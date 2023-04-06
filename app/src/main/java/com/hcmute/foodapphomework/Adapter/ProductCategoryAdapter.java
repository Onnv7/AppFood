package com.hcmute.foodapphomework.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.foodapphomework.Domain.LastProduct;
import com.hcmute.foodapphomework.R;

import java.util.ArrayList;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {
    ArrayList<LastProduct> productList = new ArrayList<>();
    @NonNull
    @Override
    public ProductCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_by_category, parent, false);
        return new ViewHolder(view);
    }

    public ProductCategoryAdapter(ArrayList<LastProduct> productList) {
        this.productList = productList;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoryAdapter.ViewHolder holder, int position) {
        LastProduct product = productList.get(position);

        holder.tvName.setText(product.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(product.getStrMealThumb())
                .into(holder.ivFoodPic);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivFoodPic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_pc);
            ivFoodPic = itemView.findViewById(R.id.iv_food_pic_pc);
        }
    }
}
