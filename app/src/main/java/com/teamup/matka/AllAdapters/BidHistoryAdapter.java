package com.teamup.matka.AllAdapters;

import android.content.Context;
import android.os.Build;
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
import com.teamup.app_sync.AppSyncCustomDialog;
import com.teamup.app_sync.AppSyncDaysTheory;
import com.teamup.matka.AllModules.TinyDB;
import com.teamup.matka.AllReqs.BidHistoryReq;
import com.teamup.matka.AllReqs.StarGamesReq;
import com.teamup.matka.R;

import java.text.ParseException;
import java.util.List;


public class BidHistoryAdapter extends RecyclerView.Adapter<BidHistoryAdapter.ViewHolder> {

    public List<BidHistoryReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    private FirebaseFirestore firebaseFireStore;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public BidHistoryAdapter(List<BidHistoryReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history, parent, false);

        context = parent.getContext();

        tinyDB = new TinyDB(context);

        return new ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


//        Flubber.with()
//                .animation(Flubber.AnimationPreset.ALPHA) // Slide up animation
//                .repeatCount(0)                              // Repeat once
//                .duration(500)                              // Last for 1000 milliseconds(1 second)
//                .createFor(holder.liner)                             // Apply it to the view
//                .start();


//        final String PostId = blog_list.get(position).FacebookPostId;
        holder.setIsRecyclable(false);

        holder.rs_txt.setText("" + blog_list.get(position).getAmount());
        try {
            holder.date_txt.setText("" + AppSyncDaysTheory.ConvertTo("yyyy-MM-dd HH:mm:ss", blog_list.get(position).getDt(), "dd/MM/yyyy"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.descTxt.setText("" + blog_list.get(position).getName());
        holder.message_txt.setText("" + blog_list.get(position).getMessage());
        holder.stats_txt.setText("Type : " + blog_list.get(position).getType());
        holder.panna_txt.setText("Panna : " + blog_list.get(position).getPana());
        holder.mtype_txt.setText("" + blog_list.get(position).getMtype());

        holder.winImg.setVisibility(View.GONE);
        if (blog_list.get(position).getWin().equalsIgnoreCase("1")) {
            holder.winImg.setVisibility(View.VISIBLE);
            holder.liner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppSyncCustomDialog.showDialog(context, R.layout.dialog_winner, R.color.BlackTransparent, true);
                    View vv = AppSyncCustomDialog.view2;
                    Button ok_btn = vv.findViewById(R.id.ok_btn);
                    TextView win_txt = vv.findViewById(R.id.win_txt);
//                    win_txt.setText("You won " + blog_list.get(position).getWinAmt() + " on this bid");
                    ok_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AppSyncCustomDialog.stopPleaseWaitDialog(context);
                        }
                    });
                }
            });
        } else {
            holder.winImg.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView winImg, imageView2, imageView3;
        TextView rs_txt, date_txt, descTxt, mtype_txt, message_txt, stats_txt, panna_txt;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        LinearLayout liner;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            winImg = itemView.findViewById(R.id.winImg);
            mtype_txt = itemView.findViewById(R.id.mtype_txt);
            panna_txt = itemView.findViewById(R.id.panna_txt);
            stats_txt = itemView.findViewById(R.id.stats_txt);
            message_txt = itemView.findViewById(R.id.message_txt);
            descTxt = itemView.findViewById(R.id.descTxt);
            date_txt = itemView.findViewById(R.id.date_txt);
            rs_txt = itemView.findViewById(R.id.rs_txt);
            liner = itemView.findViewById(R.id.liner);

        }


    }


}
