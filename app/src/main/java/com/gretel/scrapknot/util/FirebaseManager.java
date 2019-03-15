package com.gretel.scrapknot.util;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gretel.scrapknot.backend.Repairer;
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

    public void addRepairer(Repairer n){
        String id = databaseReference.push().getKey();
        Repairer repairer = new Repairer(n.getDisplayPicture(), n.getName(), n.getRating(), n.getSpeciality());
        databaseReference.child(id).setValue(repairer);
    }

    /**
     * This method adds an user object to the database
     * @param u specifies the user to be uploaded
     * @param loginType specifies the medium user used to login.
     */
    public void addUser(User u,String loginType){
        databaseReference.child(loginType).child(u.getFacebookID()).setValue(u);
    }

    /**
     * This method is used to retrieve an user object from the database
     * @param loginType specifies the type user used to login
     * @param id specifies the unique id of the user in the login type
     */
    public void getUser(final String loginType, String id){

        databaseReference = databaseReference.child(loginType).child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int index = 0;
                String displayPicture = "", name = "", email = "", number = "", address = "", id = "", loginType ="";
                for (DataSnapshot e : dataSnapshot.getChildren()) {
                    if (index == 0) {
                        address = e.getValue(String.class);
                    } else if (index == 1) {
                        displayPicture = e.getValue(String.class);
                    } else if (index == 2) {
                        email = e.getValue(String.class);
                    } else if (index == 3){
                        id = e.getValue(String.class);
                    } else if (index == 4) {
                        loginType = e.getValue(String.class);
                    } else if(index == 5) {
                        name = e.getValue(String.class);
                    } else {
                        number = e.getValue(String.class);
                    }
                    index++;
                }

                User u = new User(displayPicture,name,id,email,address,number,loginType);

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
