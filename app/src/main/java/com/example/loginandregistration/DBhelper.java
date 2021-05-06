package com.example.loginandregistration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context, "Hotelbook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table HotelDetails(name TEXT primary key, noroom NUMBER, rate TEXT, place TEXT, email TEXT)");
        db.execSQL("create table Booking(name TEXT primary key, noroom TEXT, Email TEXT, checkin TEXT, checkout TEXT)");
        db.execSQL("create table Payment(cno TEXT primary key, cvv TEXT, date TEXT, email TEXT, balance NUMBER)");
        db.execSQL("create table Adminlogin(Email TEXT primary key, name TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists HotelDetails");
        db.execSQL("drop Table if exists Booking");
        db.execSQL("drop Table if exists Payment");
        db.execSQL("drop Table if exists Adminlogin");

    }

    public boolean addhotel(String name, int noroom, String rate, String place, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("noroom", noroom);
        values.put("rate", rate);
        values.put("place", place);
        values.put("email", email);
        long result = db.insert("HotelDetails", null, values);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getdata (String place)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name from HotelDetails where place =?",new String[]{place} );
        return cursor;

    }

    public boolean addbooking(String name, String noroom, String email, String checkin, String checkout){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("noroom", noroom);
        values.put("Email", email);
        values.put("checkin", checkin);
        values.put("checkout", checkout);
        long result = db.insert("Booking", null, values);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean addcard(String cno, String cvv, String date, String email, int balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cno", cno);
        values.put("cvv", cvv);
        values.put("date", date);
        values.put("email", email);
        values.put("balance", balance);
        long result = db.insert("Payment", null, values);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getBookingdetails (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name,noroom,checkin,checkout from Booking where Email = ?", new String[]{email});
        return cursor;

    }

    public Cursor hotelnames()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name from HotelDetails", null);
        return cursor;

    }

    public void delbook(String name1){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Booking", "name = ?", new String[]{name1});
    }

    public Cursor getBookedhotel (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name from Booking where Email =?", new String[]{email});
        return cursor;

    }

    public Cursor getcard (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select cno from Payment where Email =?", new String[]{email});
        return cursor;

    }

    public Cursor getbalance (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select balance from Payment where Email =?", new String[]{email});
        return cursor;

    }

    public  void  updatebalance (String cno, int balance){
        SQLiteDatabase DB = this.getReadableDatabase();
        DB.execSQL("UPDATE Payment SET balance = balance + "+ balance +" WHERE cno = "+cno );
    }

    public Cursor getplace ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select place from HotelDetails", null);
        return cursor;

    }

    public Cursor getdatatwo (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name,noroom,rate,place from HotelDetails where email =?",new String[]{email} );
        return cursor;

    }

    public Cursor hotelprice (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name,noroom,rate from HotelDetails where name =?",new String[]{name});
        return cursor;

    }

    public boolean addadmin(String email, String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("name", name);
        values.put("password", password);
        long result = db.insert("Adminlogin", null, values);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkadmin (String email,String pass)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Adminlogin where Email = ? and password= ?",new String[]{email,pass});
        if(cursor.getCount()>0){
            return true;
        }
        else
        {
            return false;
        }

    }

    public Cursor getadminhotel (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name from HotelDetails where email =?", new String[]{email});
        return cursor;

    }

    public void delhotel(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("HotelDetails", "name = ?", new String[]{name});
    }

    public  void  updatehotel (String name1,String noroom1, String rate1, String place1, String mail){
        SQLiteDatabase DB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name1);
        contentValues.put("noroom",noroom1);
        contentValues.put("rate",rate1);
        contentValues.put("place",place1);
        DB.update("HotelDetails",contentValues,"name = ?",new String[]{mail});
    }

    public Cursor getBookingdetailsadmin (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Booking where name = ?", new String[]{name});
        return cursor;

    }

    public  void  updateadmin (String name1, String pass, String mail){
        SQLiteDatabase DB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name1);
        contentValues.put("password", pass);
        DB.update("Adminlogin",contentValues,"Email = ?",new String[]{mail});
    }

    public Cursor getroom (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select noroom from HotelDetails where name =?", new String[]{name});
        return cursor;

    }

    public  void  updateroom (String name, int room){
        SQLiteDatabase DB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("noroom",room);
        DB.update("HotelDetails",contentValues,"name = ?",new String[]{name});
    }

    public Cursor roombooked (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select noroom from Booking where name =?", new String[]{name});
        return cursor;

    }



}
