package com.example.loginandregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class book_for_adimin extends AppCompatActivity {

    DBhelper db;
    Spinner sp;
    Button B;
    ArrayList<String> namesH = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_for_adimin);
        sp=findViewById(R.id.spinner7);
        B=findViewById(R.id.button18);
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
            public void onClick(View view) {
                String name1 = String.valueOf(sp.getSelectedItem());
                Cursor res = db.getBookingdetailsadmin(name1);
                if(res.getCount()==0){
                    Toast.makeText(book_for_adimin.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("No: of Room :"+res.getString(1)+"\n");
                    buffer.append("Email :"+res.getString(2)+"\n");
                    buffer.append("CheckIn :"+res.getString(3)+"\n");
                    buffer.append("CheckOut :"+res.getString(4)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(book_for_adimin.this);
                builder.setCancelable(true);
                builder.setTitle("Hotel Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });


    }
}