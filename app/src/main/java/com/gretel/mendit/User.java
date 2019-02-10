package com.gretel.mendit;

/**
 * This class implements storing a single line of the file (for example: Emma,F,19728) for easy access as a single object.
 */
public class User {
    private String myName;
    private String myDisplayPicture;
    private String myEmail;
    private String myNumber;
    private String myAddress;

    /**
     * This constructs the class with name,gender,popularity and rank
     * @param name The name for the current entry
     * @param number The gender for the current entry
     * @param email The popularity of the current entry
     * @param address The address of the user
     * @param displayPicture The rank of the current entry
     */
    User(String displayPicture, String name, String email, String address, String number){
        myName = name;
        myNumber = number;
        myAddress = address;
        myEmail = email;
        myDisplayPicture = displayPicture;
    }

    User(){
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

}
