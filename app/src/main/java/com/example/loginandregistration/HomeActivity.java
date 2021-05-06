package com.example.loginandregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import Data.DatabaseHelper;
import Model.User;

public class HomeActivity extends AppCompatActivity {
    TextView textViewUserName, textViewName, textViewPassword, textViewEmail, textViewLogout;

    Button btnEditDetails, btnChangePassword, mapbtn,booking,call,log,bookdet,btncancel;

    BottomNavigationView nav;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    DBhelper db;

    String textViewUsernameString;
    String textViewNameString;
    String textViewEmailString;
    String textViewPasswordString;
    int textViewID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewLogout = findViewById(R.id.textViewLogout);
        mapbtn = findViewById(R.id.map);
        booking = findViewById(R.id.book);
        call = findViewById(R.id.callbutton);
        log=findViewById(R.id.log);
        bookdet = findViewById(R.id.button2);
        btncancel=findViewById(R.id.button3);
        db= new DBhelper(this);


        final Bundle b = getIntent().getExtras();


        textViewID = Integer.parseInt(b.getString("textViewId"));
        textViewUsernameString = b.getString("textViewUsername");
        textViewNameString = b.getString("textViewUsername");
        textViewEmailString = b.getString("textViewEmail");
        //textViewPasswordString = b.getString("textViewPassword");


        Log.d("Data", String.valueOf(textViewID));
        Log.d("Data", "User name: " + textViewUsernameString);
        Log.d("Data", "name: " + textViewNameString);
        Log.d("Data", "Email: " + textViewEmailString);
        //Log.d("Data", "password: " + textViewPasswordString);

        textViewUserName.setText("Hello " + textViewUsernameString);
        textViewName.setText(textViewNameString);
        //textViewPassword.setText(textViewPasswordString);
        textViewEmail.setText(textViewEmailString);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,addmoney.class);
                intent.putExtra("Email",textViewEmailString);
                startActivity(intent);

            }
        });



        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,bookhotel.class);
                intent.putExtra("Email",textViewEmailString);
                startActivity(intent);

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,cancel_booking.class);
                intent.putExtra("Email",textViewEmailString);
                startActivity(intent);
            }
        });


        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,Main2Activity.class);
                intent.putExtra("Email",textViewEmailString);
                startActivity(intent);

            }
        });

        bookdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getBookingdetails(textViewEmailString);
                if(res.getCount()==0){
                    Toast.makeText(HomeActivity.this, "No Bookings Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("No: of Room :"+res.getString(1)+"\n");
                    buffer.append("Check-in :"+res.getString(2)+"\n");
                    buffer.append("Check-out :"+res.getString(3)+"\n\n");
                }

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Booking details");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        btnEditDetails = findViewById(R.id.btnEditDetails);
        btnEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserDidals();
            }
        });

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }


    public void changePassword() {
        alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        inflater = LayoutInflater.from(HomeActivity.this);
        final View view = inflater.inflate(R.layout.popup_password, null);
        final EditText editTextPasswordPopup = view.findViewById(R.id.editTextPasswordPopup);
        final EditText editTextConfPasswordPopup = view.findViewById(R.id.editTextConfPasswordPopup);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        final User user = new User();
        user.setId(textViewID);
        user.setUserName(textViewNameString);
        user.setEmail(textViewEmailString);
        //user.setPassword(textViewPasswordString);


        final DatabaseHelper db = new DatabaseHelper(HomeActivity.this);

        Button saveButtonPassword = view.findViewById(R.id.saveButtonPassword);

        saveButtonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("check", editTextConfPasswordPopup.getText().toString());
                Log.d("check", editTextPasswordPopup.getText().toString());

                if (Integer.parseInt(editTextPasswordPopup.getText().toString()) == Integer.parseInt(editTextPasswordPopup.getText().toString()) ) {
                    user.setPassword(editTextPasswordPopup.getText().toString());
                    db.updateUser(user);
                    Toast.makeText(HomeActivity.this, "hi from if condition ", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Details Saved!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                } else{
                    Snackbar.make(view, "Don't save ", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(HomeActivity.this, "what else ", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });
    }


    public void editUserDidals() {
        alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        inflater = LayoutInflater.from(HomeActivity.this);
        final View view = inflater.inflate(R.layout.popup, null);
        final EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        final EditText editTextEmail = view.findViewById(R.id.editTextEmail);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        final User user = new User();
        user.setId(textViewID);
        user.setUserName(textViewNameString);
        user.setEmail(textViewEmailString);
        //user.setPassword(textViewPasswordString);

        editTextUsername.setText(user.getUserName());
        editTextEmail.setText(user.getEmail());

        final DatabaseHelper db = new DatabaseHelper(HomeActivity.this);

        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUserName(editTextUsername.getText().toString());
                user.setEmail(editTextEmail.getText().toString());

                if (!editTextUsername.getText().toString().isEmpty()
                        && !editTextEmail.getText().toString().isEmpty()) {
                    db.updateUser(user);
                    Snackbar.make(v, "Details Saved!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                } else {
                    Snackbar.make(view, "Don't save ", Snackbar.LENGTH_LONG).show();
                }
                dialog.dismiss();

            }
        });
    }


}
