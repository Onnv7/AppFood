package com.hcmute.foodapphomework.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hcmute.foodapphomework.Adapter.CartListAdapter;
import com.hcmute.foodapphomework.Database.FoodDatabase;
import com.hcmute.foodapphomework.Entity.Food;
import com.hcmute.foodapphomework.Listener.ChangeNumberItemsListener;
import com.hcmute.foodapphomework.Listener.RemoveCartItem;
import com.hcmute.foodapphomework.R;

import java.util.ArrayList;
import java.util.List;

public class CartListActivity extends AppCompatActivity implements RemoveCartItem{
    RecyclerView rcvCart;
    CartListAdapter adapter;
    TextView tvTotal;
    ArrayList<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        init();

        foodList = getDataCart();
        System.out.println("Cart " + foodList.get(0));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvCart.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(foodList, CartListActivity.this);

        rcvCart.setAdapter(adapter);
        tvTotal.setText(getTotal(foodList) + "");


    }
    private ArrayList<Food> getDataCart() {
        FoodDatabase db = FoodDatabase.getInstance(CartListActivity.this);
        return (ArrayList<Food>) db.foodDao().getAllFoods();
    }
    private void init() {
        rcvCart = findViewById(R.id.rcv_food_cart);
        tvTotal = findViewById(R.id.totalTxt);
    }
    private float getTotal(ArrayList<Food> list) {
        float total = 0;
        for (Food food : list) {
            float price = Float.parseFloat(food.getPrice()) * food.getQuantity();
            total += price;
        }
        return total;
    }

    @Override
    public void removeItem(int index) {
        rcvCart.getAdapter().notifyDataSetChanged();
    }
}