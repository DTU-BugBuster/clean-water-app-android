package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import android.location.Geocoder;
import android.location.Address;
import java.util.Locale;
import java.util.List;

public class SubmitWaterReport extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    /* instance variables */
    private String fromUsername;
    private Spinner waterTypeSpinner;
    private Spinner waterConditionSpinner;
    private EditText location;
    private Button submitButton;
    private Button cancelButton;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    private Double latitude;
    private Double longitude;
    private static final String TAG = "SubmitWaterReport";
    private final int LOCATION_PERMISSION_REQUEST_CODE = 123;

    /* values */
    private String[] waterTypes = {"Bottled", "Well", "Stream", "Lake", "Spring", "Other"};
    private String[] waterConditions = {"Waste", "Treatable-Clear", "Treatable-Muddy", "Potable"};

    /* database instance */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("waterReports");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_water_report);

        fromUsername = getIntent().getStringExtra("username");

        location = (EditText) findViewById(R.id.location);

        waterTypeSpinner = (Spinner) findViewById(R.id.waterTypeSpinner);
        ArrayAdapter<String> waterTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, waterTypes);
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(waterTypeAdapter);

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
                        Intent i = new Intent(SubmitWaterReport.this, HomePageActivity.class);
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
                Intent i = new Intent(SubmitWaterReport.this, HomePageActivity.class);
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
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
    }







    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * Actual process of communicating with Firebase to submit report
     * @return boolean determines successful submitting report
     */
    private boolean submitReport(String location, String waterType, String waterCondition, Double llat, Double llong) {
        WaterReport wr = new WaterReport(location, fromUsername, waterType, waterCondition, llat, llong);
        mDatabase.child(location).setValue(wr);
        return true;
    }

    /**
     * Alert Message general function for generating alerts
     * @param title title of message
     * @param body  body of message
     */
    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(SubmitWaterReport.this);
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