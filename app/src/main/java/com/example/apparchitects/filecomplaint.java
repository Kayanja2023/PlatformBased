package com.example.apparchitects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class filecomplaint extends AppCompatActivity {

    EditText roomNumberEditText, descriptionEditText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filecomplaint);

        roomNumberEditText = findViewById(R.id.roomnumber);
        descriptionEditText = findViewById(R.id.description);
        submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomNumber = roomNumberEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if(roomNumber.isEmpty() || description.isEmpty()) {
                    Toast.makeText(filecomplaint.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    ComplaintDatabaseHelper dbHelper = new ComplaintDatabaseHelper(filecomplaint.this);
                    boolean success = dbHelper.insertComplaintDetails(roomNumber, description);

                    if (success) {
                        Toast.makeText(filecomplaint.this, "Complaint details saved successfully", Toast.LENGTH_SHORT).show();
                        roomNumberEditText.setText("");
                        descriptionEditText.setText("");
                    } else {
                        Toast.makeText(filecomplaint.this, "Error saving complaint details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
                        });

            }
}