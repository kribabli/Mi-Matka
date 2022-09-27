package com.teamup.matka.AllActivities;

import static android.util.Log.*;
import static android.util.Log.wtf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDaysTheory;
import com.teamup.app_sync.AppSyncDialog;
import com.teamup.app_sync.AppSyncDirectResponse;
import com.teamup.app_sync.AppSyncDirectResponseAsync;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncErrorDialog;
import com.teamup.app_sync.AppSyncHandlers;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncSuccessDialog;
import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.app_sync.AppSyncYesNoDialog;
import com.teamup.app_sync.Configs;
import com.teamup.matka.AllAdapters.PointsAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.PointsReq;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.teamup.matka.AllActivities.OddEvenSangam.HandleWalletDialog;
import static com.teamup.matka.AllActivities.Sangam.get_data;

public class PlaceBidSingleDigit extends AppCompatActivity implements AppSyncErrorDialog.ErrorSayings, AppSyncSuccessDialog.SuccessSayings, AppSyncDialog.DialogClosed, AppSyncYesNoDialog.dialogSayings, AppSyncHandlers.runner {

    AutoCompleteTextView digit_edt;
    Button open_btn, close_btn, submit_btn, date_btn, add_btn;
    EditText points_edt;
    String OpenClose = "open";
    TextView balance_txt, total_txt;
    double balanceOfUser = -1.00;
    RecyclerView recycler;
    ArrayList<PointsReq> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_rajdhani_day_single_digit);
        Admin.HandleToolBar(this, Admin.toolTitle, findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        total_txt = findViewById(R.id.total_txt);
        add_btn = findViewById(R.id.add_btn);
        recycler = findViewById(R.id.recycler);
        date_btn = findViewById(R.id.date_btn);
        balance_txt = findViewById(R.id.balance_txt);
        submit_btn = findViewById(R.id.submit_btn);
        points_edt = findViewById(R.id.points_edt);
        digit_edt = findViewById(R.id.digit_edt);
        close_btn = findViewById(R.id.close_btn);
        open_btn = findViewById(R.id.open_btn);


//        set date of selected market
        date_btn.setText("" + Admin.date);

        ModelGetProfile.load();
        ModelGetProfile.user_balance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                balanceOfUser = Double.parseDouble(s);
                balance_txt.setText("" + s);
            }
        });


