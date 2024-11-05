package com.example.hapag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LoginOrRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove Header XD; reuse mo nalang tong dalawang code na to if need mo tanggalin yung header
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // remove Action bar
        // getSupportActionBar().hide();

        setContentView(R.layout.activity_login_or_register);

        Button login = findViewById(R.id.buttonLogin);
        Button register = findViewById(R.id.buttonRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL = new Intent(LoginOrRegister.this, Login.class);
                startActivity(intentL);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentR = new Intent(LoginOrRegister.this, Register.class);
                startActivity(intentR);
            }
        });
    }
}