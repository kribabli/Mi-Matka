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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.teamup.app_sync.ViewPagerFolder.pagerReq;
import com.teamup.matka.AllModules.TinyDB;
import com.teamup.matka.AllReqs.TransactionReq;
import com.teamup.matka.R;

import java.util.List;


public class TrnasactionAdapter extends RecyclerView.Adapter<TrnasactionAdapter.ViewHolder> {

    public List<TransactionReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    private FirebaseFirestore firebaseFireStore;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public TrnasactionAdapter(List<TransactionReq> blog_list) {
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

        holder.amount_txt.setText("" + blog_list.get(position).getAmount() + "/-");
        holder.date_txt.setText("" + blog_list.get(position).getDt());

    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView1, imageView2, imageView3;
        TextView amount_txt, date_txt, Txt3, Txt4, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        RelativeLayout liner;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            date_txt = itemView.findViewById(R.id.date_txt);
            amount_txt = itemView.findViewById(R.id.amount_txt);
            liner = itemView.findViewById(R.id.liner);

        }


    }


}
