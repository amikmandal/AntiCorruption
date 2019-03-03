package com.gretel.mendit.frontend.fragments;

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
import com.gretel.mendit.backend.Repairer;
import com.gretel.mendit.frontend.adapters.RepairerListAdapter;
import com.gretel.mendit.util.Data;

import gretel.com.mendit.R;

/**
 * This implements the Fragment to display a list of Repairers.
 * @author Amik Mandal
 */
public class RepairerListFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private RepairerListAdapter myAdapter;

    private DatabaseReference myDatabaseMechanics;
    private Data myData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repairer_list,container,false);

        myData = new Data();
        myDatabaseMechanics = FirebaseDatabase.getInstance().getReference("mechanics");

        initRecyclerView(myData,view);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        myDatabaseMechanics.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    int index=0;
                    String dataPath="", name="", speciality="";
                    Double rating=0.0;
                    for(DataSnapshot e: d.getChildren()){
                        if(index==0){
                            dataPath = e.getValue(String.class);
                        }else if(index==1){
                            name = e.getValue(String.class);
                        }else if(index==2){
                            rating = e.getValue(Double.class);
                        }else{
                            speciality = e.getValue(String.class);
                        }
                        index++;
                    }

                    Repairer temp = new Repairer(dataPath,name,rating,speciality);
                    myData.addMechanic(temp);
                    myAdapter = new RepairerListAdapter(myData.getRepairerList(), getActivity().getApplicationContext());
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
    private void initRecyclerView(Data d, View v)
    {
        //call RecyclerView
        myRecyclerView = v.findViewById(R.id.repairer_list_recycler_view);
        RepairerListAdapter adapter = new RepairerListAdapter(d.getRepairerList(),getActivity().getApplicationContext());
        myRecyclerView.setAdapter(adapter);

        //check this
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}
