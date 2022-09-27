package com.teamup.matka.AllActivities;

import static com.appolica.flubber.Flubber.AnimationPreset.ALPHA;
import static com.teamup.matka.R.color.BlackTransparent;
import static com.teamup.matka.R.id.close_img;
import static com.teamup.matka.R.id.liner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.appolica.flubber.Flubber;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCustomDialog;
import com.teamup.app_sync.AppSyncDirectResponse;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncEncryptDecrypt;
import com.teamup.app_sync.AppSyncPHPMailer;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncRandomNumber;
import com.teamup.app_sync.AppSyncSuccessDialog;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements AppSyncSuccessDialog.SuccessSayings {

    EditText fullname_edt, mobile_edt, email_edt, password_edt;
    Button registerBtn;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        context = this;

        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.registerBtn);
        fullname_edt = findViewById(R.id.fullname_edt);
        mobile_edt = findViewById(R.id.mobile_edt);
        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);

        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullname_edt.getText().toString();
                String mobileNo = mobile_edt.getText().toString();
                String email = email_edt.getText().toString();
                String password = password_edt.getText().toString();

                if (!TextUtils.isEmpty(fullName)) {
                    if (!TextUtils.isEmpty(mobileNo)) {
                        if (!TextUtils.isEmpty(email)) {
                            if (!TextUtils.isEmpty(password)) {

                                AppSyncPleaseWait.showDialog(RegisterActivity.this, "Please wait..");

                                String regestResp = AppSyncDirectResponse.getResponse(Admin.BASE_URL + "all_apis.php?func=users&email=" + email);
                                Log.e("Express", regestResp.toString());
                                if (!TextUtils.isEmpty(regestResp)) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(regestResp);
                                        if (jsonArray.length() == 0) {

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    name:fjkgdfgnkj
//                                                    email:lfjljfdj@gmail.com
//                                                    password:kudfhuih
//                                                    username:jhgj
//                                                    phone: 12345098
                                                    try {
                                                        String cmd = Admin.BASE_URL + "api/register?name=" + fullName + "&email=" + email
                                                                + "&phone=" + mobileNo + "&password=" + AppSyncEncryptDecrypt.Encrypt(password) + "&username=" + email + "&referral=" + AppSyncRandomNumber.generateRandomNumber(6);

                                                        Log.e("Hulk", cmd);
                                                        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(RegisterActivity.this);
                                                        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
                                                            @Override
                                                            public void responser(String response, String datakey) {
                                                                if (datakey.equalsIgnoreCase("JJ86")) {
                                                                    try {
                                                                        JSONObject obj = new JSONObject(response);
                                                                        String status = obj.getString("status");
                                                                        if (!status.equalsIgnoreCase("400")) {
                                                                            String id = obj.getJSONObject("result").getString("id");

                                                                            Admin.tinyDB.putBoolean("login", true);

                                                                            Admin.tinyDB.putString("name", fullName);
                                                                            Admin.tinyDB.putString("email", email);
                                                                            Admin.tinyDB.putString("userid", id);
                                                                            Admin.tinyDB.putString("mobile", mobileNo);
                                                                            AppSyncPleaseWait.stopDialog(RegisterActivity.this);
                                                                            Handle_show_earned_points_dialog();
                                                                        } else {
                                                                            AppSyncPleaseWait.stopDialog(RegisterActivity.this);
                                                                            String message = obj.getString("message");
                                                                            AppSyncToast.showToast(getApplicationContext(), message);
                                                                        }
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                        });
                                                        as.getResponseFromUrlMethod(cmd, "JJ86");
                                                        Log.wtf("Hulk-76", cmd);
                                                        AppSyncPleaseWait.stopDialog(RegisterActivity.this);

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        AppSyncPleaseWait.stopDialog(RegisterActivity.this);
                                                    }
                                                }
                                            }, 1000);
                                        } else {
                                            AppSyncToast.showToast(getApplicationContext(), "Already registered");
                                            AppSyncPleaseWait.stopDialog(RegisterActivity.this);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    AppSyncToast.showToast(getApplicationContext(), "Something went wrong..");
                                    AppSyncPleaseWait.stopDialog(RegisterActivity.this);
                                }

                            } else {
                                AppSyncToast.showToast(getApplicationContext(), "Please enter Password");
                            }
                        } else {
                            AppSyncToast.showToast(getApplicationContext(), "Please enter Email Address");
                        }
                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "Please enter Mobile Number");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter full name");
                }
            }
        });

    }

    private void Handle_show_earned_points_dialog() {
        AppSyncCustomDialog.showDialog(context, R.layout.dialog_earned_10_points, BlackTransparent, true);
        View vv = AppSyncCustomDialog.view2;
        LinearLayoutCompat liner = vv.findViewById(R.id.liner);
        Flubber.with()
                .animation(ALPHA)
                .repeatCount(0)
                .delay(0)
                .duration(500)
                .createFor(liner)
                .start();

        Button ok_btn = vv.findViewById(R.id.ok_btn);
        ImageView close_img = vv.findViewById(R.id.close_img);
        close_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSyncCustomDialog.stopPleaseWaitDialog(context);
                AppSyncSuccessDialog.dialogColsed = false;
                AppSyncPHPMailer.sendMail(context, Admin.tinyDB.getString("email"), "Welcome to Matka App", "<h2>MT Software Solutions Welcomes you to this platform</h2>");
                finishAffinity();
                startActivity(new Intent(context, MainActivity.class));
                Admin.OverrideNow(context);
            }
        });

        ok_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                close_img.callOnClick();
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

    }
}