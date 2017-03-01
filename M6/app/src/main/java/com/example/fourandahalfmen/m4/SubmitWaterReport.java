package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fourandahalfmen.m4.data.Users;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SubmitWaterReport extends AppCompatActivity {

    /* instance variables */
    private Spinner waterTypeSpinner;
    private Spinner waterConditionSpinner;
    private EditText location;

    /* values */
    private String[] waterTypes = {"Bottled", "Well", "Stream", "Lake", "Spring", "Other"};
    private String[] waterConditions = {"Waste", "Treatable-Clear", "Treatable-Muddy", "Potable"};

    /* database instance */
    private GoogleApiClient client;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reportDatabase = database.getReference("waterReports");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_water_report);

        location = (EditText) findViewById(R.id.location);
        setupSpinners();

    }

    /**
     * Functionality for submit button
     * @param v view that it button is controlled on
     */
    public void onSubmitClick(View v) {
        if (submitReport()) {
            Log.i("clicks", "You clicked the submit button.");
            Intent i = new Intent(SubmitWaterReport.this, HomePageActivity.class);
            startActivity(i);
        } else {
            // fields empty so alert
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

            dlgAlert.setMessage("Fields are empty or not selected!");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.create().show();
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        }
    }

    /**
     * Functionality for cancel button
     * @param v view that button is controlled on
     */
    public void onCancelClick(View v) {
            Log.i("clicks", "You clicked the submit button.");
            Intent i = new Intent(SubmitWaterReport.this, HomePageActivity);
            startActivity(i);
    }

    /**
     * Actual process of communicating with Firebase to submit report
     * @return boolean determines successful submitting report
     */
    private boolean submitReport() {
        String insertLocation = location.getText().toString();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
       // String reporterName = user;
        String report = reportDatabase.child("waterReports").limitToLast(1).getRef().getKey();
        int reportNumber = Integer.parseInt(report) + 1;
        String insertWaterCondition = waterConditionSpinner.getSelectedItem().toString();
        String insertWaterType = waterTypeSpinner.getSelectedItem().toString();

        if (insertLocation == null | waterTypeSpinner.getSelectedItem() == null | waterConditionSpinner.getSelectedItem() == null) {
            return false;
        }

        reportDatabase.child("waterReports").child(reportNumber).child("date_time").setValue(currentDateTimeString);
        reportDatabase.child("waterReports").child(reportNumber).child("location").setValue(insertLocation);
        reportDatabase.child("waterReports").child(reportNumber).child("water_condition").setValue(insertWaterCondition);
        reportDatabase.child("waterReports").child(reportNumber).child("water_type").setValue(insertWaterType);

        //get user from other stuff


        return true;
    }

    /**
     * Setup the dropdown spinners that allows the user to select options.
     */
    private void setupSpinners() {
        /* Setup spinners and adapter */
        ArrayAdapter<String> waterTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, waterTypes);
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(waterTypeAdapter);

        ArrayAdapter<String> waterConditionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, waterConditions);
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterConditionSpinner.setAdapter(waterConditionAdapter);
    }
}