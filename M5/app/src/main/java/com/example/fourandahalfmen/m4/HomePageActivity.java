package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.LoginDataBaseAdapter;
import com.example.fourandahalfmen.m4.data.Users;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class HomePageActivity extends Activity implements OnClickListener {

    private EditText emailField;
    private EditText passwordField;
    private Spinner userSpinner;
    private boolean editing;
    private String[] userTypes = {"User", "Worker", "Manager", "Admin"};
    private String userType;
    LoginDataBaseAdapter loginDataBaseAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Db
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("stuff");
        Button mBtn = (Button) findViewById(R.id.Save);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPressed(v);
            }
        });
        Button mBtn2 = (Button) findViewById(R.id.logout_button);
        mBtn2.setOnClickListener(this);

        emailField = (EditText) findViewById(R.id.editText2);
        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        passwordField = (EditText) findViewById(R.id.editText);

        String storedPassword = loginDataBaseAdapter.getSingleEntry(stuff);
        userType = loginDataBaseAdapter.getSingleEntry2(stuff);
        passwordField.setText(storedPassword, TextView.BufferType.EDITABLE);
        emailField.setText(stuff, TextView.BufferType.EDITABLE);
        setupSpinner(stuff);
        userType = loginDataBaseAdapter.getSingleEntry2(stuff);

    }

    /**
     * Button handler for the add new student button
     *
     * @param view the button
     */
    protected void onAddPressed(View view) {
        String email1 = emailField.getText().toString();
        String password1 = passwordField.getText().toString();
        String userType = (String) userSpinner.getSelectedItem();
        loginDataBaseAdapter.updateEntry(email1, password1, userType);
        finish();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     *
     * @param stuff info from login page to user page sent from bundle
     */
    private void setupSpinner(String stuff) {

        /* Setup spinners and adapter */
        ArrayAdapter userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);
        userType = loginDataBaseAdapter.getSingleEntry2(stuff);
        int spinnerPosition = userAdapter.getPosition(userType);
        userSpinner.setSelection(spinnerPosition);
        // Set the integer mSelected to the constant values
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.user))) {
                        userType = Users.UserEntry.TYPE_USER;
                    } else if (selection.equals(getString(R.string.worker))) {
                        userType = Users.UserEntry.TYPE_WORKER;
                    } else if (selection.equals(getString(R.string.manager))) {
                        userType = Users.UserEntry.TYPE_MANAGER;
                    } else {
                        userType = Users.UserEntry.TYPE_MANAGER;
                    }
                }
            }


            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userType = Users.UserEntry.TYPE_USER;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.i("clicks", "You Clicked B1");
        Intent i = new Intent(HomePageActivity.this, WelcomeActivity.class);
        startActivity(i);
    }
}


