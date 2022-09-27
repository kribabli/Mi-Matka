package com.teamup.matka.AllActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.teamup.app_sync.AppSyncBottomSheetDialog;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncFullScreenView;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.R;

public class BankAndOtherDeatails extends AppCompatActivity {

    RelativeLayout bank_details_reler, paytm_details_reler, google_pay_reler, phonepay, addreess_reler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);

        setContentView(R.layout.activity_bank_and_other_deatails);
        Admin.HandleToolBar(this, "Bank And Other Details", findViewById(R.id.backImg), findViewById(R.id.headToolTxt));


        addreess_reler = findViewById(R.id.addreess_reler);
        phonepay = findViewById(R.id.phonepay);
        google_pay_reler = findViewById(R.id.google_pay_reler);
        bank_details_reler = findViewById(R.id.bank_details_reler);
        paytm_details_reler = findViewById(R.id.paytm_details_reler);

        bank_details_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleBankDetails();
            }
        });
        paytm_details_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlePaytm();
            }
        });
        phonepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handle_phonepe();
            }
        });

        google_pay_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleGoogleDetails();
            }
        });
        addreess_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleAddress();
            }
        });

    }

    private void HandleAddress() {
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_address, true);
    }

    private void Handle_phonepe() {
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_phonepe, true);
    }

    private void HandleGoogleDetails() {
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_google_pay, true);
    }

    private void HandlePaytm() {
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_paytm, true);
    }

    private void HandleBankDetails() {
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_bank_details, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Admin.OverrideNow(this);
    }
}