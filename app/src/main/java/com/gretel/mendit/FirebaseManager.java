package com.gretel.mendit;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    DatabaseReference databaseMechanics;

    FirebaseManager(String database){
        databaseMechanics = FirebaseDatabase.getInstance().getReference(database);
    }

    public void addRepairer(NameEntry n){
        String id = databaseMechanics.push().getKey();
        NameEntry nameEntry = new NameEntry(n.getDisplayPicture(), n.getName(), n.getRating(), n.getSpeciality());
        databaseMechanics.child(id).setValue(nameEntry);
    }

    public void addUser(User u){
        String id = databaseMechanics.push().getKey();
        databaseMechanics.child(id).setValue(u);
    }

}
