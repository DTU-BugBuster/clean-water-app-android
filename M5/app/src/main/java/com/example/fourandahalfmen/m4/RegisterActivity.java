package com.example.fourandahalfmen.m4;
import com.example.fourandahalfmen.m4.data.LoginDataBaseAdapter;
import com.example.fourandahalfmen.m4.data.Users.UserEntry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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


    private Spinner userSpinner;
    private String[] userTypes = {"User", "Worker", "Manager", "Admin"};
    private EditText email;
    private EditText password;
    private String userType = UserEntry.TYPE_USER;
    LoginDataBaseAdapter loginDataBaseAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);

        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        setupSpinner();

        Button mBtn1 = (Button) findViewById(R.id.createUser);

        //Db
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        if (addUser()) {
            Log.i("clicks", "You Clicked B1");
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            // Username or password are empty, display and an error
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

            dlgAlert.setMessage("Either Email or password fields are empty");
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
        String insertEmail = email.getText().toString();
        String insertPassword = password.getText().toString();

        if (insertEmail == null || insertPassword == null) {
            return false;
        } else {
            loginDataBaseAdapter.insertEntry(insertEmail, insertPassword, userType);
        }
        return true;
    }


    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
    */
    private void setupSpinner() {

        /* Setup spinners and adapter */
        ArrayAdapter<String> userAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        // Set the integer mSelected to the constant values
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.user))) {
                        userType = UserEntry.TYPE_USER;
                    } else if (selection.equals(getString(R.string.worker))) {
                        userType = UserEntry.TYPE_WORKER;
                    } else if (selection.equals(getString(R.string.manager))) {
                        userType = UserEntry.TYPE_MANAGER;
                    } else {
                        userType = UserEntry.TYPE_MANAGER;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userType = UserEntry.TYPE_USER;
            }
        });
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