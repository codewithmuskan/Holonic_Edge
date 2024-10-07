package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HolonDatabase.db"; // Name of the database
    private static final int DATABASE_VERSION = 1; // Database version

    // Table Name
    public static final String TABLE_NAME = "request_response";

    // Columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REQUEST = "request";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_INCENTIVE = "incentive";
    public static final String COLUMN_TIME_TAKEN = "time_taken";

    // Create Table SQL
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REQUEST + " TEXT, " +
                    COLUMN_DEADLINE + " TEXT, " +
                    COLUMN_INCENTIVE + " TEXT, " +
                    COLUMN_TIME_TAKEN + " TEXT);";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}