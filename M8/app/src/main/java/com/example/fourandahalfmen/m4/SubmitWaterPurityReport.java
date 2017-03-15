package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.WaterPurityReport;
import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class SubmitWaterPurityReport extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    /* instance variables */
    private String fromUsername;
    private Spinner waterConditionSpinner;
    private Button submitButton;
    private Button cancelButton;
    protected GoogleApiClient mGoogleApiClient;

    private String date;
    private String time;
    private int reportNumber;
    private String user;
    private String location;
    private Double llat;
    private Double llong;
    private String waterCondition;
    private double virusPPM;
    private double contaminantPPM;

    /* values */
    private String[] waterConditions = {"Safe", "Treatable", "Unsafe"};

    /* database instance */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("waterReports");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_water_purity_report);

        fromUsername = getIntent().getStringExtra("username");


        waterConditionSpinner = (Spinner) findViewById(R.id.waterConditionSpinner);
        ArrayAdapter<String> waterConditionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, waterConditions);
        waterConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterConditionSpinner.setAdapter(waterConditionAdapter);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If fields are empty, display an error.
                if (location.getText().toString() == "") {
                    alertMessage("Blank Fields", "Location field is empty. Please fill in all fields.");

                } else {
                    getLatLongFromAddress(location.getText().toString());
                    // if successful entry, alert user
                    if (submitReport(
                            location.getText().toString(), waterTypeSpinner.getSelectedItem().toString(),
                            waterConditionSpinner.getSelectedItem().toString(), latitude, longitude)) {
                        alertMessage("Succesful Entry", "Thank you for reporting.");
                        Intent i = new Intent(SubmitWaterPurityReport.this, HomePageActivity.class);
                        i.putExtra("username", fromUsername);
                        startActivity(i);
                        // if error in entry, alert user
                    } else {
                        alertMessage("Incorrect Types", "Make sure zip is all numbers and email is valid.");
                    }
                }
            }
        });

        cancelButton = (Button) findViewById(R.id.cancelButtonWR);

        /**
         * If cancel button is clicked, goes back to welcome screen.
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View c) {
                Log.i("clicks", "You clicked the submit button.");
                Intent i = new Intent(SubmitWaterPurityReport.this, HomePageActivity.class);
                i.putExtra("username", fromUsername);
                startActivity(i);
            }
        });

        buildGoogleApiClient();

    }

    private void getLatLongFromAddress(String address)
    {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            latitude = addresses.get(0).getLatitude();
            longitude = addresses.get(0).getLongitude();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            System.out.println("Connected1");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Actual process of communicating with Firebase to submit report
     * @return boolean determines successful submitting report
     */
    private boolean submitReport(String date, String time, int reportNumber,
                                      String user, String location, double llat, double llong,
                                      String waterCondition, double virusPPM, double contaminantPPM) {

        WaterPurityReport wr = new WaterPurityReport(date, time, reportNumber, user ,location, llat, llong, waterCondition, virusPPM, contaminantPPM);
        mDatabase.child(location).setValue(wr);
        return true;
    }

    /**
     * Alert Message general function for generating alerts
     * @param title title of message
     * @param body  body of message
     */
    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(SubmitWaterPurityReport.this);
        dialog2.setCancelable(false);
        dialog2.setTitle(title);
        dialog2.setMessage(body);
        dialog2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog2.create();
        alert.show();
    }

}