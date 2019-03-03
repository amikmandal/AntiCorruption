package com.gretel.mendit.frontend.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gretel.mendit.backend.User;
import com.gretel.mendit.util.LocalStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import gretel.com.mendit.R;

/**
 * This fragment helps display the information of the logged in user.
 * @author Amik Mandal
 */
public class UserFragment extends Fragment {

    private TextView myTextName;
    private TextView myTextEmail;
    private TextView myTextAddress;
    private TextView myTextNumber;
    private CircleImageView myProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user,container,false);

        myTextName = view.findViewById(R.id.user_name);
        myTextEmail = view.findViewById(R.id.user_username);;
        myTextAddress = view.findViewById(R.id.user_address);;
        myTextNumber = view.findViewById(R.id.user_phone_number);;
        myProfilePhoto = view.findViewById(R.id.user_profile_photo);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        LocalStorage localStorage = new LocalStorage(getActivity().getApplicationContext());
        User u = localStorage.loadUser();

        myTextName.setText(u.getName());
        myTextAddress.setText(u.getAddress());
        myTextEmail.setText(u.getEmail());
        myTextNumber.setText(u.getNumber());
        Picasso.get().load(u.getDisplayPicture()).into(myProfilePhoto);

    }


}
