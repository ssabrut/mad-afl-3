package com.uc.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uc.moviedb.R;
import com.uc.moviedb.view.fragments.MovieDetailsFragment;

public class MainMenuActivity extends AppCompatActivity {

    private BottomNavigationView main_menu_bottom_nav;
    private NavHostFragment main_menu_nav_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        main_menu_bottom_nav = findViewById(R.id.main_menu_bottom_nav);
        main_menu_nav_fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_menu_nav_fragment);
        NavigationUI.setupWithNavController(main_menu_bottom_nav, main_menu_nav_fragment.getNavController());
    }

    @Override
    public void onBackPressed() {
        if (MovieDetailsFragment.backPressed != null) {
            MovieDetailsFragment.backPressed.onBackPressed();
        }
    }
}