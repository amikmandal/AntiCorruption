package com.gretel.mendit.frontend;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import gretel.com.mendit.R;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager myCallbackManager;
    private LoginButton myLoginButton;
    private TextView myTextView;

    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        myCallbackManager = CallbackManager.Factory.create();

        myLoginButton = (LoginButton) findViewById(R.id.login_button);
        myTextView = (TextView) findViewById(R.id.login_text_view);

        myLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        myLoginButton.registerCallback(myCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                myTextView.setText("Login Success \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                // App code
                myTextView.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                myTextView.setText("Login Error");
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        myCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

}