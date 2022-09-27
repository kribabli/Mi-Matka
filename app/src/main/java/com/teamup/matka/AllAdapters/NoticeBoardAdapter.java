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

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.teamup.app_sync.AppSyncImageDialog;
import com.teamup.app_sync.ViewPagerFolder.pagerReq;
import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllModules.TinyDB;
import com.teamup.matka.AllReqs.NoticeReq;
import com.teamup.matka.R;

import java.util.List;


public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.ViewHolder> {

    public List<NoticeReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    private FirebaseFirestore firebaseFireStore;
    int cur;
    public Context context;
    TinyDB tinyDB;

    public NoticeBoardAdapter(List<NoticeReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_noticeboard, parent, false);

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
        Glide.with(context).load(Admin.BASE_URL + blog_list.get(position).getImage()).into(holder.notice_img);
        holder.title_txt.setText("" + blog_list.get(position).getTitle());
        holder.descTxt.setText("" + blog_list.get(position).getDescription());

        if (!blog_list.get(position).getImage().equalsIgnoreCase("null") && blog_list.get(position).getImage() != null) {
            holder.notice_img.setVisibility(View.VISIBLE);
        } else {
            holder.notice_img.setVisibility(View.GONE);
        }

        holder.notice_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncImageDialog.show(context, Admin.BASE_URL + blog_list.get(position).getImage());
            }
        });
    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView notice_img, imageView2, imageView3;
        TextView title_txt, descTxt, Txt3, Txt4, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        LinearLayout liner;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            descTxt = itemView.findViewById(R.id.descTxt);
            title_txt = itemView.findViewById(R.id.title_txt);
            notice_img = itemView.findViewById(R.id.notice_img);
            liner = itemView.findViewById(R.id.liner);

        }


    }


}
