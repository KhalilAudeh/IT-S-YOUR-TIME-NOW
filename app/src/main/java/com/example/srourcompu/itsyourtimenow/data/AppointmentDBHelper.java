package com.example.srourcompu.itsyourtimenow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by srourcompu on 12/19/2018.
 */

public class AppointmentDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "appointment.db";

    private static final int DATABASE_VERSION = 1;

    public AppointmentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the appointment table
        String SQL_CREATE_APPOINTMENT_TABLE =  "CREATE TABLE " + AppointmentContract.AppointmentEntry.TABLE_NAME + " ("
                + AppointmentContract.AppointmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AppointmentContract.AppointmentEntry.KEY_PURPOSE + " TEXT NOT NULL, "
                + AppointmentContract.AppointmentEntry.KEY_DATE + " TEXT NOT NULL, "
                + AppointmentContract.AppointmentEntry.KEY_TIME + " TEXT NOT NULL, "
                + AppointmentContract.AppointmentEntry.KEY_LOCATION + " TEXT NOT NULL " + " );";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_APPOINTMENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
