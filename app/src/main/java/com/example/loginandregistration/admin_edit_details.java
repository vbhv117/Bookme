package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_edit_details extends AppCompatActivity {

    EditText E1,E2,E3;
    Button B;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_details);

        E1 = findViewById(R.id.editTextTextPersonName6);
        E2 = findViewById(R.id.editTextTextPersonName7);
        E3 = findViewById(R.id.editTextTextPersonName8);
        B = findViewById(R.id.button19);
        db= new DBhelper(this);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = E1.getText().toString().trim();
                String pass1 = E2.getText().toString().trim();
                String pass2 = E3.getText().toString().trim();
                String mail = getIntent().getStringExtra("Email");

                if(TextUtils.isEmpty(E1.getText().toString()) || TextUtils.isEmpty(E2.getText().toString()) || TextUtils.isEmpty(E3.getText().toString())) {
                    Toast.makeText(admin_edit_details.this, "Empty field found", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pass1.equals(pass2)) {
                        db.updateadmin(name, pass1, mail);
                        Toast.makeText(admin_edit_details.this, "UPDATED", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(admin_edit_details.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}