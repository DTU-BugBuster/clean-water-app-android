package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.WaterPurityReport;
import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by varunballari on 3/2/17.
 */

public class ViewIndividualPurityReport extends Activity {

    private String fromKey;
    private TextView viewLocation;
    private TextView viewLong;
    private TextView viewLat;
    private TextView viewDate;
    private TextView viewWaterCondition;
    private TextView viewUser;
    private TextView viewContaminantPPM;
    private TextView viewVirusPPM;
    private TextView viewReportNumber;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_individual);
        fromKey = getIntent().getStringExtra("key");

        viewDate = (TextView) findViewById(R.id.viewDate);
        viewLocation = (TextView) findViewById(R.id.viewLocation);
        viewWaterCondition = (TextView) findViewById(R.id.viewWaterCondition);
        viewContaminantPPM = (TextView) findViewById(R.id.viewContaminantPPM);
        viewVirusPPM = (TextView) findViewById(R.id.viewVirusPPM);
        viewUser = (TextView) findViewById(R.id.viewUser);
        viewLat = (TextView) findViewById(R.id.viewLat);
        viewLong = (TextView) findViewById(R.id.viewLong);
        viewReportNumber = (TextView) findViewById(R.id.viewReportNumber);



        String reflocation =  "waterPurityReports/" + fromKey.toString();
        DatabaseReference ref = database.getReference(reflocation);
        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase based on user-specific id and set them to textfields and
             * spinners on page
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WaterPurityReport post = dataSnapshot.getValue(WaterPurityReport.class);
                viewDate.setText(post.date.toString());
                viewLocation.setText(post.location.toString());
                viewContaminantPPM.setText(post.contaminantPPM.toString());
                viewVirusPPM.setText(post.virusPPM.toString());
                viewWaterCondition.setText(post.waterCondition.toString());
                viewUser.setText(post.user.toString());
                viewLat.setText(post.llat.toString());
                viewLong.setText(post.llong.toString());
                viewReportNumber.setText(post.reportNumber);

            }
            /**
             * necessary method required for firebase
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
