package com.gretel.scrapknot.backend;

import android.content.Context;
import android.os.Bundle;

import com.gretel.scrapknot.backend.User.User;
import com.gretel.scrapknot.util.FirebaseManager;

/**
 * This class helps in keeping track of all the additional data we need for User which we are unable
 * to get from login methods.
 * @author Amik Mandal
 * @date 2/22/2019
 */
public class UserForm {

    private static final String PHONE_NUMBER = "What is your Phone Number?";
    private static final String ADDRESS = "What is your Street Address?";
    private static final String ROOM_NUMBER = "What is your building/room?";
    private static final String CITY = "Which city are you from?";
    private static final String STATE = "Which state are you from?";
    private static final String ZIP = "What is your Zip Code?";
    private static final String COUNTRY = "Which Country are you from?";

    private String[] myRequirements;

    private User myUser;

    private Context myContext;

    /**
     * Constructor to initialize instance variables
     * @param context specifies current context
     */
    public UserForm(Context context){
       String[] requirements = {PHONE_NUMBER,ADDRESS,ROOM_NUMBER,CITY,STATE,ZIP,COUNTRY};
       myRequirements = requirements;
       myContext = context;
    }

    /**
     * This method calls setUser to initialize myUser and stores the user on Firebase.
     * @param data specifies the bundle data to be used to create myUser
     */
    public void makeUser(Bundle data){

        setUser(data);

        FirebaseManager firebaseManager = new FirebaseManager("user", myContext);
        firebaseManager.addUser(myUser,"facebook");

    }

    /**
     * This method initializes myUser
     * @param data specifies the Bundle data used to create myUser
     */
    public void setUser(Bundle data){
        String firstName = data.getString("firstName");
        String lastName = data.getString("lastName");
        String name = firstName + " " + lastName;
        String facebookID = data.getString("id");
        String email = data.getString("email");
        String displayPicture = data.getString("profilePicture");
        String loginType = data.getString("loginType");

        String number = "";
        String streetAddress1 = "";
        String streetAddress2 = "";
        String city = "";
        String state = "";
        String country = "";
        String zip = "";

        //data made from FormActivity
        for(int i=0; i<myRequirements.length; i++){
            switch (i){
                case 0:
                    number = data.getString(Integer.toString(i));
                    break;
                case 1:
                    streetAddress1 = data.getString(Integer.toString(i));
                    break;
                case 2:
                    streetAddress2 = data.getString(Integer.toString(i));
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

        myUser = new User(displayPicture,firstName,lastName,facebookID,email,streetAddress1,streetAddress2,city,state,zip,country,number,loginType);

    }

    /**
     * This is a getter method for myUser
     * @return the user formed at the end of form
     */
    public User getUser() { return myUser; }

    /**
     * This is a getter method to get one additional requirements.
     * @param index specifies the index whose requirement we want
     * @return the requirement at index position
     */
    public String getRequirement(int index){
        return myRequirements[index];
    }

    /**
     * This is a method to get the size of the list of the additional requirement.
     * @return the size of the list of the additional requirement.
     */
    public int getRequirementsSize(){
        return myRequirements.length;
    }

    /**
     * This is a getter method to get the static final PHONE_NUMBER
     * @return the string for PHONE_NUMBER
     */
    public static String getPhoneNumber() {
        return PHONE_NUMBER;
    }
}