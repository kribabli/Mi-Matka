package com.teamup.matka.AllActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListenNew;
import com.teamup.app_sync.AppSyncDirectResponseListenOffline;
import com.teamup.app_sync.AppSyncFigerShow;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncSimpleTextDialog;
import com.teamup.app_sync.AppSyncSuccessDialog;
import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.app_sync.AppSyncUpiPay;
import com.teamup.app_sync.Configs;
import com.teamup.matka.AllAdapters.UPIAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllModules.PhonePe;
import com.teamup.matka.Models.UpisModel;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddPoints extends AppCompatActivity implements AppSyncSuccessDialog.SuccessSayings {

    Button add_point_btn;
    EditText points_edt;
    RecyclerView recycler;
    UPIAdapter adapter;
    public static MutableLiveData<String> upi_live = new MutableLiveData<>();
    TextView upi_selected_txt;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_add_points);
        Admin.HandleToolBar(this, "Add Points", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        AddPoints.upi_live.setValue("Select UPI");

        swiper = findViewById(R.id.swiper);
        upi_selected_txt = findViewById(R.id.upi_selected_txt);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UPIAdapter(UpisModel.list);
        recycler.setAdapter(adapter);
        points_edt = findViewById(R.id.points_edt);
        add_point_btn = findViewById(R.id.add_point_btn);

        upi_live.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                upi_selected_txt.setText("" + s);
            }
        });

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                UpisModel.load();
            }
        });

        UpisModel.load();
        UpisModel.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                swiper.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

        add_point_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String points = points_edt.getText().toString();
                if (!TextUtils.isEmpty(points)) {
                    double dp = Double.parseDouble(points);
                    double min_amount = MainActivity.min_deposite;

                    if (dp >= min_amount) {
                        if (AppSyncTextUtils.check_empty_and_null(upi_live.getValue()) && !upi_live.getValue().equalsIgnoreCase("Select UPI")) {
                            AppSyncUpiPay.doPayment(AddPoints.this, getResources().getString(R.string.app_name), "" + upi_live.getValue(), points, "Adding points to wallet Userid " + Admin.tinyDB.getString("userid"));
                        } else {
                            AppSyncFigerShow.showOn(AddPoints.this, recycler, recycler);
                            AppSyncToast.showToast(getApplicationContext(), "Please select any one UPI to proceed");
                        }
                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "Add minimum " + min_amount + " ");
                    }
                } else {
                    AppSyncFigerShow.showOn(AddPoints.this, points_edt, points_edt);
                    AppSyncToast.showToast(getApplicationContext(), "Please enter points");
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSyncUpiPay.UPI_PAYMENT) {
            if (data != null) {
                if (AppSyncUpiPay.upiPaymentDataOperation(data, this)) {

//                    Payment Success

                    Admin.refresh_needed = true;
                    AppSyncDirectResponseListenNew.getResponseFromUrl(Admin.BASE_URL + "api/addFunds?userid=" + Admin.tinyDB.getString("userid") + "&amount=" + points_edt.getText().toString(), this, new AppSyncDirectResponseListenNew.ResponseListener() {
                        @Override
                        public void responser(String response) {
                            AppSyncSuccessDialog.showDialog(AddPoints.this, "Success", "Payment done successfully");
                        }
                    });

                } else {
//                    Payment Failed
                    AppSyncToast.showToast(getApplicationContext(), "Payment failed");
                }
            } else {


//                Payment failed
                AppSyncToast.showToast(getApplicationContext(), "Payment failed");

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }

    @Override
    public void doneBtnClicked() {
        finish();
        Admin.OverrideNow(this);
    }
}