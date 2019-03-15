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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.common.collect.HashBiMap;
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
import com.gretel.scrapknot.util.EditButtonListener;
import com.gretel.scrapknot.util.UserLoader;

import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.CALL_MECHANIC;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.HOME;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.ORDER_LIST;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.ORDER_TRACKER;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.REPAIRER_LIST;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.USER;

/**
 * This is the main activity of the app and hosts all the fragments related to BottomNavigationBar
 * and Navigation Drawer.
 * @author Amik Mandal
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    enum FragmentType {
        HOME,REPAIRER_LIST,ORDER_TRACKER,USER,ORDER_LIST,CALL_MECHANIC,CONTACT_US
    }

    enum TransitionType {
        SLIDE_LEFT,SLIDE_RIGHT,SLIDE_BOTTOM
    }

    private BottomNavigationView myBottomNavigationView;
    private DrawerLayout myDrawer;
    private NavigationView myDrawerNavigationView;
    private Fragment myPrevFragment;
    private Button myToolbarButton;
    private TextView myNavUser;

    private Integer myCurrentFragmentID;
    private Integer myPrevFragmentID;
    private EditButtonListener myEditButtonListener;

    private HashBiMap<FragmentType,Integer> myFragmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize variables
        // //initialize all the local variables
        myFragmentID = HashBiMap.create();
        myFragmentID.put(HOME,0);
        myFragmentID.put(REPAIRER_LIST,1);
        myFragmentID.put(ORDER_TRACKER,2);
        myFragmentID.put(USER,-1);
        myFragmentID.put(ORDER_LIST,-2);
        myFragmentID.put(CALL_MECHANIC,-3);
        myFragmentID.put(CONTACT_US,-4);

        // //initialize all the backend variables
        UserLoader userLoader = new UserLoader(this,getApplicationContext());
        userLoader.execute();

        // //initialize all the frontend variables and do the necessary
        // // //toolbar frontend
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        myToolbarButton = findViewById(R.id.menu_button);

        // // //drawer fronted
        myDrawer = findViewById(R.id.main_container_drawer);
        myDrawerNavigationView = findViewById(R.id.main_nav_view);
        View headerView =  myDrawerNavigationView.getHeaderView(0);
        myNavUser = headerView.findViewById(R.id.header_greetings);
//        myNavUser.setText("Hello"+myUser.getName()+"!");
        myDrawerNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,myDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(toggle);
        toggle.syncState();

        // // //navigation view frontend
        myBottomNavigationView = findViewById(R.id.navigation);
        myBottomNavigationView.setOnNavigationItemSelectedListener(this);

        //start with Home Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).addToBackStack(HOME.toString()).commit();
        myPrevFragment = new HomeFragment();
        myPrevFragmentID = myFragmentID.get(HOME);
        myCurrentFragmentID = myPrevFragmentID;

        myToolbarButton.setVisibility(View.GONE);
        myToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (myFragmentID.inverse().get(myCurrentFragmentID)){
                    case USER:
                        myEditButtonListener.onEditButtonSelect(myToolbarButton);
                        break;
                }
            }
        });

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof EditButtonListener){
            myEditButtonListener = (EditButtonListener) fragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myEditButtonListener = null;
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
                createFragment(R.id.content_frame,myFragmentID.get(USER),new UserFragment(),USER.toString());
                break;
            case R.id.nav_order:
                beforeOpeningDrawerFragment(item);
                createFragment(R.id.content_frame,myFragmentID.get(ORDER_LIST),new OrderListFragment(),ORDER_LIST.toString());
                break;
            case R.id.nav_contact_mechanic:
                beforeOpeningDrawerFragment(item);
                createFragment(R.id.content_frame,myFragmentID.get(CALL_MECHANIC),new CallMechanicFragment(),CALL_MECHANIC.toString());
                break;
            case R.id.nav_contact_us:
                beforeOpeningDrawerFragment(item);
                createFragment(R.id.content_frame,myFragmentID.get(CONTACT_US),new ContactUsFragment(),CONTACT_US.toString());
                break;
            case R.id.navigation_home:
                beforeOpeningNavFragment(new HomeFragment(), myFragmentID.get(HOME));
                createFragment(R.id.content_frame_small,myFragmentID.get(HOME),new HomeFragment(),HOME.toString());
                break;
            case R.id.navigation_repairer:
                beforeOpeningNavFragment(new RepairerListFragment(), myFragmentID.get(REPAIRER_LIST));
                createFragment(R.id.content_frame_small,myFragmentID.get(REPAIRER_LIST),new RepairerListFragment(),REPAIRER_LIST.toString());
                break;
            case R.id.navigation_tracker:
                beforeOpeningNavFragment(new OrderTrackerFragment(), myFragmentID.get(ORDER_TRACKER));
                createFragment(R.id.content_frame_small,myFragmentID.get(ORDER_TRACKER),new OrderTrackerFragment(),ORDER_TRACKER.toString());
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
    private TransitionType determineTransition(int newFragmentID) {

        if(myCurrentFragmentID>=0 && newFragmentID<0){
            return TransitionType.SLIDE_BOTTOM;
        } else if(myCurrentFragmentID>=0 && newFragmentID>myCurrentFragmentID){
            return TransitionType.SLIDE_RIGHT;
        } else if(myCurrentFragmentID>=0 && newFragmentID < myCurrentFragmentID){
            return TransitionType.SLIDE_LEFT;
        }
        return TransitionType.SLIDE_BOTTOM;
    }

    /**
     * This method creates a fragment
     * @param fragmentContainer specifies the part of screen the fragment must confine in.
     * @param fragment specifies the fragment to be displayed
     * @param fragmentName specifies the fragment name to be associated with in the stack
     */
    private void createFragment(int fragmentContainer, int fragmentID, Fragment fragment, String fragmentName){

        TransitionType transitionType = determineTransition(fragmentID);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (transitionType){
            case SLIDE_LEFT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case SLIDE_RIGHT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case SLIDE_BOTTOM:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_top,R.anim.slide_out_bottom,R.anim.slide_in_bottom,R.anim.slide_out_top);
                break;

        }

        fragmentTransaction.replace(fragmentContainer,fragment).addToBackStack(fragmentName).commit();

        myCurrentFragmentID = fragmentID;

        makeConstantUIChanges();

    }

    private void makeConstantUIChanges() {
        if(myCurrentFragmentID==myFragmentID.get(USER)){
            myToolbarButton.setVisibility(View.VISIBLE);
            myToolbarButton.setBackgroundResource(R.drawable.ic_edit);
        }else {
            if(myToolbarButton.getVisibility()==View.VISIBLE)
                myToolbarButton.setVisibility(View.GONE);
        }
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
        LocalStorage localStorage = new LocalStorage(getApplicationContext());

        String loginType = localStorage.loadString("loginType");
        localStorage.removeUser();

        if(loginType.equals("facebook")){
            LoginManager.getInstance().logOut();
        }

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Getter method to send textview for displaying username in Navigation Drawer upon completion of Local Storage.
     * @return
     */
    public TextView getNavHeaderTextView(){
        return myNavUser;
    }

}
