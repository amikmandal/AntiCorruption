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

    /**
     * Method to store an user locally
     * @param u specifies the user to be saved
     * @param loginType specifies the login method used by user
     */
    public void saveUser(User u, String loginType) {
        saveString("name",u.getName());
        saveString("address",u.getAddress());
        saveString("number",u.getNumber());
        saveString("id",u.getFacebookID());
        saveString("email",u.getEmail());
        saveString("displayPicture",u.getDisplayPicture());

        saveString("loginType",loginType);
    }

    /**
     * Method to retrieve an user that is saved locally
     * @return the user stored locally
     */
    public User loadUser(){

        return new User (loadString("displayPicture"),
                        loadString("name"),
                        loadString("id"),
                        loadString("email"),
                        loadString("address"),
                        loadString("number"));
    }

    /**
     * Method to delete saved data of User
     */
    public void removeUser(){

        myStorage.edit().remove("displayPicture").apply();
        myStorage.edit().remove("name").apply();
        myStorage.edit().remove("id").apply();
        myStorage.edit().remove("email").apply();
        myStorage.edit().remove("address").apply();
        myStorage.edit().remove("number").apply();

        myStorage.edit().remove("loginType").apply();

    }
}
