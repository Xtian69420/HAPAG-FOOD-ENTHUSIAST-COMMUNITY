package com.example.hapag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private AccountHandle accountHandle;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(this);

        // Remove the default title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ImageView profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle profile icon click
            }
        });

        ImageView settingsIcon = findViewById(R.id.action_settings);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle settings icon click
            }
        });

        ImageView searchIcon = findViewById(R.id.action_search);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        accountHandle = loadAccountData();

        if (accountHandle == null) {
            Intent intent = new Intent(Dashboard.this, LoginOrRegister.class);
            startActivity(intent);
            finish();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Dashboard.this, Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_create:
                        startActivity(new Intent(Dashboard.this, Create.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_leaderboards:
                        startActivity(new Intent(Dashboard.this, Leaderboards.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        // Initialize RecyclerView and DatabaseHelper
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);
    }

    private String getUserFullName(long userId) {
        String firstName = databaseHelper.getUserFirstName(userId);
        String lastName = databaseHelper.getUserLastName(userId);
        return firstName + " " + lastName;
    }

    private AccountHandle loadAccountData() {
        SharedPreferences sharedPreferences = getSharedPreferences("account_data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("account", null);
        return gson.fromJson(json, AccountHandle.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                overridePendingTransition(0, 0);
                return true;
            case R.id.nav_create:
                startActivity(new Intent(this, Create.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.nav_leaderboards:
                startActivity(new Intent(this, Leaderboards.class));
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
