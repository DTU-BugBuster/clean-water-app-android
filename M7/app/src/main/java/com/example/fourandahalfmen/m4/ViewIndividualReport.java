package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.Users;
import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by varunballari on 3/2/17.
 */

public class ViewIndividualReport extends Activity {

    private String fromKey;
    private TextView viewLocation;
    private TextView viewLong;
    private TextView viewLat;
    private TextView viewDate;
    private TextView viewWaterCondition;
    private TextView viewWaterType;
    private TextView viewUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_individual);
        fromKey = getIntent().getStringExtra("key");

        viewDate = (TextView) findViewById(R.id.viewDate);
        viewLocation = (TextView) findViewById(R.id.viewLocation);
        viewWaterCondition = (TextView) findViewById(R.id.viewWaterCondition);
        viewWaterType = (TextView) findViewById(R.id.viewWaterType);
        viewUser = (TextView) findViewById(R.id.viewUser);
        viewLat = (TextView) findViewById(R.id.viewLat);
        viewLong = (TextView) findViewById(R.id.viewLong);


        String reflocation =  "waterReports/" + fromKey.toString();
        DatabaseReference ref = database.getReference(reflocation);
        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase based on user-specific id and set them to textfields and
             * spinners on page
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WaterReport post = dataSnapshot.getValue(WaterReport.class);
                viewDate.setText(post.date.toString());
                viewLocation.setText(post.location.toString());
                viewWaterType.setText(post.waterType.toString());
                viewWaterCondition.setText(post.waterCondition.toString());
                viewUser.setText(post.user.toString());
                viewLat.setText(post.llat.toString());
                viewLong.setText(post.llong.toString());
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
