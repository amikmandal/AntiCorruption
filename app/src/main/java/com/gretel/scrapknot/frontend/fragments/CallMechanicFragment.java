package com.gretel.scrapknot.frontend.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gretel.scrapknot.R;

public class CallMechanicFragment extends Fragment {

    private ImageView myRepairerImage;
    private TextView myRepairerName;
    private TextView myRepairerAddress;
    private ConstraintLayout myRepairerHolder;
    private Button myCallButton;
    private Button myChatButton;
    private Button myRateButton;
    private Button myReportButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_call_mechanic,container,false);

        myRepairerHolder = view.findViewById(R.id.repairer_holder);
        myRepairerImage = view.findViewById(R.id.repairer_image);
        myRepairerName = view.findViewById(R.id.repairer_name);
        myRepairerAddress = view.findViewById(R.id.repairer_address);
        myCallButton = view.findViewById(R.id.call_button);
        myChatButton = view.findViewById(R.id.chat_button);
        myRateButton = view.findViewById(R.id.rate_mechanic);
        myReportButton = view.findViewById(R.id.report_mechanic);

        if(false){
            myRepairerHolder.setVisibility(View.GONE);
            myCallButton.setVisibility(View.GONE);
            myChatButton.setVisibility(View.GONE);
            myRateButton.setVisibility(View.GONE);
            myReportButton.setVisibility(View.GONE);
        }

        myCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCallButton();
            }
        });
        myChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChatButton();
            }
        });


        return view;

    }

    private void handleChatButton() {

    }

    private void handleCallButton() {

        int MY_PERMISSIONS_REQUEST_CALL_PHONE = 23;
        String number = "9199802891";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" +number));

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                startActivity(intent);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
