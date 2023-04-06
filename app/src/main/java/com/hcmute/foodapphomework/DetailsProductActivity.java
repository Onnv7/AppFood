package com.hcmute.foodapphomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hcmute.foodapphomework.Api.MainApiService;
import com.hcmute.foodapphomework.Api.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsProductActivity extends AppCompatActivity {
    TextView tvName, tvPrice, tvDes;
    ImageView ivProduct;
    MainApiService mainApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        init();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        mainApiService = RetrofitClient.getRetrofit().create(MainApiService.class);
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody productId = RequestBody.create(mediaType, id);
        Log.d("nva", "adu" + productId.toString() + "-" + id);
        mainApiService.getDetailsProduct(productId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                Log.d("nva", response.toString());
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
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void init() {
        tvName = (TextView) findViewById(R.id.tv_name_details);
        tvPrice = (TextView) findViewById(R.id.tv_price_details);
        tvDes = (TextView) findViewById(R.id.tv_des_details);
        ivProduct = (ImageView) findViewById(R.id.iv_food_details);
    }
}