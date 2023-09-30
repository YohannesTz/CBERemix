package com.github.yohannestz.cberemix.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.databinding.ActivityNavdrawerBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class NavdrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.github.yohannestz.cberemix.databinding.ActivityNavdrawerBinding binding = ActivityNavdrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavdrawer.toolbar);
        binding.appBarNavdrawer.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        DrawerLayout drawer = binding.drawerLayout;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        MaterialToolbar materialToolbar = binding.appBarNavdrawer.toolbar;
        materialToolbar.setNavigationIcon(R.drawable.ic_nav_menu);
        materialToolbar.setNavigationOnClickListener(v -> {
            if (drawer.isOpen()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

        BottomNavigationView bottomNavigationView = binding.appBarNavdrawer.bottomNav;
        NavController bottomNavController = Navigation.findNavController(this,  R.id.nav_host_fragment_content_navdrawer);
        bottomNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.serviceDetailFragment) {
                Log.e("getId", getResources().getResourceEntryName(navDestination.getId()));
                Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(false);
                getSupportActionBar()
                        .setDisplayHomeAsUpEnabled(false);
            } else{

                Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
                getSupportActionBar().show();
                getSupportActionBar()
                        .setDisplayHomeAsUpEnabled(true);
            }
        });
        NavigationUI.setupWithNavController(bottomNavigationView, bottomNavController);

        getWindow().setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));
        getWindow().setStatusBarColor(SurfaceColors.SURFACE_0.getColor(this));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navdrawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navdrawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}