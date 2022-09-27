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
import com.teamup.matka.AllAdapters.StarlineResultHistoryAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.StarGamesReq;
import com.teamup.matka.AllReqs.StarlineResultReq;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class StarlineResultHistory extends AppCompatActivity {

    RecyclerView recycler;
    SwipeRefreshLayout swiper;
    ArrayList<StarlineResultReq> list;
    StarlineResultHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_starline_result_history);
        Admin.HandleToolBar(this, "Starline Result History", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new StarlineResultHistoryAdapter(list);
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
                if (datakey.equalsIgnoreCase("1908")) {
                    try {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        JSONObject ooo = new JSONObject(response).getJSONObject("result");

                        Iterator<?> keys = ooo.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            Log.wtf("Hulk-75", key);

                            StarlineResultReq sgr = new StarlineResultReq(key, ooo.getString(key));
                            list.add(sgr);
                            adapter.notifyDataSetChanged();


                        }


                        swiper.setRefreshing(false);

                    } catch (JSONException e) {
                        Log.wtf("Hulk-87", e.getMessage());
                        swiper.setRefreshing(false);
                    }
                }
            }
        });
        String cmd = Admin.BASE_URL + "api/starlineResultHistory?userid=" + Admin.tinyDB.getString("userid");
        as.getResponseFromUrlMethod(cmd, "1908");

        Log.wtf("Hulk-96", cmd);
    }
}