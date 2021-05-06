package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class delete_admin_entry extends AppCompatActivity {

    Spinner sp;
    Button B;
    DBhelper db;
    ArrayList<String> namesH = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_admin_entry);
        String email =  getIntent().getStringExtra("Email");
        sp=findViewById(R.id.spinner5);
        B=findViewById(R.id.button16);
        db= new DBhelper(this);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH);
        namesH.clear();
        Cursor res = db.getadminhotel(email);
        while (res.moveToNext()){
            String S = res.getString(0);
            namesH.add(S);
        }
        sp.setAdapter(adapter);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String S = String.valueOf(sp.getSelectedItem());
                db.delhotel(S);
                Toast.makeText(delete_admin_entry.this, "Hotel Removed", Toast.LENGTH_SHORT).show();
            }
        });



    }
}