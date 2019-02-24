package com.gretel.mendit.backend;

/**
 * This class implements storing a single line of the file (for example: Emma,F,19728) for easy access as a single object.
 */
public class User {

    private String myName;
    private String myFacebookID;
    private String myID;
    private String myEmail;
    private String myNumber;
    private String myAddress;
    private String myDisplayPicture;

    /**
     * This constructs the class with name,gender,popularity and rank
     * @param user The name for the current entry
     */
    public User(User user, String id){
        myName = user.getName();
        myNumber = user.getNumber();
        myAddress = user.getAddress();
        myEmail = user.getEmail();
        myFacebookID = user.getFacebookID();
        myDisplayPicture = user.getDisplayPicture();
        myID = id;
    }

    public User(String displayPicture, String name, String facebookID, String email, String address, String number){
        myName = name;
        myNumber = number;
        myAddress = address;
        myFacebookID = facebookID;
        myEmail = email;
        myDisplayPicture = displayPicture;
    }

    /**
     * This returns the name
     * @return the name of given entry
     */
    public String getName(){
        return myName;
    }

    /**
     * This returns the gender
     * @return the gender of given entry
     */
    public String getNumber(){
        return myNumber;
    }

    /**
     * This returns the gender
     * @return the gender of given entry
     */
    public String getAddress(){
        return myAddress;
    }

    /**
     * This returns the popularity
     * @return the popularity of given entry
     */
    public String getEmail(){
        return myEmail;
    }

    /**
     * This returns the rank
     * @return the rank of given entry
     */
    public String getDisplayPicture(){
        return myDisplayPicture;
    }

    public String getID() {
        return myID;
    }

    public String getFacebookID() {return myFacebookID;}

}
