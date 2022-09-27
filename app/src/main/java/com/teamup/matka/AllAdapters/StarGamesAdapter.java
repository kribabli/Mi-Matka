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
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDaysTheory;
import com.teamup.matka.AllActivities.PlaceBidActivity;
import com.teamup.matka.AllActivities.StarlinePlaceBidActivity;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllModules.TinyDB;
import com.teamup.matka.AllReqs.StarGamesReq;
import com.teamup.matka.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class StarGamesAdapter extends RecyclerView.Adapter<StarGamesAdapter.ViewHolder> {

    public List<StarGamesReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    private FirebaseFirestore firebaseFireStore;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public StarGamesAdapter(List<StarGamesReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_starline_game, parent, false);

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

        if (AppSyncDaysTheory.compareDate(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:SS"), blog_list.get(position).getStime(), "HH:mm:ss") == 1) {

//                Not opened Yet
            holder.play_img.setAlpha(0.3f);
            holder.play_img.setEnabled(false);
            holder.statusTxt.setText("Closed");

        } else {
            holder.play_img.setAlpha(1f);
            holder.play_img.setEnabled(true);
            holder.statusTxt.setText("Running");
        }

        holder.descTxt.setText("" + blog_list.get(position).getResult());
        holder.dt_txt.setText("" + blog_list.get(position).getStime());
        holder.handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date1 = null;
                    try {
                        date1 = simpleDateFormat.parse(Admin.addHour_no_seconds("" + blog_list.get(position).getStime()));
                    } catch (ParseException e) {
                        holder.handler.removeCallbacksAndMessages(null);
                    }

                    Date date2 = null;
                    try {
                        date2 = simpleDateFormat.parse(AppSyncCurrentDate.getDateTimeInFormat("HH:mm:ss"));
                    } catch (ParseException e) {
                        holder.handler.removeCallbacksAndMessages(null);
                    }

                    try {
                        if (!Admin.printDifference(date2, date1).contains("-")) {
                            holder.timer_txt.setText("" + Admin.printDifference_no_seconds(date2, date1));
                        } else {
                            holder.timer_txt.setText("--");
                        }
                    } catch (Exception e) {
                        Log.wtf("Hulk-120", e.getMessage());
                    }

                } catch (Exception e) {
                    holder.handler.removeCallbacksAndMessages(null);
                    Log.wtf("Hulk-125", e.getMessage());
                }

                holder.handler.postDelayed(this, 1000);
            }
        }, 1000);


        holder.play_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.marketId = blog_list.get(position).getId();
                Admin.toolTitle = blog_list.get(position).getStime();
                Admin.date = blog_list.get(position).getStime();
                if (Admin.starline) {
                    context.startActivity(new Intent(context, StarlinePlaceBidActivity.class));
                    Admin.OverrideNow(context);
                } else {
                    context.startActivity(new Intent(context, StarlinePlaceBidActivity.class));
                    Admin.OverrideNow(context);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView play_img, imageView2, imageView3;
        TextView descTxt, statusTxt, dt_txt, timer_txt, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        LinearLayout liner;
        CardView cardView;
        CheckBox checkBox;
        public Handler handler = new Handler();

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            play_img = itemView.findViewById(R.id.play_img);
            timer_txt = itemView.findViewById(R.id.timer_txt);
            dt_txt = itemView.findViewById(R.id.dt_txt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            liner = itemView.findViewById(R.id.liner);

        }


    }


}
