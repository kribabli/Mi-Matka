package com.teamup.matka.AllActivities;

import static android.util.Log.wtf;

import static com.appolica.flubber.Flubber.AnimationPreset.ALPHA;
import static com.teamup.matka.AllModules.Admin.getLineNumber;
import static com.teamup.matka.R.color.BlackTransparent;
import static com.teamup.matka.R.id.close_img;
import static com.teamup.matka.R.id.liner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appolica.flubber.Flubber;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.teamup.app_sync.AppSyncBackPressed;
import com.teamup.app_sync.AppSyncBottomSheetDialog;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncChatBot;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncCustomDialog;
import com.teamup.app_sync.AppSyncDirectResponse;
import com.teamup.app_sync.AppSyncDirectResponseAsync;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncHandlers;
import com.teamup.app_sync.AppSyncInitialize;
import com.teamup.app_sync.AppSyncOpenUrl;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncShareApp;
import com.teamup.app_sync.AppSyncSimpleTextShare;
import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.app_sync.Configs;
import com.teamup.app_sync.Reqs.ChatReq;
import com.teamup.matka.AllAdapters.MarketsAdapter;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.BuildConfig;
import com.teamup.matka.Models.BidHistoryModel;
import com.teamup.matka.Models.ModelBank;
import com.teamup.matka.Models.ModelGetProfile;
import com.teamup.matka.Models.ModelMarkets;
import com.teamup.matka.Models.ModelReferralCheck;
import com.teamup.matka.Models.ModelSettings;
import com.teamup.matka.Models.ModelSettingsValue;
import com.teamup.matka.Models.ModelTopData;
import com.teamup.matka.Models.ModelTransactions;
import com.teamup.matka.Models.ModelWinningHistory;
import com.teamup.matka.Models.ModelWithdrawHistory;
import com.teamup.matka.Models.UpisModel;
import com.teamup.matka.R;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AppSyncHandlers.runner, AppSyncCurrentDate.NetworkDatePhpFormat {

    public static double min_deposite = 100;
    public static double min_withdraw = 1000;
    public static String whatsapp_mobile_number = "";
    FloatingActionButton addBtn;
    RelativeLayout starline_reler, roulette_reler;
    ImageView wallet_img, notif_img;
    com.getbase.floatingactionbutton.FloatingActionButton whatsapp_btn, app_suport;
    String name = "", mobileNo = "", emailId = "", description = "";
    TextView balance_txt, username_txt, whatsapp_txt, call_txt, starline_games_txt, wallet_txt_bottom;
    TextView username_text, fullname_txt, mobile_txt, referral_txt;
    NestedScrollView scroll;
    String name1;
    String phone;
    ImageCarousel carousel;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_main);
        context = this;
        AppSyncInitialize.init(this);
        scroll = findViewById(R.id.scroll);
        notif_img = findViewById(R.id.notif_img);
        wallet_txt_bottom = findViewById(R.id.wallet_txt_bottom);
        starline_games_txt = findViewById(R.id.starline_games_txt);
        call_txt = findViewById(R.id.call_txt);
        whatsapp_txt = findViewById(R.id.whatsapp_txt);
        username_txt = findViewById(R.id.username_txt);
        balance_txt = findViewById(R.id.balance_txt);
        app_suport = findViewById(R.id.app_suport);
        whatsapp_btn = findViewById(R.id.whatsapp_btn);
        starline_reler = findViewById(R.id.starline_reler);
        roulette_reler = findViewById(R.id.roulette_reler);
        wallet_img = findViewById(R.id.wallet_img);
//        addBtn = findViewById(R.id.addBtn);

//        userid
        wtf("Hulk-116", "User id : " + Admin.tinyDB.getString("userid"));

//        Load Models
        ModelGetProfile.load();
        UpisModel.load();
        ModelSettings.load();
        ModelBank.load();
        ModelMarkets.load();
        ModelTopData.load();
        ModelWithdrawHistory.load();
        ModelWinningHistory.load();
        ModelTransactions.load();

        ModelSettingsValue.load();

        ModelSettings.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    whatsapp_txt.setText("" + MainActivity.whatsapp_mobile_number);
                }
            }
        });

//        handle drawer navigation code here
        HandleDrawerLayout();
        Admin.tinyDB.putBoolean("login", true);

        Handle_load_whatsapp_number();

        LoadProfile();
//        click on wallet code here
        wallet_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
                Admin.OverrideNow(MainActivity.this);
            }
        });

        notif_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Notifications.class));
                Admin.OverrideNow(MainActivity.this);
            }
        });


