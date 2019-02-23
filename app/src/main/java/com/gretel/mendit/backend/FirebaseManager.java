package com.gretel.mendit.backend;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    DatabaseReference databaseMechanics;

    public FirebaseManager(String database){
        databaseMechanics = FirebaseDatabase.getInstance().getReference(database);
    }

    public void addRepairer(NameEntry n){
        String id = databaseMechanics.push().getKey();
        NameEntry nameEntry = new NameEntry(n.getDisplayPicture(), n.getName(), n.getRating(), n.getSpeciality());
        databaseMechanics.child(id).setValue(nameEntry);
    }

    public void addUser(User u){
        String id = databaseMechanics.push().getKey();
        User user = new User(u.getDisplayPicture(),u.getName(),u.getEmail(),u.getAddress(),u.getNumber(),id);
        databaseMechanics.child(id).setValue(user);
    }

}
