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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomePageActivity extends Activity {

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


    /* database instance */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        fromUsername = getIntent().getStringExtra("username");

        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        final ArrayAdapter<String> userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        password = (EditText) findViewById(R.id.editPasswordHP);
        email = (EditText) findViewById(R.id.editEmailHP);
        street_address = (EditText) findViewById(R.id.editStreetHP);
        city = (EditText) findViewById(R.id.editCityHP);
        state = (EditText) findViewById(R.id.editStateHP);
        zip_code = (EditText) findViewById(R.id.editZipHP);

        logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity.this, WelcomeActivity.class);
                startActivity(i);
            }
        });

        submitButton = (Button) findViewById(R.id.submit_a_report);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity.this, SubmitWaterReport.class);
                i.putExtra("username", fromUsername);
                startActivity(i);
            }
        });

        String reflocation = "users/" + fromUsername;
        DatabaseReference ref = database.getReference(reflocation);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users post = dataSnapshot.getValue(Users.class);
                password.setText(post.password);
                email.setText(post.email);
                userAdapter.getPosition(post.account_type.toString());
                street_address.setText(post.street_address);
                city.setText(post.city);
                state.setText(post.state);
                zip_code.setText(String.valueOf(post.zip_code));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save = (Button) findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (password.getText().toString() == "" || email.getText().toString() == "" ||
                        street_address.getText().toString() == "" || city.getText().toString() == null ||
                        state.getText().toString() == "" || zip_code.getText().toString() == null) {

                    // If Fields are empty, display an error.
                    alertMessage("Blank Fields", "Some fields are empty. Please fill in all fields.");

                } else {
                    if (!writeNewUser(fromUsername, password.getText().toString(), userSpinner.getSelectedItem().toString(),
                            email.getText().toString(), street_address.getText().toString(), city.getText().toString(),
                            state.getText().toString(), zip_code.getText().toString())) {
                        alertMessage("Incorrect Types", "Make sure zip is all numbers and email is valid.");
                    } else {
                        alertMessage("Successful Change", "Your changes have been recorded.");
                    }
                }
            }
        });
    }


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
                zip, 0, false);
        mDatabase.child(username).setValue(user);
        return true;
    }


    public void onReportClick(View v) {
        Intent i = new Intent(HomePageActivity.this, SubmitWaterReport.class);
        i.putExtra("username", fromUsername);
        HomePageActivity.this.startActivity(i);
    }

    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(HomePageActivity.this);
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

