package com.gretel.scrapknot.view.activities.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.google.common.collect.HashBiMap;
import com.gretel.scrapknot.R;
import com.gretel.scrapknot.view.fragments.DashboardFragment;
import com.gretel.scrapknot.view.fragments.RequestListFragment;
import com.gretel.scrapknot.view.fragments.UpdateProgressFragment;

import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_CUSTOMER;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.DASHBOARD;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.REPAIRER;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.REQUEST_LIST;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.UPDATE_PROGRESS;

public class RepairerPrimaryActivity extends PrimaryActivity {

    @Override
    protected void createFragmentTypeMap() {
        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(DASHBOARD,0);
        myFragmentIDs.put(REQUEST_LIST,1);
        myFragmentIDs.put(UPDATE_PROGRESS,2);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_primary_repairer);
    }

    @Override
    protected void initializeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.primary_fragment_container,new DashboardFragment()).addToBackStack(DASHBOARD.toString()).commit();
        myPrevFragment = new DashboardFragment();
        myPrevFragmentID = myFragmentIDs.get(DASHBOARD);
        myCurrentFragmentID = myPrevFragmentID;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case R.id.nav_repairer:
                openDrawerFragment(item,REPAIRER);
                break;
            case R.id.nav_contact_customer:
                openDrawerFragment(item,CONTACT_CUSTOMER);
                break;
            case R.id.navigation_dashboard:
                openNavFragment(new DashboardFragment(), myFragmentIDs.get(DASHBOARD));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(DASHBOARD),new DashboardFragment(),DASHBOARD.toString());
                break;
            case R.id.navigation_request_list:
                openNavFragment(new RequestListFragment(), myFragmentIDs.get(REQUEST_LIST));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(REQUEST_LIST),new RequestListFragment(),REQUEST_LIST.toString());
                break;
            case R.id.navigation_update_progress:
                openNavFragment(new UpdateProgressFragment(), myFragmentIDs.get(UPDATE_PROGRESS));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(UPDATE_PROGRESS),new UpdateProgressFragment(),UPDATE_PROGRESS.toString());
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
        return new Intent(getApplicationContext(),RepairerSecondaryActivity.class);
    }

}
