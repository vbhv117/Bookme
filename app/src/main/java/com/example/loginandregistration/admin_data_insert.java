package com.example.loginandregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_data_insert extends AppCompatActivity {

    Button b1,b2;
    EditText E1,E2,E3,E4;
    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_insert);
        b1=findViewById(R.id.button4);
        b2 = findViewById(R.id.button6);
        E1=findViewById(R.id.editTextTextPersonName3);
        E2=findViewById(R.id.autoCompleteTextView);
        E3=findViewById(R.id.editTextNumber);
        E4=findViewById(R.id.editTextTextPersonName2);
        db= new DBhelper(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = E1.getText().toString();
                String noroom = E2.getText().toString();
                int norromint = Integer.parseInt(noroom);
                String rate = E3.getText().toString();
                //int rateint = Integer.parseInt(rate);
                String place = E4.getText().toString();
                String email =  getIntent().getStringExtra("Email");
                Boolean insert = db.addhotel(name, norromint, rate, place, email);

                if(insert == true)
                    Toast.makeText(admin_data_insert.this, "Hotel added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(admin_data_insert.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =  getIntent().getStringExtra("Email");
                Cursor res = db.getdatatwo(email);
                if(res.getCount()==0){
                    Toast.makeText(admin_data_insert.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("No: of Room :"+res.getString(1)+"\n");
                    buffer.append("Rate of Room :"+res.getString(2)+"\n");
                    buffer.append("Place :"+res.getString(3)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(admin_data_insert.this);
                builder.setCancelable(true);
                builder.setTitle("Hotel Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });

    }
}