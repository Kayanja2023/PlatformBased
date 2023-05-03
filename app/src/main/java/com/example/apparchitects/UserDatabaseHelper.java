package com.example.apparchitects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "userdatabase";
    protected static final int DATABASE_VERSION = 1;
    protected static final String TABLE_NAME = "users";
    protected static final String ADMIN_TABLE_NAME = "Admin";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_USERNAME = "username";
    protected static final String COLUMN_PASSWORD = "password";
    protected static final String COLUMN_EMAIL = "email";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";


        db.execSQL(createTableQuery);

    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUserDetails(String username, String password,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_EMAIL,email);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }
    public boolean checkIfUserExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=? COLLATE BINARY AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null, null);

        boolean userExists = cursor.moveToFirst();
        cursor.close();

        return userExists;
    }




}