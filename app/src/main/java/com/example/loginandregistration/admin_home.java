package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class admin_home extends AppCompatActivity {

    ImageView V1,V2,V3,V4,V5;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        V1=findViewById(R.id.imageView13);
        V2=findViewById(R.id.imageView15);
        V3=findViewById(R.id.imageView14);
        V4=findViewById(R.id.imageView17);
        V5=findViewById(R.id.imageView20);
        email =  getIntent().getStringExtra("Email");
        V1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,admin_data_insert.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });
        V2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,delete_admin_entry.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });
        V3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,admin_update.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });

        V4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,book_for_adimin.class);
                intent.putExtra("Email",email);
                startActivity(intent);

            }
        });

        V5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,admin_edit_details.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });



    }
}