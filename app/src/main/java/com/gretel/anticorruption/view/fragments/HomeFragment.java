package com.gretel.anticorruption.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Agent.Report;
import com.gretel.anticorruption.view.adapters.ReportAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private ReportAdapter myAdapter;

    private DatabaseReference myReportDatabase;
    private List<Report> myReports;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        myReports = new ArrayList<>();
        myReportDatabase = FirebaseDatabase.getInstance().getReference("reports");

        initRecyclerView(view);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        myReports.clear();
        myReportDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()) {

                    Gson gson = new Gson();
                    String json = d.getValue(String.class);
                    Report r = gson.fromJson(json, Report.class);

                    myReports.add(r);
                    myAdapter = new ReportAdapter(myReports, getActivity().getApplicationContext());
                    myRecyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    /**
     * This method initializes the Recycler View for the home fragment
     * @param v specifies the view to which the recycler view for the list will be attached
     */
    private void initRecyclerView(View v)
    {
        //call RecyclerView
        myRecyclerView = v.findViewById(R.id.report_recycler_view);
        ReportAdapter adapter = new ReportAdapter(myReports,getActivity().getApplicationContext());
        myRecyclerView.setAdapter(adapter);

        //check this
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }


}
