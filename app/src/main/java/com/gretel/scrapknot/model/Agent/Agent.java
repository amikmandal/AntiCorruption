package com.gretel.scrapknot.model.Agent;

/**
 * This class represents the user.
 * @author Amik Mandal
 * @date 2/2/2019
 */
abstract public class Agent {

    private String myFirstName;
    private String myLastName;
    private String myID;
    private String myEmail;
    private String myNumber;
    private String myStreetAddress1;
    private String myStreetAddress2;
    private String myCity;
    private String myState;
    private String myCountry;
    private String myZIP;
    private String myDisplayPicture;
    private String myLoginType;

    /**
     * Constructor to fill details for User
     */
    public Agent(String displayPicture, String firstName, String lastName, String facebookID, String email, String streetAddress1, String streetAddress2, String city, String state, String zip, String country, String number,String loginType){

        myFirstName = firstName;
        myLastName = lastName;
        myNumber = number;
        myID = facebookID;
        myEmail = email;
        myDisplayPicture = displayPicture;
        myLoginType = loginType;

        myStreetAddress1 = streetAddress1;
        myStreetAddress2 = streetAddress2;
        myCity = city;
        myState = state;
        myCountry = country;
        myZIP = zip;

    }

    public boolean equals (Agent other){
        if(!this.myFirstName.equals(other.getFirstName()))
            return false;
        if(!this.myLastName.equals(other.getLastName()))
            return false;
        if(!this.myNumber.equals(other.myNumber))
            return false;
        if(!this.myStreetAddress1.equals(other.getStreetAddress1()))
            return false;
        if(!this.myStreetAddress2.equals(other.getStreetAddress2()))
            return false;
        if(!this.myCity.equals(other.getCity()))
            return false;
        if(!this.myState.equals(other.getState()))
            return false;
        if(!this.myZIP.equals(other.getZIP()))
            return false;
        if(!this.myCountry.equals(other.myCountry))
            return false;
        if(!this.myID.equals(other.myID))
            return false;
        if(!this.myEmail.equals(other.myEmail))
            return false;
        if(!this.myDisplayPicture.equals(other.myDisplayPicture))
            return false;
        if(!this.myLoginType.equals(other.myLoginType))
            return false;
        return true;
    }

    public static String makeFirstName(String name){
        return name.split(" ")[0];
    }

    public static String makeLastName(String name){
        return name.replaceAll(makeFirstName(name)+" ","");
    }

    public String getName(){
        return myFirstName + " " + myLastName;
    }

    public String getNumber(){
        return myNumber;
    }

    public String getAddress(){
        return myStreetAddress1 + ", "+ myStreetAddress2 + ", " + myCity + ", " + myState + ", " + myZIP + ", " + myCountry;
    }

    public String getEmail(){
        return myEmail;
    }

    public String getDisplayPicture(){
        return myDisplayPicture;
    }

    public String getID() {return myID;}

    public String getLoginType() {return myLoginType;}

    public String getFirstName() {
        return myFirstName;
    }

    public String getLastName() {
        return myLastName;
    }

    public String getStreetAddress1() {return myStreetAddress1;}

    public String getStreetAddress2() {return myStreetAddress2;}

    public String getCity() { return myCity; }

    public String getCountry() { return myCountry; }

    public String getState() { return myState; }

    public String getZIP() { return myZIP; }


}
