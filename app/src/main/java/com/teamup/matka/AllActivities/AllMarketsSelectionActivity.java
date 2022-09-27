package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllAdapters.SelectMarketAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.MarketsReq;
import com.teamup.matka.AllReqs.SelectMarketReq;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllMarketsSelectionActivity extends AppCompatActivity {

    RecyclerView recycler;
    SwipeRefreshLayout swiper;
    ArrayList<SelectMarketReq> list;
    SelectMarketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_all_markets_selection);
        Admin.HandleToolBar(this, "Markets", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new SelectMarketAdapter(list);
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
                if (datakey.equalsIgnoreCase("9283")) {
                    try {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String name = obj.getString("name");
                                String id = obj.getString("id");

                                SelectMarketReq mr = new SelectMarketReq(id, name);
                                list.add(mr);
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
        as.getResponseFromUrlMethod(Admin.BASE_URL + "", "9283");
    }
}