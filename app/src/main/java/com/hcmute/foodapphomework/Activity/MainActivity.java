package com.hcmute.foodapphomework.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hcmute.foodapphomework.Adapter.CategoryAdapter;
import com.hcmute.foodapphomework.Adapter.LastProductAdapter;
import com.hcmute.foodapphomework.Api.MainApiService;
import com.hcmute.foodapphomework.Api.RetrofitClient;
import com.hcmute.foodapphomework.DetailsProductActivity;
import com.hcmute.foodapphomework.Domain.CategoryDomain;
import com.hcmute.foodapphomework.Domain.LastProduct;
import com.hcmute.foodapphomework.Listener.OnCategoryClickListener;
import com.hcmute.foodapphomework.Listener.OnProductClickListener;
import com.hcmute.foodapphomework.Model.User;
import com.hcmute.foodapphomework.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnCategoryClickListener, OnProductClickListener {
    TextView tvName;
    ImageView ivAvatar;
    RecyclerView rcvCategory;
    RecyclerView rcvProduct;
    FloatingActionButton btnCart;
    private LastProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    MainApiService mainApiService;
    ArrayList<CategoryDomain> categoriesList = new ArrayList<>();
    ArrayList<LastProduct> productsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
        init();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        tvName.setText("Hi " + intent.getStringExtra("fname"));
        Glide.with(this)
//                .load(intent.getStringExtra("images"))
                .load(sharedPreferences.getString("images", ""))
                .into(ivAvatar);



        loadCategories();
        loadProducts();

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProfile  = new Intent(MainActivity.this, ProfileActivity.class);
                intentProfile.putExtra("user", user);
                startActivity(intentProfile);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
        Glide.with(this)
                .load(sharedPreferences.getString("images", ""))
                .into(ivAvatar);
    }
    private void loadProducts() {
        mainApiService = RetrofitClient.getRetrofit().create(MainApiService.class);
        mainApiService.getLastProducts().enqueue(new Callback<ArrayList<LastProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<LastProduct>> call, Response<ArrayList<LastProduct>> response) {
                productsList = response.body();
                setProductsRecyclerView(productsList);
            }

            @Override
            public void onFailure(Call<ArrayList<LastProduct>> call, Throwable t) {

            }
        });
    }
    private void  setProductsRecyclerView(ArrayList<LastProduct> foodList) {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productAdapter = new LastProductAdapter(foodList);
        productAdapter.setLOnProductClickListener(MainActivity.this);
        rcvProduct.setAdapter(productAdapter);
    }
    private void loadCategories() {
        mainApiService = RetrofitClient.getRetrofit().create(MainApiService.class);
        mainApiService.getCatgories().enqueue(new Callback<ArrayList<CategoryDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDomain>> call, Response<ArrayList<CategoryDomain>> response) {
                categoriesList = response.body();

                setCategoryRecyclerView(categoriesList);
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDomain>> call, Throwable t) {

            }
        });
    }
    private void setCategoryRecyclerView(ArrayList<CategoryDomain> category1) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(category1);
        categoryAdapter.setOnCategoryClickListener(MainActivity.this);
        rcvCategory.setAdapter(categoryAdapter);
    }


    private void init() {
        rcvCategory = findViewById(R.id.rcv_category);
        rcvProduct= findViewById(R.id.rcv_product);
        tvName = findViewById(R.id.tv_name_main);
        ivAvatar = findViewById(R.id.iv_avatar_main);
        btnCart = findViewById(R.id.btn_car);

    }

    @Override
    public void onCategoryClick(String categoryId, String name) {
        Intent intent = new Intent(MainActivity.this, ProductByCategoryActivity.class);
        intent.putExtra("idcategory", categoryId);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void onProductClick(String id) {
        Intent intent = new Intent(MainActivity.this, DetailsProductActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}