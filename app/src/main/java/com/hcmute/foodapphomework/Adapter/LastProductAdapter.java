package com.hcmute.foodapphomework.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.foodapphomework.Domain.LastProduct;
import com.hcmute.foodapphomework.Listener.OnCategoryClickListener;
import com.hcmute.foodapphomework.Listener.OnProductClickListener;
import com.hcmute.foodapphomework.R;

import java.util.ArrayList;

public class LastProductAdapter extends RecyclerView.Adapter<LastProductAdapter.ViewHolder> {
    ArrayList<LastProduct> productList = new ArrayList<>();
    private OnProductClickListener listener;

    public void setLOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    public LastProductAdapter(ArrayList<LastProduct> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LastProduct product = productList.get(position);

        holder.tvName.setText(product.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(product.getStrMealThumb())
                .into(holder.ivFoodPic);

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Cliedc", Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onProductClick(product.getIdMeal());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivFoodPic;
        ConstraintLayout layoutMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivFoodPic = itemView.findViewById(R.id.iv_food_pic);
            layoutMain = itemView.findViewById(R.id.layout_last_product);
        }
    }
}
