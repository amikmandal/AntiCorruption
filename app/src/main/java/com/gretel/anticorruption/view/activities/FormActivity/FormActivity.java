package com.gretel.anticorruption.view.activities.FormActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gretel.anticorruption.model.Agent.Agent;
import com.gretel.anticorruption.model.Agent.Authority;
import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.model.FormData.FormData;
import com.gretel.anticorruption.model.FormData.AuthorityForm;
import com.gretel.anticorruption.model.FormData.UserForm;
import com.gretel.anticorruption.view.activities.MainActivity.RepairerPrimaryActivity;
import com.gretel.anticorruption.view.activities.MainActivity.UserPrimaryActivity;
import com.gretel.anticorruption.util.LocalStorage;
import com.gretel.anticorruption.R;

import static com.gretel.anticorruption.view.activities.FormActivity.FormActivity.FormType.USER_FORM;

abstract public class FormActivity extends AppCompatActivity {

    enum FormType {
        USER_FORM, REPAIRER_FORM
    }

    protected FormType myFormType;
    protected EditText myInfo;

    protected void makeNextButton(){
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshPage();
            }
        });
    }

    private void refreshPage() {

        FormData tempFormData;
        if(myFormType==USER_FORM)
            tempFormData = new UserForm(getApplicationContext());
        else
            tempFormData = new AuthorityForm(getApplicationContext());

        Integer index = 0;
        Bundle userData = getIntent().getExtras();
        if(userData.containsKey("index"))
            index = Integer.parseInt(userData.getString("index"));

        doNecessary(tempFormData,index,userData);

        index++;

        userData.putString("index",index.toString());

        //still requirements left
        if(index<tempFormData.getRequirementsSize()){
            Intent intent;
            if(myFormType==USER_FORM){
                intent = new Intent(getApplicationContext(),UserFormActivity.class);
            } else {
                intent = new Intent(getApplicationContext(),RepairerFormActivity.class);
            }
            intent.putExtras(userData);
            startActivity(intent);
        //done with requirements
        } else {
            tempFormData.setAgent(userData);
            tempFormData.makeAgent(userData);
            Agent a = tempFormData.getAgent();

            LocalStorage localStorage = new LocalStorage(getApplicationContext());

            if(myFormType == USER_FORM){
                localStorage.saveUser((User) a);

                Intent intent = new Intent(getApplicationContext(),UserPrimaryActivity.class);
                startActivity(intent);
            } else {
                localStorage.saveRepairer((Authority) a);

                Intent intent = new Intent(getApplicationContext(),RepairerPrimaryActivity.class);
                startActivity(intent);
            }

        }

    }

    protected abstract void doNecessary(FormData tempFormData, Integer index, Bundle userData);


}
