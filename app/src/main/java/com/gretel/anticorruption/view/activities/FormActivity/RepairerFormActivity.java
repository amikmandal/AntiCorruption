package com.gretel.anticorruption.view.activities.FormActivity;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.model.FormData.FormData;
import com.gretel.anticorruption.model.FormData.AuthorityForm;
import com.gretel.anticorruption.util.LocalStorage;
import com.hbb20.CountryCodePicker;

import static com.gretel.anticorruption.view.activities.FormActivity.FormActivity.FormType.REPAIRER_FORM;

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

        AuthorityForm authorityForm = new AuthorityForm(getApplicationContext());

        myInfo.setHint(authorityForm.getRequirement(i));
        ccp.setVisibility(View.GONE);
        storeUserInBundle(userBundle);
        makeNextButton();
    }

    private void storeUserInBundle(Bundle userBundle) {

        //int size = new UserForm(getApplicationContext()).getRequirementsSize();
        LocalStorage localStorage = new LocalStorage(getApplicationContext());
        User u = localStorage.loadUser();

        Gson gson = new Gson();
        String json = gson.toJson(u);
        userBundle.putString("userData", json);

        getIntent().putExtras(userBundle);
    }

    @Override
    protected void doNecessary(FormData tempFormData, Integer index, Bundle userData) {
        userData.putString(index.toString(), myInfo.getText().toString());
    }
}
