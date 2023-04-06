package com.hcmute.foodapphomework.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hcmute.foodapphomework.Model.User;
import com.hcmute.foodapphomework.R;
import com.hcmute.foodapphomework.UploadImageActivity;

public class ProfileActivity extends AppCompatActivity {
    TextView tvId, tvUsername, tvFname, tvEmail, tvGender;
    ImageView ivAvatar;
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
                startActivity(intentUpload);
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