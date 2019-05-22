package com.gretel.anticorruption.view.activities.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;

import com.google.common.collect.HashBiMap;
import com.gretel.anticorruption.view.activities.ReportActivity.ReportActivity;
import com.gretel.anticorruption.view.fragments.HomeFragment;
import com.gretel.anticorruption.view.fragments.FriendsFragment;
import com.gretel.anticorruption.view.fragments.AuthorityListFragment;
import com.gretel.anticorruption.R;

import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.AUTHORITIES;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.COMPLAINT_TRACKER;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.FIND_FRIENDS;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.FRIENDS;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.HOME;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.USER;

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
        myFragmentIDs.put(AUTHORITIES,1);
        myFragmentIDs.put(FRIENDS,2);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_primary_user);
        FloatingActionButton newReport = findViewById(R.id.new_report);
        newReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });
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
            case R.id.nav_contact_us:
                openDrawerFragment(item,CONTACT_US);
                break;
            case R.id.nav_complaint_tracker:
                openDrawerFragment(item,COMPLAINT_TRACKER);
                break;
            case R.id.nav_find_friends:
                openDrawerFragment(item,FIND_FRIENDS);
                break;
            case R.id.navigation_home:
                openNavFragment(new HomeFragment(), myFragmentIDs.get(HOME));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(HOME),new HomeFragment(),HOME.toString());
                break;
            case R.id.authorities:
                openNavFragment(new AuthorityListFragment(), myFragmentIDs.get(AUTHORITIES));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(AUTHORITIES),new AuthorityListFragment(),AUTHORITIES.toString());
                break;
            case R.id.navigation_friends:
                openNavFragment(new FriendsFragment(), myFragmentIDs.get(FRIENDS));
                createFragment(R.id.primary_fragment_container,myFragmentIDs.get(FRIENDS),new FriendsFragment(),FRIENDS.toString());
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
