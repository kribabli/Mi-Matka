package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncAlertWithList;
import com.teamup.app_sync.AppSyncBottomSheetDialog;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDialog;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncInitialize;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WalletActivity extends AppCompatActivity implements AppSyncAlertWithList.AlertDialogList {

    RelativeLayout add_funds_reler, withdraw_reler, withdraw_his_reler, winning_history_reler, transaction_reler;
    double userBalance = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_wallet);
        Admin.HandleToolBar(this, "Wallet", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        withdraw_his_reler = findViewById(R.id.withdraw_his_reler);
        transaction_reler = findViewById(R.id.transaction_reler);
        winning_history_reler = findViewById(R.id.winning_history_reler);
        withdraw_reler = findViewById(R.id.withdraw_reler);
        add_funds_reler = findViewById(R.id.add_funds_reler);
        add_funds_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletActivity.this, AddPoints.class));
                Admin.OverrideNow(WalletActivity.this);
            }
        });

        findViewById(R.id.bid_history_reler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletActivity.this, BidHistory.class));
                Admin.OverrideNow(WalletActivity.this);
            }
        });

        withdraw_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                boolean at_least_one_available = false;
                Log.wtf("Hulk-70", Admin.gpay);
                if (AppSyncTextUtils.check_empty(Admin.gpay) && !Admin.gpay.equalsIgnoreCase("null")) {
                    list.add("Gpay");
                    at_least_one_available = true;
                }
                if (AppSyncTextUtils.check_empty(Admin.phonepe) && !Admin.phonepe.equalsIgnoreCase("null")) {
                    list.add("Phonepe");
                    at_least_one_available = true;
                }
                if (AppSyncTextUtils.check_empty(Admin.paytm) && !Admin.paytm.equalsIgnoreCase("null")) {
                    list.add("Paytm");
                    at_least_one_available = true;
                }
                if (!at_least_one_available) {
                    AppSyncDialog.showDialog(WalletActivity.this, "Error", "No Payment details added\nadd payment details before withdrawing", "ok");
                } else {
                    AppSyncAlertWithList.showListDialog(WalletActivity.this, list, R.drawable.money_bag_img, "Select Payment Option");
                }
            }
        });
        winning_history_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletActivity.this, WinningHistory.class));
                Admin.OverrideNow(WalletActivity.this);
            }
        });

        transaction_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletActivity.this, TransactionHistory.class));
                Admin.OverrideNow(WalletActivity.this);
            }
        });

        withdraw_his_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletActivity.this, WithdrawHistory.class));
                Admin.OverrideNow(WalletActivity.this);
            }
        });
    }

    private void HandleWithdrawmaking(String selected) {
        AppSyncBottomSheetDialog.showRounded(WalletActivity.this, R.layout.bottom_withdraw_funds, true);
        View vv = AppSyncBottomSheetDialog.view2;
        Button withdraw_btn = vv.findViewById(R.id.withdraw_btn);
        AppSyncInitialize.init(this);
        TextView balance_txt = vv.findViewById(R.id.balance_txt);
        EditText edt_amout = vv.findViewById(R.id.edt_amout);

        LoadBalance(balance_txt);

        withdraw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amout = edt_amout.getText().toString();
                if (AppSyncTextUtils.check_empty(amout)) {
                    double mt = Double.parseDouble(amout);
                    if (userBalance >= 0) {

                        if (mt <= userBalance && userBalance >= MainActivity.min_withdraw) {
                            AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(WalletActivity.this);
                            as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
                                @Override
                                public void responser(String response, String datakey) {
                                    if (datakey.equalsIgnoreCase("ooisds")) {
                                        Admin.refresh_needed = true;
                                        AppSyncBottomSheetDialog.dismiss(WalletActivity.this);
                                        AppSyncToast.showToast(getApplicationContext(), "Withdraw request submitted successfully");
                                    }
                                }
                            });
                            as.getResponseFromUrlMethod(Admin.BASE_URL + "api/withdraw?userid=" + Admin.tinyDB.getString("userid") + "&amount=" + mt + "&to_ac=" + selected, "ooisds");
                        } else {
                            AppSyncToast.showToast(getApplicationContext(), "Enter lesser amount");
                        }

                    } else {
                        AppSyncBottomSheetDialog.dismiss(WalletActivity.this);
                        AppSyncDialog.showDialog(WalletActivity.this, "Warning", "1000 points needed to withdraw", "ok");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter amount to withdraw");
                }
            }
        });
    }

    private void LoadBalance(TextView balance_txt) {
        AppSyncPleaseWait.showDialog(WalletActivity.this, "Loading..");
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("pppo")) {
                    try {
                        JSONObject ooo = new JSONObject(response).getJSONObject("result");
                        String balance = ooo.getString("balance");
                        userBalance = Double.parseDouble(balance);
                        balance_txt.setText("" + balance);

                        AppSyncPleaseWait.stopDialog(WalletActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AppSyncPleaseWait.stopDialog(WalletActivity.this);
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/getProfile?userid=" + Admin.tinyDB.getString("userid"), "pppo");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }

    @Override
    public void selectedFromAlertDialogList(String selected) {
        HandleWithdrawmaking(selected);
    }

    @Override
    public void AlertDialogWithListDismissed() {

    }
}