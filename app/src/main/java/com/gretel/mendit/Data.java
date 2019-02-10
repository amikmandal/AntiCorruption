package com.gretel.mendit;

/**
 *This class implements making a treemap called myData to store all the source data in a treemap of string and arraylist
 * of NameEntry and a hashmap called myMetData to store some key information from myData to be used later.
 * @author Amik Mandal
 * @version 1.0
 */

import java.util.HashSet;

public class Data {
    private HashSet<NameEntry> myRepairerList = new HashSet<>();
    private HashSet<User> myUserList = new HashSet<>();
    //private TreeMap<String, ArrayList<String>> myMetaData = new TreeMap<>();

    public void addMechanic(NameEntry n){
        myRepairerList.add(n);
    }

    public void addUser(User u){
        myUserList.add(u);
    }
    /**
     * This method creates Data from Files
     */
    public void createDataFromFiles() {
        String[] record1 = {"Mary","3.6","Computers","https://media.beliefnet.com/~/media/photos-with-attribution/faith/misc/marymother.jpg?as=1&w=400"};
        uploadMechanic(record1);
        String[] record2 = {"Anna","4.1","Furniture","https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTU3OTIwMTk4MzkxNzY5MDM4/anna-faris-attends-the-22nd-annual-screen-actors-guild-awards-at-the-shrine-auditorium-on-january-30--in-los-angeles-california-photo-by-dan-macmedan_wireimage-square.jpg"};
        uploadMechanic(record2);
        String[] record3 = {"Emma","3.8","Cars","https://m.media-amazon.com/images/M/MV5BMTQ3ODE2NTMxMV5BMl5BanBnXkFtZTgwOTIzOTQzMjE@._V1_UY317_CR21,0,214,317_AL_.jpg"};
        uploadMechanic(record3);
        String[] record4 = {"Elizabeth","3.9","Cars","https://img1.thelist.com/img/gallery/the-untold-truth-of-elizabeth-taylor/intro-1533659497.jpg"};
        uploadMechanic(record4);
        String[] record5 = {"Minnie","4.5","Electrical","https://images-na.ssl-images-amazon.com/images/I/61WzQgJdUbL.jpg"};
        uploadMechanic(record5);
        String[] record6 = {"Margaret","4.2","Computers","https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/Margaret_Qualley_by_Gage_Skidmore.jpg/220px-Margaret_Qualley_by_Gage_Skidmore.jpg"};
        uploadMechanic(record6);
        String[] record7 = {"Ida","4.9","Electronics","https://m.media-amazon.com/images/M/MV5BMmMxYTdlZDctMmZjMC00MjEyLWIwZWMtY2Y0MTg1YzQ1ZDIxXkEyXkFqcGdeQXVyMjQwMDg0Ng@@._V1_.jpg"};
        uploadMechanic(record7);
        String[] record8 = {"Alice","3.5","Smartphones","https://www-tc.pbs.org/wgbh/americanexperience/media/filer_public_thumbnails/filer_public/25/c9/25c9ad10-5f28-441d-8857-cd9c2ea137e2/wilson-alice-paul-1919-loc.jpg__400x574_q85_crop_subsampling-2_upscale.jpg"};
        uploadMechanic(record8);
    }

    public void createUserFromFiles() {
        String[] record1 = {"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Liam_Hemsworth_June_2016.jpg/220px-Liam_Hemsworth_June_2016.jpg","Liam","liam.hemsworth@gmail.com","9th Street, 64th Block, Apt. 123, Los Angeles, CA, USA, 27708", "(919) 123 5674"};
        uploadUser(record1);
        String[] record2 = {"https://mondrian.mashable.com/uploads%252Fcard%252Fimage%252F778467%252F8e958b3f-4c0e-43bd-87dc-7b6d8fb616ae.jpg%252F950x534__filters%253Aquality%252890%2529.jpg?signature=3Z6t55sErP1hfO1pKwdBYcIBUxs=&source=https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com","Noah","noah.trevor@gmail.com","19th Street, 60th Block, Apt. 13, New York, NY, USA, 97708", "(919) 103 5074"};
        uploadUser(record2);
        String[] record3 = {"https://specials-images.forbesimg.com/imageserve/5ba415b6a7ea434e4c694719/416x416.jpg?background=000000&cropX1=1503&cropX2=4716&cropY1=21&cropY2=3236","Mark","mark.zuck@gmail.com","20th Street, 4th Block, Apt. 3, San Francisco, CA, USA, 27008", "(919) 000 5674"};
        uploadUser(record3);
    }


    /**
     * This method stores data in myData
     * @param entry This takes in the string entry which will be converted manually into a NameEntry but it was never used
     */
    public void uploadMechanic(String[] entry){
        NameEntry newEntry = new NameEntry(entry[3], entry[0], Double.parseDouble(entry[1]), entry[2]);
        //myData.add(newEntry);

        FirebaseManager firebaseManager = new FirebaseManager("mechanic");
        firebaseManager.addRepairer(newEntry);
    }

    public void uploadUser(String[] entry){
        User user  = new User(entry[0], entry[1], entry[2], entry[3], entry[4]);
        //myData.add(newEntry);

        FirebaseManager firebaseManager = new FirebaseManager("user");
        firebaseManager.addUser(user);
    }

    /**
     * This method returns myData created
     * @return the treemap myData for a given Data object
     */
    public HashSet<NameEntry> getRepairerList(){
        return myRepairerList;
    }

    /**
     * This method returns myData created
     * @return the treemap myData for a given Data object
     */
    public HashSet<User> getUserList(){
        return myUserList;
    }

    /**
     * This method returns myMetaData created
     * @return the treemap myMetaData for a given Data object
     */

}
