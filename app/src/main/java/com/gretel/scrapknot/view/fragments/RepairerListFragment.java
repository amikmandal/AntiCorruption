package com.gretel.scrapknot.view.fragments;

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
import com.gretel.scrapknot.model.Agent.Repairer;
import com.gretel.scrapknot.model.Agent.User;
import com.gretel.scrapknot.view.adapters.RepairerListAdapter;
import com.gretel.scrapknot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This implements the Fragment to display a list of Repairers.
 * @author Amik Mandal
 */
public class RepairerListFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private RepairerListAdapter myAdapter;

    private DatabaseReference myDatabaseMechanics;
    private List<Repairer> myRepairerList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repairer_list,container,false);

        myRepairerList = new ArrayList<>();
        myDatabaseMechanics = FirebaseDatabase.getInstance().getReference("repairer");

        initRecyclerView(myRepairerList,view);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        myDatabaseMechanics.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()) {

                    Gson gson = new Gson();
                    String json = d.getValue(String.class);
                    Repairer r = gson.fromJson(json, Repairer.class);

                    myRepairerList.add(r);
                    myAdapter = new RepairerListAdapter(myRepairerList, getActivity().getApplicationContext());
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
     * This method initializes the Recycler View for the app
     * @param d specifies the data object to be used to display the list of repairers
     * @param v specifies the view to which the recycler view for the list will be attached
     */
    private void initRecyclerView(List<Repairer> repairers, View v)
    {
        //call RecyclerView
        myRecyclerView = v.findViewById(R.id.repairer_list_recycler_view);
        RepairerListAdapter adapter = new RepairerListAdapter(myRepairerList,getActivity().getApplicationContext());
        myRecyclerView.setAdapter(adapter);

        //check this
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}
