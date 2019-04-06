package com.gretel.scrapknot.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.gretel.scrapknot.backend.User.User;

/**
 * This class is used to store files locally in Android. Uses SharedPreference.
 * @author Amik Mandal
 */
public class LocalStorage {

    private SharedPreferences myStorage;
    private SharedPreferences.Editor myEditor;
    private Context myContext;

    /**
     * Constructor to initialize instance variables
     * @param context specifies the context of the activity called from
     */
    public LocalStorage(Context context){
        myContext = context;
        myStorage = PreferenceManager.getDefaultSharedPreferences(context);
        myEditor = myStorage.edit();
        myEditor.apply();
    }

    /**
     * Method to save a string locally
     * @param key specifies the key used to save
     * @param value specifies the value to be stored
     */
    public void saveString(String key, String value){
        myEditor.putString(key,value).apply();
    }

    /**
     * Method to retrieve a string locally
     * @param key key that was used to save a data
     * @return the value retrieved
     */
    public String loadString(String key){
        return myStorage.getString(key, "");
    }

    /**
     * Method to store an user locally
     * @param u specifies the user to be saved
     */
    public void saveUser(User u) {

        Gson gson = new Gson();
        String json = gson.toJson(u);
        saveString("myUser", json);

    }

    /**
     * Method to retrieve an user that is saved locally
     * @return the user stored locally
     */
    public User loadUser(){

        Gson gson = new Gson();
        String json = loadUserJSON();
        return gson.fromJson(json, User.class);

    }

    public String loadUserJSON() {
        return loadString("myUser");
    }

    /**
     * Method to delete saved data of User
     */
    public void removeUser(){

        myEditor.remove("myUser").apply();

    }

    public void editUser(User user){
        removeUser();
        saveUser(user);
    }

    public boolean checkIfUserPresent(){
        String checkJSON = loadUserJSON();
        if (checkJSON.equals("")){
            return false;
        }
        return true;
    }
}
