package com.gretel.mendit.util;

import android.util.Pair;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gretel.mendit.backend.NameEntry;
import com.gretel.mendit.backend.User;

public class FirebaseManager {
    private DatabaseReference databaseReference;

    public FirebaseManager(String database){
        databaseReference = FirebaseDatabase.getInstance().getReference(database);
    }

    public void addRepairer(NameEntry n){
        String id = databaseReference.push().getKey();
        NameEntry nameEntry = new NameEntry(n.getDisplayPicture(), n.getName(), n.getRating(), n.getSpeciality());
        databaseReference.child(id).setValue(nameEntry);
    }

    public User addUser(User u){
        String id = databaseReference.push().getKey();
        User user = new User(u,id);
        databaseReference.child(id).setValue(user);
        return user;
    }

    public void addUserLogin(User user){
        String id = databaseReference.push().getKey();
        Pair<String,String> facebookIDMap = new Pair<>(user.getFacebookID(),user.getID());
        databaseReference.child(id).setValue(facebookIDMap);

    }

}
