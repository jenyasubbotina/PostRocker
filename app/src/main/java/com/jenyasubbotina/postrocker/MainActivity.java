package com.jenyasubbotina.postrocker;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    NavGraph graph;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        navigationView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavInflater navInflater = navController.getNavInflater();
        graph = navInflater.inflate(R.navigation.mobile_navigation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        NavigationUI.setupWithNavController(navigationView, Navigation.findNavController(this, R.id.my_nav_host_fragment));
       // navController.navigate(R.id.contest_nav);
    }
}