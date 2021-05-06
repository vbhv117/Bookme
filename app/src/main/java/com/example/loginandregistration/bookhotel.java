package com.example.loginandregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class bookhotel extends AppCompatActivity {

    EditText E1,E2;
    TextView E4,E5;
    Button b1,b2,b3;
    DBhelper db;
    Spinner sp,sp2,sp3;
    String S;
    int a;

    ArrayList<String> namesH = new ArrayList<String>();
    ArrayList<String> namesH2 = new ArrayList<String>();
    ArrayList<String> namesH3 = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    private static final String TAG = "bookhotel";
    private DatePickerDialog.OnDateSetListener dateset1;
    private DatePickerDialog.OnDateSetListener dateset2;
    String checkin;
    String checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookhotel);

        E2=findViewById(R.id.editTextTextEmailAddress);
        E4=findViewById(R.id.editTextTime);
        E5=findViewById(R.id.editTextDate);
        b1=findViewById(R.id.button7);
        b2=findViewById(R.id.button12);
        b3=findViewById(R.id.button13);
        sp=findViewById(R.id.spinner);
        sp2=findViewById(R.id.spinner4);
        db= new DBhelper(this);


        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH);
        adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH2);
        adapter3 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH3);

        namesH.clear();
        Cursor res = db.getplace();
        while (res.moveToNext()){
            String S = res.getString(0);
            namesH.add(S);
        }

        sp.setAdapter(adapter);

        E4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dlg = new DatePickerDialog(bookhotel.this,R.style.Theme_Design_Light,dateset1,year,month,day);
                dlg.show();
            }
        });

        dateset1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + year + "/" + month + "/" + dayOfMonth);
                checkin = month + "/" + dayOfMonth + "/" + year;
                E4.setText(checkin);
            }
        };

        E5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dlg = new DatePickerDialog(bookhotel.this,R.style.Theme_Design_Light,dateset2,year,month,day);
                dlg.show();
            }
        });

        dateset2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + year + "/" + month + "/" + dayOfMonth);
                checkout = month + "/" + dayOfMonth + "/" + year;
                E5.setText(checkout);
            }
        };


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String place = String.valueOf(sp.getSelectedItem());
                namesH2.clear();
                Cursor res1 = db.getdata(place);
                while (res1.moveToNext()){
                    String S = res1.getString(0);
                    namesH2.add(S);
                }

                sp2.setAdapter(adapter2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = String.valueOf(sp2.getSelectedItem());
                Cursor res = db.hotelprice(name1);
                if(res.getCount()==0){
                    Toast.makeText(bookhotel.this, "Select Hotel", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("No: of Rooms available :"+res.getString(1)+"\n");
                    buffer.append("Rate of Room :"+res.getString(2)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(bookhotel.this);
                builder.setCancelable(true);
                builder.setTitle("Hotel Details");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(sp2.getSelectedItem());
                Cursor r1 = db.getroom(name);
                while(r1.moveToNext()){
                    S = r1.getString(0);
                }
                a = Integer.parseInt(S);
                String noroom = E2.getText().toString();
                int norromint = Integer.parseInt(noroom);
                String email =  getIntent().getStringExtra("Email");
                //int rateint = Integer.parseInt(rate);
                String checkin1 = checkin;
                String checkout1 = checkout;
                if(a>norromint) {
                    Boolean insert = db.addbooking(name, noroom, email, checkin1, checkout1);
                    if (insert == true) {
                        int newroom = a-norromint;
                        db.updateroom(name, newroom);
                        Toast.makeText(bookhotel.this, "Booking success", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(bookhotel.this, "Already Booked", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(bookhotel.this, "Only " + a + " rooms left", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}