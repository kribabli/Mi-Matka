package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllAdapters.WinningAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WinningRatioActivity extends AppCompatActivity {

    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_winning_ratio);
        Admin.HandleToolBar(this, "Game Rate", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));


        swiper = findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HandleGameWinRatioForAllBids();
            }
        });

        HandleGameWinRatioForAllBids();
    }

    private void HandleGameWinRatioForAllBids() {
        ArrayList<String> list = new ArrayList<>();
        RecyclerView recycler;
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        WinningAdapter adapter = new WinningAdapter(list);
        recycler.setAdapter(adapter);

        swiper.setRefreshing(true);
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("5137")) {
                    try {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        JSONObject oo = new JSONObject(response);
                        JSONArray jsonArray = oo.getJSONArray("result");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String title = obj.getString("desciption");

                                    list.add(title);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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

                    HandleStarLineWinRatio();
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/winRatio?type=all", "5137");

    }

    private void HandleStarLineWinRatio() {
        ArrayList<String> list = new ArrayList<>();
        RecyclerView recycler;
        recycler = findViewById(R.id.recycler2);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        WinningAdapter adapter = new WinningAdapter(list);
        recycler.setAdapter(adapter);

        swiper.setRefreshing(true);
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("dsds")) {
                    try {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        JSONObject oo = new JSONObject(response);
                        JSONArray jsonArray = oo.getJSONArray("result");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String title = obj.getString("desciption");

                                    list.add(title);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/winRatio?type=starline", "dsds");

        Log.wtf("Hulk-137", Admin.BASE_URL + "api/winRatio?type=starline");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }

}