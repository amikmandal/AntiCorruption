package com.gretel.scrapknot.model.Agent;

public class Repairer extends Agent {

    private String myRegistrationNumber;
    private String myExperience;
    private double myRating;
    private String mySpeciality;

    public Repairer(Agent a, String registrationNumber, String experience, Double rating, String speciality){
        super(a.getDisplayPicture(),a.getFirstName(),a.getLastName(),a.getID(),a.getEmail(),a.getStreetAddress1(),a.getStreetAddress2(),a.getCity(),a.getState(),a.getZIP(),a.getCountry(),a.getNumber(),a.getLoginType());
        myRegistrationNumber = registrationNumber;
        myExperience = experience;
        myRating = rating;
        mySpeciality = speciality;
    }

    public String getRegistrationNumber() { return  myRegistrationNumber; }

    public String getExperience() { return myExperience; }

    public Double getRating(){
        return myRating;
    }

    public String getSpeciality(){
        return mySpeciality;
    }
}
