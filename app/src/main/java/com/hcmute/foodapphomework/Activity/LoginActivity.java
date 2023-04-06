package com.hcmute.foodapphomework.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hcmute.foodapphomework.Api.AuthApiService;
import com.hcmute.foodapphomework.Api.RetrofitClient;
import com.hcmute.foodapphomework.Model.User;
import com.hcmute.foodapphomework.R;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    ImageView btnLogin;
    AuthApiService authApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, RequestBody> body = new HashMap<String, RequestBody>();
                MediaType mediaType = MediaType.parse("text/plain");
                body.put("username", RequestBody.create(mediaType, edtEmail.getText().toString()));
                body.put("password", RequestBody.create(mediaType, edtPassword.getText().toString()));

                authApiService.login(body, "login").enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonObject result = res.getAsJsonObject("user");

                        if(result != null) {
                            Toast.makeText(LoginActivity.this, "Login ok", Toast.LENGTH_SHORT).show();

                            String fname = result.get("fname").getAsString();
                            String images = result.get("images").getAsString();
                            int id = result.get("id").getAsInt();
                            String username = result.get("username").getAsString();
                            String email = result.get("email").getAsString();
                            String gender = result.get("gender").getAsString();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("fname", fname);
                            intent.putExtra("images", images);
                            intent.putExtra("id", result.get("id").getAsString());
                            intent.putExtra("username", result.get("username").getAsString());
                            intent.putExtra("email", result.get("email").getAsString());
                            intent.putExtra("gender", result.get("gender").getAsString());
                            User user = new User(id, username, fname, email, gender, images);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("nva", t.getMessage());
                    }
                });


            }
        });
    }

    private void init() {
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (ImageView) findViewById(R.id.btn_login);

        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
    }
}