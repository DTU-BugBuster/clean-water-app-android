package com.example.fourandahalfmen.m4;
import com.example.fourandahalfmen.m4.data.LoginDataBaseAdapter;
import com.example.fourandahalfmen.m4.data.Users;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class RegisterActivity extends Activity implements OnClickListener {

    /* instance variables */
    private EditText username;
    private EditText password;
    private Spinner userSpinner;
    private EditText email;
    public EditText house_num;
    public EditText street_address;
    public EditText city;
    public EditText state;
    public EditText zip_code;

    /* values */
    private String[] userTypes = {"User", "Worker", "Manager", "Admin"};

    /* database instance */
    private GoogleApiClient client;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("users");

//    private String userType = UserEntry.TYPE_USER;
//    LoginDataBaseAdapter loginDataBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.editPassword);
        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        email = (EditText) findViewById(R.id.editEmail);
        house_num = (EditText) findViewById(R.id.house_num);
        street_address = (EditText) findViewById(R.id.street_address);
        house_num = (EditText) findViewById(R.id.house_num);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zip_code = (EditText) findViewById(R.id.zip_code);

        setupSpinners();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void onCreateClick(View v) {
        if (addUser()) {
            Log.i("clicks", "Create button clicked - go to HomePageActivity page");
            Intent i = new Intent(RegisterActivity.this, HomePageActivity.class);
            i.putExtra("username", username.getText().toString());
            startActivity(i);
        } else {
            // Fields are empty, display an error.
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Some fields are empty!");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.create().show();
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        }

    }

    /**
     * method to add a user in register page
     * @return boolean than indicates whether or not user was added
     */
    public boolean addUser() {
        String insertUsername = username.getText().toString();
        String insertPassword = password.getText().toString();
        String insertUserType = userSpinner.getSelectedItem().toString();
        String insertEmail = email.getText().toString()
        String insertStreetAddress = street_address.getText().toString();
        String insertHouseNum = house_num.getText().toString();
        String insertCity = city.getText().toString();
        String insertState = state.getText().toString();
        String insertZipCode = (zip_code).getText().toString();


        if (insertUsername == null || insertPassword == null ) {
            return false;
        }
        //writeNewUser(username, password, account_type, email, house_num, street_address, city, state, zip_code);
//        mDatabase.child("users").child(insertEmail).setValue(user);
        mDatabase.child(insertUsername).child("password").setValue(insertPassword);
        mDatabase.child(insertUsername).child("account_type").setValue(insertUserType);
        mDatabase.child(insertUsername).child("email").setValue(insertEmail);
        mDatabase.child(insertUsername).child("street_address").setValue(insertStreetAddress);
        mDatabase.child(insertUsername).child("house_nu ").setValue(insertHouseNum);
        mDatabase.child(insertUsername).child("city").setValue(insertCity);
        mDatabase.child(insertUsername).child("state").setValue(insertState);
        mDatabase.child(insertUsername).child("zip_code").setValue(insertZipCode);
        return true;
    }

//    private void writeNewUser(String username, String password, String account_type,
//                              String email, int house_num, String street_address, String city,
//                              String state, String zip_code) {
//
//        Users user = new Users(username, password, account_type, email, house_num,
//                street_address, city, state, zip_code);
//        mDatabase.child("users").child(username).setValue(user);
//    }

//    private void updateUser(String username, String password, String account_type,
//                            String email, int house_num, String street_address, String city,
//                            String state, String zip_code) {
//
//        Users user = new Users(username, password, account_type, email, house_num,
//                street_address, city, state, zip_code);
//
//        mDatabase.child("users").child(username).setValue(user);
//    }

    /**
     * Setup the dropdown spinners.
    */
    private void setupSpinners() {
        /* Setup spinners and adapter */
        ArrayAdapter<String> userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);
//        // Set the integer mSelected to the constant values
//        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selection = (String) parent.getItemAtPosition(position);
//                if (!TextUtils.isEmpty(selection)) {
//                    if (selection.equals(getString(R.string.user))) {
//                        userType = UserEntry.TYPE_USER;
//                    } else if (selection.equals(getString(R.string.worker))) {
//                        userType = UserEntry.TYPE_WORKER;
//                    } else if (selection.equals(getString(R.string.manager))) {
//                        userType = UserEntry.TYPE_MANAGER;
//                    } else {
//                        userType = UserEntry.TYPE_MANAGER;
//                    }
//                }
//            }
//
//            // Because AdapterView is an abstract class, onNothingSelected must be defined
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                userType = UserEntry.TYPE_USER;
//            }
//        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}