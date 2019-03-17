package com.gretel.scrapknot.frontend.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;

import com.google.common.collect.HashBiMap;
import com.gretel.scrapknot.R;
import com.gretel.scrapknot.frontend.fragments.CallMechanicFragment;
import com.gretel.scrapknot.frontend.fragments.ContactUsFragment;
import com.gretel.scrapknot.frontend.fragments.OrderListFragment;
import com.gretel.scrapknot.frontend.fragments.UserFragment;
import com.gretel.scrapknot.util.EditButtonListener;

import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.CALL_MECHANIC;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.ORDER_LIST;
import static com.gretel.scrapknot.frontend.activities.MainActivity.FragmentType.USER;
import static com.gretel.scrapknot.frontend.activities.MainActivity.TransitionType.SLIDE_BOTTOM;
import static com.gretel.scrapknot.frontend.fragments.UserFragment.FragmentMode.EDIT;

public class SecondaryActivity extends MainActivity {

    private EditButtonListener myEditButtonListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_secondary);

        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(USER,0);
        myFragmentIDs.put(ORDER_LIST,1);
        myFragmentIDs.put(CALL_MECHANIC,2);
        myFragmentIDs.put(CONTACT_US,3);

        addUIElements();
    }

    @Override
    protected void initializeFragment() {
        switch((FragmentType) getIntent().getSerializableExtra("openFragment")){
            case USER:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(USER),new UserFragment(),USER.toString());
                break;
            case ORDER_LIST:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(ORDER_LIST),new OrderListFragment(),ORDER_LIST.toString());
                break;
            case CALL_MECHANIC:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CALL_MECHANIC),new CallMechanicFragment(),CALL_MECHANIC.toString());
                break;
            case CONTACT_US:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_US),new ContactUsFragment(),CONTACT_US.toString());
                break;
        }
    }

    @Override
    protected TransitionType determineTransition(int fragmentID) {
        return SLIDE_BOTTOM;
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
            case R.id.nav_order:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(ORDER_LIST),new OrderListFragment(),ORDER_LIST.toString());
                break;
            case R.id.nav_contact_mechanic:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_US),new CallMechanicFragment(),CALL_MECHANIC.toString());
                break;
            case R.id.nav_contact_us:
                prepareForNewFragment(item);
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CALL_MECHANIC),new ContactUsFragment(),CONTACT_US.toString());
                break;
        }

        return super.onNavigationItemSelected(item);
    }

    private void prepareForNewFragment(MenuItem item) {
        item.setChecked(true);
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            if (fragment!=null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
        //hideKeyboard();
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
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(myDrawer.isDrawerOpen(GravityCompat.START)){
            myDrawer.closeDrawer(GravityCompat.START);
        } else if (count == 0) {
            super.onBackPressed();
        } else {
            //uncheck selected item from navigation drawer
            int size = myDrawerNavigationView.getMenu().size();
            for (int i = 0; i < size; i++) {
                myDrawerNavigationView.getMenu().getItem(i).setChecked(false);
            }
            handleSpecializedBackPress();
            //remove all fragments
            for (Fragment fragment:getSupportFragmentManager().getFragments()) {
                if (fragment!=null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            finish();
            SecondaryActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    }

    private void handleSpecializedBackPress() {
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
