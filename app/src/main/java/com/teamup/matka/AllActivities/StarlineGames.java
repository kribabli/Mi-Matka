package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncSimpleTextDialog;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllAdapters.StarGamesAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.StarGamesReq;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StarlineGames extends AppCompatActivity {

    RecyclerView recycler;
    Button bid_hist_btn, result_history_btn, starline_result_chart;
    SwipeRefreshLayout swiper;
    ArrayList<StarGamesReq> list;
    StarGamesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_starline_games);
        Admin.HandleToolBar(this, "StarLine Games", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        starline_result_chart = findViewById(R.id.starline_result_chart);
        result_history_btn = findViewById(R.id.result_history_btn);
        bid_hist_btn = findViewById(R.id.bid_hist_btn);
        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new StarGamesAdapter(list);
        recycler.setAdapter(adapter);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadStarGames();
            }
        });

        LoadStarGames();

        bid_hist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StarlineGames.this, StarlineBidHistory.class));
                Admin.OverrideNow(StarlineGames.this);
            }
        });
        result_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StarlineGames.this, StarlineResultHistory.class));
                Admin.OverrideNow(StarlineGames.this);
            }
        });

        starline_result_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncSimpleTextDialog.showTextDialog(StarlineGames.this, "Checkout Starline Result Chart by clicking below link\n\nhttp://adminapp.tech/matka/sresult");
            }
        });
    }

    private void LoadStarGames() {
        swiper.setRefreshing(true);
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("945")) {
                    try {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        JSONObject ooo = new JSONObject(response);
                        JSONArray djsonArray = ooo.getJSONArray("result");
                        if (djsonArray.length() > 0) {
                            for (int i = 0; i < djsonArray.length(); i++) {
                                JSONObject obj = djsonArray.getJSONObject(i);
                                String id = obj.getString("id");
                                String result = obj.getString("result");
                                String stime = obj.getString("stime");
                                String dt = obj.getString("dt");

                                StarGamesReq sgr = new StarGamesReq(id, dt, result, stime);
                                list.add(sgr);
                                adapter.notifyDataSetChanged();
                            }
                            swiper.setRefreshing(false);
                        } else {
//                            Empty
                            AppSyncToast.showToast(getApplicationContext(), "Nothing to show");
                            swiper.setRefreshing(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        swiper.setRefreshing(false);
                    }
                }
            }
        });
        String cmd = Admin.BASE_URL + "api/starlines?userid=" + Admin.tinyDB.getString("userid");
        as.getResponseFromUrlMethod(cmd, "945");

        Log.wtf("Hulk-127", cmd);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }


}