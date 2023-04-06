package com.hcmute.foodapphomework.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hcmute.foodapphomework.R;

public class IntroActivity extends AppCompatActivity {
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        init();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });
    }

    private void init() {
        btnStart = (Button) findViewById(R.id.btn_start);
    }
}