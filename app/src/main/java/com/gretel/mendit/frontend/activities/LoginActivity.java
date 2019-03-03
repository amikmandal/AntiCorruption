package com.gretel.mendit.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gretel.mendit.util.FirebaseManager;
import com.gretel.mendit.util.JSONParser;
import com.gretel.mendit.util.LocalStorage;

import org.json.JSONObject;

import java.util.Arrays;

import gretel.com.mendit.R;

public class LoginActivity extends AppCompatActivity {

    private LoginButton myLoginButton;

    private CallbackManager myCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myCallbackManager = CallbackManager.Factory.create();

        checkIfLoggedIn();

        myLoginButton = findViewById(R.id.login_button);

        //get permissions
        myLoginButton.setReadPermissions(Arrays.asList("email","public_profile"));

        // Callback registration
        myLoginButton.registerCallback(myCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                myLoginButton.setVisibility(View.GONE);

                GraphRequest loginGraphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        JSONParser jsonParser = new JSONParser();
                        final JSONObject jsonObject = object;
                        final String id = jsonParser.readID(jsonObject);

                        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("user");
                        databaseUsers = databaseUsers.child("facebook");

                        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                if (snapshot.hasChild(id)) {

                                    FirebaseManager firebaseManager = new FirebaseManager("user",getApplicationContext());
                                    firebaseManager.getUser("facebook", id);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {

                                    JSONParser jsonParser = new JSONParser();

                                    Intent intent = new Intent(getApplicationContext(), FormActivity.class);
                                    intent.putExtras(jsonParser.makePreliminaryUserData(jsonObject));
                                    startActivity(intent);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                return;
                            }
                        });
                    }

                });

                //get parameters in a bundle
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,first_name,last_name");
                loginGraphRequest.setParameters(parameters);
                loginGraphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast toast = Toast.makeText(getApplicationContext(), "Login Cancelled.", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast toast = Toast.makeText(getApplicationContext(), "Login Error. Please Try Again.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private void checkIfLoggedIn() {
        LocalStorage localStorage = new LocalStorage(getApplicationContext());
        String id = localStorage.loadString("id");

        if(!id.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Login Cancelled.", Toast.LENGTH_SHORT);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        myCallbackManager.onActivityResult(requestCode,resultCode,data);
    }
}