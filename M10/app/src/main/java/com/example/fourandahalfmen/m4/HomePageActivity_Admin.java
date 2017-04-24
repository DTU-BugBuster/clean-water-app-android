package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fourandahalfmen.m4.data.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by varunballari on 3/13/17.
 */

public class HomePageActivity_Admin extends Activity {

    private String fromUsername;
    private EditText password;
    private Spinner userSpinner;
    private EditText email;
    private EditText street_address;
    private EditText city;
    private EditText state;
    private EditText zip_code;
    private String[] userTypes = {"User", "Worker", "Manager", "Admin"};
    private Button logout;
    private Button submitButton;
    private Button save;
    private Button views_securityLogs;
    private Button views_all_users;


    /* database instance */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_admin);
        fromUsername = getIntent().getStringExtra("username");

        Log.d("Sup", "here1");

        userSpinner = (Spinner) findViewById(R.id.userSpinnerMan);
        final ArrayAdapter<String> userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        password = (EditText) findViewById(R.id.editPasswordMan);
        email = (EditText) findViewById(R.id.editEmailMan);
        street_address = (EditText) findViewById(R.id.editStreetMan);
        city = (EditText) findViewById(R.id.editCityMan);
        state = (EditText) findViewById(R.id.editStateMan);
        zip_code = (EditText) findViewById(R.id.editZipMan);



        logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity_Admin.this, LoginActivity.class);
                startActivity(i);
            }
        });


        views_securityLogs = (Button) findViewById(R.id.views_securityLogs);
        views_securityLogs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if click on this button, send submitwaterreport activity
                Intent i = new Intent(HomePageActivity_Admin.this, ListViewSecurityActivity.class);
                i.putExtra("username", fromUsername);
                startActivity(i);
            }
        });


        views_all_users  = (Button) findViewById(R.id.views_all_users);
        views_all_users.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if click on this button, send WaterAvailability activity
                Intent i = new Intent(HomePageActivity_Admin.this, ListViewUsersActivity.class);
                startActivity(i);
            }
        });

        String reflocation = "users/" + fromUsername;
        Log.d("reflocation", reflocation);
        DatabaseReference ref = database.getReference(reflocation);
        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase based on user-specific id and set them to textfields and
             * spinners on page
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users post = dataSnapshot.getValue(Users.class);
                password.setText(post.password.toString());
                email.setText(post.email.toString());
                userAdapter.getPosition(post.account_type.toString());
                street_address.setText(post.street_address.toString());
                city.setText(post.city.toString());
                state.setText(post.state.toString());
                zip_code.setText(String.valueOf(post.zip_code));
            }
            /**
             * necessary method required for firebase
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save = (Button) findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if fields are empty, display an error.
                if (password.getText().toString() == "" || email.getText().toString() == "" ||
                        street_address.getText().toString() == "" || city.getText().toString() == null ||
                        state.getText().toString() == "" || zip_code.getText().toString() == null) {

                    alertMessage("Blank Fields", "Some fields are empty. Please fill in all fields.");

                } else {
                    // if wrong type, send alert
                    if (!writeNewUser(fromUsername, password.getText().toString(), userSpinner.getSelectedItem().toString(),
                            email.getText().toString(), street_address.getText().toString(), city.getText().toString(),
                            state.getText().toString(), zip_code.getText().toString())) {
                        alertMessage("Incorrect Types", "Make sure zip is all numbers and email is valid.");
                        // else all good
                    } else {
                        alertMessage("Successful Change", "Your changes have been recorded.");
                    }
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

        if (!email.contains("@")) {
            return false;
        }

        int zip = 0;
        try {
            zip = Integer.parseInt(zip_code);

        } catch (Exception e) {
            return false;
        }

        Users user = new Users(username, password, account_type, email, street_address, city, state,
                Long.valueOf(zip), Long.valueOf(0), false);
        mDatabase.child(username).setValue(user);
        return true;
    }

    /**
     * Alert Message general function for generating alerts
     * @param title title of message
     * @param body  body of message
     */
    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(HomePageActivity_Admin.this);
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
