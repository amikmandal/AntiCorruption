package com.gretel.scrapknot.view.activities.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
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
public class UserPrimaryActivity extends PrimaryActivity  {

    @Override
    protected void createFragmentTypeMap() {
        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(HOME,0);
        myFragmentIDs.put(REPAIRER_LIST,1);
        myFragmentIDs.put(ORDER_TRACKER,2);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_primary_user);
    }

    @Override
    protected void initializeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.primary_fragment_container,new HomeFragment()).addToBackStack(HOME.toString()).commit();
        myPrevFragment = new HomeFragment();
        myPrevFragmentID = myFragmentIDs.get(HOME);
        myCurrentFragmentID = myPrevFragmentID;
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

    @Override
    protected Intent getSecondaryActivity() {
        return new Intent(getApplicationContext(),UserSecondaryActivity.class);
    }

}
