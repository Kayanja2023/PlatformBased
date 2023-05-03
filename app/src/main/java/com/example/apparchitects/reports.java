package com.example.apparchitects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class reports extends AppCompatActivity {

    protected RecyclerView recyclerViewReports;
    protected ComplaintsAdapter complaintsAdapter;
    protected ComplaintDatabaseHelper complaintDatabaseHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Initialize your database handler class.
        complaintDatabaseHelper = new ComplaintDatabaseHelper (this);

        // Fetch the complaints data from the database.
        List<Complaint> complaints = complaintDatabaseHelper.getAllComplaints();
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        // Initialize the RecyclerView and set the adapter.
        RecyclerView recyclerView = findViewById(R.id.recycler_view_reports);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ComplaintsAdapter(complaints, isAdmin));
    }




}
