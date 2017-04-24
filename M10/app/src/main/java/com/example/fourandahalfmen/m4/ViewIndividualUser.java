package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.Users;
import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by varunballari on 3/2/17.
 */

public class ViewIndividualUser extends Activity {

    private String fromKey;
    private TextView account_type;
    private TextView attempts;
    private TextView email;
    private TextView locked;
    private TextView password;
    private TextView username;
    private String street_address;
    private String city;
    private String state;
    private String zip_code;
    private String userAdapter;
    private Button delete;
    private Button unban;

    /* database instance */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_individual);
        fromKey = getIntent().getStringExtra("key");

        account_type = (TextView) findViewById(R.id.account_type);
        attempts = (TextView) findViewById(R.id.attempts);
        email = (TextView) findViewById(R.id.email);
        locked = (TextView) findViewById(R.id.locked);
        password = (TextView) findViewById(R.id.password);
        username = (TextView) findViewById(R.id.username);


        String reflocation =  "users/" + fromKey.toString();
        DatabaseReference ref = database.getReference(reflocation);
        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase based on user-specific id and set them to textfields and
             * spinners on page
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users post = dataSnapshot.getValue(Users.class);
                if (post != null) {
                    account_type.setText(post.account_type.toString());
                    attempts.setText(String.valueOf(post.attempts));
                    city = post.city.toString();
                    email.setText(post.email.toString());
                    locked.setText(String.valueOf(post.locked));
                    password.setText(post.password.toString());
                    username.setText(post.username.toString());
                    userAdapter = post.account_type.toString();
                    street_address = post.street_address.toString();
                    state = post.state.toString();
                    zip_code = String.valueOf(post.zip_code);
                }
            }
            /**
             * necessary method required for firebase
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String reflocation =  "users/" + fromKey.toString();
            DatabaseReference ref2 = database.getReference(reflocation);
            ref2.addValueEventListener(new ValueEventListener() {
                /**
                 * get data from firebase based on user-specific id and set them to textfields and
                 * spinners on page
                 */
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().setValue(null);
                    onBackPressed();
                }
                /**
                 * necessary method required for firebase
                 */
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }});





        unban = (Button) findViewById(R.id.unban);
        unban.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (writeNewUser(username.getText().toString(), password.getText().toString(), userAdapter,
                        email.getText().toString(), street_address, city,
                        state, zip_code)) {
                    alertMessage("Successful Change", "Your changes have been recorded.");
                }
            }
        });

    }


    /**
     * update user and push to firebase
     * @param username          username of user
     * @param password          password of user
     * @param account_type      account type of user
     * @param email             email of user
     * @param street_address    street address of user
     * @param city              city of address
     * @param state             state of address
     * @param zip_code          zipcode of address
     * @return  boolean value based on successful input into firebase
     */
    private boolean writeNewUser(String username, String password, String account_type,
                                 String email, String street_address, String city,
                                 String state, String zip_code) {

        Users user = new Users(username, password, account_type, email, street_address, city, state,
                Long.valueOf(zip_code), Long.valueOf(0), false);
        mDatabase.child(username).setValue(user);
        return true;
    }

    /**
     * Alert Message general function for generating alerts
     * @param title title of message
     * @param body  body of message
     */
    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(ViewIndividualUser.this);
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
