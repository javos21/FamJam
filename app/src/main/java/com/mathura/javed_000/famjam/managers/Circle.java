package com.mathura.javed_000.famjam.managers;

/**
 * Created by Nicholas on 5/4/2015.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


import java.text.SimpleDateFormat;


public final class Circle{

    protected SQLiteDatabase db;
    protected final SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static abstract class CircleEntry implements BaseColumns {
        public static final String TABLE_NAME = "Circle";
        public static final String GUID = "guid";
        public static final String PERSON_NAME = "name";
        public static final String EMAIL = "email";
        public static final String TIME_AKEN = "time_taken";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + CircleEntry.TABLE_NAME + " (" +
                    CircleEntry._ID + " INTEGER PRIMARY KEY, " +
                    CircleEntry.GUID + " INTEGER , " +
                    CircleEntry.PERSON_NAME + " TEXT , " +
                    CircleEntry.EMAIL + " TEXT , " +
                    ") ;";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + CircleEntry.TABLE_NAME;

    public Circle(SQLiteDatabase db) {  this.db = db;  }


    /**
     * For inerting new entries
     * */
    public boolean InsertEntry(int value, String name, String email) {
        ContentValues cv = new ContentValues();
        cv.put(CircleEntry.GUID, value);
        cv.put(CircleEntry.PERSON_NAME, name);
        cv.put(CircleEntry.EMAIL, email);
      //  cv.put(CircleEntry.TIME_TAKEN, dFormat.format(new Date()));

        long res = this.db.insert(CircleEntry.TABLE_NAME, null, cv);
        return (res != -1);
    }

    /**
     * need to create an update values method
     * */

        public boolean updateEntry(int guid, String name, String Email){
            return false;
        }

    /**
     * just to test db not really useful for individual values
     * */
    public String[]  retrieveAllEntries () {



        String[] temp = new String[9999];
        StringBuilder stb = new StringBuilder();
        stb.append("SELECT * FROM ")
                .append(CircleEntry.TABLE_NAME);
        int i =0;
        Cursor res = db.rawQuery(stb.toString(), null);
        while(res.moveToNext()){

            temp[i]="Accelerometer Values X: " +(res.getInt(res.getColumnIndex(CircleEntry.GUID)))+
                    " Y: " +  (res.getString(res.getColumnIndex(CircleEntry.PERSON_NAME)))+ " Z: "
                    +(res.getString(res.getColumnIndex(CircleEntry.EMAIL)));
            i++;
        }
        String[] result = new String[i];
        for (int j=0; j<i ; j++)
            result[j]=temp[j];

        if(i==0)
            return null;

        return result;

    }

    /**
     * still have to do
     * */
    public boolean deleteEntry(int id) {
        return false;
    }



}