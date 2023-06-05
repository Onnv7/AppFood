package com.hcmute.foodapphomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hcmute.foodapphomework.Api.MainApiService;
import com.hcmute.foodapphomework.Api.RetrofitClient;
import com.hcmute.foodapphomework.Database.FoodDatabase;
import com.hcmute.foodapphomework.Domain.LastProduct;
import com.hcmute.foodapphomework.Entity.Food;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsProductActivity extends AppCompatActivity {
    TextView tvName, tvPrice, tvDes, numberOrderTxt;
    ImageView ivProduct;
    TextView addToCartBtn;
    ImageView plusBtn, minusBtn, picFood;
    private LastProduct object;
    MainApiService mainApiService;
    int numberOrder = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        init();
        Food food = new Food();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        mainApiService = RetrofitClient.getRetrofit().create(MainApiService.class);
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody productId = RequestBody.create(mediaType, id);
//        Log.d("nva", "adu" + productId.toString() + "-" + id);
        numberOrderTxt.setText(String.valueOf(numberOrder));
        System.out.println(id + " NE1  ");
        mainApiService.getDetailsProduct(productId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
//                Log.d("nva", response.toString());
                if(res.get("success").getAsBoolean() == true) {
                    JsonArray result = res.getAsJsonArray("result");
                    Log.d("nva", result.get(0).toString());
                    JsonObject detail = result.get(0).getAsJsonObject();
                    tvName.setText(detail.get("meal").getAsString());
                    tvPrice.setText(detail.get("price").getAsString());
                    tvDes.setText(detail.get("instructions").getAsString());

                    Glide.with(DetailsProductActivity.this)
                            .load(detail.get("strmealthumb").getAsString())
                            .into(ivProduct);

                    food.setId(id);
                    food.setName(detail.get("meal").getAsString());
                    food.setPrice(detail.get("price").getAsString());
                    food.setImages(detail.get("strmealthumb").getAsString());
                    food.setQuantity(numberOrder);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder++;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                food.setQuantity(numberOrder);
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder--;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                food.setQuantity(numberOrder);
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsProductActivity.this, "ok" + food.getQuantity(), Toast.LENGTH_SHORT).show();
                add(food);
//                addFoodToCart(id, detail.get("meal").getAsString(), detail.get("price").getAsFloat(), detail.get("strmealthumb").getAsString(), numberOrder );

//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//                        FoodDatabase db = FoodDatabase.getInstance(DetailsProductActivity.this);
//                        db.foodDao().insertFood(food);
////                        food = new Food(id);
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        super.onPostExecute(aVoid);
//                        Toast.makeText(DetailsProductActivity.this, "Da them vao cart", Toast.LENGTH_SHORT).show();
//                    }
//                }.execute();
            }
        });
    }
    private void add(Food food) {
        FoodDatabase db = FoodDatabase.getInstance(DetailsProductActivity.this);
        List<Food> foods = db.foodDao().checkExisted(food.getId());
        if(foods.size() > 0)
        {
            int oldQuantity = foods.get(0).getQuantity();
            food.setQuantity(oldQuantity + food.getQuantity());
            db.foodDao().updateFood(food);
            food.setQuantity(numberOrder);
            return;
        }
        db.foodDao().insertFood(food);
    }
    private void addFoodToCart(String id, String name, float price, String images, int quantity) {
//        Food food = new Food(id, name, price, images, quantity);
//        FoodDatabase db = FoodDatabase.getInstance(DetailsProductActivity.this);
//        db.foodDao().insertFood(food);
    }
    private void init() {
        tvName = (TextView) findViewById(R.id.tv_name_details);
        tvPrice = (TextView) findViewById(R.id.tv_price_details);
        tvDes = (TextView) findViewById(R.id.tv_des_details);
        ivProduct = (ImageView) findViewById(R.id.iv_food_details);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        addToCartBtn = findViewById(R.id.addToCartBtn);
//        picFood = findViewById(R.id.iv_food_details);
    }
}