package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncEncryptDecrypt;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncPHPMailer;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button register_btn, submitBtn;
    EditText email_edt, password_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_login);

        submitBtn = findViewById(R.id.submitBtn);
        password_edt = findViewById(R.id.password_edt);
        email_edt = findViewById(R.id.email_edt);
        register_btn = findViewById(R.id.register_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_edt.getText().toString();
                String password = password_edt.getText().toString();

                if (!TextUtils.isEmpty(email)) {
                    if (!TextUtils.isEmpty(password)) {
                        checkForthsiEmail(email, password);
                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "please enter Password");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "please enter mobile number properly");
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                Admin.OverrideNow(LoginActivity.this);
            }
        });
    }

    private void checkForthsiEmail(String email, String passwordS) {
        AppSyncPleaseWait.showDialog(LoginActivity.this, "Please wait..");

        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("1796")) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONObject finalObj = obj.getJSONObject("result");

                        String name = finalObj.getString("name");
                        String mobile = finalObj.getString("phone");
                        String email = finalObj.getString("email");
                        String password = finalObj.getString("pass");
                        String id = finalObj.getString("id");

                        if (AppSyncEncryptDecrypt.Encrypt(passwordS).equals(password)) {

                            Admin.tinyDB.putBoolean("login", true);
                            Admin.tinyDB.putString("email", email);
                            Admin.tinyDB.putString("userid", id);
                            Admin.tinyDB.putString("mobile", mobile);
                            Admin.tinyDB.putString("name", name);

                            AppSyncToast.showToast(getApplicationContext(), "Welcome Back..!!");

                            AppSyncPHPMailer.sendMail(LoginActivity.this, Admin.tinyDB.getString("email"), "Welcome to Matka App", "<h2>MT Software Solutions Welcomes you to this platform</h2>");
                            finishAffinity();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Admin.OverrideNow(LoginActivity.this);
                        } else {
                            AppSyncToast.showToast(getApplicationContext(), "Wrong password entered");
                            password_edt.setText("");
                        }

                        AppSyncPleaseWait.stopDialog(LoginActivity.this);

                    } catch (JSONException e) {
                        AppSyncToast.showToast(getApplicationContext(), "Please try with correct credentials");
                        e.printStackTrace();
                        AppSyncPleaseWait.stopDialog(LoginActivity.this);
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/login?password=" + AppSyncEncryptDecrypt.Encrypt(passwordS) + "&phone=" + email, "1796");

        Log.wtf("Hulk-125", Admin.BASE_URL + "api/login?password=" + AppSyncEncryptDecrypt.Encrypt(passwordS) + "&phone=" + email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}