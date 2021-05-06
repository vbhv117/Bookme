package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class cardDetails extends AppCompatActivity {

    EditText E1,E2;
           TextView E3;
    Button b1;
    DBhelper db;
    long cnoc;
    int cvva;
    long a = Long.parseLong("999999999999999");
    long b = Long.parseLong("10000000000000000");
    private DatePickerDialog.OnDateSetListener dateset1;
    private static final String TAG = "bookhotel";
    String checkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        E1=findViewById(R.id.editTextNumber2);
        E2=findViewById(R.id.editTextNumber3);
        E3=findViewById(R.id.editTextNumber4);
        b1=findViewById(R.id.button8);
        db= new DBhelper(this);

        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dlg = new DatePickerDialog(cardDetails.this,android.R.style.Theme_Holo_Dialog_MinWidth,dateset1,year,month,day);
                dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dlg.show();

            }
        });

        dateset1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + year + "/" + month + "/" + dayOfMonth);
                checkin = month + "/" + dayOfMonth + "/" + year;
                E3.setText(checkin);
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(E1.getText().toString()) || TextUtils.isEmpty(E2.getText().toString()) || TextUtils.isEmpty(E3.getText().toString())) {
                    Toast.makeText(cardDetails.this, "Empty field found", Toast.LENGTH_SHORT).show();
                }
                else {
                    String cno = E1.getText().toString();
                    cnoc = Long.parseLong(cno);
                    if (cnoc > a && cnoc < b) {
                        String cvv = E2.getText().toString();
                        cvva = Integer.parseInt(cvv);
                        if (cvva > 99 && cvva < 1000) {
                            String email = getIntent().getStringExtra("Email");
                            //int rateint = Integer.parseInt(rate);
                            String balance = E3.getText().toString();
                            Boolean insert = db.addcard(cno, cvv, balance, email, 0);
                            if (insert == true) {
                                Toast.makeText(cardDetails.this, "Card added Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(cardDetails.this,addmoney.class);
                                intent.putExtra("Email",email);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(cardDetails.this, "Please try again with a different card", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(cardDetails.this, "Invalid CVV", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(cardDetails.this, "Invalid card Number", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}