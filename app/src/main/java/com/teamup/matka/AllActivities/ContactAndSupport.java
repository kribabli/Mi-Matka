package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.Configs;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

public class ContactAndSupport extends AppCompatActivity {

    TextView mobile_txt, msg_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        msg_txt = findViewById(R.id.msg_txt);
        mobile_txt = findViewById(R.id.mobile_txt);
        mobile_txt.setText("" + MainActivity.whatsapp_mobile_number);
        msg_txt.setText("" + MainActivity.whatsapp_mobile_number);

        setContentView(R.layout.activity_contact_and_support);
        Admin.HandleToolBar(this, "Contact & Support", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}