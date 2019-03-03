package com.gretel.mendit.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gretel.mendit.backend.User;

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
     * @param context the context of the activity called from
     */
    public LocalStorage(Context context){
        myContext = context;
        myStorage = PreferenceManager.getDefaultSharedPreferences(context);
        myEditor = myStorage.edit();
        myEditor.apply();
    }

    /**
     * Method to save a string locally
     * @param key key used to save
     * @param value value to be stored
     */
    public void saveString(String key, String value){
        myEditor.putString(key,value);
        myEditor.apply();
    }

    /**
     * Method to retrieve a string locally
     * @param key key that was used to save a data
     * @return the value retrieved
     */
    public String loadString(String key){
        return myStorage.getString(key, "");
    }

    public void saveUser(User u) {
        saveString("name",u.getName());
        saveString("address",u.getAddress());
        saveString("number",u.getNumber());
        saveString("id",u.getFacebookID());
        saveString("email",u.getEmail());
        saveString("displayPicture",u.getDisplayPicture());
    }

    public User loadUser(){

        System.out.println("We trying---------->"+loadString("galapagos"));

        return new User (loadString("displayPicture"),
                        loadString("name"),
                        loadString("id"),
                        loadString("email"),
                        loadString("address"),
                        loadString("number"));
    }
}
