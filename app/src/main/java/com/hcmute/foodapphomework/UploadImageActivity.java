package com.hcmute.foodapphomework;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.foodapphomework.Api.RetrofitClient;
import com.hcmute.foodapphomework.Api.UploadApiService;
import com.hcmute.foodapphomework.Utils.RealPathUtil;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    ImageView ivAvatar;
    TextView btnChoose, btnUpload;
    UploadApiService uploadApiService;
    Uri avatarUri;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if(data == null) {
                    return;
                }
                Uri uri = data.getData();
                avatarUri = uri;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    ivAvatar.setImageBitmap(bitmap);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        uploadApiService = RetrofitClient.getRetrofit().create(UploadApiService.class);

        init();
        Intent intent = getIntent();
        String images = intent.getStringExtra("images");
        String id = intent.getStringExtra("id");

        Glide.with(this)
                .load(images)
                .into(ivAvatar);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, RequestBody> body = new HashMap<>();
                MultipartBody.Part filePart = null;
                if(avatarUri != null) {
                    String realPath  = RealPathUtil.getRealPath(UploadImageActivity.this, avatarUri);
                    File file = new File(realPath);
                    Log.d("nva", "onClick: " + realPath);
                    String extension = MimeTypeMap.getFileExtensionFromUrl(realPath);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    RequestBody requestBody = RequestBody.create(MediaType.parse(mimeType), file);
                    filePart = MultipartBody.Part.createFormData("images", file.getName(), requestBody);
                    avatarUri = null;
                }
                MediaType mediaType = MediaType.parse("text/plain");

                body.put("id", RequestBody.create(mediaType, id));

                uploadApiService.uploadImage(body, filePart).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("nva", response.toString());
                        if(response.isSuccessful()) {
                            Toast.makeText(UploadImageActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            JsonObject res = response.body();
                            JsonArray result = res.getAsJsonArray("result");
                            JsonObject an = result.get(0).getAsJsonObject();
                            String images = an.get("images").getAsString();
                            intent.putExtra("images", images);
                            setResult(RESULT_OK, intent);
//                            JsonObject res = response.body();
//                            JsonObject result = res.getAsJsonObject("result");
//                            String newImages = result.get("images").getAsString();
//                            Glide.with(UploadImageActivity.this)
//                                    .load(newImages)
//                                    .into(ivAvatar);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("nva", t.getMessage());

                    }
                });
            }
        });
    }
    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
        else {
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openGallery();
            }
        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }
    private void init() {
        ivAvatar = findViewById(R.id.iv_avatar_upload);
        btnUpload = findViewById(R.id.btn_upload_image);
        btnChoose = findViewById(R.id.btn_choose_file);
    }
}