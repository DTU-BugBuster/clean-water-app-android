package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fourandahalfmen.m4.data.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends Activity {

    /* instance variables */
    private EditText username;
    private EditText password;
    private Spinner userSpinner;
    private EditText email;
    private EditText street_address;
    private EditText city;
    private EditText state;
    private EditText zip_code;
    private Button onCreateUser;
    private Button cancelButton;


    /* values */
    private String[] userTypes = {"User", "Worker", "Manager", "Admin"};

    /* database instance */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        ArrayAdapter<String> userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);
        email = (EditText) findViewById(R.id.editEmail);
        street_address = (EditText) findViewById(R.id.editStreet);
        city = (EditText) findViewById(R.id.editCity);
        state = (EditText) findViewById(R.id.editState);
        zip_code = (EditText) findViewById(R.id.editZip);
        onCreateUser = (Button) findViewById(R.id.createUser);

        onCreateUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if any textfield is empty, send alert to user
                if (username.getText().toString() == "" ||
                        password.getText().toString() == "" || email.getText().toString() == "" ||
                        street_address.getText().toString() == "" || city.getText().toString() == null ||
                        state.getText().toString() == "" || zip_code.getText().toString() == null) {

                    // If Fields are empty, display an error.
                    alertMessage("Blank Fields", "Some fields are empty. Please fill in all fields.");

                } else {
                    // if all good and user is written to firebase, send to loginactivity page
                    if (writeNewUser(username.getText().toString(), password.getText().toString(), userSpinner.getSelectedItem().toString(),
                            email.getText().toString(), street_address.getText().toString(), city.getText().toString(),
                            state.getText().toString(), zip_code.getText().toString())) {
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        i.putExtra("username", username.getText().toString());
                        startActivity(i);

                    // else send alert to user
                    } else {
                        alertMessage("Incorrect Types", "Make sure zip is all numbers and email is valid.");
                    }
                }
            }
        });

        cancelButton = (Button) findViewById(R.id.cancel_button_RP);

        /**
         * If cancel button is clicked, goes back to welcome screen.
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View c) {
                username.setText("");
                password.setText("");
//                Intent i = new Intent(RegisterActivity.this, WelcomeActivity.class);
//                startActivity(i);
                finish();
                onBackPressed();
            }
        });
    }

    /**
     * create new user and add to firebase
     * @param username          username of user
     * @param password          password of user
     * @param account_type      account type of user
     * @param email             email of user
     * @param street_address    street addree of user
     * @param city              city of address
     * @param state             state of address
     * @param zip_code          zipcode of address
     * @return  boolean value based on successful input into firebase
     */
    private boolean writeNewUser(String username, String password, String account_type,
                                 String email, String street_address, String city,
                                 String state, String zip_code) {

        // check if email as @ symbol
        if (!email.contains("@")) {
            return false;
        }

        // check if zip is int
        int zip = 0;
        try {
            zip = Integer.parseInt(zip_code);

        } catch (Exception e) {
            return false;
        }

        Users user = new Users(username, password, account_type, email, street_address, city, state,
                zip, 0, false);
        mDatabase.child(username).setValue(user);
        return true;
    }

    /**
     * Alert Message general function for generating alerts
     * @param title title of message
     * @param body  body of message
     */
    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(RegisterActivity.this);
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