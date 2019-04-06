package com.gretel.scrapknot.backend.User;

/**
 * This class implements storing a single line of the file (for example: Emma,F,19728) for easy access as a single object.
 */
public class Mechanic {
    private String myName;
    private double myRating;
    private String mySpeciality;
    private String myDisplayPicture;

    /**
     * This constructs the class with name,gender,popularity and rank
     * @param name The name for the current entry
     * @param rating The gender for the current entry
     * @param speciality The popularity of the current entry
     * @param displayPicture The rank of the current entry
     */
    public Mechanic(String displayPicture, String name, double rating, String speciality){
        myName = name;
        myRating = rating;
        mySpeciality = speciality;
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
    public double getRating(){
        return myRating;
    }

    /**
     * This returns the popularity
     * @return the popularity of given entry
     */
    public String getSpeciality(){
        return mySpeciality;
    }

    /**
     * This returns the rank
     * @return the rank of given entry
     */
    public String getDisplayPicture(){
        return myDisplayPicture;
    }

}

