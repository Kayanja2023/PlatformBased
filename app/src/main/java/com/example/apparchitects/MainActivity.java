package com.example.apparchitects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private UserDatabaseHelper dbHelper;
    private AdminDBHelper adminDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.password);
        dbHelper = new UserDatabaseHelper(this);
        adminDbHelper = new AdminDBHelper(this);

        MaterialButton loginButton = findViewById(R.id.login_btn);
        Button signupButton = findViewById(R.id.signupbtn);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, Signup.class);
                startActivity(signUpIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                boolean isUserExists = dbHelper.checkIfUserExists(username, password);
                boolean isAdminExists = adminDbHelper.checkIfUserExists(username, password);

                if (isUserExists) {
                    Toast.makeText(MainActivity.this, "USER LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, dashboardpage.class);
                    startActivity(intent);
                } else if (isAdminExists) {
                    Toast.makeText(MainActivity.this, "ADMIN LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

