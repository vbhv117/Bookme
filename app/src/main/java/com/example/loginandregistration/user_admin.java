package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class user_admin extends AppCompatActivity {
    Button b1,b2;
    ImageView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        Animation animation= AnimationUtils.loadAnimation(user_admin.this,R.anim.bounce);
        b1=findViewById(R.id.button);
        v=findViewById(R.id.imageView10);
        b2=findViewById(R.id.button5);
        v.startAnimation(animation);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToLogin = new Intent(user_admin.this, MainActivity.class);
                startActivity(moveToLogin);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToadd = new Intent(user_admin.this, admin_login.class);
                startActivity(moveToadd);
            }
        });
    }
}