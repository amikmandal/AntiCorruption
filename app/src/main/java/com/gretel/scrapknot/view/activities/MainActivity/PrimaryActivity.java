package com.gretel.scrapknot.view.activities.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.google.common.collect.HashBiMap;
import com.gretel.scrapknot.view.fragments.HomeFragment;
import com.gretel.scrapknot.view.fragments.OrderTrackerFragment;
import com.gretel.scrapknot.view.fragments.RepairerListFragment;
import com.gretel.scrapknot.R;

import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.CALL_MECHANIC;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.HOME;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.ORDER_LIST;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.ORDER_TRACKER;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.REPAIRER_LIST;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.USER;

/**
 * This is the main activity of the app and hosts all the fragments related to BottomNavigationBar
 * and Navigation Drawer.
 * @author Amik Mandal
 */
public class PrimaryActivity extends MainActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView myBottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(HOME,0);
        myFragmentIDs.put(REPAIRER_LIST,1);
        myFragmentIDs.put(ORDER_TRACKER,2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        myBottomNavigationView = findViewById(R.id.navigation);
        myBottomNavigationView.setOnNavigationItemSelectedListener(this);

        addUIElements();

    }

    @Override
    protected void initializeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.primary_fragment_container,new HomeFragment()).addToBackStack(HOME.toString()).commit();
        myPrevFragment = new HomeFragment();
        myPrevFragmentID = myFragmentIDs.get(HOME);
        myCurrentFragmentID = myPrevFragmentID;
    }

    @Override
    protected void addToolBarButton() {
        myToolbarButton.setBackgroundResource(R.drawable.ic_notifications);
    }

    @Override
    public void onBackPressed(){

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(myDrawer.isDrawerOpen(GravityCompat.START)){
            myDrawer.closeDrawer(GravityCompat.START);
        } else if (count == 0) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case R.id.nav_user:
                openDrawerFragment(item,USER);
                break;
            case R.id.nav_order:
                openDrawerFragment(item,ORDER_LIST);
                break;
            case R.id.nav_contact_mechanic:
                openDrawerFragment(item,CALL_MECHANIC);
                break;
            case R.id.nav_contact_us:
                openDrawerFragment(item,CONTACT_US);
                break;
            case R.id.navigation_home:
                openNavFragment(new HomeFragment(), myFragmentIDs.get(HOME));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(HOME),new HomeFragment(),HOME.toString());
                break;
            case R.id.navigation_repairer:
                openNavFragment(new RepairerListFragment(), myFragmentIDs.get(REPAIRER_LIST));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(REPAIRER_LIST),new RepairerListFragment(),REPAIRER_LIST.toString());
                break;
            case R.id.navigation_tracker:
                openNavFragment(new OrderTrackerFragment(), myFragmentIDs.get(ORDER_TRACKER));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(ORDER_TRACKER),new OrderTrackerFragment(),ORDER_TRACKER.toString());
                break;
            case R.id.nav_review:
                break;
            case R.id.nav_logout:
                logOutUser();
                break;

        }

        return super.onNavigationItemSelected(item);

    }


    protected TransitionType determineTransition(int newFragmentID) {

        if(newFragmentID>myCurrentFragmentID){
            return TransitionType.SLIDE_RIGHT;
        } else {
            return TransitionType.SLIDE_LEFT;
        }
    }


    /**
     * This method is called before opening another Drawer Fragment while one Fragment is already open.
     */
    private void openDrawerFragment(MenuItem item,FragmentType type){
        item.setChecked(true);
        Intent intent = new Intent(getApplicationContext(),SecondaryActivity.class);
        intent.putExtra("openFragment",type);
        startActivity(intent);
    }

    /**
     * This method is called before opening any Navigation Fragment while one Fragment is open
     * @param prevFragment
     * @param prevID
     */
    private void openNavFragment(Fragment prevFragment, int prevID) {
        myPrevFragment = prevFragment;
        myPrevFragmentID = prevID;
    }

}
