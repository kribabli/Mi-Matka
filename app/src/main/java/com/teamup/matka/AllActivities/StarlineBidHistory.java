package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllAdapters.StarLineBidHistoryAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.StarGamesReq;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StarlineBidHistory extends AppCompatActivity {
    RecyclerView recycler;
    SwipeRefreshLayout swiper;
    ArrayList<StarGamesReq> list;
    StarLineBidHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);


        setContentView(R.layout.activity_starline_bid_history);
        Admin.HandleToolBar(this, "Starline Bid History", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new StarLineBidHistoryAdapter(list);
        recycler.setAdapter(adapter);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadAllCities();
            }
        });

        LoadAllCities();
    }

    private void LoadAllCities() {
        swiper.setRefreshing(true);
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                try {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    JSONObject ooo = new JSONObject(response);
                    JSONArray jsonArray = ooo.getJSONArray("result");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String id = obj.getString("id");
                            String result = obj.getString("result");
                            String dt = obj.getString("dt");
                            String stime = obj.getString("stime");

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
        });
        String cmd = Admin.BASE_URL + "api/starlines?userid=" + Admin.tinyDB.getString("userid") + "&starline=3&date=" + AppSyncCurrentDate.getDate();
        as.getResponseFromUrlMethod(cmd, "7230");

        Log.wtf("Hulk-95", cmd);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}