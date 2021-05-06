package com.example.loginandregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    Button B;
    Spinner sp;
    DBhelper db;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<String> namesH = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        String email = getIntent().getStringExtra("Email");


        B = findViewById(R.id.button20);
        sp = findViewById(R.id.spinner9);
        db= new DBhelper(this);

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, namesH);

        namesH.clear();
        Cursor res = db.getBookedhotel(email);
        while (res.moveToNext()){
            String S = res.getString(0);
            namesH.add(S);
        }

        sp.setAdapter(adapter);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main2Activity.this, "getLocationcalled", Toast.LENGTH_LONG).show();
                if(ActivityCompat.checkSelfPermission(Main2Activity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    String value = String.valueOf(sp.getSelectedItem());
                    getLocation(value);

                }
                else{
                    ActivityCompat.requestPermissions(Main2Activity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    Toast.makeText(Main2Activity.this, "Please enable GPS", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void getLocation(final String value) {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){

                    try {
                        Geocoder geocoder = new Geocoder(Main2Activity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String L = String.valueOf(addresses.get(0).getLatitude());
                        String A = String.valueOf(addresses.get(0).getLongitude());
                        DisplayTrack(L,A,value);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void DisplayTrack(String L,String A, String V){
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + L+","+ A +"/" + V);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
