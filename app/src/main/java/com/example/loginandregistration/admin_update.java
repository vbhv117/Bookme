package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class admin_update extends AppCompatActivity {

    Spinner sp;
    EditText E1,E2,E3,E4;
    Button B;
    DBhelper db;
    ArrayList<String> namesH = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update);

        sp=findViewById(R.id.spinner6);
        B=findViewById(R.id.button17);
        E1 = findViewById(R.id.editTextTextPersonName4);
        E2 = findViewById(R.id.editTextNumber6);
        E3 = findViewById(R.id.editTextNumber7);
        E4 = findViewById(R.id.editTextTextPersonName5);
        db= new DBhelper(this);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH);


        String email =  getIntent().getStringExtra("Email");




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
                String Em = String.valueOf(sp.getSelectedItem());
                String S1 = E1.getText().toString();
                String S2 = E2.getText().toString();
                String S3 = E3.getText().toString();
                String S4 = E4.getText().toString();
                db.updatehotel(S1,S2,S3,S4,Em);
                Toast.makeText(admin_update.this, "Hotel Details Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }
}