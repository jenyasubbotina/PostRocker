package com.jenyasubbotina.postrocker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    NavGraph graph;
    NavController navController;
    SessionManager sm;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = new SessionManager(this);
        navigationView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavInflater navInflater = navController.getNavInflater();
        graph = navInflater.inflate(R.navigation.mobile_navigation);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.programming_nav:
                    navController.navigate(R.id.navigationFragment);
                    break;
                case R.id.personal_nav:
                    if (sm.isAuthenticated()) {
                        navController.navigate(R.id.cabinetFragment);
                    } else {
                        navController.navigate(R.id.loginActivity);
                    }
                    break;
            }
            return true;
        });
    }
}
