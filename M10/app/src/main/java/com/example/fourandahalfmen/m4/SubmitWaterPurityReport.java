package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fourandahalfmen.m4.data.WaterPurityReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class SubmitWaterPurityReport extends AppCompatActivity {

    /* instance on a null object referencevariables */
    private Spinner waterConditionSpinner;
    private Button submitButton;
    private Button cancelButton;

    private int reportNumber;
    private String user;
    private Double llat;
    private Double llong;
    private double virusPPM;
    private double contaminantPPM;
    private String locationName;

    private EditText location;
    private EditText virusLable;
    private EditText contaminantLabel;

    /* values */
    private String[] waterConditions = {"Safe", "Treatable", "Unsafe"};

    /* database instance */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("waterPurityReports");
    private DatabaseReference nDatabase = database.getReference("numReportNum");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_water_purity_report);

        user = getIntent().getStringExtra("username");
        location = (EditText) findViewById(R.id.location);
        virusLable = (EditText) findViewById(R.id.virusPPM);
        contaminantLabel = (EditText) findViewById(R.id.contaminantPPM);

        waterConditionSpinner = (Spinner) findViewById(R.id.waterConditionSpinner);
        ArrayAdapter<String> waterConditionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, waterConditions);
        waterConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterConditionSpinner.setAdapter(waterConditionAdapter);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If fields are empty, display an error.
                Log.d("Sup", location.getText().toString());
                if (location.getText().toString() == "") {
                    alertMessage("Blank Fields", "Location field is empty. Please fill in all fields.");

                } else {
                    DatabaseReference ref = database.getReference("numReportNum");
                    ref.addValueEventListener(new ValueEventListener() {
                        /**
                         * get data from firebase based on user-specific id and set them to textfields and
                         * spinners on page
                         */
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int post = dataSnapshot.getValue(int.class);
                            reportNumber = post;
                            reportNumber = reportNumber + 1;
                            String numberAsString = String.valueOf(reportNumber);
                            Log.d("Report", numberAsString);
                        }

                        /**
                         * necessary method required for firebase
                         */
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    virusPPM = Double.parseDouble(virusLable.getText().toString());
                    contaminantPPM = Double.parseDouble(contaminantLabel.getText().toString());
                    nDatabase.setValue(reportNumber);

                    locationName = location.getText().toString();
                    Log.d("Hi", locationName + " " + virusPPM + " " + contaminantPPM);

                    getLatLongFromAddress(locationName);

                    submitReport(reportNumber, user,locationName, llat, llong,
                            waterConditionSpinner.getSelectedItem().toString(), virusPPM, contaminantPPM);
                    finish();
                    onBackPressed();
                }
            }
        });

        cancelButton = (Button) findViewById(R.id.cancelButtonWR);

        /*
          If cancel button is clicked, goes back to welcome screen.
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View c) {
//                Log.i("clicks", "You clicked the submit button.");
//                Intent i = new Intent(SubmitWaterPurityReport.this, HomePageActivity.class);
//                i.putExtra("username", fromUsername);
//                startActivity(i);
                finish();
                onBackPressed();
            }
        });
    }

    /**
     * getting cordinates from address
     * @param address address of location
     */
    private void getLatLongFromAddress(String address)
    {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            llat = addresses.get(0).getLatitude();
            llong = addresses.get(0).getLongitude();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }




    /**
     * Actual process of communicating with Firebase to submit report
     * @return boolean determines successful submitting report
     */
    private boolean submitReport(int reportNumber,
                                      String user, String locationName, Double llat, Double llong,
                                      String waterCondition, Double virusPPM, Double contaminantPPM) {

        WaterPurityReport wr = new WaterPurityReport(reportNumber, user, locationName, llat, llong, waterCondition, virusPPM, contaminantPPM);
        int ppm = virusPPM.intValue();
        String ppmString = "" + ppm;
        mDatabase.child(locationName + ppmString).setValue(wr);
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