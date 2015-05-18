package com.mathura.javed_000.famjam.managers;

/**
 * Created by Nicholas on 5/4/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


import com.mathura.javed_000.famjam.CirclePerson;
import com.mathura.javed_000.famjam.utils.PersonLocation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public final class CircleDB extends SQLiteOpenHelper{

    protected final SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "famjam.db";

    public static abstract class CircleEntry implements BaseColumns {
        public static final String TABLE_NAME = "Circle";
        public static final String PERSON_NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHOTO_URL = "photoUrl";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + CircleEntry.TABLE_NAME + " (" +
                    CircleEntry._ID + " INTEGER PRIMARY KEY, " +
                    CircleEntry.PERSON_NAME + " TEXT , " +
                    CircleEntry.EMAIL + " TEXT , " +
                    CircleEntry.PHOTO_URL + " TEXT ," +
                    CircleEntry.LONGITUDE + " REAL , " +
                    CircleEntry.LATITUDE + " REAL " +
                    ") ;";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + CircleEntry.TABLE_NAME;

    public CircleDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

    /**
     * For inerting new entries
     * */
    public boolean insertEntry(String name, String email,String Url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CircleEntry.PERSON_NAME, name);
        cv.put(CircleEntry.EMAIL, email);
        cv.put(CircleEntry.PHOTO_URL, Url);

        long res = db.insert(CircleEntry.TABLE_NAME, null, cv);
        Log.e("CircleDB", (name + " Added"));
        return (res != -1);
    }

    /**
     * need to create an update values method
     * */

    public boolean updateEntry(String name, String email, double longitude, double latitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CircleEntry.PERSON_NAME, name);
        cv.put(CircleEntry.EMAIL, email);
        cv.put(CircleEntry.LONGITUDE, longitude);
        cv.put(CircleEntry.LATITUDE, latitude);
        long res = db.update(CircleEntry.TABLE_NAME, cv, CircleEntry.PERSON_NAME + " = '" + name + "'", null);
        return (res != -1);
    }

    public boolean deleteEntry(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("CircleDB",(name + " deleted successfully"));
        return db.delete(CircleEntry.TABLE_NAME,
                CircleEntry.PERSON_NAME + " = '" + name + "'",null) > 0;
    }

    public boolean updaterow(int row, double longitude, double latitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CircleEntry._ID, row);
        cv.put(CircleEntry.LONGITUDE, longitude);
        cv.put(CircleEntry.LATITUDE, latitude);

        long res = db.update(CircleEntry.TABLE_NAME, cv, CircleEntry._ID + " = " + row, null);
        return (res != -1);
    }

    public boolean checkTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + CircleEntry.TABLE_NAME, null);
        boolean isfull = true;
        isfull = c.moveToFirst();
        return isfull;
    }

    public boolean ifPersonExists(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " +
                CircleEntry.TABLE_NAME + " WHERE " +
                CircleEntry.PERSON_NAME + "= '" + name + "'";
        Cursor c = db.rawQuery(query,null);
        boolean ifexists = false;
        ifexists = c.moveToFirst();
        return ifexists;
    }

    public ArrayList<CirclePerson> retrieveAllCardEntries () {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<CirclePerson> persons  = new ArrayList<CirclePerson>();
        String query = "SELECT * FROM " +
                CircleEntry.TABLE_NAME + " ORDER BY " +
                CircleEntry._ID;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        while(res.moveToNext()){
            persons.add(new CirclePerson((res.getString(res.getColumnIndex(CircleEntry.PERSON_NAME))),
                    (res.getString(res.getColumnIndex(CircleEntry.PHOTO_URL)))));
            Log.e("CircleDB",(res.getString(res.getColumnIndex(CircleEntry.PERSON_NAME))) + " Here");
        }
        return persons;
    }

    public ArrayList<PersonLocation>  retrieveAllLocations () {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PersonLocation> locations = new ArrayList<PersonLocation>();
        String query = "SELECT * FROM " +
                CircleEntry.TABLE_NAME + " ORDER BY " +
                CircleEntry._ID;
        Cursor res = db.rawQuery(query, null);
        while(res.moveToNext()){
            locations.add(new PersonLocation((res.getString(res.getColumnIndex(CircleEntry.PERSON_NAME))),"",
                    (res.getDouble(res.getColumnIndex(CircleEntry.LATITUDE))),
                    (res.getDouble(res.getColumnIndex(CircleEntry.LONGITUDE)))));
        }
        return locations;
    }

    public String getName(){
        SQLiteDatabase db = this.getWritableDatabase();
        String name = new String();
        String query = "SELECT " + CircleEntry.PERSON_NAME + " FROM " +
                CircleEntry.TABLE_NAME + " ORDER BY " +
                CircleEntry._ID;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        name = res.getString(res.getColumnIndex(CircleEntry.PERSON_NAME));
        return name;
    }

    public String getEmail(){
        SQLiteDatabase db = this.getWritableDatabase();
        String email = new String();
        String query = "SELECT " + CircleEntry.EMAIL + " FROM " +
                CircleEntry.TABLE_NAME + " ORDER BY " +
                CircleEntry._ID;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        email = res.getString(res.getColumnIndex(CircleEntry.EMAIL));
        return email;
    }

    /**
     * still have to do
     * */
    public boolean deleteEntry(int id) {
        return false;
    }



}