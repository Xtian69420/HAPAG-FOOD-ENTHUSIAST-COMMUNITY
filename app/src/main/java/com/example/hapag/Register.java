package com.example.hapag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove Header
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Remove Action bar
        // getSupportActionBar().hide();

        setContentView(R.layout.activity_register);

        ProgressBar progressBar = findViewById(R.id.progressBar);

        // Declare
        EditText email = findViewById(R.id.Email);
        EditText pass = findViewById(R.id.Pass);
        EditText confirmpass = findViewById(R.id.ConfirmPass);
        EditText f = findViewById(R.id.Fname);
        EditText l = findViewById(R.id.Lname);
        //EditText bd = findViewById(R.id.Bday);
        Button register = findViewById(R.id.Register);
        CheckBox ChckBox = findViewById(R.id.checkBoxTandC);
        Button back = findViewById(R.id.button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String emailText = email.getText().toString();
                String passwordText = pass.getText().toString();
                String firstNameText = f.getText().toString();
                String lastNameText = l.getText().toString();
                String confirmpasswordText = confirmpass.getText().toString();

                // lahat ng fields dapat may values if merong wala, magt throw ng toast
                if (emailText.isEmpty() || passwordText.isEmpty() || firstNameText.isEmpty() ||
                        lastNameText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input all fields", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    // email format standard B)
                    if (!isValidEmail(emailText)) {
                        Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else if (passwordText.length() < 8) {
                        // password format standard
                        Toast.makeText(getApplicationContext(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else if (!passwordText.equals(confirmpasswordText)) {
                        Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    else if (!ChckBox.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Terms and Condition not satisfied", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else {
                        // check db if already exist na yung email
                        if (checkIfEmailExists(emailText)) {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            // insert user input sa db natin
                            long userId = insertUserData(emailText, firstNameText, lastNameText, passwordText);
                            if (userId != -1) {
                                // delay simulation
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // hide the progress bar after 2 seconds (simulating a network call or data processing)
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, Login.class);
                                        startActivity(intent);
                                    }
                                }, 2000); // 2000 milliseconds = 2 seconds
                            }
                            else {
                                // Handle errors
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean checkIfEmailExists(String email) {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USER + " WHERE " + DatabaseHelper.COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    // insert data to database
    private long insertUserData(String email, String fname, String lname, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        return dbHelper.insertUser(email, fname, lname, password);
    }
}