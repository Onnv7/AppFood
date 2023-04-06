package com.hcmute.foodapphomework.Api;

import com.google.gson.JsonObject;
import com.hcmute.foodapphomework.Domain.CategoryDomain;
import com.hcmute.foodapphomework.Domain.LastProduct;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MainApiService {
    @GET("appfoods/categories.php")
    Call<ArrayList<CategoryDomain>> getCatgories();

    @GET("appfoods/lastproduct.php")
    Call<ArrayList<LastProduct>> getLastProducts();

    @Multipart

    @POST("appfoods/getcategory.php")
    Call<ArrayList<LastProduct>> getProductByCgrId(@Part("idcategory") RequestBody id);


    @Multipart
    @POST("appfoods/newmealdetail.php?")
    Call<JsonObject> getDetailsProduct(@Part("id") RequestBody id);
}
