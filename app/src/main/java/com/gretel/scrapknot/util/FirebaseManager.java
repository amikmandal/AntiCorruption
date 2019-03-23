package com.gretel.scrapknot.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gretel.scrapknot.backend.Mechanic;
import com.gretel.scrapknot.backend.User;

/**
 * This class is responsible of most of the Firebase tasks that can be done outside an activity.
 * @author Amik Mandal
 * @date 2/22/2019
 */
public class FirebaseManager {
    private DatabaseReference databaseReference;
    private Context myContext;

    /**
     * Constructor to initialize instance variables
     * @param database specifies the database referred to
     * @param context specifies the current context
     */
    public FirebaseManager(String database, Context context){
        databaseReference = FirebaseDatabase.getInstance().getReference(database);
        myContext = context;
    }

    public void addRepairer(Mechanic n){
        String id = databaseReference.push().getKey();
        Mechanic mechanic = new Mechanic(n.getDisplayPicture(), n.getName(), n.getRating(), n.getSpeciality());
        databaseReference.child(id).setValue(mechanic);
    }

    /**
     * This method adds an user object to the database
     * @param u specifies the user to be uploaded
     * @param loginType specifies the medium user used to login.
     */
    public void addUser(User u,String loginType){
        Gson gson = new Gson();
        String json = gson.toJson(u);
        databaseReference.child(loginType).child(u.getFacebookID()).setValue(json);
    }

    /**
     * This method is used to retrieve an user object from the database
     * @param loginType specifies the type user used to login
     * @param id specifies the unique id of the user in the login type
     */
    public void getUser(String loginType, String id){

        databaseReference = databaseReference.child(loginType).child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Gson gson = new Gson();
                String json = dataSnapshot.getValue(String.class);
                User u = gson.fromJson(json, User.class);

                LocalStorage localStorage = new LocalStorage(myContext);
                localStorage.saveUser(u);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public User editUser(User user){
        databaseReference.child(user.getLoginType()).child(user.getFacebookID()).removeValue();
        addUser(user,user.getLoginType());
        return user;
    }
}
