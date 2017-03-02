package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomePageActivity extends Activity {

    private String fromUsername;
    private EditText username;
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
    private GoogleApiClient client;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDatabase = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        fromUsername = getIntent().getStringExtra("username");

        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        ArrayAdapter<String> userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
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

        save = (Button) findViewById(R.id.Save);
//        save.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
////
////                Users user = new Users(username, password, account_type, email, house_num,
////                        street_address, city, state, zip_code);
////                mDatabase.child("users").child(fromUsername).setValue(user);
//            }
//        });
//
//        private void updateUser(String username, String password, String account_type,
//                            String email, int house_num, String street_address, String city,
//                            String state, String zip_code) {
//        }
    }

    /**
     * Button handler for the add new student button
     *
     * @param v the button
     */
    public void onSaveClick(View v) {
        String insertEmail = email.getText().toString();
        String insertPassword = password.getText().toString();
        String insertUserType = (String) userSpinner.getSelectedItem();

        userDatabase.child(fromUsername).child("email").setValue(insertEmail);
        userDatabase.child(fromUsername).child("password").setValue(insertPassword);
        userDatabase.child(fromUsername).child("account_type").setValue(insertUserType);
    }

    public void onReportClick(View v) {
        Intent i = new Intent(HomePageActivity.this, SubmitWaterReport.class);
        i.putExtra("username", fromUsername);
        HomePageActivity.this.startActivity(i);
    }
}

