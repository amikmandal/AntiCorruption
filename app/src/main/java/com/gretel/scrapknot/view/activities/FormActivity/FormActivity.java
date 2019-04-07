package com.gretel.scrapknot.view.activities.FormActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gretel.scrapknot.model.Agent.User;
import com.gretel.scrapknot.model.FormData.FormData;
import com.gretel.scrapknot.model.FormData.RepairerForm;
import com.gretel.scrapknot.model.FormData.UserForm;
import com.gretel.scrapknot.view.activities.MainActivity.PrimaryActivity;
import com.gretel.scrapknot.util.LocalStorage;
import com.gretel.scrapknot.R;

import static com.gretel.scrapknot.view.activities.FormActivity.FormActivity.FormType.REPAIRER_FORM;
import static com.gretel.scrapknot.view.activities.FormActivity.FormActivity.FormType.USER_FORM;

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
            tempFormData = new RepairerForm(getApplicationContext());

        Integer index = 0;
        Bundle userData = getIntent().getExtras();
        if(userData.containsKey("index"))
            index = Integer.parseInt(userData.getString("index"));

        doNecessary(tempFormData,index,userData);

        index++;

        userData.putString("index",index.toString());

        if(index<tempFormData.getRequirementsSize()){
            Intent intent;
            if(myFormType==USER_FORM){
                intent = new Intent(getApplicationContext(),UserFormActivity.class);
            } else {
                intent = new Intent(getApplicationContext(),RepairerFormActivity.class);
            }
            intent.putExtras(userData);
            startActivity(intent);
        } else {
            if(myFormType == USER_FORM){
                tempFormData.makeAgent(userData);
                User u = (User) tempFormData.getAgent();

                LocalStorage localStorage = new LocalStorage(getApplicationContext());
                localStorage.saveUser(u);

                Intent intent = new Intent(getApplicationContext(),PrimaryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(),PrimaryActivity.class);
                startActivity(intent);
            }

        }

    }

    protected abstract void doNecessary(FormData tempFormData, Integer index, Bundle userData);


}
