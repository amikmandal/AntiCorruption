package com.gretel.anticorruption.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Agent.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{

    private ArrayList<String> myOfficer = new ArrayList<String>();
    private ArrayList<String> myAuthority = new ArrayList<String>();
    private ArrayList<String> myPlaces = new ArrayList<String>();
    private ArrayList<String> myReports = new ArrayList<String>();
    private ArrayList<String> myDates = new ArrayList<String>();
    private Context myContext;

    public ReportAdapter(List<Report> data, Context context){
        for(Report temp: data){
            myOfficer.add(temp.getOfficer());
            myAuthority.add(temp.getAuthority());
            myPlaces.add(temp.getPlace());
            myReports.add(temp.getReport());
            myDates.add(temp.getReportDate());
        }
        myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.holder_report,viewGroup,false);
        final ReportAdapter.ViewHolder reportHolder = new ReportAdapter.ViewHolder(row);
        return  reportHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.myOfficerText.setText(myOfficer.get(position));
        viewHolder.myAuthorityText.setText(myAuthority.get(position));
        viewHolder.myPlaceText.setText(myPlaces.get(position));
        viewHolder.myReportText.setText(myReports.get(position));
        viewHolder.myDateText.setText(myDates.get(position));
    }

    //not meant to be 0. To be changed to a variable based on database.
    @Override
    public int getItemCount() {
        return myReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myOfficerText;
        TextView myAuthorityText;
        TextView myPlaceText;
        TextView myReportText;
        TextView myDateText;
        RelativeLayout myRelativeLayout;
        LinearLayout myLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myRelativeLayout=itemView.findViewById(R.id.report_relative_layout);
            myOfficerText=itemView.findViewById(R.id.text_officer);
            myAuthorityText=itemView.findViewById(R.id.text_authority);
            myPlaceText=itemView.findViewById(R.id.text_place);
            myReportText=itemView.findViewById(R.id.text_report);
            myDateText=itemView.findViewById(R.id.text_date);
            myLinearLayout=itemView.findViewById(R.id.report_linear_layout);
        }
    }
}
