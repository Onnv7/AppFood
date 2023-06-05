package com.hcmute.foodapphomework.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.foodapphomework.Adapter.LastProductAdapter;
import com.hcmute.foodapphomework.Adapter.ProductCategoryAdapter;
import com.hcmute.foodapphomework.Api.MainApiService;
import com.hcmute.foodapphomework.Api.RetrofitClient;
import com.hcmute.foodapphomework.DetailsProductActivity;
import com.hcmute.foodapphomework.Domain.CategoryDomain;
import com.hcmute.foodapphomework.Domain.LastProduct;
import com.hcmute.foodapphomework.Listener.OnCategoryClickListener;
import com.hcmute.foodapphomework.Listener.OnProductClickListener;
import com.hcmute.foodapphomework.R;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductByCategoryActivity extends AppCompatActivity implements OnProductClickListener {
    TextView tv_name_cgr;
    RecyclerView rcvProduct;
    private ProductCategoryAdapter productAdapter;
    ArrayList<LastProduct> productsList = new ArrayList<>();
    MainApiService mainApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);

        init();
        loadProducts();

    }

    private void loadProducts() {
        Intent intent = getIntent();
        String idcategory = intent.getStringExtra("idcategory");
        String name = intent.getStringExtra("name");
        tv_name_cgr.setText(name);
        Toast.makeText(this, idcategory, Toast.LENGTH_SHORT).show();
        mainApiService = RetrofitClient.getRetrofit().create(MainApiService.class);
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody id = RequestBody.create(mediaType, idcategory);
        mainApiService.getProductByCgrId(id).enqueue(new Callback<ArrayList<LastProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<LastProduct>> call, Response<ArrayList<LastProduct>> response) {
                productsList = response.body();
                Log.d("nva", "adsd" + response.toString());
                setProductsRecyclerView(productsList);
            }

            @Override
            public void onFailure(Call<ArrayList<LastProduct>> call, Throwable t) {
                Log.d("nva", t.getMessage());
            }
        });
    }
    private void  setProductsRecyclerView(ArrayList<LastProduct> foodList) {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductCategoryAdapter(foodList);
        productAdapter.setOnProductClickListener(ProductByCategoryActivity.this);
        rcvProduct.setAdapter(productAdapter);
    }
    private void init() {
        rcvProduct = findViewById(R.id.rcv_product_cgr);
        tv_name_cgr = findViewById(R.id.tv_cgr_pc);
    }

    @Override
    public void onProductClick(String id) {
        Intent intent = new Intent(ProductByCategoryActivity.this, DetailsProductActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}