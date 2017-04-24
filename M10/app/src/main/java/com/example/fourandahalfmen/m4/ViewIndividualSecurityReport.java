package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.securityReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by varunballari on 3/2/17.
 */

public class ViewIndividualSecurityReport extends Activity {

    public String fromKey;
    public TextView viewLoginTimeStamp;
    public TextView success_status;
    public TextView user;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_security_report_individual);
        fromKey = getIntent().getStringExtra("key");

        viewLoginTimeStamp = (TextView) findViewById(R.id.viewLoginTimeStamp);
        success_status = (TextView) findViewById(R.id.success_status);
        user = (TextView) findViewById(R.id.user);



        String reflocation =  "loginReports/" + fromKey.toString();
        DatabaseReference ref = database.getReference(reflocation);
        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase based on user-specific id and set them to textfields and
             * spinners on page
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                securityReport post = dataSnapshot.getValue(securityReport.class);
                viewLoginTimeStamp.setText(fromKey.toString());
                success_status.setText(String.valueOf(post.success_status));
                user.setText(post.user.toString());
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
