package com.teamup.matka.AllAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.teamup.app_sync.ViewPagerFolder.pagerReq;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllModules.TinyDB;
import com.teamup.matka.AllReqs.SelectMarketReq;
import com.teamup.matka.R;

import java.util.List;


public class SelectMarketAdapter extends RecyclerView.Adapter<SelectMarketAdapter.ViewHolder> {

    public List<SelectMarketReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    private FirebaseFirestore firebaseFireStore;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public SelectMarketAdapter(List<SelectMarketReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_select_market, parent, false);

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

        holder.title_txt.setText("" + blog_list.get(position));

        holder.reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.marketId = blog_list.get(position).getId();
                ((Activity) context).finish();
                Admin.OverrideNow(context);
            }
        });

    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView1, imageView2, imageView3;
        TextView title_txt, Txt2, Txt3, Txt4, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        RelativeLayout reler;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            title_txt = itemView.findViewById(R.id.title_txt);
            reler = itemView.findViewById(R.id.reler);

        }


    }


}
