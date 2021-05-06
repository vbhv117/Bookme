package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class admin_signup extends AppCompatActivity {

    Button b1;
    EditText E1,E2,E3,E4;
    DBhelper db;
    TextView T1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        b1=findViewById(R.id.button15);
        E1=findViewById(R.id.editTextTextPersonName);
        E2=findViewById(R.id.editTextTextEmailAddress3);
        E3=findViewById(R.id.editTextTextPassword2);
        E4=findViewById(R.id.editTextTextPassword3);
        T1 = findViewById(R.id.textView12);
        db= new DBhelper(this);

        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_signup.this,admin_login.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(E1.getText().toString()) || TextUtils.isEmpty(E2.getText().toString()) || TextUtils.isEmpty(E3.getText().toString()) || TextUtils.isEmpty(E4.getText().toString())) {
                    Toast.makeText(admin_signup.this, "Empty field found", Toast.LENGTH_SHORT).show();
                }
                else {
                    String s1 = E1.getText().toString();
                    String s2 = E2.getText().toString();
                    String s3 = E3.getText().toString();
                    String s4 = E4.getText().toString();
                    if(s3.equals(s4)){
                        Boolean insert = db.addadmin(s2, s1, s3);
                        if (insert == true) {
                            Toast.makeText(admin_signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(admin_signup.this,admin_home.class);
                            intent.putExtra("Email",s2);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(admin_signup.this, "Account already exists, Please Login", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(admin_signup.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}