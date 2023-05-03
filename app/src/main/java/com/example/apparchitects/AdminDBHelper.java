package com.example.apparchitects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDBHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "admindatabase";
    protected static final int DATABASE_VERSION = 1;
    protected static final String ADMIN_TABLE_NAME = "Admin";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_USERNAME = "username";
    protected static final String COLUMN_PASSWORD = "password";
    protected static final String COLUMN_EMAIL = "email";

    public AdminDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAdminTableQuery = "CREATE TABLE " + ADMIN_TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        String insertAdminQuery = "INSERT INTO " + ADMIN_TABLE_NAME + " (" +
                COLUMN_USERNAME + ", " + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + ") " +
                "VALUES ('Admin', 'Admin@dut4life.ac.za', 'Dut@0123')";
        db.execSQL(createAdminTableQuery);
        db.execSQL(insertAdminQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_TABLE_NAME);
        onCreate(db);
    }

    public boolean checkIfUserExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ADMIN_TABLE_NAME, new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=? COLLATE BINARY AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null, null);

        boolean userExists = cursor.moveToFirst();
        cursor.close();

        return userExists;
    }
}
