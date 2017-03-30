package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class SubmitHistoricalReportInfo extends AppCompatActivity {

    /* instance variables */
    private Spinner waterConditionSpinner;
    private Button submitButton;
    private Button cancelButton;

    private String user;
    private EditText location;
    private RadioButton virus;
    private RadioButton contaminant;
    private EditText year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_historical_report_info);

        user = getIntent().getStringExtra("username");

        location = (EditText) findViewById(R.id.location);
        virus = (RadioButton) findViewById(R.id.virusButton);
        contaminant = (RadioButton) findViewById(R.id.contaminantButton);
        year = (EditText) findViewById(R.id.editTextYear);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (location.getText().toString() == "") {
                    alertMessage("Blank Fields", "Location field is empty. Please fill in all fields.");

                } else {
                    Intent i = new Intent(SubmitHistoricalReportInfo.this, WaterQualityHistoryGraphActivity.class);
                    i.putExtra("username", user);
                    i.putExtra("location", location.getText().toString());
                    if (!virus.isChecked()) {
                        i.putExtra("PPM", "contaminant");
                    } else {
                        i.putExtra("PPM", "virus");
                    }
                    i.putExtra("year", Integer.valueOf(year.getText().toString()));
                    startActivity(i);
                    finish();
                    onBackPressed();
                }
            }
        });

        cancelButton = (Button) findViewById(R.id.cancelButtonWR);

        /**
         * If cancel button is clicked, goes back to welcome screen.
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View c) {
                finish();
                onBackPressed();
            }
        });
    }

    /**
     * Alert Message general function for generating alerts
     * @param title title of message
     * @param body  body of message
     */
    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(SubmitHistoricalReportInfo.this);
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