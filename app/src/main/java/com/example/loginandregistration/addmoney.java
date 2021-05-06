package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class addmoney extends AppCompatActivity {

    Button b1,b2;
    Spinner sp;
    EditText T;
    TextView V;
    String emailS;
    int balance=0;
    DBhelper db;
    ArrayList<String> namesH = new ArrayList<String>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmoney);
        b2=findViewById(R.id.button11);
        b1=findViewById(R.id.button10);
        V=findViewById(R.id.textView6);
        T=findViewById(R.id.editTextNumber5);
        db= new DBhelper(this);
        emailS = getIntent().getStringExtra("Email");
        sp=findViewById(R.id.spinner3);

        Cursor cur = db.getbalance(emailS);
        while (cur.moveToNext()){
            String S = cur.getString(0);
            balance = balance + Integer.parseInt(S);
        }

        String D = String.valueOf(balance);
        V.setText(D);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balance = 0;
                String cnoS = String.valueOf(sp.getSelectedItem());
                String bal = T.getText().toString();
                int bal1 = Integer.parseInt(bal);
                db.updatebalance(cnoS,bal1);
                Cursor cur = db.getbalance(emailS);

                while (cur.moveToNext()){
                    String S = cur.getString(0);
                    balance = balance + Integer.parseInt(S);
                }

                String D = String.valueOf(balance);
                V.setText(D);
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH);
        namesH.clear();
        Cursor res = db.getcard(emailS);
        while (res.moveToNext()){
            String S = res.getString(0);
            namesH.add(S);
        }
        sp.setAdapter(adapter);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addmoney.this,cardDetails.class);
                intent.putExtra("Email",emailS);
                startActivity(intent);
            }
        });
    }
}