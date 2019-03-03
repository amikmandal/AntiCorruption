package com.gretel.mendit.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.gretel.mendit.frontend.fragments.CallMechanicFragment;
import com.gretel.mendit.frontend.fragments.ContactUsFragment;
import com.gretel.mendit.frontend.fragments.HomeFragment;
import com.gretel.mendit.frontend.fragments.OrderListFragment;
import com.gretel.mendit.frontend.fragments.OrderTrackerFragment;
import com.gretel.mendit.frontend.fragments.RepairerListFragment;
import com.gretel.mendit.frontend.fragments.UserFragment;
import com.gretel.mendit.util.LocalStorage;

import gretel.com.mendit.R;

/**
 * This is the main activity of the app and hosts all the fragments related to BottomNavigationBar
 * and Navigation Drawer.
 * @author Amik Mandal
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView myBottomNavigationView;
    private DrawerLayout myDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize all the frontend variables and do the necessary
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        myDrawer = findViewById(R.id.main_container_drawer);
        NavigationView navigationView = findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,myDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(toggle);
        toggle.syncState();
        myBottomNavigationView = findViewById(R.id.navigation);
        myBottomNavigationView.setOnNavigationItemSelectedListener(myNavListener);

        //start with Home Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).addToBackStack("currentFragment").commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener myNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_repairer:
                    selectedFragment = new RepairerListFragment();
                    break;
                case R.id.navigation_tracker:
                    selectedFragment = new OrderTrackerFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame_small,selectedFragment).addToBackStack("currentFragment").commit();

            return true;
        }
    };


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
                beforeOpeningFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new UserFragment()).addToBackStack("user").commit();
                break;
            case R.id.nav_order:
                beforeOpeningFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new OrderListFragment()).addToBackStack("order list").commit();
                break;
            case R.id.nav_contact_mechanic:
                beforeOpeningFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new CallMechanicFragment()).addToBackStack("call repairer").commit();
                break;
            case R.id.nav_contact_us:
                beforeOpeningFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new ContactUsFragment()).addToBackStack("contact us").commit();
                break;
            case R.id.nav_review:
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new UserFragment()).commit();
                break;
            case R.id.nav_logout:
                logOutUser();
                break;

        }

        myDrawer.closeDrawer(GravityCompat.START);
        return true;

    }

    /**
     * This method is called before opening another Fragment while one Fragment is already open.
     */
    private void beforeOpeningFragment(){
        myBottomNavigationView.setVisibility(View.GONE);
        getSupportFragmentManager().popBackStack();
    }

    /**
     * This method is used to delete user data from shared preference and log out a user.
     */
    private void logOutUser() {
        LocalStorage localStorage = new LocalStorage(getApplicationContext());
        String loginType = localStorage.loadString("loginType");
        localStorage.removeUser();

        if(loginType.equals("facebook")){
            LoginManager.getInstance().logOut();
        }

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
