package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncFileManager;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHandlers;
import com.teamup.app_sync.AppSyncInitialize;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

import static com.teamup.matka.AllModules.Admin.intializeLocalRoom;

public class SplashScreenActivity extends AppCompatActivity implements AppSyncHandlers.runner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        AppSyncFullScreenView.FullScreencall(this);
        setContentView(R.layout.activity_splash_screen);
        AppSyncInitialize.init(this);
        intializeLocalRoom(SplashScreenActivity.this);
        AppSyncHandlers.run(this, 4000, 41);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);

                if (("" + key).contains("stream")) {

                }
            }
        }

        try {
            FirebaseMessaging.getInstance().subscribeToTopic("all");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void play(int code) {
        if (Admin.tinyDB.getBoolean("login")) {
            finishAffinity();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finishAffinity();
            startActivity(new Intent(this, LoginActivity.class));
        }
        Admin.OverrideNow(this);
    }
}