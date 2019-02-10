package com.gretel.mendit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gretel.com.mendit.R;

public class RepairerListActivity extends AppCompatActivity {

    private Data myData;
    private RecyclerView myRecyclerView;
    private RepairerListAdapter myAdapter;
    private DatabaseReference databaseMechanics;
    private BottomNavigationView myBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairer_list);

        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(myIntent);
                        break;
                    case R.id.navigation_dashboard:
                        break;
                    case R.id.navigation_notifications:
                        break;
                }
                return false;
            }
        };

        myData = new Data();
        //myData.createDataFromFiles();
        myBottomNavigationView = findViewById(R.id.navigation);
        myBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = myBottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        databaseMechanics = FirebaseDatabase.getInstance().getReference("mechanics");

        initRecyclerView(myData);
    }

    @Override
    public void onStart(){
        super.onStart();

        databaseMechanics.addValueEventListener(new ValueEventListener() {

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

                    NameEntry temp = new NameEntry(dataPath,name,rating,speciality);
                    myData.addMechanic(temp);
                    myAdapter = new RepairerListAdapter(myData.getRepairerList(), getApplicationContext());
                    myRecyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    private void initRecyclerView(Data d)
    {
        //call RecyclerView
        myRecyclerView = findViewById(R.id.repairer_list_recycler_view);
        RepairerListAdapter adapter = new RepairerListAdapter(d.getRepairerList(),this);
        myRecyclerView.setAdapter(adapter);

        //check this
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}