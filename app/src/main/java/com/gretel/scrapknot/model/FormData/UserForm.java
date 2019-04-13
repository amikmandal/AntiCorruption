package com.gretel.scrapknot.model.FormData;

import android.content.Context;
import android.os.Bundle;

import com.gretel.scrapknot.model.Agent.Repairer;
import com.gretel.scrapknot.model.Agent.User;
import com.gretel.scrapknot.util.BackEndManager.FirebaseManager;

/**
 * This class helps in keeping track of all the additional data we need for User which we are unable
 * to get from login methods.
 * @author Amik Mandal
 * @date 2/22/2019
 */
public class UserForm extends FormData {

    private static final String PHONE_NUMBER = "What is your Phone Number?";
    private static final String ADDRESS = "What is your Street Address?";
    private static final String ROOM_NUMBER = "What is your building/room?";
    private static final String CITY = "Which city are you from?";
    private static final String STATE = "Which state are you from?";
    private static final String ZIP = "What is your Zip Code?";
    private static final String COUNTRY = "Which Country are you from?";

    public UserForm(Context context){
       super("user",context);
       String[] requirements = {PHONE_NUMBER,ADDRESS,ROOM_NUMBER,CITY,STATE,ZIP,COUNTRY};
       myRequirements = requirements;
    }

    @Override
    protected FirebaseManager getFirebaseDatabase(Context context) {
        return new FirebaseManager("user",context);
    }

    public void setAgent(Bundle data){
        myAgent = setCommon(data);
    }

    /**
     * This is a getter method to get the static final PHONE_NUMBER
     * @return the string for PHONE_NUMBER
     */
    public static String getPhoneNumber() {
        return PHONE_NUMBER;
    }
}