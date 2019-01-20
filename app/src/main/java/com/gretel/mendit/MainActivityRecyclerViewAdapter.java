package com.gretel.mendit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gretel.com.mendit.R;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> myProfilePhotos = new ArrayList<String>();
    private ArrayList<String> myRepairerNames = new ArrayList<String>();
    private ArrayList<String> myRatings = new ArrayList<String>();
    private Context myContext;

    public MainActivityRecyclerViewAdapter(ArrayList<String> profilePhotos, ArrayList<String> repairers, ArrayList ratings, Context context){
        myProfilePhotos = profilePhotos;
        myRepairerNames = repairers;
        myRatings = ratings;
        myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mainView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main, viewGroup, false);
        ViewHolder holder = new ViewHolder(mainView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Glide.with(myContext)
                .asBitmap()
                .load(myProfilePhotos.get(position))
                .into(viewHolder.profilePhoto);

        viewHolder.repairerName.setText(myRepairerNames.get(position));
        viewHolder.repairerRatings.setText(myRepairerNames.get(position));
    }

    //not meant to be 0. To be changed to a variable based on database.
    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePhoto;
        TextView repairerName;
        TextView repairerRatings;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePhoto=itemView.findViewById(R.id.repairer_profile_photo);
            repairerName=itemView.findViewById(R.id.repairer_name);
            repairerRatings=itemView.findViewById(R.id.line_partner_text_view);
        }
    }
}
