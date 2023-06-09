package com.hcmute.foodapphomework.Api;

import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface AuthApiService {
        @Multipart
        @POST("/appfoods/registrationapi.php")
        Call<JsonObject> login(@PartMap HashMap<String, RequestBody> body, @Query("apicall") String apicall);
}
