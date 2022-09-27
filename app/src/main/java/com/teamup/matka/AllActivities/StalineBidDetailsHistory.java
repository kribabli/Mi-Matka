package com.teamup.matka.AllActivities;

import static com.teamup.app_sync.AppSyncJsonArray.jsonArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncNewPleaseWait;
import com.teamup.matka.AllAdapters.StarlineBidDetialAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.StarlineBidHistoryReq;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StalineBidDetailsHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    StarlineBidDetialAdapter adapter;
    ArrayList<StarlineBidHistoryReq> list;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_staline_bid_details_history);
        Admin.HandleToolBar(this, "Starline Bid History", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        swiper = findViewById(R.id.swiper);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new StarlineBidDetialAdapter(list);
        recyclerView.setAdapter(adapter);

        LoadData();

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });
    }

    private void LoadData() {

        AppSyncNewPleaseWait.showDialog(this, "Loading..", R.color.colorPrimaryDark, 0, 5000);


        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("7230")) {
                    try {
                        JSONArray jsonArray = new JSONObject(response).getJSONArray("result");
                        if (jsonArray.length() > 0) {
                            list.clear();
                            adapter.notifyDataSetChanged();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id = obj.getString("id");
                                String marketName = obj.getString("marketName");
                                String dt = obj.getString("dt");
                                String starline = obj.getString("starline");
                                String digit = obj.getString("digit");
                                String cdigit = obj.getString("cdigit");
                                String points = obj.getString("points");
                                String mtype = obj.getString("mtype");
                                String pana = obj.getString("pana");
                                String win = obj.getString("win");

                                StarlineBidHistoryReq sgr = new StarlineBidHistoryReq(
                                        marketName, id, starline, digit, cdigit, points, dt, mtype, pana, win
                                );
                                list.add(sgr);

                                swiper.setRefreshing(false);
                                adapter.notifyItemInserted(i);

                            }
                            swiper.setRefreshing(false);

                            AppSyncNewPleaseWait.stopDialog(StalineBidDetailsHistory.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        swiper.setRefreshing(false);
                        AppSyncNewPleaseWait.stopDialog(StalineBidDetailsHistory.this);

                    }
                }
            }
        });
        String cmd = Admin.BASE_URL + "api/sbidHistory?userid=" + Admin.tinyDB.getString("userid") + "&starline=" + Admin.marketId + "&date=" + AppSyncCurrentDate.getDateTimeInFormat("yyyy-MM-dd");
        as.getResponseFromUrlMethod(cmd, "7230");

        Log.wtf("Hulk-95", cmd);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }

}