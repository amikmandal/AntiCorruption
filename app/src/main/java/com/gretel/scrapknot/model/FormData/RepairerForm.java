package com.gretel.scrapknot.model.FormData;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.gretel.scrapknot.model.Agent.Repairer;
import com.gretel.scrapknot.model.Agent.User;
import com.gretel.scrapknot.util.BackEndManager.FirebaseManager;

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
    protected FirebaseManager getFirebaseDatabase(Context context) {
        return new FirebaseManager("repairer", context);
    }

    @Override
    public void setAgent(Bundle data) {
        String userJson = data.getString("userData");
        Gson gson = new Gson();
        User u = gson.fromJson(userJson,User.class);
        String registrationNumber = "", experience = "", speciality = "";
        Double rating = data.getDouble("rating");
        for(int i=0; i<myRequirements.length; i++){
            switch (i){
                case 0:
                    registrationNumber = data.getString(Integer.toString(i));
                    break;
                case 1:
                    experience = data.getString(Integer.toString(i));
                    break;
                case 2:
                    speciality = data.getString(Integer.toString(i));
                    break;
            }
        }
        myAgent = new Repairer(u,registrationNumber,experience,rating,speciality);
    }
}
