package com.example.hapag;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove Header XD
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);

        EditText em = findViewById(R.id.editTextTextEmailAddress);
        EditText pass = findViewById(R.id.editTextTextPassword);
        Button LoginButton = findViewById(R.id.buttonLogin);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingUtil.showLoading(progressBar);

                String email = em.getText().toString();
                String password = pass.getText().toString();
                Log.d("Input", "Email: " + email);
                Log.d("Input", "Password: " + password);
                if (email.isEmpty() || password.isEmpty()) {
                    LoadingUtil.hideLoading(progressBar);
                    Toast.makeText(getApplicationContext(), "Please input all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.equals("admin") && password.equals("admin")) {
                        LoadingUtil.hideLoading(progressBar);
                        Toast.makeText(getApplicationContext(), "Login as admin success", Toast.LENGTH_SHORT).show();
                    } else {

                        DatabaseHelper dbHelper = new DatabaseHelper(Login.this);

                        String storedPassword = dbHelper.getUserPassword(email);
                        long userId = dbHelper.getUserId(email);


                        if (password.equals(storedPassword)) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LoadingUtil.hideLoading(progressBar);

                                    long UserId = dbHelper.getUserId(email);
                                    String Email = dbHelper.getUserEmail(userId);
                                    String firstName = dbHelper.getUserFirstName(userId);
                                    String lastName = dbHelper.getUserLastName(userId);
                                    String Password = dbHelper.getUserPassword(userId);


                                    AccountHandle account = new AccountHandle(UserId, Email, firstName, lastName, Password);

                                    // Save account data to SharedPreferences
                                    saveAccountData(account);

                                    Toast.makeText(getApplicationContext(), "Login success for User ID: " + userId, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Dashboard.class);
                                    startActivity(intent);
                                }
                            }, 2000);
                        }
                        else {
                            LoadingUtil.hideLoading(progressBar);
                            Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
    private void saveAccountData(AccountHandle account) {
        SharedPreferences sharedPreferences = getSharedPreferences("account_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(account);
        editor.putString("account", json);
        editor.apply();
    }
}