//        wallet text at bottom
        wallet_txt_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
                Admin.OverrideNow(MainActivity.this);
            }
        });

//        on profile data received
        ModelGetProfile.user_balance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                update balance code here
                balanceOfUser = Double.parseDouble(s);
                balance_txt.setText("" + s + "/-");
                wallet_txt_bottom.setText("" + s + "/-");
            }
        });

//       goto starline section code here
        starline_reler.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.starline = true;
                startActivity(new Intent(MainActivity.this, StarlineGames.class));
                Admin.OverrideNow(MainActivity.this);
            }
        });

//        starline games section here
        starline_games_txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.starline = true;
                startActivity(new Intent(MainActivity.this, StarlineGames.class));
                Admin.OverrideNow(MainActivity.this);

            }
        });

//        this feature is not in app scope beed hidden
        roulette_reler.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RouletteGame.class));
                Admin.OverrideNow(MainActivity.this);
            }
        });

//        load all markets
        HandleMarkets();
        LoadTopData();

        whatsapp_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncOpenUrl.openUrlViaIntent(MainActivity.this, "http://wa.me/+" + MainActivity.whatsapp_mobile_number);
            }
        });

        whatsapp_txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncOpenUrl.openUrlViaIntent(MainActivity.this, "http://wa.me/+" + MainActivity.whatsapp_mobile_number);
            }
        });

        call_txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncOpenUrl.openUrl(MainActivity.this, getResources().getString(R.string.youtube_link));
            }
        });

        app_suport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Call_for_app_support();
            }
        });

    }

    private void Handle_load_whatsapp_number() {

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
                        name1 = oo.getString("name");
                        String pass = oo.getString("pass");
                        String usertype = oo.getString("usertype");
                        String imgpath = oo.getString("imgpath");
                        String referral = oo.getString("referral");
                        Admin.tinyDB.putString("referral", referral);
                        String referral_added = oo.getString("referral_added");


                        if (!AppSyncTextUtils.check_empty_and_null(referral_added)) {
                            if (!Admin.tinyDB.getBoolean("reff")) {
                                Admin.tinyDB.putBoolean("reff", true);
                                Handle_referral_dialog();
                            }
                        }

                        phone = oo.getString("phone");

                        Admin.tinyDB.putString("name", name);
                        username_text.setText("Username : " + name1);
//                        fullname_txt.setText("My FullName : " +name1);
                        mobile_txt.setText("Mobile No. : " + phone);
                        referral_txt.setText("Referral Code : " + referral);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api/getProfile?userid=" + Admin.tinyDB.getString("userid"), "HHU");
    }

    String refferal = "";

    private void Handle_referral_dialog() {
        AppSyncCustomDialog.showDialog(context, R.layout.dialog_add_referral, BlackTransparent, true);
        View vv = AppSyncCustomDialog.view2;
        LinearLayoutCompat liner = vv.findViewById(R.id.liner);
        Flubber.with()
                .animation(ALPHA)
                .repeatCount(0)
                .delay(0)
                .duration(500)
                .createFor(liner)
                .start();

        Button submit_btn = vv.findViewById(R.id.submit_btn);
        EditText referral_edt = vv.findViewById(R.id.referral_edt);
        ImageView close_img = vv.findViewById(R.id.close_img);
        close_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSyncCustomDialog.stopPleaseWaitDialog(context);
            }
        });

        submit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                refferal = referral_edt.getText().toString();
                if (AppSyncTextUtils.check_empty_and_null(refferal, context, "Please add referral then submit")) {
                    ModelReferralCheck.load(refferal);
                    AppSyncPleaseWait.showDialog(context, "checking", true);

                }
            }
        });

        ModelReferralCheck.response_data.setValue(null);
        ModelReferralCheck.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    if (!s.equals("[]")) {
                        AppSyncPleaseWait.stopDialog(context);
                        Admin.makeItQuery("update user set balance = balance + 10 where id = " + Admin.tinyDB.getString("userid"), context);
                        Admin.makeItQuery("update user set referral_added = '" + refferal + "' where id = " + Admin.tinyDB.getString("userid"), context);
                        AppSyncCustomDialog.stopPleaseWaitDialog(context);
                    } else {
                        AppSyncPleaseWait.stopDialog(context);
                        AppSyncToast.showToast(context, "no such referral code exist");
                    }
                }
            }
        });

    }

    private void LoadTopData() {
        show = 0;

        LinearLayout top_reler = findViewById(R.id.top_reler);
        TextView top_txt = findViewById(R.id.top_txt);
        top_txt.setText("");

        carousel = findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());
        List<CarouselItem> list_imgs = new ArrayList<>();

        ModelTopData.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (ModelTopData.img_list != null) {
                    if (ModelTopData.img_list.size() > 0) {
                        /* banners available */
                        list_imgs.clear();
                        for (int i = 0; i < ModelTopData.img_list.size(); i++) {
                            list_imgs.add(new CarouselItem(ModelTopData.img_list.get(i), "null"));
                        }

                        carousel.setData(list_imgs);
                    } else {
                        /* banners not available */
                    }
                }

            }
        });

        ModelTopData.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (AppSyncTextUtils.check_empty_and_null(s)) {
                    top_reler.setVisibility(View.GONE);
                    ModelTopData.message.setValue("");
                }
            }
        });


        wtf("Hulk-170", Admin.BASE_URL + "api/banners");

        ModelGetProfile.load();
        ModelGetProfile.user_balance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                balanceOfUser = Double.parseDouble(s);
                wtf("Hulk-264", s);
            }
        });

    }

    double balanceOfUser = 0.00;


    public static String how_to_play = "https://youtu.be/07Yw2sUfdZE";


    private void LoadBankDetails() {
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("HH665")) {
                    try {
                        JSONObject obj = new JSONObject(response).getJSONObject("result");
                        Admin.paytm = obj.getString("paytm");
                        Admin.gpay = obj.getString("upi");
                        Admin.phonepe = obj.getString("phonepe");
                        Admin.acc_name = obj.getString("name");
                        Admin.ac_no = obj.getString("ac_no");
                        Admin.ifsc = obj.getString("ifsc");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Check_internet_timing(MainActivity.this);
            }
        });
        String cmd = Admin.BASE_URL + "api/getBank?userid=" + Admin.tinyDB.getString("userid");
        as.getResponseFromUrlMethod(cmd, "HH665");
        wtf("Hulk-" + getClass().getName() + "-" + getLineNumber(), "" + cmd);

    }

    int show = 0;

    private void HandleMarkets() {
        RecyclerView recycler;
        SwipeRefreshLayout swiper;
        MarketsAdapter adapter;

        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MarketsAdapter(ModelMarkets.list);
        recycler.setAdapter(adapter);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ModelMarkets.load();
            }
        });

        ModelMarkets.response_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                swiper.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

        AppSyncHandlers.run(MainActivity.this, 60000, 54);
    }

    private void HandleDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        referral_txt = header.findViewById(R.id.referral_txt);
        username_text = header.findViewById(R.id.username_txt);
        fullname_txt = header.findViewById(R.id.fullname_txt);
        mobile_txt = header.findViewById(R.id.mobile_txt);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        ImageView back_img = findViewById(R.id.navImg);

