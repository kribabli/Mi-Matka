package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCustomDialog;
import com.teamup.app_sync.AppSyncDirectResponse;
import com.teamup.app_sync.AppSyncDirectResponseAsync;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncSuccessDialog;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.app_sync.AppSyncYesNoDialog;
import com.teamup.matka.AllAdapters.OddEvenAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class OddEvenSangam extends AppCompatActivity implements AppSyncYesNoDialog.dialogSayings, AppSyncSuccessDialog.SuccessSayings {

    Button open_btn, close_btn;
    RadioButton rb_odd, rb_even;
    RecyclerView recycler;
    EditText points_edt;
    Button submit_btn;
    OddEvenAdapter adapter;
    ArrayList<String> list;
    TextView balance_txt;
    double balanceOfUser = 0.00;
    String odd_or_even = "";
    String OpenClose = "open";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_odd_even_sangam);
        Admin.HandleToolBar(this, Admin.toolTitle, findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        balance_txt = findViewById(R.id.balance_txt);
        submit_btn = findViewById(R.id.submit_btn);
        points_edt = findViewById(R.id.points_edt);
        recycler = findViewById(R.id.recycler);
        rb_even = findViewById(R.id.rb_even);
        rb_odd = findViewById(R.id.rb_odd);
        open_btn = findViewById(R.id.open_btn);
        close_btn = findViewById(R.id.close_btn);

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

        if (Admin.open_enbled) {
            ButtonSelected(open_btn);
        } else {
            open_btn.setEnabled(false);
            ButtonSelected(close_btn);
        }

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<>();
        adapter = new OddEvenAdapter(list);
        recycler.setAdapter(adapter);
        recycler.setVisibility(View.GONE);

        LoadAllOdds();
        ModelGetProfile.load();
        ModelGetProfile.user_balance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                balanceOfUser = Double.parseDouble(s);
                balance_txt.setText("" + s + "/-");
            }
        });


        rb_even.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Admin.mtype = "oe";
                    LoadAllEvens();
                }
            }
        });
        rb_odd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Admin.mtype = "oo";
                    LoadAllOdds();
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


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String points = points_edt.getText().toString();

//                    Odd even submission to server
                if (!TextUtils.isEmpty(points)) {
                    double amountAdded = Double.parseDouble(points);

                    if (amountAdded >= 0) {
                        if (balanceOfUser >= amountAdded) {

                            Submit_odd_even();

                        } else {
                            HandleWalletDialog(OddEvenSangam.this, balanceOfUser + "");
                        }
                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "Please add more than 99 points");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter points");
                }
            }
        });
    }

    private void Submit_odd_even() {

        String cmd = Admin.BASE_URL + "api/bid?type=" + OpenClose + "&digit=" + 0 + "&points=" +
                "" + points_edt.getText().toString() + "&market=" + Admin.marketId + "&userid=" + Admin.tinyDB.getString("userid") + "&mtype=" + Admin.mtype;

        AppSyncDirectResponseAsync.getResponse(cmd);

        AppSyncSuccessDialog.showDialog(OddEvenSangam.this, "Success", "bids placed successfully");

        Log.wtf("Hulk-113", cmd);
    }

    public static void HandleWalletDialog(Context context, String rs) {
        AppSyncCustomDialog.showDialog(context, R.layout.dialog_no_en_balance, R.color.BlackTransparent, true);
        View vv = AppSyncCustomDialog.view2;
        TextView rs_txt = vv.findViewById(R.id.rs_txt);
        rs_txt.setText("" + rs);
        Button top_up_btn = vv.findViewById(R.id.top_up_btn);

        top_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncCustomDialog.stopPleaseWaitDialog(context);
                ((Activity) context).startActivity(new Intent(context, AddPoints.class));
            }
        });

    }

    private void LoadAllOdds() {
        list.clear();

        list.add("1");
        list.add("3");
        list.add("5");
        list.add("9");

        adapter.notifyDataSetChanged();
    }

    private void LoadAllEvens() {
        list.clear();

        list.add("0");
        list.add("2");
        list.add("4");
        list.add("6");
        list.add("8");

        adapter.notifyDataSetChanged();
    }

    @Override
    public void greenSignal() {
        list.remove(Admin.position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void greenSignal(String code) {

    }

    @Override
    public void redSignal() {

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
    public void doneBtnClicked() {
        finish();
        Admin.OverrideNow(this);
    }


}