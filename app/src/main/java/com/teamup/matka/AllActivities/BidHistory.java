package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDaysTheory;
import com.teamup.matka.AllAdapters.BidHistoryAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.Models.BidHistoryModel;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.R;

import java.text.ParseException;
import java.util.Calendar;

public class BidHistory extends AppCompatActivity {

    RecyclerView recycler;
    SwipeRefreshLayout swiper;
    BidHistoryAdapter adapter;
    TextView balance_txt, end_date_txt, start_date_txt, search_txt;
    String start_date = "", end_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_bid_history);
        Admin.HandleToolBar(this, "Bid History", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        search_txt = findViewById(R.id.search_txt);
        end_date_txt = findViewById(R.id.end_date_txt);
        start_date_txt = findViewById(R.id.start_date_txt);
        balance_txt = findViewById(R.id.balance_txt);
        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BidHistoryAdapter(BidHistoryModel.list);
        recycler.setAdapter(adapter);

        search_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_date = start_date_txt.getText().toString();
                end_date = end_date_txt.getText().toString();
                BidHistoryModel.load(start_date, end_date);
            }
        });

        end_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar newCalendar = Calendar.getInstance();
                final DatePickerDialog StartTime = new DatePickerDialog(BidHistory.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        try {
                            end_date_txt.setText(AppSyncDaysTheory.ConvertTo("d", "" + dayOfMonth, "dd") + "/"
                                    + AppSyncDaysTheory.ConvertTo("m", "" + (monthOfYear + 1), "mm") + "/"
                                    + year + "");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                StartTime.show();
            }
        });

        start_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar newCalendar = Calendar.getInstance();
                final DatePickerDialog StartTime = new DatePickerDialog(BidHistory.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        try {
                            start_date_txt.setText(AppSyncDaysTheory.ConvertTo("d", "" + dayOfMonth, "dd") + "/"
                                    + AppSyncDaysTheory.ConvertTo("m", "" + (monthOfYear + 1), "mm") + "/"
                                    + year + "");


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                StartTime.show();
            }
        });

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BidHistoryModel.load(start_date, end_date);
            }
        });

        BidHistoryModel.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                swiper.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

        ModelGetProfile.load();

        ModelGetProfile.user_balance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                balanceOfUser = Double.parseDouble(s);
                balance_txt.setText("" + s + "/-");
            }
        });
    }


    double balanceOfUser = 0.00;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}