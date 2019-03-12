package com.gretel.scrapknot.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.gretel.scrapknot.backend.User;
import com.gretel.scrapknot.frontend.fragments.CallMechanicFragment;
import com.gretel.scrapknot.frontend.fragments.ContactUsFragment;
import com.gretel.scrapknot.frontend.fragments.HomeFragment;
import com.gretel.scrapknot.frontend.fragments.OrderListFragment;
import com.gretel.scrapknot.frontend.fragments.OrderTrackerFragment;
import com.gretel.scrapknot.frontend.fragments.RepairerListFragment;
import com.gretel.scrapknot.frontend.fragments.UserFragment;
import com.gretel.scrapknot.util.LocalStorage;
import com.gretel.scrapknot.R;

/**
 * This is the main activity of the app and hosts all the fragments related to BottomNavigationBar
 * and Navigation Drawer.
 * @author Amik Mandal
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView myBottomNavigationView;
    private DrawerLayout myDrawer;
    private NavigationView myDrawerNavigationView;
    private Fragment myPrevFragment;

    private LocalStorage myLocalStorage;
    private User myUser;
    private Integer myCurrentFragmentID;
    private Integer myPrevFragmentID;
    private TextView myNavUser;

    private final static Integer HOME = 0;
    private final static Integer REPAIRER_LIST = 1;
    private final static Integer ORDER_TRACKER = 2;
    private final static Integer USER = -1;
    private final static Integer ORDER_LIST = -2;
    private final static Integer CALL_MECHANIC = -3;
    private final static Integer CONTACT_US = -4;

    private final static Integer SLIDE_RIGHT = 1;
    private final static Integer SLIDE_LEFT = -1;
    private final static Integer SLIDE_VERTICAL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize all the backend variables
        myLocalStorage = new LocalStorage(getApplicationContext());
        myUser = myLocalStorage.loadUser();

        //initialize all the frontend variables and do the necessary
        //toolbar frontend
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //drawer fronted
        myDrawer = findViewById(R.id.main_container_drawer);
        myDrawerNavigationView = findViewById(R.id.main_nav_view);
        View headerView =  myDrawerNavigationView.getHeaderView(0);
        myNavUser = headerView.findViewById(R.id.header_greetings);
        myNavUser.setText("Hello"+myUser.getName()+"!");
        myDrawerNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,myDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(toggle);
        toggle.syncState();

        //navigation view frontend
        myBottomNavigationView = findViewById(R.id.navigation);
        myBottomNavigationView.setOnNavigationItemSelectedListener(this);

        //start with Home Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).addToBackStack(HOME.toString()).commit();
        myPrevFragment = new HomeFragment();
        myPrevFragmentID = HOME;
        myCurrentFragmentID = myPrevFragmentID;

    }

    @Override
    public void onBackPressed(){

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(myDrawer.isDrawerOpen(GravityCompat.START)){
            myDrawer.closeDrawer(GravityCompat.START);
        } else if (count == 0) {
            super.onBackPressed();
        } else if (myCurrentFragmentID>=0) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            myBottomNavigationView.setVisibility(View.VISIBLE);

            //uncheck selected item from navigation drawer
            int size = myDrawerNavigationView.getMenu().size();
            for (int i = 0; i < size; i++) {
                myDrawerNavigationView.getMenu().getItem(i).setChecked(false);
            }
            getSupportFragmentManager().popBackStack();
            createFragment(R.id.content_frame_small,myPrevFragmentID,myPrevFragment,myPrevFragmentID.toString());

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case R.id.nav_user:
                beforeOpeningDrawerFragment(item);
                createFragment(R.id.content_frame,USER,new UserFragment(),USER.toString());
                break;
            case R.id.nav_order:
                beforeOpeningDrawerFragment(item);
                createFragment(R.id.content_frame,ORDER_LIST,new OrderListFragment(),ORDER_LIST.toString());
                break;
            case R.id.nav_contact_mechanic:
                beforeOpeningDrawerFragment(item);
                createFragment(R.id.content_frame,CALL_MECHANIC,new CallMechanicFragment(),CALL_MECHANIC.toString());
                break;
            case R.id.nav_contact_us:
                beforeOpeningDrawerFragment(item);
                createFragment(R.id.content_frame,CONTACT_US,new ContactUsFragment(),CONTACT_US.toString());
                break;
            case R.id.navigation_home:
                beforeOpeningNavFragment(new HomeFragment(), HOME);
                createFragment(R.id.content_frame_small,HOME,new HomeFragment(),HOME.toString());
                break;
            case R.id.navigation_repairer:
                beforeOpeningNavFragment(new RepairerListFragment(), REPAIRER_LIST);
                createFragment(R.id.content_frame_small,REPAIRER_LIST,new RepairerListFragment(),REPAIRER_LIST.toString());
                break;
            case R.id.navigation_tracker:
                beforeOpeningNavFragment(new OrderTrackerFragment(), ORDER_TRACKER);
                createFragment(R.id.content_frame_small,ORDER_TRACKER,new OrderTrackerFragment(),ORDER_TRACKER.toString());
                break;
            case R.id.nav_review:
                break;
            case R.id.nav_logout:
                logOutUser();
                break;

        }

        myDrawer.closeDrawer(GravityCompat.START);
        return true;

    }

    /**
     * This method determines the transition type between fragments involved in main activity
     * @param newFragmentID specifies the id of the new fragment
     * @return the transition type
     */
    private int determineTransition(int newFragmentID) {

        if(myCurrentFragmentID>=0 && newFragmentID<0){
            return SLIDE_VERTICAL;
        } else if(myCurrentFragmentID>=0 && newFragmentID>myCurrentFragmentID){
            return SLIDE_RIGHT;
        } else if(myCurrentFragmentID>=0 && newFragmentID < myCurrentFragmentID){
            return SLIDE_LEFT;
        }
        return SLIDE_VERTICAL;
    }

    /**
     * This method creates a fragment
     * @param fragmentContainer specifies the part of screen the fragment must confine in.
     * @param fragment specifies the fragment to be displayed
     * @param fragmentName specifies the fragment name to be associated with in the stack
     */
    private void createFragment(int fragmentContainer, int fragmentID, Fragment fragment, String fragmentName){

        int transitionType = determineTransition(fragmentID);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(transitionType > 0)
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        else if(transitionType < 0)
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
        else
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_top,R.anim.slide_out_bottom,R.anim.slide_in_bottom,R.anim.slide_out_top);

        fragmentTransaction.replace(fragmentContainer,fragment).addToBackStack(fragmentName).commit();

        myCurrentFragmentID = fragmentID;

    }

    /**
     * This method is called before opening another Drawer Fragment while one Fragment is already open.
     */
    private void beforeOpeningDrawerFragment(MenuItem item){
        item.setChecked(true);
        myBottomNavigationView.setVisibility(View.GONE);
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            if (fragment!=null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

    /**
     * This method is called before opening any Navigation Fragment while one Fragment is open
     * @param prevFragment
     * @param prevID
     */
    private void beforeOpeningNavFragment(Fragment prevFragment, int prevID) {
        myPrevFragment = prevFragment;
        myPrevFragmentID = prevID;
    }

    /**
     * This method is used to delete user data from shared preference and log out a user.
     */
    private void logOutUser() {
        String loginType = myLocalStorage.loadString("loginType");
        myLocalStorage.removeUser();

        if(loginType.equals("facebook")){
            LoginManager.getInstance().logOut();
        }

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
