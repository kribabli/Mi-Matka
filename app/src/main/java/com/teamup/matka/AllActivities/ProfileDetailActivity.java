package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponse;
import com.teamup.app_sync.AppSyncDirectResponseAsync;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncEncryptDecrypt;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncSuccessDialog;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileDetailActivity extends AppCompatActivity implements AppSyncSuccessDialog.SuccessSayings {

    TextView username_txt, fullname_txt, mobile_txt;
    Button submit_btn, bank_and_other_details_btn;
    EditText new_pass_edt, confirm_password_edt;
    Switch notifications_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_profile_detail);
        Admin.HandleToolBar(this, "Profile", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));

        notifications_switch = findViewById(R.id.notifications_switch);
        notifications_switch.setChecked(true);
        bank_and_other_details_btn = findViewById(R.id.bank_and_other_details_btn);
        confirm_password_edt = findViewById(R.id.confirm_password_edt);
        new_pass_edt = findViewById(R.id.new_pass_edt);
        submit_btn = findViewById(R.id.submit_btn);
        mobile_txt = findViewById(R.id.mobile_txt);
        username_txt = findViewById(R.id.username_txt);
        fullname_txt = findViewById(R.id.fullname_txt);

        notifications_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Admin.tinyDB.putBoolean("notifs", isChecked);
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = new_pass_edt.getText().toString().trim();
                String confirmPass = confirm_password_edt.getText().toString().trim();

                if (!TextUtils.isEmpty(newPass)) {
                    if (!TextUtils.isEmpty(confirmPass)) {

                        if (newPass.equals(confirmPass)) {
                            AppSyncDirectResponseAsync.getResponse(Admin.BASE_URL + "api/updatepassword?userid=" + Admin.tinyDB.getString("userid") + "&password=" + AppSyncEncryptDecrypt.Encrypt(confirmPass));
                            AppSyncSuccessDialog.showDialog(ProfileDetailActivity.this, "Success", "Profile information updated successfully");
                        } else {
                            AppSyncToast.showToast(getApplicationContext(), "Please enter similar password in Confirm Password field");
                        }
                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "Please enter Confirm password");
                    }

                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter new password");
                }
            }
        });

        bank_and_other_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileDetailActivity.this, BankAndOtherDeatails.class));
                Admin.OverrideNow(ProfileDetailActivity.this);
            }
        });

        LoadProfile();
    }

    private void LoadProfile() {
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("HHU")) {
                    try {
                        JSONObject oo = new JSONObject(response).getJSONObject("result");
                        String id = oo.getString("id");
                        String email = oo.getString("email");
                        String name = oo.getString("name");
                        String pass = oo.getString("pass");
                        String usertype = oo.getString("usertype");
                        String imgpath = oo.getString("imgpath");
                        String phone = oo.getString("phone");

                        Admin.tinyDB.putString("name", name);

                        username_txt.setText("Username : " + phone);
                        fullname_txt.setText("FullName : " + name);
                        mobile_txt.setText("Mobile No. : " + phone);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/getProfile?userid=" + Admin.tinyDB.getString("userid"), "HHU");
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