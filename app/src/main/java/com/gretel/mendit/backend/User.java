package com.gretel.mendit.backend;

/**
 * This class represents the user.
 * @author Amik Mandal
 * @date 2/2/2019
 */
public class User {

    private String myName;
    private String myFacebookID;
    private String myEmail;
    private String myNumber;
    private String myAddress;
    private String myDisplayPicture;

    /**
     * Constructor to fill details for User
     * @param displayPicture URL for displayPicture
     * @param name name of User
     * @param facebookID facebook id of User
     * @param email email of User
     * @param address address of User
     * @param number phone number of User
     */
    public User(String displayPicture, String name, String facebookID, String email, String address, String number){
        myName = name;
        myNumber = number;
        myAddress = address;
        myFacebookID = facebookID;
        myEmail = email;
        myDisplayPicture = displayPicture;
    }

    /**
     * This returns the name of User
     * @return the name
     */
    public String getName(){
        return myName;
    }

    /**
     * This returns the number of User
     * @return the number
     */
    public String getNumber(){
        return myNumber;
    }

    /**
     * This returns the address of User
     * @return the address
     */
    public String getAddress(){
        return myAddress;
    }

    /**
     * This returns the email of User
     * @return the email
     */
    public String getEmail(){
        return myEmail;
    }

    /**
     * This returns the displayPicture of User
     * @return the displayPicture
     */
    public String getDisplayPicture(){
        return myDisplayPicture;
    }

    /**
     * This returns the facebookID of User
     * @return
     */
    public String getFacebookID() {return myFacebookID;}

}
