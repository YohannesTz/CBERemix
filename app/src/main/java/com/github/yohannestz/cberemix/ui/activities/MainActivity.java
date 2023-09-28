package com.github.yohannestz.cberemix.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.yohannestz.cberemix.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent navDrawerIntent = new Intent(this, NavdrawerActivity.class);
        startActivity(navDrawerIntent);
    }
}