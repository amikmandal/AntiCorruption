package com.gretel.scrapknot.frontend.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.gretel.scrapknot.backend.User;
import com.gretel.scrapknot.frontend.activities.MainActivity;
import com.gretel.scrapknot.util.FirebaseManager;
import com.gretel.scrapknot.util.LocalStorage;
import com.gretel.scrapknot.R;
import com.gretel.scrapknot.util.EditButtonListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This fragment helps display the information of the logged in user.
 * @author Amik Mandal
 */
public class UserFragment extends Fragment implements EditButtonListener {

    private ViewSwitcher mySwitcherName;
    private TextView myTextName;
    private EditText myEditName;
    private ViewSwitcher mySwitcherEmail;
    private TextView myTextEmail;
    private EditText myEditEmail;
    private ViewSwitcher mySwitcherNumber;
    private TextView myTextNumber;
    private EditText myEditNumber;
    private ViewSwitcher mySwitcherAddress;
    private TextView myTextAddress;
    private EditText myEditAddress;
    private Button mySaveButton;
    private Button myCancelButton;
    private Button myToolbarButton;
    private CircleImageView myProfilePhoto;

    private Context myContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user,container,false);

        myContext = getActivity().getApplicationContext();

        mySwitcherName = view.findViewById(R.id.view_switcher_name);
        mySwitcherEmail = view.findViewById(R.id.view_switcher_email);
        mySwitcherNumber = view.findViewById(R.id.view_switcher_phone_number);
        mySwitcherAddress = view.findViewById(R.id.view_switcher_address);

        myTextName = view.findViewById(R.id.user_name);
        myTextEmail = view.findViewById(R.id.user_email);
        myTextAddress = view.findViewById(R.id.user_address);;
        myTextNumber = view.findViewById(R.id.user_phone_number);;
        myProfilePhoto = view.findViewById(R.id.user_profile_photo);

        myEditName = view.findViewById(R.id.edit_user_name);
        myEditEmail = view.findViewById(R.id.edit_user_email);
        myEditNumber = view.findViewById(R.id.edit_user_phone_number);
        myEditAddress = view.findViewById(R.id.edit_user_address);

        mySaveButton = view.findViewById(R.id.save_button);
        myCancelButton = view.findViewById(R.id.cancel_button);
        mySaveButton.setVisibility(View.GONE);
        myCancelButton.setVisibility(View.GONE);
        mySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveButton(view);
            }
        });
        myCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancelButton(view);
            }
        });

        return view;
    }

    private void handleSaveButton(View view) {
        switchToViewMode();
        LocalStorage localStorage = new LocalStorage(myContext);
        User oldUser = localStorage.loadUser();
        User newUser = new User(oldUser.getDisplayPicture(),myTextName.getText().toString(),oldUser.getFacebookID(),myTextEmail.getText().toString(),myTextAddress.getText().toString(),myTextNumber.getText().toString(),oldUser.getLoginType());
        if(!oldUser.equals(newUser)){
            hideKeyBoard(view);
            scrollUp(view);
            switchToViewMode();
            myToolbarButton.setVisibility(View.VISIBLE);
            saveProfile(newUser);
            Toast.makeText(myContext,"Your changes have been saved",Toast.LENGTH_SHORT).show();
        } else {
            scrollUp(view);
            switchToEditMode();
            activateEditText(myEditName);
            Toast.makeText(myContext,"Nothing to update",Toast.LENGTH_SHORT).show();
        }
    }

    public void handleCancelButton(final View view) {
        handleCancelButton(view);
        switchToViewMode();
        LocalStorage localStorage = new LocalStorage(myContext);
        User oldUser = localStorage.loadUser();
        User newUser = new User(oldUser.getDisplayPicture(),myTextName.getText().toString(),oldUser.getFacebookID(),myTextEmail.getText().toString(),myTextAddress.getText().toString(),myTextNumber.getText().toString(),oldUser.getLoginType());
        if(!oldUser.equals(newUser)){
            new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                    .setTitle("Cancel Changes")
                    .setMessage("Are you sure you want to cancel editing your profile?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            hideKeyBoard(view);
                            switchToViewMode();
                            myToolbarButton.setVisibility(View.VISIBLE);
                            setDefault();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            scrollUp(view);
                            onEditButtonSelect(myToolbarButton);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            hideKeyBoard(view);
            switchToViewMode();
            myToolbarButton.setVisibility(View.VISIBLE);
        }
    }

    private void scrollUp(View v) {
        final ScrollView sv = v.findViewById(R.id.user_container);
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(sv.FOCUS_UP);
            }
        });
    }

    private void setDefault() {
        LocalStorage localStorage = new LocalStorage(getActivity().getApplicationContext());
        User u = localStorage.loadUser();

        myTextName.setText(u.getName());
        myTextAddress.setText(u.getAddress());
        myTextEmail.setText(u.getEmail());
        myTextNumber.setText(u.getNumber());
        Picasso.get().load(u.getDisplayPicture()).into(myProfilePhoto);
    }

    private void hideKeyBoard(View view) {
        System.out.println("trying hard to hide");
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    private void saveProfile(User u) {
        LocalStorage localStorage = new LocalStorage(myContext);
        FirebaseManager firebaseManager = new FirebaseManager("user", myContext);
        firebaseManager.editUser(u);
        localStorage.editUser(u);
    }

    @Override
    public void onStart(){
        super.onStart();
        setDefault();

    }

    @Override
    public void onEditButtonSelect(Button toolbarButton) {

        myToolbarButton = toolbarButton;

        switchToEditMode();

        //set Name to be editable by default
        activateEditText(myEditName);
    }

    private void switchToViewMode(){
        switchInTextView(mySwitcherName,myTextName,myEditName);
        switchInTextView(mySwitcherEmail,myTextEmail,myEditEmail);
        switchInTextView(mySwitcherNumber,myTextNumber,myEditNumber);
        switchInTextView(mySwitcherAddress,myTextAddress,myEditAddress);

        mySaveButton.setVisibility(View.GONE);
        myCancelButton.setVisibility(View.GONE);
    }

    private void switchToEditMode(){
        switchInEditText(mySwitcherName,myEditName,myTextName);
        switchInEditText(mySwitcherEmail,myEditEmail, myTextEmail);
        switchInEditText(mySwitcherAddress, myEditAddress, myTextAddress);
        switchInEditText(mySwitcherNumber, myEditNumber,myTextNumber);

        myToolbarButton.setVisibility(View.GONE);
    }

    private void switchInEditText(ViewSwitcher switcher, EditText editText, TextView textView) {
        switcher.setDisplayedChild(1);
        editText.setText(textView.getText());
        showKeyBoard(editText);
    }

    private void switchInTextView(ViewSwitcher switcher, TextView textView, EditText editText) {
        switcher.setDisplayedChild(0);
        textView.setText(editText.getText().toString());
    }

    private void activateEditText(EditText editText) {

        editText.setSelection(editText.getText().length());
        editText.requestFocus();
        editText.setCursorVisible(true);
        showKeyBoard(editText);

        mySaveButton.setVisibility(View.VISIBLE);
        myCancelButton.setVisibility(View.VISIBLE);
    }

}