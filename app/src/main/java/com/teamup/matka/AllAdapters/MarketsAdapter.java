package com.teamup.matka.AllAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDaysTheory;
import com.teamup.app_sync.AppSyncOpenUrl;
import com.teamup.app_sync.Configs;
import com.teamup.matka.AllActivities.PlaceBidActivity;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllModules.TinyDB;
import com.teamup.matka.AllReqs.MarketsReq;
import com.teamup.matka.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MarketsAdapter extends RecyclerView.Adapter<MarketsAdapter.ViewHolder> {

    public List<MarketsReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    private FirebaseFirestore firebaseFireStore;
    int cur;
    public Context context;
    TinyDB tinyDB;

    public MarketsAdapter(List<MarketsReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_market, parent, false);

        context = parent.getContext();

        tinyDB = new TinyDB(context);

        return new ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


//        Flubber.with()
//                .animation(Flubber.AnimationPreset.ALPHA) // Slide up animation
//                .repeatCount(0)                              // Repeat once
//                .duration(500)                              // Last for 1000 milliseconds(1 second)
//                .createFor(holder.liner)                             // Apply it to the view
//                .start();


//        final String PostId = blog_list.get(position).FacebookPostId;
        holder.setIsRecyclable(false);

        holder.nameTxt.setText("" + blog_list.get(position).getName());
        if (!blog_list.get(position).getResult().equalsIgnoreCase("null")) {
            holder.descTxt.setText("" + blog_list.get(position).getResult());
        } else {
            holder.descTxt.setText("***_**_***");
        }


        try {
            holder.opent_time.setText("" + AppSyncDaysTheory.ConvertTo("HH:mm:ss", blog_list.get(position).getO_otime(), "hh:mm a") + " - " + AppSyncDaysTheory.ConvertTo("HH:mm:ss", blog_list.get(position).getO_ctime(), "hh:mm a"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            holder.close_time_txt.setText(AppSyncDaysTheory.ConvertTo("HH:mm:ss", blog_list.get(position).getC_ctime(), "hh:mm a"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            holder.timer_txt.setText("" + AppSyncDaysTheory.ConvertTo("HH:mm:ss", blog_list.get(position).getO_otime(), "HH:mm a"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String o_otime = blog_list.get(position).getO_otime();
        String o_ctime = blog_list.get(position).getO_ctime();

//        holder.handler.postDelayed(new Runnable() {
//            public void run() {
//                try {
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                    Date date1 = null;
//                    try {
//                        if (blog_list.get(position).getType().equalsIgnoreCase("open")) {
//                            date1 = simpleDateFormat.parse(Admin.addHour("" + blog_list.get(position).getO_ctime()));
//                        } else {
//                            date1 = simpleDateFormat.parse(Admin.addHour("" + blog_list.get(position).getC_ctime()));
//                        }
//                    } catch (ParseException e) {
//                        holder.handler.removeCallbacksAndMessages(null);
//                        e.printStackTrace();
//                    }
//
//
//                    Date date2 = null;
//                    try {
//                        date2 = simpleDateFormat.parse(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:ss"));
//                    } catch (ParseException e) {
//                        holder.handler.removeCallbacksAndMessages(null);
//                        e.printStackTrace();
//                    }
//
//                    if (!Admin.printDifference(date2, date1).contains("-")) {
//                        holder.timer_txt.setText("" + Admin.printDifference(date2, date1));
//                    } else {
//                        holder.timer_txt.setText("--");
//                    }
//
//                } catch (Exception e) {
//                    holder.handler.removeCallbacksAndMessages(null);
//                    e.printStackTrace();
//                }
//
//
//                holder.handler.postDelayed(this, 1000);
//            }
//        }, 1000);

//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                holder.timer_txt.setText("seconds remaining: " + millisUntilFinished / 1000);
//                //here you can have your logic to set text to edittext
//            }
//
//            public void onFinish() {
//                holder.timer_txt.setText("00:00:00");
//            }
//
//        }.start();


        if (blog_list.get(position).getType().equalsIgnoreCase("open")) {

//            ON Open
            if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:SS"), blog_list.get(position).getO_otime(), "HH:mm:ss") == -1) {

//                Not opened Yet
                holder.liner.setAlpha(0.3f);
                holder.liner.setEnabled(false);
                holder.statusTxt.setText("Not opened yet");

            } else if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:ss"), blog_list.get(position).getC_ctime(), "HH:mm:ss") == 1) {

//                Start time not arrived yet

                holder.liner.setAlpha(0.3f);
                holder.liner.setEnabled(false);

                holder.statusTxt.setText("CLOSED FOR TODAY");
                holder.statusTxt.setTextColor(context.getResources().getColor(R.color.red));
            } else {

//                Good for bidding
//                current time is in middle of start time and close time

                holder.liner.setAlpha(1f);
                holder.liner.setEnabled(true);

                holder.statusTxt.setText("RUNNING FOR TODAY");
                holder.statusTxt.setTextColor(context.getResources().getColor(R.color.black));
            }
        } else {
//            for Close
            Log.wtf("Hulk-206", "In close");
            if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:SS"), blog_list.get(position).getO_ctime(), "HH:mm:ss") == -1) {

//                not started yet
//                start time is bigger than current time
                holder.liner.setAlpha(0.3f);
                holder.liner.setEnabled(false);
                holder.statusTxt.setText("Not opened yet");


            } else if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:ss"), blog_list.get(position).getC_ctime(), "HH:mm:ss") == 1) {


                holder.liner.setAlpha(0.3f);
                holder.liner.setEnabled(false);


                holder.statusTxt.setText("CLOSED FOR TODAY");
                holder.statusTxt.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                holder.liner.setAlpha(1f);
                holder.liner.setEnabled(true);
                holder.statusTxt.setText("RUNNING FOR TODAY");
                holder.statusTxt.setTextColor(context.getResources().getColor(R.color.black));
            }
        }

        holder.liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.close_end_time = blog_list.get(position).getC_ctime();
                Admin.open_end_time = blog_list.get(position).getO_ctime();
                if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:ss"), blog_list.get(position).getO_ctime(), "HH:mm:ss") == 1) {

//                    Open finished
//                    cant place bid in open
                    Admin.open_enbled = false;
                } else {
                    Admin.open_enbled = true;
                }

                Admin.marketId = blog_list.get(position).getId();
                Admin.toolTitle = blog_list.get(position).getName();
                try {
                    Admin.date = AppSyncDaysTheory.ConvertTo("HH:mm:ss", blog_list.get(position).getC_ctime(), "hh:mm:ss a");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                context.startActivity(new Intent(context, PlaceBidActivity.class));
                Admin.OverrideNow(context);
            }
        });

        holder.whatsapp_reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncOpenUrl.openUrlViaIntent(context, "http://wa.me/+" + Configs.getValue(context, "mobile_number"));
            }
        });

    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView play_btn_img, imageView2, imageView3;
        TextView nameTxt, descTxt, opent_time, close_time_txt, timer_txt, statusTxt;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        LinearLayout liner;
        RelativeLayout whatsapp_reler;
        CardView cardView;
        public Handler handler = new Handler();
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            whatsapp_reler = itemView.findViewById(R.id.whatsapp_reler);
            play_btn_img = itemView.findViewById(R.id.play_btn_img);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            timer_txt = itemView.findViewById(R.id.timer_txt);
            close_time_txt = itemView.findViewById(R.id.close_time_txt);
            opent_time = itemView.findViewById(R.id.opent_time);
            descTxt = itemView.findViewById(R.id.descTxt);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            liner = itemView.findViewById(R.id.liner);

        }


    }


}
