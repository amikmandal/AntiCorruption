package com.gretel.scrapknot.view.activities.FormActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.gretel.scrapknot.R;
import com.gretel.scrapknot.model.Agent.Repairer;
import com.gretel.scrapknot.model.Agent.User;
import com.gretel.scrapknot.model.FormData.FormData;
import com.gretel.scrapknot.model.FormData.RepairerForm;
import com.gretel.scrapknot.model.FormData.UserForm;
import com.gretel.scrapknot.util.LocalStorage;
import com.hbb20.CountryCodePicker;

import static com.gretel.scrapknot.view.activities.FormActivity.FormActivity.FormType.REPAIRER_FORM;

public class RepairerFormActivity extends FormActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        CountryCodePicker ccp = findViewById(R.id.ccp);
        myInfo = findViewById(R.id.form_input);
        myFormType = REPAIRER_FORM;
        Bundle userBundle = getIntent().getExtras();
        if(userBundle == null)
            userBundle = new Bundle();

        Integer i=0;
        if(userBundle.containsKey("index"))
            i = Integer.parseInt(userBundle.getString("index"));

        RepairerForm repairerForm = new RepairerForm(getApplicationContext());

        myInfo.setHint(repairerForm.getRequirement(i));
        ccp.setVisibility(View.GONE);
        storeUserInBundle(userBundle);
        makeNextButton();
    }

    private void storeUserInBundle(Bundle userBundle) {

        int size = new UserForm(getApplicationContext()).getRequirementsSize();
        LocalStorage localStorage = new LocalStorage(getApplicationContext());
        User u = localStorage.loadUser();

        userBundle.putString("firstName",u.getFirstName());
        userBundle.putString("lastName",u.getLastName());
        userBundle.putString("id",u.getID());
        userBundle.putString("email",u.getEmail());
        userBundle.putString("profilePicture",u.getDisplayPicture());
        userBundle.putString("loginType",u.getLoginType());

        for(int i=0; i<size; i++){
            switch (i){
                case 0:
                    userBundle.putString(Integer.toString(i),u.getNumber());
                    break;
                case 1:
                    userBundle.putString(Integer.toString(i),u.getStreetAddress1());
                    break;
                case 2:
                    userBundle.putString(Integer.toString(i),u.getStreetAddress2());
                    break;
                case 3:
                    userBundle.putString(Integer.toString(i),u.getCity());
                    break;
                case 4:
                    userBundle.putString(Integer.toString(i),u.getState());
                    break;
                case 5:
                    userBundle.putString(Integer.toString(i),u.getCountry());
                    break;
                case 6:
                    userBundle.putString(Integer.toString(i),u.getZIP());
                    break;
            }
        }

        getIntent().putExtras(userBundle);
    }

    @Override
    protected void doNecessary(FormData tempFormData, Integer index, Bundle userData) {
        ;
    }
}
