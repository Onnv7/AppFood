package com.hcmute.foodapphomework.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hcmute.foodapphomework.Model.User;
import com.hcmute.foodapphomework.R;
import com.hcmute.foodapphomework.UploadImageActivity;

public class ProfileActivity extends AppCompatActivity {
    TextView tvId, tvUsername, tvFname, tvEmail, tvGender;
    ImageView ivAvatar;


    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            SharedPreferences sharedPreferences = ProfileActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
            Toast.makeText(ProfileActivity.this, "lmao", Toast.LENGTH_SHORT).show();
            if(result != null && result.getResultCode() == RESULT_OK) {
                Toast.makeText(ProfileActivity.this, result.getData().toString(), Toast.LENGTH_SHORT).show();
                if(result.getData() != null) {
                    Glide.with(ProfileActivity.this)
                            .load(result.getData().getStringExtra("images"))
                            .into(ivAvatar);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("images", result.getData().getStringExtra("images"));
                    editor.apply();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
//        Log.d("nva", "pro" + user.toString() + "---" + user.getId());
        tvId.setText(user.getId() + "");
        tvUsername.setText(user.getUsername());
        tvFname.setText(user.getFname());
        tvEmail.setText(user.getEmail());
        tvGender.setText(user.getGender());

        Glide.with(this)
                .load(user.getImages())
                .into(ivAvatar);

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpload = new Intent(ProfileActivity.this, UploadImageActivity.class);
                intentUpload.putExtra("images", user.getImages());
                intentUpload.putExtra("id", user.getId() + "");
//                startActivity(intentUpload);
                resultLauncher.launch(intentUpload);
            }
        });
    }



    private void init() {
        tvId = findViewById(R.id.tv_id_profile);
        tvUsername = findViewById(R.id.tv_username_profile);
        tvFname = findViewById(R.id.tv_name_profile);
        tvEmail = findViewById(R.id.tv_email_profile);
        tvGender = findViewById(R.id.tv_gender_profile);
        ivAvatar = findViewById(R.id.iv_avatar_profile);
    }
}