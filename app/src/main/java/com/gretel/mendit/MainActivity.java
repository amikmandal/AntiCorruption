package com.gretel.mendit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

import gretel.com.mendit.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Data myData;
    private RecyclerView myRecyclerView;
    private MainActivityRecyclerViewAdapter myAdapter;
    DatabaseReference databaseMechanics;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myData = new Data();
        //myData.createDataFromFiles();
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        databaseMechanics = FirebaseDatabase.getInstance().getReference("mechanics");

        getData();
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
                    System.out.println("Name----------->"+dataPath+" "+name+" "+rating+" "+speciality );
                    NameEntry temp = new NameEntry(dataPath,name,rating,speciality);
                    myData.addMechanic(temp);

                    for(NameEntry s: myData.getData()) {
                        System.out.println("Data Stored --------->" + s.getName());
                    }

                    myAdapter = new MainActivityRecyclerViewAdapter(myData.getData(), getApplicationContext());
                    myRecyclerView.setAdapter(myAdapter);
                    myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    myRecyclerView.setAdapter(new MainActivityRecyclerViewAdapter(myData.getData(),getApplicationContext()));


                    //myData.addMechanic(new NameEntry(entry[2].substring(5),Double.parseDouble(entry[3].substring(8,11)),entry[1].substring(11),entry[0].substring(15)));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public void getData(){
        initRecyclerView(myData);
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//
//        databaseMechanics.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                myData.resetData();
//
//                for(DataSnapshot mechanicSnapshot: dataSnapshot.getChildren()){
//                    NameEntry temp = mechanicSnapshot.getValue(NameEntry.class);
//                    myData.addMechanic(temp);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void initRecyclerView(Data d)
    {
        //call RecyclerView
        myRecyclerView = findViewById(R.id.main_recycler_view);
        MainActivityRecyclerViewAdapter adapter = new MainActivityRecyclerViewAdapter(d.getData(),this);
        myRecyclerView.setAdapter(adapter);

        //check this
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
