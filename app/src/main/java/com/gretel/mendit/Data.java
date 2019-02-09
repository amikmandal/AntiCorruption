package com.gretel.mendit;

/**
 *This class implements making a treemap called myData to store all the source data in a treemap of string and arraylist
 * of NameEntry and a hashmap called myMetData to store some key information from myData to be used later.
 * @author Amik Mandal
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashSet;

public class Data {
    private HashSet<NameEntry> myData = new HashSet<>();
    //private TreeMap<String, ArrayList<String>> myMetaData = new TreeMap<>();

    /**
     * This method creates Data from Files
     */
    public void createDataFromFiles() {
        String[] record1 = {"Mary","3.6","Computers","https://media.beliefnet.com/~/media/photos-with-attribution/faith/misc/marymother.jpg?as=1&w=400"};
        storeInMap(record1);
        String[] record2 = {"Anna","4.1","Furniture","https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTU3OTIwMTk4MzkxNzY5MDM4/anna-faris-attends-the-22nd-annual-screen-actors-guild-awards-at-the-shrine-auditorium-on-january-30--in-los-angeles-california-photo-by-dan-macmedan_wireimage-square.jpg"};
        storeInMap(record2);
        String[] record3 = {"Emma","3.8","Cars","https://m.media-amazon.com/images/M/MV5BMTQ3ODE2NTMxMV5BMl5BanBnXkFtZTgwOTIzOTQzMjE@._V1_UY317_CR21,0,214,317_AL_.jpg"};
        storeInMap(record3);
        String[] record4 = {"Elizabeth","3.9","Cars","https://img1.thelist.com/img/gallery/the-untold-truth-of-elizabeth-taylor/intro-1533659497.jpg"};
        storeInMap(record4);
        String[] record5 = {"Minnie","4.5","Electrical","https://images-na.ssl-images-amazon.com/images/I/61WzQgJdUbL.jpg"};
        storeInMap(record5);
        String[] record6 = {"Margaret","4.2","Computers","https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/Margaret_Qualley_by_Gage_Skidmore.jpg/220px-Margaret_Qualley_by_Gage_Skidmore.jpg"};
        storeInMap(record6);
        String[] record7 = {"Ida","4.9","Electronics","https://m.media-amazon.com/images/M/MV5BMmMxYTdlZDctMmZjMC00MjEyLWIwZWMtY2Y0MTg1YzQ1ZDIxXkEyXkFqcGdeQXVyMjQwMDg0Ng@@._V1_.jpg"};
        storeInMap(record7);
        String[] record8 = {"Alice","3.5","Smartphones","https://www-tc.pbs.org/wgbh/americanexperience/media/filer_public_thumbnails/filer_public/25/c9/25c9ad10-5f28-441d-8857-cd9c2ea137e2/wilson-alice-paul-1919-loc.jpg__400x574_q85_crop_subsampling-2_upscale.jpg"};
        storeInMap(record8);
    }


    /**
     * This method stores data in myData
     * @param entry This takes in the string entry which will be converted manually into a NameEntry but it was never used
     */
    public void storeInMap(String[] entry){
        NameEntry newEntry = new NameEntry(entry[3], entry[0], Double.parseDouble(entry[1]), entry[2]);
        //myData.add(newEntry);

        FirebaseManager firebaseManager = new FirebaseManager();
        firebaseManager.addRepairer(newEntry);
    }

    public void addMechanic(NameEntry n){
        myData.add(n);
    }

    /**
     * This method creates Data from URL
     */
//    public void createDataFromURL() throws Exception {
//        for(int year=START_YEAR; year<=END_YEAR; year++) {
//            URL file = new URL("https://www2.cs.duke.edu/courses/compsci307/spring19/assign/01_data/data/yob"+year+".txt");
//            URLConnection connection = file.openConnection();
//            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            int ftoMchange = 0;
//            int rankTracker = 1;
//            while ((inputLine = input.readLine()) != null) {
//                String[] entry = inputLine.split(",");
//                if (ftoMchange == 0 && entry[1].equals("M")) {
//                    rankTracker = 1;
//                    ftoMchange = 1;
//                }
//                storeInMap(Integer.toString(year),entry,rankTracker,ftoMchange);
//                rankTracker++;
//            }
//            input.close();
//        }
//    }

    /**
     * This initializes myMetaData with vital information such as most common name, most common gender, most common
     * initial for female names and most common initial overall
     */
//    public void createMetaData(){
//        for(String y: myData.keySet()){
//            String topMaleName= "", topFemaleName = "", topName = "", topGender = "", topLetter="";
//            Integer[] femaleInitialsCount = new Integer[256], maleInitialsCount = new Integer[256];
//
//            for(int i = 0; i<256; i++){
//                maleInitialsCount[i]=0;
//                femaleInitialsCount[i]=0;
//            }
//
//            int topMalePop=0, topFemalePop=0, maleCount = 0, femaleCount = 0;
//
//            ArrayList<NameEntry> current = myData.get(y);
//
//            for(NameEntry n: current){
//                if(n.getRank()==1&&n.getGender().equals("M")){
//                    topMaleName = n.getName();
//                    topMalePop = n.getPopularity();
//                }
//                if(n.getRank()==1&&n.getGender().equals("F")){
//                    topFemaleName = n.getName();
//                    topFemalePop = n.getPopularity();
//                }
//                if(n.getGender().equals("F")) {
//                    femaleCount++;
//                    femaleInitialsCount[n.getName().charAt(0)]++;
//                }
//                if(n.getGender().equals("M")) {
//                    maleCount++;
//                    maleInitialsCount[n.getName().charAt(0)]++;
//                }
//            }
//
//            if(topMalePop>topFemalePop)
//                topName = topMaleName;
//            else
//                topName = topFemaleName;
//
//            if(maleCount>femaleCount)
//                topGender = "M";
//            else
//                topGender = "F";
//
//            int maxMaleInitialsCount=0, maxFemaleInitialsCount=0;
//            String maxMaleInitials = "", maxFemaleInitials = "";
//
//            for(int i = 'A'; i<='Z'; i++){
//                if(maleInitialsCount[i]>maxMaleInitialsCount){
//                    maxMaleInitials=Character.toString(i);
//                    maxMaleInitialsCount=maleInitialsCount[i];
//                }
//                if(femaleInitialsCount[i]>maxFemaleInitialsCount){
//                    maxFemaleInitials=Character.toString(i);
//                    maxFemaleInitialsCount=femaleInitialsCount[i];
//                }
//            }
//            if(maxMaleInitialsCount>maxFemaleInitialsCount)
//                topLetter = maxMaleInitials;
//            else
//                topLetter = maxFemaleInitials;
//
//            ArrayList<String> save = new ArrayList<String>();
//            save.add(topName);
//            save.add(topGender);
//            save.add(maxFemaleInitials);
//            save.add(topLetter);
//            myMetaData.put(y,save);
//        }
//    }

    /**
     * This method returns myData created
     * @return the treemap myData for a given Data object
     */
    public HashSet<NameEntry> getData(){
        return myData;
    }

    /**
     * This method returns myMetaData created
     * @return the treemap myMetaData for a given Data object
     */
//    public TreeMap<String, ArrayList<String>> getMetaData(){
//        return myMetaData;
//    }

}
