package com.hcmute.foodapphomework.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.foodapphomework.Database.FoodDatabase;
import com.hcmute.foodapphomework.Entity.Food;
import com.hcmute.foodapphomework.Listener.ChangeNumberItemsListener;
import com.hcmute.foodapphomework.Listener.RemoveCartItem;
import com.hcmute.foodapphomework.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<Food> foodDomains;
    private RemoveCartItem removeCartItem;

    public CartListAdapter(ArrayList<Food> foodDomains, RemoveCartItem removeCartItem) {
        this.foodDomains = foodDomains;
        this.removeCartItem = removeCartItem;
//        this.managementCart = new ManagementCart(context);
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        Food food = foodDomains.get(position);
        holder.title.setText(foodDomains.get(position).getName());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(position).getPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foodDomains.get(position).getQuantity() * Float.parseFloat(foodDomains.get(position).getPrice())) * 100)/100));
        holder.num.setText(foodDomains.get(position).getQuantity() + "");
        Glide.with(holder.itemView.getContext())
                .load(foodDomains.get(position).getImages())
                .into(holder.pic);
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodDatabase db = FoodDatabase.getInstance(view.getContext());
                holder.num.setText(food.getQuantity() + 1 + "");
                food.setQuantity(food.getQuantity() + 1);
                db.foodDao().updateFood(food);
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.num.setText(foodDomains.get(position).getQuantity() - 1);
                FoodDatabase db = FoodDatabase.getInstance(view.getContext());
                int currentPosition = holder.getAdapterPosition();
                int quantity = food.getQuantity() - 1;
                if(quantity == 0)
                {
                    db.foodDao().deleteFood(food);
                    foodDomains.remove(currentPosition);
                    notifyDataSetChanged();
                    removeCartItem.removeItem(currentPosition);
                    return;
                }
                holder.num.setText(quantity + "");
                food.setQuantity(quantity);
                db.foodDao().updateFood(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(foodDomains != null)
            return foodDomains.size();
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_name_cart);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            num = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