//        if starline selected no need of open / close buttons
        if (Admin.starline) {
            open_btn.setVisibility(View.GONE);
            open_btn.setVisibility(View.GONE);
            close_btn.setVisibility(View.GONE);
        }

        if (Admin.open_enbled) {
            ButtonSelected(open_btn);
        } else {
            open_btn.setEnabled(false);
            ButtonSelected(close_btn);
        }

        if (Admin.mtype.equalsIgnoreCase("jd")) {
            open_btn.setVisibility(View.GONE);
            close_btn.setVisibility(View.GONE);
        }

        open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonSelected(open_btn);
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonSelected(close_btn);
            }
        });

        if (!Admin.auto_type.equalsIgnoreCase("dp")) {
            digit_edt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String digi = digit_edt.getText().toString();
                    if (AppSyncTextUtils.check_empty(digi)) {
                        get_data(digit_edt.getText().toString(), PlaceBidSingleDigit.this, digit_edt);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                        return "";
                    }
                }

                return null;
            }
        };
        if (Admin.auto_type.equalsIgnoreCase("dp")) {
            digit_edt.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(3)});
        } else {
            digit_edt.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(Admin.digitLimit)});
        }
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OpenClose.equalsIgnoreCase("open")) {
                    if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:ss"), Admin.open_end_time, "HH:mm:ss") == 1) {
                        do_close = true;
                        AppSyncDialog.showDialog(PlaceBidSingleDigit.this, "Open Finished", "Open is closed for bidding", "ok");
                        return;
                    }
                }

                if (OpenClose.equalsIgnoreCase("close")) {
                    if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:ss"), Admin.close_end_time, "HH:mm:ss") == 1) {
                        do_close = true;
                        AppSyncDialog.showDialog(PlaceBidSingleDigit.this, "Close Finished", "Close is closed for bidding", "ok");
                        return;
                    }
                }
                if (list.size() > 0) {
                    double amountAdded = 0.00;
                    for (int i = 0; i < list.size(); i++) {
                        amountAdded = amountAdded + Double.parseDouble(list.get(i).getPoints());
                    }

                    if (amountAdded >= 0) {
                        if (balanceOfUser >= amountAdded) {

                            submit_btn.setEnabled(false);

                            for (int i = 0; i < list.size(); i++) {

                                String cmd = "";
                                if (Admin.starline) {

                                    cmd = Admin.BASE_URL + "api/sbid?type=" + list.get(i).getType() + "&digit=" + list.get(i).getDigit() + "&points=" +
                                            "" + list.get(i).getPoints() + "&market=" + Admin.marketId + "&userid=" + Admin.tinyDB.getString("userid") + "&mtype=" + Admin.mtype + "&pana=0&cdigit=0&starline=" + Admin.marketId;

                                } else {
                                    cmd = Admin.BASE_URL + "api/bid?type=" + list.get(i).getType() + "&digit=" + list.get(i).getDigit() + "&points=" +
                                            "" + list.get(i).getPoints() + "&market=" + Admin.marketId + "&userid=" + Admin.tinyDB.getString("userid") + "&mtype=" + Admin.mtype;
                                }

                                Admin.refresh_needed = true;
                                AppSyncDirectResponseAsync.getResponse(cmd);
                                wtf("Hulk-113", cmd);
                            }

                            AppSyncSuccessDialog.showDialog(PlaceBidSingleDigit.this, "Success", "bids placed successfully");

                        } else if (balanceOfUser < 0) {
                            do_close = true;
                            AppSyncDialog.showDialog(PlaceBidSingleDigit.this, "Failed to load Balance", "Your balance was not loaded properly, press ok and try again", "ok1");
                        } else {

                            HandleWalletDialog(PlaceBidSingleDigit.this, balanceOfUser + "");
                            do_close = true;
                            AppSyncDialog.showDialog(PlaceBidSingleDigit.this, "Not Enough Balance", "Please add funds in your wallet to place your bidding", "ok");
                        }
                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "Please add more than 99 points");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please add bid before submission");
                }

            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String points = points_edt.getText().toString();
                String digits = digit_edt.getText().toString();

                if (!TextUtils.isEmpty(points)) {
                    if (!TextUtils.isEmpty(digits)) {

                        int min_points = Integer.parseInt(Configs.getValue(PlaceBidSingleDigit.this, "min_bid_place_amount"));
                        int points_i = Integer.parseInt(points);

                        if (points_i < min_points) {
                            AppSyncToast.showToast(getApplicationContext(), "Add more than " + min_points + " points");
                            return;
                        }

                        if (Admin.selected.equalsIgnoreCase("single_panna")) {
                            if (!check(digits)) {
                                PointsReq pr = new PointsReq("" + digits, "" + points, "" + OpenClose);
                                list.add(pr);
                                refrehsPointsAdded();
                            } else {
                                AppSyncToast.showToast(getApplicationContext(), "do not enter repeated digits");
                            }
                        } else if (Admin.selected.equalsIgnoreCase("double_panna")) {
                            if (check(digits)) {
                                PointsReq pr = new PointsReq("" + digits, "" + points, "" + OpenClose);
                                list.add(pr);
                                refrehsPointsAdded();
                            } else {
                                AppSyncToast.showToast(getApplicationContext(), "Please enter 2 repeated digits");
                            }
                        } else if (Admin.selected.equalsIgnoreCase("tripple_panna")) {
                            if (checkThreeSame(digits)) {
                                PointsReq pr = new PointsReq("" + digits, "" + points, "" + OpenClose);
                                list.add(pr);
                                refrehsPointsAdded();
                            } else {
                                AppSyncToast.showToast(getApplicationContext(), "Please enter 3 repeated digits");
                            }
                        } else {
                            PointsReq pr = new PointsReq("" + digits, "" + points, "" + OpenClose);
                            list.add(pr);
                            refrehsPointsAdded();
                        }


                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "Please enter Digits");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter Points");
                }
            }
        });

        refrehsPointsAdded();
    }

    boolean do_close = false;

    private boolean checkThreeSame(CharSequence g) {
        char first, second, third;
        try {
            if ((g.charAt(0) + "").equals((g.charAt(1) + "")) && (g.charAt(1) + "").equals((g.charAt(2) + ""))) {
                return true;
            }
        } catch (Exception e) {
            do_close = false;
            AppSyncDialog.showDialog(PlaceBidSingleDigit.this, "Failure", "Place three same digits", "ok");
        }
        return false;
    }

    private void refrehsPointsAdded() {
        points_edt.setText("");
        digit_edt.setText("");

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        PointsAdapter adapter = new PointsAdapter(list);
        recycler.setAdapter(adapter);

        try {
            double tot = 0.00;
            for (int i = 0; i < list.size(); i++) {
                tot = tot + Double.parseDouble(list.get(i).getPoints());
            }

            total_txt.setText("" + tot);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (list.size() > 0) {
            submit_btn.setEnabled(true);
            submit_btn.setAlpha(1f);
        } else {
            submit_btn.setEnabled(false);
            submit_btn.setAlpha(0.3f);
        }
    }


    private void ButtonSelected(Button btn_selected) {
        open_btn.setBackgroundColor(getResources().getColor(R.color.whiite));
        close_btn.setBackgroundColor(getResources().getColor(R.color.whiite));
        open_btn.setTextColor(getResources().getColor(R.color.black));
        close_btn.setTextColor(getResources().getColor(R.color.black));

        btn_selected.setBackgroundColor(getResources().getColor(R.color.Blue_Dress));
        btn_selected.setTextColor(getResources().getColor(R.color.white));
        OpenClose = btn_selected.getText().toString().toLowerCase();
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
    public void closed() {
        if (do_close) {
            finish();
            Admin.OverrideNow(this);
        }
    }

    @Override
    public void greenSignal() {
        list.remove(Admin.position);
        refrehsPointsAdded();
    }

    @Override
    public void greenSignal(String code) {

    }

    @Override
    public void redSignal() {

    }

    public static boolean check(CharSequence g) {
        for (int i = 0; i < g.length(); i++) {
            for (int j = i + 1; j < g.length(); j++) {
                if (g.charAt(i) == g.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppSyncHandlers.run(PlaceBidSingleDigit.this, 4000, 55);
    }

    @Override
    public void play(int code) {
        AppSyncPleaseWait.stopDialog(this);
    }

}