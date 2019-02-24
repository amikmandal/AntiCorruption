package com.gretel.mendit.backend;

import android.os.Bundle;

import com.gretel.mendit.util.FirebaseManager;

public class UserForm {

    private static final String PHONE_NUMBER = "What is your Phone Number?";
    private static final String ADDRESS = "What is your Street Address?";
    private static final String ROOM_NUMBER = "What is your building/room number?";
    private static final String CITY = "Which city are you from?";
    private static final String STATE = "Which state are you from?";
    private static final String ZIP = "What is your Zip Code?";
    private static final String COUNTRY = "Which Country are you from?";

    private String[] myRequirements;

    private User myUser;

    public UserForm(){
       String[] requirements = {PHONE_NUMBER,ADDRESS,ROOM_NUMBER,CITY,STATE,ZIP,COUNTRY};
       myRequirements = requirements;
    }

    public void setUser(Bundle data){
        String firstName = data.getString("firstName");
        String lastName = data.getString("lastName");
        String name = firstName + lastName;
        String facebookID = data.getString("id");
        String email = data.getString("email");
        String displayPicture = data.getString("profilePicture");

        String number = "";
        String streetAddress = "";
        String apartment = "";
        String city = "";
        String state = "";
        String country = "";
        String zip = "";

        for(int i=0; i<myRequirements.length; i++){
            switch (i){
                case 0:
                    number = data.getString(Integer.toString(i));
                    break;
                case 1:
                    streetAddress = data.getString(Integer.toString(i));
                    break;
                case 2:
                    apartment = data.getString(Integer.toString(i));
                    break;
                case 3:
                    city = data.getString(Integer.toString(i));
                    break;
                case 4:
                    state = data.getString(Integer.toString(i));
                    break;
                case 5:
                    country = data.getString(Integer.toString(i));
                    break;
                case 6:
                    zip = data.getString(Integer.toString(i));
                    break;
            }
        }

        String address = streetAddress + ", " + apartment + ", " + city + ", " + state + ", " + country + ", " + zip;

        myUser = new User(displayPicture,name,facebookID,email,address,number);

    }

    public void makeUser(Bundle data){
        setUser(data);

        FirebaseManager firebaseManager = new FirebaseManager("user");
        myUser = firebaseManager.addUser(myUser);

        firebaseManager = new FirebaseManager("logins");
        if(myUser.getID()!=null) {
            firebaseManager.addUserLogin(myUser);
        }
    }

    public int getRequirementsSize(){
        return myRequirements.length;
    }

    public String getRequirement(int index){
        return myRequirements[index];
    }

    public static String getPhoneNumber() {
        return PHONE_NUMBER;
    }
}