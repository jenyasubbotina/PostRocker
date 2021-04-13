package com.jenyasubbotina.postrocker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
    public static final String CONTEST_ID = "CONTEST_ID";

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavInflater navInflater = navController.getNavInflater();
        graph = navInflater.inflate(R.navigation.mobile_navigation);
        NavigationUI.setupWithNavController(navigationView, Navigation.findNavController(this, R.id.my_nav_host_fragment));
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.contest_nav:
                    navController.navigate(R.id.contestsFragment);
                    break;
                case R.id.cabinet_nav:
                    navController.navigate(R.id.cabinetFragment);
                    break;
            }
            return true;
        });
    }
}
