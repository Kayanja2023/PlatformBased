package com.example.apparchitects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.regex.Pattern;
import com.google.android.material.button.MaterialButton;

public class Signup extends AppCompatActivity {

    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper=new UserDatabaseHelper(this);


        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        EditText email = (EditText) findViewById(R.id.email);
        EditText repassword = (EditText) findViewById(R.id.repassword);

        MaterialButton regbtn = (MaterialButton) findViewById(R.id.signupbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                String email1=email.getText().toString();
                String rePassword1=repassword.getText().toString();

                if (checkIfUserExists(username1)) {
                    // if the user already exists, show a toast message
                    Toast.makeText(Signup.this, "Username already exists. Please choose a different username", Toast.LENGTH_SHORT).show();

                }  else if (!password1.equals(rePassword1)) { // Add this condition to check if the password and re-password match
                    Toast.makeText(Signup.this, "Passwords do not match,Please review", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email1)) { // Add this condition to check if the email is valid
                    Toast.makeText(Signup.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }else {
                    boolean success = dbHelper.insertUserDetails(username1, password1, email1);
                    if (success) {
                        Toast.makeText(Signup.this, "User details saved successfully", Toast.LENGTH_SHORT).show();
                        // navigate to the dashboard page
                        Intent dashboardIntent = new Intent(Signup.this, dashboardpage.class);
                        startActivity(dashboardIntent);
                        finish(); // prevent going back to the sign up page
                    } else {
                        Toast.makeText(Signup.this, "Error saving user details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean checkIfUserExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserDatabaseHelper.TABLE_NAME + " WHERE " + UserDatabaseHelper.COLUMN_USERNAME + "=?", new String[]{username});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        return pattern.matcher(email).matches();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}


