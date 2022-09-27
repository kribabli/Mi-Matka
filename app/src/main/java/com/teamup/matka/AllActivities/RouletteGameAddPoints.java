package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.matka.R;

public class RouletteGameAddPoints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_roulette_game_add_points);
    }
}