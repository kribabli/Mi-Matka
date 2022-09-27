package com.teamup.matka.AllActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

public class StarlinePlaceBidActivity extends AppCompatActivity {

    ImageView single_digit_img, single_panna_img, double_panna_img, tripple_panna_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.starline_layout);
        Admin.HandleToolBar(this, Admin.toolTitle, findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        tripple_panna_img = findViewById(R.id.tripple_panna_img);
        double_panna_img = findViewById(R.id.double_panna_img);
        single_panna_img = findViewById(R.id.single_panna_img);

        single_digit_img = findViewById(R.id.single_digit_img);
        Admin.starline = true;
        single_digit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "sd";
                Admin.auto_type = "sp";
                Admin.selected = "single_digit";
                Admin.digitLimit = 1;
                startActivity(new Intent(StarlinePlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(StarlinePlaceBidActivity.this);
            }
        });

        single_panna_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "sp";
                Admin.auto_type = "sp";
                Admin.selected = "single_panna";
                Admin.digitLimit = 3;
                startActivity(new Intent(StarlinePlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(StarlinePlaceBidActivity.this);
            }
        });
        double_panna_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "dp";
                Admin.auto_type = "dp";
                Admin.selected = "double_panna";
                Admin.digitLimit = 3;
                startActivity(new Intent(StarlinePlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(StarlinePlaceBidActivity.this);
            }
        });

        tripple_panna_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "tp";
                Admin.selected = "tripple_panna";
                Admin.auto_type = "tp";
                Admin.digitLimit = 3;
                startActivity(new Intent(StarlinePlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(StarlinePlaceBidActivity.this);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}