package com.gretel.scrapknot.util;

import android.content.Context;
import android.os.AsyncTask;

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
        String name = myLocalStorage.loadUser().getName();
        while(name.equals("")){
            name = myLocalStorage.loadUser().getName();
        }
        return name;
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
