package com.gretel.scrapknot.backend;

public class Repairer extends User {

    private double myRating;
    private String mySpeciality;

    public Repairer(String displayPicture, String firstName, String lastName, String ID, String email, String streetAddress1, String streetAddress2, String city, String state, String zip, String country, String number,String loginType, Double rating, String speciality){

        super(displayPicture, firstName, lastName, ID, email, streetAddress1, streetAddress2, city, state, zip, country, number, loginType);

        myRating = rating;
        mySpeciality = speciality;

    }

    public Double getRating(){
        return myRating;
    }

    public String getSpeciality(){
        return mySpeciality;
    }
}
