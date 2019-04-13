package com.gretel.scrapknot.view.activities.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.gretel.scrapknot.R;

abstract public class PrimaryActivity extends MainActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        createFragmentTypeMap();

        super.onCreate(savedInstanceState);

        setLayout();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        addUIElements();

    }

    protected abstract void setLayout();

    protected abstract void createFragmentTypeMap();


    @Override
    protected void addToolBarButton() {
        myToolbarButton.setBackgroundResource(R.drawable.ic_notifications);
    }

    protected TransitionType determineTransition(int newFragmentID) {

        if(newFragmentID>myCurrentFragmentID){
            return TransitionType.SLIDE_RIGHT;
        } else {
            return TransitionType.SLIDE_LEFT;
        }
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

    /**
     * This method is called before opening another Drawer Fragment while one Fragment is already open.
     */
    protected void openDrawerFragment(MenuItem item, FragmentType type){
        item.setChecked(true);
        Intent intent = new Intent(getApplicationContext(),UserSecondaryActivity.class);
        intent.putExtra("openFragment",type);
        startActivity(intent);
    }

    /**
     * This method is called before opening any Navigation Fragment while one Fragment is open
     * @param prevFragment
     * @param prevID
     */
    protected void openNavFragment(Fragment prevFragment, int prevID) {
        myPrevFragment = prevFragment;
        myPrevFragmentID = prevID;
    }



}
