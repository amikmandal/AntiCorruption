package com.gretel.mendit.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gretel.mendit.backend.User;
import com.gretel.mendit.backend.UserForm;
import com.gretel.mendit.util.Data;
import com.gretel.mendit.util.LocalStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import gretel.com.mendit.R;

public class UserActivity extends AppCompatActivity {

    private BottomNavigationView myBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private DatabaseReference databaseUsers;
    private Data myData;
    private TextView myTextName;
    private TextView myTextEmail;
    private TextView myTextAddress;
    private TextView myTextNumber;
    private CircleImageView myProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_dashboard:
                        Intent myIntent = new Intent(getApplicationContext(), RepairerListActivity.class);
                        startActivity(myIntent);
                        break;
                    case R.id.navigation_notifications:
                        break;
                }
                return false;
            }
        };

        myBottomNavigationView = findViewById(R.id.navigation);
        myBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //createUser
        UserForm userForm = new UserForm(getApplicationContext());
        if(getIntent().getExtras().containsKey("index") && getIntent().getExtras().get("index").equals(Integer.toString(userForm.getRequirementsSize()))){
            userForm.makeUser(getIntent().getExtras());
        }


        Menu menu = myBottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        databaseUsers = FirebaseDatabase.getInstance().getReference("user");

        myTextName = (TextView)findViewById(R.id.user_name);
        myTextEmail = (TextView)findViewById(R.id.user_username);;
        myTextAddress = (TextView)findViewById(R.id.user_address);;
        myTextNumber = (TextView)findViewById(R.id.user_phone_number);;
        myProfilePhoto = (CircleImageView) findViewById(R.id.user_profile_photo);

        myData = new Data();
//        Data d = new Data();
//        d.createUserFromFiles();
        //initUserRecyclerView(new Data());
    }

    @Override
    public void onStart(){
        super.onStart();

        LocalStorage localStorage = new LocalStorage(getApplicationContext());

        User u = localStorage.loadUser();

        System.out.println("Email----------->"+u.getEmail());

        myTextName.setText(u.getName());
        myTextAddress.setText(u.getAddress());
        myTextEmail.setText(u.getEmail());
        myTextNumber.setText(u.getNumber());
        Picasso.get().load(u.getDisplayPicture()).into(myProfilePhoto);



    }

    private void initUserRecyclerView(Data d)
    {
        //call RecyclerView
//        myRecyclerView = findViewById(R.id.user_recycler_view);
//        RepairerListAdapter adapter = new RepairerListAdapter(d.getRepairerList(),this);
//        myRecyclerView.setAdapter(adapter);

        //check this
//        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
