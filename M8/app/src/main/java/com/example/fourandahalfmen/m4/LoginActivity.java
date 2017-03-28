package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fourandahalfmen.m4.data.LoginReports;
import com.example.fourandahalfmen.m4.data.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /* instance variables */
    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button cancelButton;


    /* database instance */
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("loginReports");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.sign_in_button);

        /**
         * Login Button Listener
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Login Button Click Method
             * @param v view that button was clicked on
             */
            public void onClick(View v) {
                if (username.getText().toString() != "" && password.getText().toString() != "") {
                    attemptLogin();
                } else {
                    // If fields are empty, display an error.
                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(LoginActivity.this);
                    dialog2.setCancelable(false);
                    dialog2.setTitle("Invalid Login");
                    dialog2.setMessage("Please try again with both login and passord.");
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
        });

        cancelButton = (Button) findViewById(R.id.cancel_button);

        /**
         * If cancel button is clicked, goes back to welcome screen.
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View c) {
                username.setText("");
                password.setText("");
//                Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
//                startActivity(i);
                finish();
                onBackPressed();
            }
        });
    }

    /**
     * method to attempt a login by getting username and password
     */
    private void attemptLogin() {

        // Store values at the time of the login attempt.
        final String insertUsername = username.getText().toString();
        final String insertPassword = password.getText().toString();

        String reflocation = "users/" + insertUsername;
        DatabaseReference ref = database.getReference(reflocation);
        ref.addValueEventListener(new ValueEventListener() {

            /**
             * get data from firebase based on user-specific id, username and password
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // if could not find key in users database
                if (dataSnapshot.getValue() == null) {
                    alertMessage("Incorrect Username", "Couldn't find account. Please try again.");
                } else {
                    // check password in database and if equal to input, then send to Homepageactivity
                    Users post = dataSnapshot.getValue(Users.class);
//                    System.out.println(post);
                    if (insertPassword.equals(post.password)) {
                        // login reporting database
                        LoginReports lr = new LoginReports(true, insertUsername);
                        mDatabase.child(new Date().toString()).setValue(lr);

                        if (post.account_type.toString().equals("User")) {
                            Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                            i.putExtra("username", insertUsername);
                            startActivity(i);
                        }

                        if (post.account_type.toString().equals("Worker")) {
                            Intent i = new Intent(LoginActivity.this, HomePageActivity_Worker.class);
                            i.putExtra("username", insertUsername);
                            startActivity(i);
                        }

                        if (post.account_type.toString().equals("Manager")) {
                            Log.d("Sup", "here0");

                            Intent i = new Intent(LoginActivity.this, HomePageActivity_Manager.class);
                            i.putExtra("username", insertUsername);
                            startActivity(i);
                        }

                        if (post.account_type.toString().equals("Admin")) {
                            Intent i = new Intent(LoginActivity.this, HomePageActivity_Admin.class);
                            i.putExtra("username", insertUsername);
                            startActivity(i);
                        } else {
                            Log.d("Login Error", "There was a problem with the user type login attempt.");
                        }

                    // wrong password
                    } else {
                        alertMessage("Incorrect Password", "Couldn't find account. Please try again.");
                    }
                }
            }
            /**
             * necessary method required for firebase
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    /**
     * Alert Message general function for generating alerts
     * @param title title of message
     * @param body  body of message
     */
    private void alertMessage(String title, String body) {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(LoginActivity.this);
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

