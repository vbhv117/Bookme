package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class cancel_booking extends AppCompatActivity {

    DBhelper db;
    Spinner sp;
    String S,D;
    int a,x;
    ArrayList<String> namesH = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        String email = getIntent().getStringExtra("Email");

        db= new DBhelper(this);
        b=findViewById(R.id.button9);
        sp=findViewById(R.id.spinner2);

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH);

        namesH.clear();
        Cursor res = db.getBookedhotel(email);
        while (res.moveToNext()){
            String S = res.getString(0);
            namesH.add(S);
        }

        sp.setAdapter(adapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = String.valueOf(sp.getSelectedItem());
                Cursor r1 = db.getroom(value);
                while(r1.moveToNext()){
                    S = r1.getString(0);
                }
                a=0;
                a = Integer.parseInt(S);

                Cursor r2 = db.roombooked(value);
                while(r2.moveToNext()){
                    D = r2.getString(0);
                }
                x = Integer.parseInt(D);

                a=a+x;

                db.updateroom(value,a);


                db.delbook(value);
                Toast.makeText(cancel_booking.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }


}