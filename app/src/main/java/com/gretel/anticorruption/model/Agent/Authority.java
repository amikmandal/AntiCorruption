package com.gretel.anticorruption.model.Agent;

public class Authority extends Agent {

    private String myAuthorityName;
    private String myPosition;
    private double myRating;
    private String myField;

    public Authority(Agent a, String authorityName, String position, Double rating, String field){
        super(a.getDisplayPicture(),a.getFirstName(),a.getLastName(),a.getID(),a.getEmail(),a.getNumber(),a.getLoginType());
        myAuthorityName = authorityName;
        myPosition = position;
        myRating = rating;
        myField = field;
    }

    public String getAuthorityName() { return  myAuthorityName; }

    public String getPosition() { return myPosition; }

    public Double getRating(){
        return myRating;
    }

    public String getField(){
        return myField;
    }
}
