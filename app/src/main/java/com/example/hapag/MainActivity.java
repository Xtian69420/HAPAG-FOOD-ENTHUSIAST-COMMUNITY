package com.example.hapag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, LoginOrRegister.class);
        startActivity(intent);
        /*
        if (isAccountSaved()) {

            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        } else {

            Intent intent = new Intent(MainActivity.this, LoginOrRegister.class);
            startActivity(intent);
            finish();
        }*/
    }
    private boolean isAccountSaved() {
        SharedPreferences sharedPreferences = getSharedPreferences("account_data", MODE_PRIVATE);
        return sharedPreferences.contains("account");
    }
}