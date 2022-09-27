package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;

public class RouletteGame extends AppCompatActivity {

    Button play_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_roulette_game);

        play_btn = findViewById(R.id.play_btn);

        LuckyWheelView luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);
        List<LuckyItem> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LuckyItem luckyItem = new LuckyItem();
            luckyItem.topText = "" + i;
            Random rnd = new Random();
            luckyItem.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            data.add(luckyItem);
        }
        luckyWheelView.setData(data);
        luckyWheelView.setRound(5);
        luckyWheelView.setTouchEnabled(false);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luckyWheelView.startLuckyWheelWithRandomTarget();
            }
        });


// listener after finish lucky wheel
        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                // do something with index
                startActivity(new Intent(RouletteGame.this, RouletteGameOptions.class));
                Admin.OverrideNow(RouletteGame.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }

}