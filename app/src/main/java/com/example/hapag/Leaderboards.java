package com.example.hapag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Leaderboards extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_leaderboards);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Leaderboards.this, Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_create:
                        startActivity(new Intent(Leaderboards.this, Create.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_leaderboards:
                        return true;
                }
                return false;
            }
        });
    }
}
