package com.gretel.mendit.util;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class JSONParser {

    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ID = "id";

    public URL readURL(JSONObject userData){
        URL readURL = null;
        try{
            readURL = new URL("https://graph.facebook.com/"+userData.getString(ID)+"/picture?width=250&height=250");
        }catch (JSONException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return readURL;
    }

    public String readString(JSONObject userData,String field){
        String readString = "";
        try{
            readString = userData.getString(field);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return readString;
    }

    public Long readLong(JSONObject userData){
        Long readLong = (long) 0;
        try{
            readLong = userData.getLong(ID);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return readLong;
    }

    public Bundle makePreliminaryUserData(JSONObject userData) {

        Bundle userBundle = new Bundle();

        userBundle.putString("firstName", readString(userData,FIRST_NAME));
        userBundle.putString("lastName", readString(userData,LAST_NAME));
        userBundle.putString("id", readLong(userData).toString());
        userBundle.putString("profilePicture", readURL(userData).toString());
        userBundle.putString("email", readString(userData,EMAIL));

        return userBundle;

    }
}
