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
import com.gretel.anticorruption.model.Agent.Authority;
import com.gretel.anticorruption.view.adapters.RepairerListAdapter;
import com.gretel.anticorruption.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This implements the Fragment to display a list of Repairers.
 * @author Amik Mandal
 */
public class AuthorityListFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private RepairerListAdapter myAdapter;

    private DatabaseReference myDatabaseMechanics;
    private List<Authority> myAuthorityList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repairer_list,container,false);

        myAuthorityList = new ArrayList<>();
        myDatabaseMechanics = FirebaseDatabase.getInstance().getReference("repairer");

        initRecyclerView(myAuthorityList,view);

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
                    Authority r = gson.fromJson(json, Authority.class);

                    myAuthorityList.add(r);
                    myAdapter = new RepairerListAdapter(myAuthorityList, getActivity().getApplicationContext());
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
     * @param d specifies the data object to be used to display the list of authorities
     * @param v specifies the view to which the recycler view for the list will be attached
     */
    private void initRecyclerView(List<Authority> authorities, View v)
    {
        //call RecyclerView
        myRecyclerView = v.findViewById(R.id.repairer_list_recycler_view);
        RepairerListAdapter adapter = new RepairerListAdapter(myAuthorityList,getActivity().getApplicationContext());
        myRecyclerView.setAdapter(adapter);

        //check this
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}
