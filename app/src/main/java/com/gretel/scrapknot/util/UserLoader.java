package com.gretel.scrapknot.util;

import android.content.Context;
import android.os.AsyncTask;

import com.gretel.scrapknot.backend.User;
import com.gretel.scrapknot.frontend.activities.MainActivity;
import com.gretel.scrapknot.frontend.activities.PrimaryActivity;

import java.lang.ref.WeakReference;

public class UserLoader extends AsyncTask<Void,Void,String> {

    private WeakReference<MainActivity> myMainActivityWeakReference;
    private LocalStorage myLocalStorage;

    public UserLoader(MainActivity mainActivity, Context context){
        myMainActivityWeakReference = new WeakReference<>(mainActivity);
        myLocalStorage = new LocalStorage(context);
    }

    @Override
    protected String doInBackground(Void... voids) {
        User u = myLocalStorage.loadUser();
        while (u==null){
            u = myLocalStorage.loadUser();
        }
        return u.getName();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        MainActivity mainActivity = myMainActivityWeakReference.get();
        if(mainActivity ==null|| mainActivity.isFinishing()){
            return;
        }

        s = "Hello " + s + "!";
        mainActivity.getNavHeaderTextView().setText(s);
    }
}
