package com.gretel.scrapknot.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gretel.scrapknot.backend.User;
import com.gretel.scrapknot.backend.UserForm;
import com.gretel.scrapknot.util.LocalStorage;
import com.gretel.scrapknot.R;
import com.hbb20.CountryCodePicker;

public class FormActivity extends AppCompatActivity {

    private EditText myInfo;
    private CountryCodePicker myCcp;
    private Button myNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        myInfo = findViewById(R.id.form_input);
        myCcp = findViewById(R.id.ccp);
        myNextButton = findViewById(R.id.next_button);



        UserForm userForm = new UserForm(getApplicationContext());

        //get Country Code
//        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        String countryCodeValue = tm.getNetworkCountryIso();

        Bundle userBundle = getIntent().getExtras();

        Integer i=0;
        if(userBundle.containsKey("index"))
            i = Integer.parseInt(userBundle.getString("index"));

        myCcp.setVisibility(View.GONE);
        myInfo.setHint(userForm.getRequirement(i));

        if(myInfo.getHint().equals(userForm.getPhoneNumber())) {
            myCcp.setVisibility(View.VISIBLE);
            myCcp.setDefaultCountryUsingNameCode(myCcp.getDefaultCountryNameCode());
            myCcp.setNumberAutoFormattingEnabled(true);
            myInfo.setInputType(InputType.TYPE_CLASS_PHONE);
            myInfo.setSingleLine();
        }

        myNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshPage();
            }
        });
    }

    private void refreshPage() {

        UserForm tempUserForm = new UserForm(getApplicationContext());

        Integer index = 0;
        Bundle userData = getIntent().getExtras();
        if(userData.containsKey("index"))
            index = Integer.parseInt(userData.getString("index"));
        if(tempUserForm.getPhoneNumber()==tempUserForm.getRequirement(index)){
            userData.putString(Integer.toString(-1),myCcp.getSelectedCountryCode());
        }
        userData.putString(index.toString(),myInfo.getText().toString());

        index++;

        userData.putString("index",index.toString());

        if(index<tempUserForm.getRequirementsSize()){
            Intent intent = new Intent(getApplicationContext(),FormActivity.class);
            intent.putExtras(userData);
            startActivity(intent);
        } else {

            tempUserForm.makeUser(userData);
            User u = tempUserForm.getUser();

            LocalStorage localStorage = new LocalStorage(getApplicationContext());
            localStorage.saveUser(u);

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

    }


}
