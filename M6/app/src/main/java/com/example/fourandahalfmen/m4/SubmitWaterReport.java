package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitWaterReport extends AppCompatActivity {

    /* instance variables */
    private Spinner waterTypeSpinner;
    private Spinner waterConditionSpinner;
    private EditText location;
    private Button submitButton;
    private Button cancelButton;

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

        location = (EditText) findViewById(R.id.location);

        waterTypeSpinner = (Spinner) findViewById(R.id.waterTypeSpinner);
        ArrayAdapter<String> waterTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, waterTypes);
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(waterTypeAdapter);

        waterConditionSpinner = (Spinner) findViewById(R.id.waterConditionSpinner);
        ArrayAdapter<String> waterConditionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, waterConditions);
        waterConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterConditionSpinner.setAdapter(waterConditionAdapter);

        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (location.getText().toString() == "") {

                    // If Fields are empty, display an error.
                    alertMessage("Blank Fields", "Location field is empty. Please fill in all fields.");

                } else {
                    if (submitReport(location.getText().toString(), waterTypeSpinner.getSelectedItem().toString(),
                            waterConditionSpinner.getSelectedItem().toString())) {
                        alertMessage("Succesful Entry", "Thank you for reporting.");
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
                startActivity(i);
            }
        });
    }



    /**
     * Actual process of communicating with Firebase to submit report
     * @return boolean determines successful submitting report
     */
    private boolean submitReport(String location, String waterType, String waterCondition) {

        WaterReport wr = new WaterReport(location, waterType, waterCondition);
        mDatabase.child(location).setValue(wr);
        return true;
    }

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