//        username_text.setText("My Username : " + name1);
//        fullname_txt.setText("My FullName : " +name1);
//        mobile_txt.setText("My Mobile No. : " + phone);


        back_img.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (!drawer.isDrawerOpen(Gravity.START)) drawer.openDrawer(Gravity.START);
                else drawer.closeDrawer(Gravity.END);
            }
        });

    }

    @Override
    public void onBackPressed() {
//        on back pressed code here
        AppSyncBackPressed.enable(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        item id selected from navigation drawer
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            Admin.tinyDB.clear();
            finishAffinity();
            startActivity(new Intent(this, SplashScreenActivity.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_paytm_details) {
            HandlePaytm();
        } else if (id == R.id.nav_phonepe_details) {
            Handle_phonepe();
        } else if (id == R.id.nav_google_details) {
            HandleGoogleDetails();
        } else if (id == R.id.nav_bank_details) {
            HandleBankDetails();
        } else if (id == R.id.nav_add_points) {
            startActivity(new Intent(this, AddPoints.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_notification) {
            startActivity(new Intent(this, Notifications.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_share_app) {
            AppSyncSimpleTextShare.share(MainActivity.this, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        } else if (id == R.id.nav_rate) {
            AppSyncOpenUrl.openUrlViaIntent(MainActivity.this, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        } else if (id == R.id.nav_wallet_page) {
            startActivity(new Intent(this, WalletActivity.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_bid_history) {
            startActivity(new Intent(this, BidHistory.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_wining_history) {
            startActivity(new Intent(this, WinningHistory.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_transaction_history) {
            startActivity(new Intent(this, TransactionHistory.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_transaction_history) {
            startActivity(new Intent(this, TransactionHistory.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_withdraw_history) {
            startActivity(new Intent(this, WithdrawHistory.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_withdraw_points) {
            startActivity(new Intent(this, WithdrawHistory.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_noticboard) {
            startActivity(new Intent(this, NoticeboardActivity.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_winning_ratio) {
            startActivity(new Intent(this, WinningRatioActivity.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_profile_page) {
            startActivity(new Intent(this, ProfileDetailActivity.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_contact_and_support) {
            Call_for_app_support();
        } else if (id == R.id.nav_history_page) {
            startActivity(new Intent(this, HistoryPageActivity.class));
            Admin.OverrideNow(this);
        } else if (id == R.id.nav_how_to_play) {
            AppSyncOpenUrl.openUrl(this, how_to_play);
        } else if (id == R.id.nav_share) {
            AppSyncShareApp.shareApp(this, "\nDownload this app", BuildConfig.APPLICATION_ID);
        }

//      close drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void Call_for_app_support() {
//        app support chat bot code here
        ArrayList<ChatReq> list = new ArrayList<>();
        list.add(new ChatReq("Welcome to Contact & Support.\nPls provide some information so we can help you..!\n\nWhat is your name?", AppSyncChatBot.TYPE_MESSAGE));
        list.add(new ChatReq("What is your mobile number?", AppSyncChatBot.TYPE_NUMBER));
        list.add(new ChatReq("alright just send me email id?", AppSyncChatBot.TYPE_MESSAGE));
        list.add(new ChatReq("and now you are done..\nThank You..!!\n\nWhat is your query about?", AppSyncChatBot.TYPE_MESSAGE));

        AppSyncChatBot.set_bot_questions(list);
        AppSyncChatBot.set_bot_head_name(getResources().getString(R.string.app_name) + " Bot");
        AppSyncChatBot.set_bot_image(R.drawable.logo);
        AppSyncChatBot.set_bot_end_response("Well done.\n\nI have saved everything.\nOur customer support will contact you soon.\nPlease press proceed to finish.");

        startActivityForResult(new Intent(this, AppSyncChatBot.class), 888);

    }

    private void Handle_phonepe() {
//        handle phonepe dialog code here
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_phonepe, true);
        View vv = AppSyncBottomSheetDialog.view2;
        EditText phone_pe_edt = vv.findViewById(R.id.phone_pe_edt);

        phone_pe_edt.setText("" + Admin.phonepe);

        Button update_btn = vv.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_pe = phone_pe_edt.getText().toString();
                AppSyncPleaseWait.showDialog(MainActivity.this, "Updating..");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String cmd = Admin.BASE_URL + "api/addbank?userid=" + Admin.tinyDB.getString("userid") + "&phonepe=" + phone_pe;
                        wtf("Hulk-" + getClass().getName() + "-" + getLineNumber(), "cmd : " + cmd);
                        String response = AppSyncDirectResponse.getResponse(cmd);
                        Admin.tinyDB.putString("phone_pe", phone_pe);
                        AppSyncPleaseWait.stopDialog(MainActivity.this);
                        AppSyncBottomSheetDialog.dismiss(MainActivity.this);
                        ModelBank.load();
                    }
                }, 1000);

            }
        });

    }

    private void HandleGoogleDetails() {
//        handle google pay dialog code here
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_google_pay, true);
        View vv = AppSyncBottomSheetDialog.view2;
        EditText phone_pe_edt = vv.findViewById(R.id.phone_pe_edt);

        phone_pe_edt.setText("" + Admin.gpay);
        Button update_btn = vv.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_pe = phone_pe_edt.getText().toString();
                AppSyncPleaseWait.showDialog(MainActivity.this, "Updating..");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String response = AppSyncDirectResponse.getResponse(Admin.BASE_URL + "api/addbank?userid=" + Admin.tinyDB.getString("userid") + "&upi=" + phone_pe);
                        Admin.tinyDB.putString("upi", phone_pe);
                        AppSyncPleaseWait.stopDialog(MainActivity.this);
                        AppSyncBottomSheetDialog.dismiss(MainActivity.this);
                        ModelBank.load();
                    }
                }, 1000);

            }
        });
    }

    private void HandlePaytm() {

        /*
         * Handle paytm dialog code here
         * */
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_paytm, true);
        View vv = AppSyncBottomSheetDialog.view2;
        EditText phone_pe_edt = vv.findViewById(R.id.phone_pe_edt);
        phone_pe_edt.setText("" + Admin.paytm);
        Button update_btn = vv.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_pe = phone_pe_edt.getText().toString();
                AppSyncPleaseWait.showDialog(MainActivity.this, "Updating..");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String response = AppSyncDirectResponse.getResponse(Admin.BASE_URL + "api/addbank?userid=" + Admin.tinyDB.getString("userid") + "&paytm=" + phone_pe);
                        Admin.tinyDB.putString("paytm", phone_pe);
                        AppSyncPleaseWait.stopDialog(MainActivity.this);
                        AppSyncBottomSheetDialog.dismiss(MainActivity.this);
                        ModelBank.load();
                    }
                }, 1000);

            }
        });
    }

    private void HandleBankDetails() {
        /*Bank details code here*/
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_bank_details, true);
        View vv = AppSyncBottomSheetDialog.view2;
        EditText acc_holder_edt = vv.findViewById(R.id.acc_holder_edt);
        EditText acc_number_edt = vv.findViewById(R.id.acc_number_edt);
        EditText confirm_acc_number_edt = vv.findViewById(R.id.confirm_acc_number_edt);
        EditText acc_ifsc_code_edt = vv.findViewById(R.id.acc_ifsc_code_edt);
        Button update_btn = vv.findViewById(R.id.update_btn);

        acc_holder_edt.setText("" + Admin.acc_name);
        acc_number_edt.setText("" + Admin.ac_no);
        acc_ifsc_code_edt.setText("" + Admin.ifsc);

        update_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc_holder = acc_holder_edt.getText().toString();
                String acc_number = acc_number_edt.getText().toString();
                String acc_ifsc_code = acc_ifsc_code_edt.getText().toString();
                String confirm_acc_number = confirm_acc_number_edt.getText().toString();


                if (!TextUtils.isEmpty(acc_holder)) {
                    if (!TextUtils.isEmpty(acc_ifsc_code)) {
                        if (!TextUtils.isEmpty(acc_number)) {
                            if (!TextUtils.isEmpty(confirm_acc_number) && acc_number.equalsIgnoreCase(confirm_acc_number)) {

                                AppSyncPleaseWait.showDialog(MainActivity.this, "Updating..");

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String response = AppSyncDirectResponse.getResponse(Admin.BASE_URL + "api/addbank?userid=" + Admin.tinyDB.getString("userid") + "&name=" + acc_holder + "&ac_no=" + acc_number + "&ifsc=" + acc_ifsc_code);

                                        AppSyncPleaseWait.stopDialog(MainActivity.this);
                                        AppSyncBottomSheetDialog.dismiss(MainActivity.this);
                                        ModelBank.load();
                                    }
                                }, 1000);
                            } else {
                                AppSyncToast.showToast(getApplicationContext(), "Please confirm account number");
                            }
                        } else {
                            AppSyncToast.showToast(getApplicationContext(), "Please enter account number");
                        }
                    } else {
                        AppSyncToast.showToast(getApplicationContext(), "Please enter account IFSC code");
                    }
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter account holder name");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 888) {
                /*App support code here after getting response*/
                String response = data.getStringExtra("result");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");

                            if (i == 0) {
                                name = message;
                            } else if (i == 1) {
                                mobileNo = message;
                            } else if (i == 2) {
                                emailId = message;
                            } else if (i == 3) {
                                description = message;
                            }

                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AppSyncDirectResponseAsync.getResponse(Admin.BASE_URL + "api/support?userid=" + Admin.tinyDB.getString("userid") + "&msg=" + description + "&mobile=" + mobileNo + "&email=" + emailId + "&name=" + name);
                                wtf("Hulk-421", Admin.BASE_URL + "api/support?userid=" + Admin.tinyDB.getString("userid") + "&msg=" + description + "&mobile=" + mobileNo + "&email=" + emailId + "&name=" + name);
                            }
                        }, 500);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Admin.starline = false;
        if (Admin.refresh_needed) {
            Admin.refresh_needed = false;

        }
        ModelGetProfile.load();
    }

    private static void Check_internet_timing(Context context) {
        AppSyncCurrentDate.get_network_date_in_php_format(context, "h:i");
    }

    @Override
    public void play(int code) {
        ModelMarkets.load();
    }


    @Override
    public void gotDate_php_format(String date) {
        wtf("Hulk-713", date + " : " + AppSyncCurrentDate.getDateTimeInFormat("hh:mm"));

    }

}