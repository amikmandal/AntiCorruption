package com.gretel.anticorruption.view.activities.ReportActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gretel.anticorruption.R;
import com.gretel.anticorruption.model.Agent.Report;
import com.gretel.anticorruption.model.Agent.User;
import com.gretel.anticorruption.util.BackEndManager.FirebaseManager;
import com.gretel.anticorruption.util.LocalStorage;
import com.gretel.anticorruption.view.activities.MainActivity.UserPrimaryActivity;

import java.util.Calendar;

public class ReportActivity extends AppCompatActivity{

    private String myAuthority;
    private boolean myMode;

    private EditText myOfficerEdit;
    private EditText myPlaceEdit;
    private EditText myReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        myOfficerEdit = findViewById(R.id.input_officer);
        myPlaceEdit = findViewById(R.id.input_place);
        myReport = findViewById(R.id.input_report);

        setSpinner(R.id.input_authority, R.array.agencies);
        setSpinner(R.id.input_mode,R.array.mode);

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNextButton();
            }
        });

    }

    private void handleNextButton() {
        String officer = myOfficerEdit.getText().toString();
        String place = myPlaceEdit.getText().toString();
        String reportText = myReport.getText().toString();

        if(officer.equals("")||place.equals("")||reportText.equals("")||myAuthority.equals("Select an Item")||myAuthority.equals("")){
            new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog))
                    .setTitle("Alert!")
                    .setMessage("The report is missing details. Please complete them.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            Report report = new Report(officer,myAuthority,place,Calendar.getInstance().getTime().toString(),reportText);
            System.out.println("what what what: "+officer);
            addReport(report);
        }
    }

    private void setSpinner(final int spinnerID, int arrayID) {
        Spinner spinner = findViewById(spinnerID);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayID, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                switch (spinnerID){
                    case R.id.input_authority:
                        myAuthority = text;
                        break;
                    case R.id.input_mode:
                        switch (text){
                            case "Yes":
                                myMode = true;
                                break;
                            case "No":
                                myMode = false;
                                break;
                        }
                        break;
                }
            }

            @Override
                public void onNothingSelected(AdapterView<?> parent) {
                switch (spinnerID) {
                    case R.id.input_authority:
                        myAuthority = "Others";
                        break;
                    case R.id.input_mode:
                        myMode = true;
                }
            }
        });
    }

    private void addReport(Report report){

        FirebaseManager firebaseManager = new FirebaseManager("reports",this);
        String reportID = firebaseManager.addReport(report);

        firebaseManager = new FirebaseManager("reports by time", this);
        firebaseManager.addTimestamp(reportID);

        LocalStorage localStorage = new LocalStorage(getApplicationContext());
        User u = localStorage.loadUser();

        if(!myMode){
            firebaseManager = new FirebaseManager("reports by user",this);
            firebaseManager.addReportToUser(u,reportID);
        }

        localStorage.addReportToUser(reportID);

        Toast.makeText(this, reportID+" was added successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), UserPrimaryActivity.class);
        startActivity(intent);
    }
}
