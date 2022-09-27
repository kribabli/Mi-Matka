package com.teamup.matka.AllActivities;

import static com.teamup.matka.AllActivities.OddEvenSangam.HandleWalletDialog;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.teamup.app_sync.AppSyncAutoCompleteHelper;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDialog;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncErrorDialog;
import com.teamup.app_sync.AppSyncHandlers;
import com.teamup.app_sync.AppSyncInitialize;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncSuccessDialog;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class HalfSangam extends AppCompatActivity implements AppSyncErrorDialog.ErrorSayings, AppSyncSuccessDialog.SuccessSayings, AppSyncDialog.DialogClosed, AppSyncHandlers.runner, AppSyncAutoCompleteHelper.ItemSelected {

    AutoCompleteTextView close_panna_edt;
    EditText bid_points_edt, open_digit_edt;
    Button open_btn, close_btn, submit_btn, date_btn;
    String OpenClose = "open";
    TextView balance_txt, digit_txt, panna_txt;
    double balanceOfUser = 0.00;

    boolean open_eeeddo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sangam);
        Admin.HandleToolBar(this, Admin.toolTitle, findViewById(R.id.backImg), findViewById(R.id.headToolTxt));
        panna_txt = findViewById(R.id.panna_txt);
        digit_txt = findViewById(R.id.digit_txt);
        bid_points_edt = findViewById(R.id.bid_points_edt);
        close_panna_edt = findViewById(R.id.close_panna_edt);
        open_digit_edt = findViewById(R.id.open_digit_edt);

        date_btn = findViewById(R.id.date_btn);
        balance_txt = findViewById(R.id.balance_txt);
        submit_btn = findViewById(R.id.submit_btn);
        close_btn = findViewById(R.id.close_btn);
        open_btn = findViewById(R.id.open_btn);

        if (Admin.open_enbled) {
            ButtonSelected(open_btn);
        } else {
            open_btn.setEnabled(false);
            ButtonSelected(close_btn);
        }
        date_btn.setText("" + Admin.date);

        if (Admin.mtype.equalsIgnoreCase("hsd")) {
            open_btn.setVisibility(View.VISIBLE);
            close_btn.setVisibility(View.VISIBLE);
        }


        close_panna_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                open_eeeddo = false;
                get_data(close_panna_edt.getText().toString(), HalfSangam.this, close_panna_edt);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ModelGetProfile.load();
        AppSyncInitialize.init(this);
        ModelGetProfile.user_balance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                balanceOfUser = Double.parseDouble(s);
                balance_txt.setText("" + s);
            }
        });


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
        open_digit_edt.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(1)});

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String open = open_digit_edt.getText().toString();
                String close = close_panna_edt.getText().toString();
                String bid = bid_points_edt.getText().toString();

                if (CheckIfNotEmpty(open, "Please enter Open Digit")) {
                    if (CheckIfNotEmpty(close, "Please enter Close Panna")) {
                        if (CheckIfNotEmpty(bid, "Please enter Bid Points")) {

                            double bid_d = Double.parseDouble(bid);

                            if (bid_d >= 0) {
                                if (balanceOfUser >= Double.parseDouble(bid)) {

                                    AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(HalfSangam.this);
                                    as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
                                        @Override
                                        public void responser(String response, String datakey) {
                                            if (datakey.equalsIgnoreCase("OO88")) {
                                                AppSyncSuccessDialog.showDialog(HalfSangam.this, "Success", "bids placed successfully");
                                            }
                                        }
                                    });

                                    String cmd = Admin.BASE_URL + "api/bid?type=" + OpenClose + "&digit=" + open_digit_edt.getText().toString() + "&points=" +
                                            "" + bid_points_edt.getText().toString() + "&market=" + Admin.marketId + "&userid=" + Admin.tinyDB.getString("userid") + "&mtype=" + Admin.mtype + "&pana=" + close_panna_edt.getText().toString();

                                    as.getResponseFromUrlMethod(cmd, "OO88");
                                    Log.wtf("Hulk-128", cmd);

                                } else {
                                    HandleWalletDialog(HalfSangam.this, balanceOfUser + "");
                                    AppSyncToast.showToast(getApplicationContext(), "No enough points in wallet");
                                }
                            } else {
                                AppSyncToast.showToast(getApplicationContext(), "Please add more than 99 points");
                            }

                        }
                    }
                }

            }
        });
    }

    private boolean CheckIfNotEmpty(String checkString, String totastString) {
        if (!TextUtils.isEmpty(checkString)) {
            return true;
        } else {
            AppSyncToast.showToast(getApplicationContext(), totastString);
            return false;
        }
    }

    public static MutableLiveData<ArrayList<String>> auto_list = new MutableLiveData<>();

    public static void get_data(String key, Context context, AutoCompleteTextView edi) {
        ArrayList<String> list = new ArrayList<>();
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(context);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("RRRTT443")) {

                    try {
                        JSONObject ooo = new JSONObject(response);
                        JSONArray jsonArray = ooo.getJSONArray("result");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String digit = obj.getString("digit");
                                list.add(digit);
                            }
                            auto_list.setValue(list);
                            AppSyncAutoCompleteHelper as = new AppSyncAutoCompleteHelper();
                            as.set_plugin(edi, auto_list.getValue(), context);
                            as.set_threshold(2);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/suggestpanas?type=" + Admin.auto_type + "&q=" + key + "&userid=6", "RRRTT443");

    }


    private void ButtonSelected(Button btn_selected) {
        open_btn.setBackgroundColor(getResources().getColor(R.color.whiite));
        close_btn.setBackgroundColor(getResources().getColor(R.color.whiite));
        open_btn.setTextColor(getResources().getColor(R.color.black));
        close_btn.setTextColor(getResources().getColor(R.color.black));

        btn_selected.setBackgroundColor(getResources().getColor(R.color.Blue_Dress));
        btn_selected.setTextColor(getResources().getColor(R.color.white));
        OpenClose = btn_selected.getText().toString().toLowerCase();

        digit_txt.setText(btn_selected.getText().toString() + " Digit");
        if (btn_selected.getText().toString().equalsIgnoreCase("open")) {
            panna_txt.setText("Close Panna");
        } else {
            panna_txt.setText("Open Panna");
        }
    }

    @Override
    public void doneBtnClicked() {
        finish();
        Admin.OverrideNow(this);
    }

    @Override
    public void closed() {
        finish();
        Admin.OverrideNow(this);
    }


    @Override
    public void play(int code) {
        AppSyncPleaseWait.stopDialog(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }

    @Override
    public void item_selected(String selected) {
        if (open_eeeddo) {
            open_digit_edt.setText(selected);
        } else {
            close_panna_edt.setText(selected);
        }
    }
}