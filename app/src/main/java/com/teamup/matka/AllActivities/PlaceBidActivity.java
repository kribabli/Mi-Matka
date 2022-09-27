package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDaysTheory;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

public class PlaceBidActivity extends AppCompatActivity {

    ImageView single_digit_img, jodi_digit_img, single_panna_img, double_panna_img, tripple_panna_img,
            half_snagam_img, full_sangam_img, odd_even_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_rajdhani_day);
        Admin.HandleToolBar(this, Admin.toolTitle, findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        odd_even_img = findViewById(R.id.odd_even_img);
        full_sangam_img = findViewById(R.id.full_sangam_img);
        half_snagam_img = findViewById(R.id.half_snagam_img);
        tripple_panna_img = findViewById(R.id.tripple_panna_img);
        double_panna_img = findViewById(R.id.double_panna_img);
        single_panna_img = findViewById(R.id.single_panna_img);
        jodi_digit_img = findViewById(R.id.jodi_digit_img);
        single_digit_img = findViewById(R.id.single_digit_img);

        if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:SS"), Admin.open_end_time, "HH:mm:ss") == 1) {

            Make_disable(jodi_digit_img);
            Make_disable(half_snagam_img);
            Make_disable(full_sangam_img);
        }

        single_digit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "sd";
                Admin.auto_type = "sp";
                Admin.selected = "single_digit";
                Admin.digitLimit = 1;
                startActivity(new Intent(PlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });

        jodi_digit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "jd";
                Admin.auto_type = "dp";
                Admin.selected = "jodi_digit";
                Admin.digitLimit = 2;
                startActivity(new Intent(PlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });

        single_panna_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "sp";
                Admin.selected = "single_panna";
                Admin.digitLimit = 3;
                startActivity(new Intent(PlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });
        double_panna_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "dp";
                Admin.auto_type = "dp";
                Admin.selected = "double_panna";
                Admin.digitLimit = 2;
                startActivity(new Intent(PlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });

        tripple_panna_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "tp";
                Admin.auto_type = "tp";
                Admin.selected = "tripple_panna";
                Admin.digitLimit = 3;
                startActivity(new Intent(PlaceBidActivity.this, PlaceBidSingleDigit.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });

        half_snagam_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "hsd";
                Admin.auto_type = "hsd";
                Admin.selected = "half_sangam";
                Admin.digitLimit = 3;
                startActivity(new Intent(PlaceBidActivity.this, HalfSangam.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });

        full_sangam_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "fsd";
                Admin.auto_type = "fsd";
                Admin.selected = "full_sangam";
                Admin.digitLimit = 3;
                startActivity(new Intent(PlaceBidActivity.this, Sangam.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });

        odd_even_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.mtype = "oe";
                Admin.selected = "odd_even_sangam";
                Admin.digitLimit = 3;
                startActivity(new Intent(PlaceBidActivity.this, OddEvenSangam.class));
                Admin.OverrideNow(PlaceBidActivity.this);
            }
        });
    }

    private void Make_disable(View view) {
        view.setAlpha(0.3f);
        view.setEnabled(false);
        view.setClickable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}