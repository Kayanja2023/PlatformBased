package com.example.apparchitects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ComplaintDatabaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "complaintdatabase";
    protected static final int DATABASE_VERSION = 2;
    protected static final String TABLE_NAME = "complaints";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_ROOMNUMBER = "roomnumber";
    protected static final String COLUMN_DESCRIPTION = "description";
    protected static final String COLUMN_STATUS = "status";
    public ComplaintDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROOMNUMBER + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_STATUS + " TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertComplaintDetails(String roomNumber, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ROOMNUMBER, roomNumber);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_STATUS, "Submitted");

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }
    public void updateComplaintStatus(int complaintId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, newStatus);

        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(complaintId)});
        db.close();
    }

    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int roomNumberIndex = cursor.getColumnIndex(COLUMN_ROOMNUMBER);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int statusIndex = cursor.getColumnIndex(COLUMN_STATUS);

                int id = cursor.getInt(idIndex);
                String roomNumber = cursor.getString(roomNumberIndex);
                String description = cursor.getString(descriptionIndex);
                String status = cursor.getString(statusIndex);

                Complaint complaint = new Complaint(id, roomNumber, description, status);
                complaints.add(complaint);
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();

        return complaints;
    }







}
