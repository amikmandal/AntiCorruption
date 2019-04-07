package com.gretel.scrapknot.model.FormData;

import android.content.Context;
import android.os.Bundle;

import com.gretel.scrapknot.model.Agent.Repairer;
import com.gretel.scrapknot.model.Agent.User;

public class RepairerForm extends FormData {

    private static final String REGISTRATION_NUMBER = "What is the business registration number?";
    private static final String EXPERIENCE = "What is your experience?";
    private static final String SPECIALITY = "What is your speciality?";

    public RepairerForm(Context context){
        super("user",context);
        String[] requirements = {REGISTRATION_NUMBER,EXPERIENCE,SPECIALITY};
        myRequirements = requirements;
    }

    @Override
    public void setAgent(Bundle data) {
        User u = setCommon(data);
        myAgent = new Repairer(u,data.getString("registrationNumber"),data.getString("experience"),data.getDouble("rating"),data.getString("speciality"));
    }
}
