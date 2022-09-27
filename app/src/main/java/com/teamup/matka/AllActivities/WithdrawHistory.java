package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncAlertWithList;
import com.teamup.app_sync.AppSyncBottomSheetDialog;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCustomDialog;
import com.teamup.app_sync.AppSyncDialog;
import com.teamup.app_sync.AppSyncDirectResponseAsync;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncDirectResponseListenOffline;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncInitialize;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncSimpleTextDialog;
import com.teamup.app_sync.AppSyncSuccessDialog;
import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllAdapters.WithdrawHistoryAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.WithdrawReq;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.Models.ModelWithdrawHistory;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WithdrawHistory extends AppCompatActivity implements AppSyncSuccessDialog.SuccessSayings,
        AppSyncSimpleTextDialog.SimpleTextDialog, AppSyncAlertWithList.AlertDialogList {


    double balanceOfUser = 0.00;
    TextView balance_txt, withdraw_txt;
    WithdrawHistoryAdapter adapter;
    ImageView noter_img;
    RecyclerView recycler;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_withdraw_history);
        Admin.HandleToolBar(this, "Withdraw History", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WithdrawHistoryAdapter(ModelWithdrawHistory.list);
        recycler.setAdapter(adapter);
        noter_img = findViewById(R.id.noter_img);
        noter_img.setVisibility(View.GONE);
        balance_txt = findViewById(R.id.balance_txt);
        withdraw_txt = findViewById(R.id.withdraw_txt);
        swiper = findViewById(R.id.swiper);

        ModelWithdrawHistory.load();

//        After loading data if change avail
        ModelWithdrawHistory.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                swiper.setRefreshing(false);
                adapter = new WithdrawHistoryAdapter(ModelWithdrawHistory.list);
                recycler.setAdapter(adapter);
            }
        });

//        if data not available
        ModelWithdrawHistory.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (AppSyncTextUtils.check_empty_and_null(s)) {
                    swiper.setRefreshing(false);
                    AppSyncSimpleTextDialog.showTextDialog(WithdrawHistory.this, s);
                    ModelWithdrawHistory.message.setValue("");
                }
            }
        });

        withdraw_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (balanceOfUser > 0) {
//                    User have more than 0 points in wallet

                    ArrayList<String> list = new ArrayList<>();
                    boolean at_least_one_available = false;
                    if (AppSyncTextUtils.check_empty(Admin.gpay) && !Admin.gpay.equalsIgnoreCase("null")) {
                        list.add("Gpay");
                        at_least_one_available = true;
                    }
                    if (AppSyncTextUtils.check_empty(Admin.phonepe) && !Admin.gpay.equalsIgnoreCase("null")) {
                        list.add("Phonepe");
                        at_least_one_available = true;
                    }
                    if (AppSyncTextUtils.check_empty(Admin.paytm) && !Admin.gpay.equalsIgnoreCase("null")) {
                        list.add("Paytm");
                        at_least_one_available = true;
                    }

                    if (!at_least_one_available) {
                        AppSyncDialog.showDialog(WithdrawHistory.this, "Error", "No Payment details added\nadd payment details before withdrawing", "ok");
                    } else {
                        AppSyncAlertWithList.showListDialog(WithdrawHistory.this, list, R.drawable.money_bag_img, "Select Payment Option");
                    }
                } else {
//                    user has no balance in wallet
                    Show_empty_wallet_dialog();
                }
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

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ModelWithdrawHistory.load();
            }
        });


    }

    private void LoadBalance(TextView balance_txt) {
        AppSyncPleaseWait.showDialog(this, "Loading..");
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("pppo")) {
                    try {
                        JSONObject ooo = new JSONObject(response).getJSONObject("result");
                        String balance = ooo.getString("balance");
                        balanceOfUser = Double.parseDouble(balance);
                        balance_txt.setText("" + balance);

                        AppSyncPleaseWait.stopDialog(WithdrawHistory.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AppSyncPleaseWait.stopDialog(WithdrawHistory.this);
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/getProfile?userid=" + Admin.tinyDB.getString("userid"), "pppo");
    }


    private void HandleWithdrawmaking(String selected) {
        AppSyncBottomSheetDialog.showRounded(this, R.layout.bottom_withdraw_funds, true);
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
                    if (balanceOfUser >= 0) {

                        if (mt <= balanceOfUser && balanceOfUser >= MainActivity.min_withdraw) {
                            AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(WithdrawHistory.this);
                            as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
                                @Override
                                public void responser(String response, String datakey) {
                                    if (datakey.equalsIgnoreCase("ooisds")) {
                                        Admin.refresh_needed = true;
                                        AppSyncBottomSheetDialog.dismiss(WithdrawHistory.this);
                                        AppSyncToast.showToast(getApplicationContext(), "Withdraw request submitted successfully");
                                    }
                                }
                            });
                            as.getResponseFromUrlMethod(Admin.BASE_URL + "api/withdraw?userid=" + Admin.tinyDB.getString("userid") + "&amount=" + mt + "&to_ac=" + selected, "ooisds");
                        } else {
                            AppSyncToast.showToast(getApplicationContext(), "Enter lesser amount");
                        }

                    } else {
                        AppSyncBottomSheetDialog.dismiss(WithdrawHistory.this);
                        AppSyncDialog.showDialog(WithdrawHistory.this, "Warning", "1000 points needed to withdraw", "ok");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter amount to withdraw");
                }
            }
        });
    }


    private void Withdraw_api_call(String selected) {

        AppSyncDirectResponseAsync.getResponse(Admin.BASE_URL + "api/withdraw?userid=" + Admin.tinyDB.getString("userid") + "&amount=" + balanceOfUser + "&to_ac=" + selected);
        AppSyncSuccessDialog.showDialog(this, "Success", "Withdraw points success");
    }

    private void Show_empty_wallet_dialog() {
        AppSyncCustomDialog.showDialog(this, R.layout.dialog_empty_wallet, R.color.BlackTransparent, true);
        View vv = AppSyncCustomDialog.view2;
        Button ok_btn = vv.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncCustomDialog.stopPleaseWaitDialog(WithdrawHistory.this);
            }
        });
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

    @Override
    public void selectedFromAlertDialogList(String selected) {
        HandleWithdrawmaking(selected);

    }

    @Override
    public void AlertDialogWithListDismissed() {

    }

    @Override
    public void dialog_closed() {

    }
}