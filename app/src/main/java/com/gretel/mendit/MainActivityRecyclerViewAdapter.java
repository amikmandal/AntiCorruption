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
import gretel.com.linex.R;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder>{

    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> myProfilePhotos = new ArrayList<String>();
    private ArrayList<String> myPartners = new ArrayList<String>();
    private ArrayList<String> myPartnerPayments = new ArrayList<String>();
    private Context myContext;

    public MainActivityRecyclerViewAdapter(ArrayList<String> profilePhotos, ArrayList<String> partners, ArrayList partnerPayments, Context context){
        myProfilePhotos = profilePhotos;
        myPartners = partners;
        myPartnerPayments = partnerPayments;
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

        viewHolder.partnerName.setText(myPartners.get(position));
        viewHolder.partnerPaid.setText(myPartners.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profilePhoto;
        TextView partnerName;
        TextView partnerPaid;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePhoto=itemView.findViewById(R.id.line_partner_profile_photo);
            partnerName=itemView.findViewById(R.id.line_partner_name);
            partnerPaid=itemView.findViewById(R.id.line_partner_text_view);
        }
    }
}
