package com.gretel.mendit.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gretel.mendit.frontend.fragments.CallMechanicFragment;
import com.gretel.mendit.frontend.fragments.ContactUsFragment;
import com.gretel.mendit.frontend.fragments.OrderListFragment;
import com.gretel.mendit.frontend.fragments.UserFragment;

import gretel.com.mendit.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView myBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    private DrawerLayout myDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        myDrawer = findViewById(R.id.main_container_drawer);

        NavigationView navigationView = findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,myDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(myIntent);
                        break;
                    case R.id.navigation_dashboard:
                        myIntent = new Intent(getApplicationContext(), RepairerListActivity.class);
                        startActivity(myIntent);
                        break;
                    case R.id.navigation_notifications:
                        myIntent = new Intent(getApplicationContext(), UserActivity.class);
                        startActivity(myIntent);
                        break;
                }
                return false;
            }
        };

        myBottomNavigationView = findViewById(R.id.navigation);
        myBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = myBottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed(){

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(myDrawer.isDrawerOpen(GravityCompat.START)){
            myDrawer.closeDrawer(GravityCompat.START);
        } else if (count == 0) {
            super.onBackPressed();
        } else {
            myBottomNavigationView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStack();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_user:
                myBottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new UserFragment()).addToBackStack("user").commit();
                break;
            case R.id.nav_order:
                myBottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new OrderListFragment()).addToBackStack("order list").commit();
                break;
            case R.id.nav_contact_mechanic:
                myBottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new CallMechanicFragment()).addToBackStack("call repairer").commit();
                break;
            case R.id.nav_contact_us:
                myBottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new ContactUsFragment()).addToBackStack("contact us").commit();
                break;
            case R.id.nav_review:
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new UserFragment()).commit();
                break;
            case R.id.nav_logout:
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new UserFragment()).commit();
                break;

        }

        myDrawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
