package com.teamup.matka.AllAdapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.teamup.matka.AllModules.TinyDB;
import com.teamup.matka.AllReqs.StarlineBidHistoryReq;
import com.teamup.matka.R;

import java.util.List;


public class StarlineBidDetialAdapter extends RecyclerView.Adapter<StarlineBidDetialAdapter.ViewHolder> {

    public List<StarlineBidHistoryReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    private FirebaseFirestore firebaseFireStore;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public StarlineBidDetialAdapter(List<StarlineBidHistoryReq> blog_list) {
        this.blog_list = blog_list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_transaction, parent, false);

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

        holder.date_txt.setText("" + blog_list.get(position).getDt());
        holder.amount_txt.setText("" + blog_list.get(position).getPoints());
        holder.descTxt.setText("" + blog_list.get(position).getMarketName());
        holder.success_txt.setText("Digit : " + blog_list.get(position).getDigit());


    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView1, imageView2, imageView3;
        TextView date_txt, amount_txt, descTxt, success_txt, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        RelativeLayout liner;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            success_txt = itemView.findViewById(R.id.success_txt);
            descTxt = itemView.findViewById(R.id.descTxt);
            amount_txt = itemView.findViewById(R.id.amount_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            liner = itemView.findViewById(R.id.liner);

        }


    }


}
