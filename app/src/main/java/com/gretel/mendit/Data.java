package com.gretel.mendit;

/**
 *This class implements making a treemap called myData to store all the source data in a treemap of string and arraylist
 * of NameEntry and a hashmap called myMetData to store some key information from myData to be used later.
 * @author Amik Mandal
 * @version 1.0
 */

import java.util.TreeMap;

public class Data {
    private TreeMap<String, NameEntry> myData = new TreeMap<>();
    //private TreeMap<String, ArrayList<String>> myMetaData = new TreeMap<>();

    /**
     * This method creates Data from Files
     */
    public void createDataFromFiles() {
        String[] record1 = {"Mary","3.6","Computers","https://media.beliefnet.com/~/media/photos-with-attribution/faith/misc/marymother.jpg?as=1&w=400"};
        storeInMap(Integer.toString(1),record1);
    }


    /**
     * This method stores data in myData
     * @param id This takes in the year which will act as key for myData
     * @param entry This takes in the string entry which will be converted manually into a NameEntry but it was never used
     */
    private void storeInMap(String id, String[] entry){
        NameEntry newEntry = new NameEntry(entry[0], Double.parseDouble(entry[1]), entry[2], entry[3]);
        myData.put(id, newEntry);
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
    public TreeMap<String, NameEntry> getData(){
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
