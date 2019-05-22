package com.gretel.anticorruption.view.activities.MainActivity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.google.common.collect.HashBiMap;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.view.fragments.ComplaintTrackerFragment;
import com.gretel.anticorruption.view.fragments.ContactUsFragment;
import com.gretel.anticorruption.view.fragments.FindFriendsFragment;
import com.gretel.anticorruption.view.fragments.UserFragment;

import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.FIND_FRIENDS;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.COMPLAINT_TRACKER;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.anticorruption.view.activities.MainActivity.MainActivity.FragmentType.USER;
import static com.gretel.anticorruption.view.fragments.UserFragment.FragmentMode.EDIT;

public class UserSecondaryActivity extends SecondaryActivity {

    @Override
    protected void createFragmentTypeMap() {
        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(USER,0);
        myFragmentIDs.put(COMPLAINT_TRACKER,1);
        myFragmentIDs.put(FIND_FRIENDS,2);
        myFragmentIDs.put(CONTACT_US,3);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_secondary_user);
    }

    @Override
    protected void initializeFragment() {
        switch((FragmentType) getIntent().getSerializableExtra("openFragment")){
            case USER:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(USER),new UserFragment(),USER.toString());
                break;
            case COMPLAINT_TRACKER:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(COMPLAINT_TRACKER),new ComplaintTrackerFragment(),COMPLAINT_TRACKER.toString());
                break;
            case FIND_FRIENDS:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(FIND_FRIENDS),new FindFriendsFragment(),FIND_FRIENDS.toString());
                break;
            case CONTACT_US:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_US),new ContactUsFragment(),CONTACT_US.toString());
                break;
        }
    }

    @Override
    protected void addToolBarButton() {
        switch (myFragmentIDs.inverse().get(myCurrentFragmentID)){
            case USER:
                myToolbarButton.setBackgroundResource(R.drawable.ic_edit);
                myToolbarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myEditButtonListener.onEditButtonSelect(myToolbarButton);
                    }
                });
                break;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_user:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(USER),new UserFragment(),USER.toString());
                break;
            case R.id.nav_complaint_tracker:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(COMPLAINT_TRACKER),new ComplaintTrackerFragment(),COMPLAINT_TRACKER.toString());
                break;
            case R.id.nav_find_friends:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(FIND_FRIENDS),new FindFriendsFragment(),FIND_FRIENDS.toString());
                break;
            case R.id.nav_contact_us:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_US),new ContactUsFragment(),CONTACT_US.toString());
                break;
        }

        return super.onNavigationItemSelected(item);
    }

    protected void handleSpecializedBackPress() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.secondary_fragment_container);
        switch (myFragmentIDs.inverse().get(myCurrentFragmentID)){
            case USER:
                UserFragment userFragment = (UserFragment) fragment;
                if (userFragment != null && userFragment.changesMade() && userFragment.getCurrentMode() == EDIT) {
                    userFragment.handleCancelButton(fragment.getView());
                }
                break;
        }
    }

}