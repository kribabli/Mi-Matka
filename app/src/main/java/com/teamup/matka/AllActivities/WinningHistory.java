package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncDirectResponseListenOffline;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncSimpleTextDialog;
import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllAdapters.WithdrawHistoryAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.Models.ModelWinningHistory;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WinningHistory extends AppCompatActivity {
    double balanceOfUser = 0.00;
    TextView balance_txt;
    ImageView noter_img;
    RecyclerView recycler;
    SwipeRefreshLayout swiper;
    WithdrawHistoryAdapter adapter;
//    variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        code from here
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_winning_history);
        Admin.HandleToolBar(this, "Winning History", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

//        code here
        balance_txt = findViewById(R.id.balance_txt);
        swiper = findViewById(R.id.swiper);
        adapter = new WithdrawHistoryAdapter(ModelWinningHistory.list);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        noter_img = findViewById(R.id.noter_img);
        noter_img.setVisibility(View.GONE);

        ModelGetProfile.load();
        ModelGetProfile.user_balance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                balanceOfUser = Double.parseDouble(s);
                balance_txt.setText("" + s + "/-");
            }
        });

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ModelWinningHistory.load();
            }
        });

//        Load model
        ModelWinningHistory.load();

//        after loading model
        ModelWinningHistory.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                swiper.setRefreshing(false);
                adapter.notifyDataSetChanged();

            }
        });

//        if data not avaialable
        ModelWinningHistory.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (AppSyncTextUtils.check_empty_and_null(s)) {
                    swiper.setRefreshing(false);
                    AppSyncToast.showToast(getApplicationContext(), s);
                    ModelWinningHistory.message.setValue("");
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }

}