package com.gretel.mendit;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseManager {
    DatabaseReference databaseMechanics;

    public void addRepairer(NameEntry n){
        databaseMechanics = FirebaseDatabase.getInstance().getReference("mechanics");

        String id = databaseMechanics.push().getKey();
        NameEntry nameEntry = new NameEntry(n.getDisplayPicture(), n.getName(), n.getRating(), n.getSpeciality());

        databaseMechanics.child(id).setValue(nameEntry);
    }

}
