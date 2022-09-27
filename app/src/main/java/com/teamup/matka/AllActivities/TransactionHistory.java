package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncSimpleTextDialog;
import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllAdapters.TrnasactionAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.TransactionReq;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.Models.ModelTransactions;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransactionHistory extends AppCompatActivity {
    RecyclerView recycler;
    SwipeRefreshLayout swiper;
    TrnasactionAdapter adapter;
    TextView banalance_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);


        setContentView(R.layout.activity_transaction_history);
        Admin.HandleToolBar(this, "Transaction History", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        banalance_txt = findViewById(R.id.banalance_txt);
        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrnasactionAdapter(ModelTransactions.list);
        recycler.setAdapter(adapter);

        banalance_txt.setText("" + ModelGetProfile.user_balance.getValue());

//        swipe to refresh code here
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ModelTransactions.load();
            }
        });

//        load or call model here
//        ModelTransactions.load();

//        after loading data from api model
        ModelTransactions.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                swiper.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

//        if data not available
        ModelTransactions.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (AppSyncTextUtils.check_empty_and_null(s)) {
                    swiper.setRefreshing(false);
                    AppSyncSimpleTextDialog.showTextDialog(TransactionHistory.this, s);
                    ModelTransactions.message.setValue("");
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