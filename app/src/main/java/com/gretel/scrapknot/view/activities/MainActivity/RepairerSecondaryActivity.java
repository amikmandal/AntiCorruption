package com.gretel.scrapknot.view.activities.MainActivity;

import android.view.View;

import com.google.common.collect.HashBiMap;
import com.gretel.scrapknot.R;
import com.gretel.scrapknot.view.fragments.ContactCustomerFragment;
import com.gretel.scrapknot.view.fragments.ContactUsFragment;
import com.gretel.scrapknot.view.fragments.RepairerFragment;

import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_CUSTOMER;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.CONTACT_US;
import static com.gretel.scrapknot.view.activities.MainActivity.MainActivity.FragmentType.REPAIRER;


public class RepairerSecondaryActivity extends SecondaryActivity{

    @Override
    protected void createFragmentTypeMap() {
        myFragmentIDs = HashBiMap.create();
        myFragmentIDs.put(REPAIRER,0);
        myFragmentIDs.put(CONTACT_CUSTOMER,1);
        myFragmentIDs.put(CONTACT_US,2);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_secondary_repairer);
    }

    @Override
    protected void initializeFragment() {
        switch((FragmentType) getIntent().getSerializableExtra("openFragment")){
            case REPAIRER:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(REPAIRER),new RepairerFragment(),REPAIRER.toString());
                break;
            case CONTACT_CUSTOMER:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_CUSTOMER),new ContactCustomerFragment(),CONTACT_CUSTOMER.toString());
                break;
            case CONTACT_US:
                createFragment(R.id.secondary_fragment_container,myFragmentIDs.get(CONTACT_US),new ContactUsFragment(),CONTACT_US.toString());
                break;
        }

    }

    @Override
    protected void addToolBarButton() {
        switch (myFragmentIDs.inverse().get(myCurrentFragmentID)){
            case REPAIRER:
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
    protected void handleSpecializedBackPress() {

    }

}
