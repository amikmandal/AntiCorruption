package com.gretel.scrapknot.backend;

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
    private String myLoginType;

    /**
     * Constructor to fill details for User
     * @param displayPicture URL for displayPicture
     * @param name name of User
     * @param facebookID facebook id of User
     * @param email email of User
     * @param address address of User
     * @param number phone number of User
     */
    public User(String displayPicture, String name, String facebookID, String email, String address, String number,String loginType){
        myName = name;
        myNumber = number;
        myAddress = address;
        myFacebookID = facebookID;
        myEmail = email;
        myDisplayPicture = displayPicture;
        myLoginType = loginType;
    }

    public boolean equals (User other){
        if(!this.myName.equals(other.myName))
            return false;
        if(!this.myNumber.equals(other.myNumber))
            return false;
        if(!this.myAddress.equals(other.myAddress))
            return false;
        if(!this.myFacebookID.equals(other.myFacebookID))
            return false;
        if(!this.myEmail.equals(other.myEmail))
            return false;
        if(!this.myDisplayPicture.equals(other.myDisplayPicture))
            return false;
        if(!this.myLoginType.equals(other.myLoginType))
            return false;
        return true;
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

    /**
     * This returns the loginType of User
     * @return
     */
    public String getLoginType() {return myLoginType;}

}
