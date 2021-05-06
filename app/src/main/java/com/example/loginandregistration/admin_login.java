package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class admin_login extends AppCompatActivity {

    TextView T1;
    EditText E1,E2;
    Button B;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        T1 = findViewById(R.id.textView10);
        E1=findViewById(R.id.editTextTextEmailAddress2);
        E2=findViewById(R.id.editTextTextPassword);
        B=findViewById(R.id.button14);
        db= new DBhelper(this);
        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_login.this,admin_signup.class);
                startActivity(intent);
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String E = E1.getText().toString();
                String P = E2.getText().toString();
                boolean N = db.checkadmin(E,P);
                if(N == true){
                    Intent intent = new Intent(admin_login.this,admin_home.class);
                    intent.putExtra("Email",E);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(admin_login.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}