package com.gretel.mendit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

import gretel.com.mendit.R;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> myProfilePhotos = new ArrayList<String>();
    private ArrayList<String> myRepairerNames = new ArrayList<String>();
    private ArrayList<Double> myRatings = new ArrayList<Double>();
    private ArrayList<String> mySpecialities = new ArrayList<String>();
    private Context myContext;

    public MainActivityRecyclerViewAdapter(HashSet<NameEntry> data, Context context){
        for(NameEntry temp: data){
            myProfilePhotos.add(temp.getDisplayPicture());
            myRepairerNames.add(temp.getName());
            myRatings.add(temp.getRating());
            mySpecialities.add(temp.getSpeciality());
        }
        myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.mechanic_holder,viewGroup,false);
        final MainActivityRecyclerViewAdapter.ViewHolder mechanicHolder = new MainActivityRecyclerViewAdapter.ViewHolder(row);
        return  mechanicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Glide.with(myContext)
                .asBitmap()
                .load(myProfilePhotos.get(position))
                .into(viewHolder.profilePhoto);

        viewHolder.repairerName.setText(myRepairerNames.get(position));
        viewHolder.repairerRatings.setText(myRatings.get(position).toString());
        viewHolder.repairerSpecialities.setText(mySpecialities.get(position));
    }

    //not meant to be 0. To be changed to a variable based on database.
    @Override
    public int getItemCount() {
        return myRepairerNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePhoto;
        TextView repairerName;
        TextView repairerRatings;
        TextView repairerSpecialities;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.main_relative_layout);
            profilePhoto=itemView.findViewById(R.id.repairer_profile_photo);
            repairerName=itemView.findViewById(R.id.repairer_name);
            linearLayout=itemView.findViewById(R.id.rating_linear_layout);
            repairerRatings=itemView.findViewById(R.id.repairer_rating);
            repairerSpecialities=itemView.findViewById(R.id.repairer_speciality);
        }
    }
}
