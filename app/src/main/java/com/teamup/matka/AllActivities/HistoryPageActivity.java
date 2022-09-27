package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

public class HistoryPageActivity extends AppCompatActivity {

    RelativeLayout transaction_reler, bid_history_reler, withdraw_his_reler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history_page);
        Admin.HandleToolBar(this, "History Page", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        withdraw_his_reler = findViewById(R.id.withdraw_his_reler);
        transaction_reler = findViewById(R.id.transaction_reler);
        bid_history_reler = findViewById(R.id.bid_history_reler);

//        bid_history_reler = findViewById(R.id.bid_history_reler);
        transaction_reler = findViewById(R.id.transaction_reler);

        bid_history_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryPageActivity.this, BidHistory.class));
                Admin.OverrideNow(HistoryPageActivity.this);
            }
        });
        transaction_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryPageActivity.this, TransactionHistory.class));
                Admin.OverrideNow(HistoryPageActivity.this);
            }
        });

        withdraw_his_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryPageActivity.this, WithdrawHistory.class));
                Admin.OverrideNow(HistoryPageActivity.this);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}