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
import android.widget.TextView;

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
    private TextView signupLink;


    private String fromKey;
    private String account_type;
    private Long attempts;
    private String email;
    private String locked;
    private String password1;
    private String username1;
    private String street_address;
    private String city;
    private String state;
    private Long zip_code;
    private String userAdapter;
    private Button delete;
    private Button unban;
    int counter = 0;


    /* database instance */
    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase2 = database2.getReference("users");



    /* database instance */
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("loginReports");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

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
                    counter = 0;
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

        signupLink = (TextView) findViewById(R.id.link_signup);

        /**
         * If cancel button is clicked, goes back to welcome screen.
         */
        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            /**
             * get data from firebase based on user-specific id, username and password
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users post = dataSnapshot.getValue(Users.class);
                account_type = post.account_type.toString();
                attempts = Long.valueOf(post.attempts);
                city = post.city.toString();
                email = post.email.toString();
                locked = String.valueOf(post.locked);
                password1 = post.password.toString();
                username1 = post.username.toString();
                userAdapter = post.account_type.toString();
                street_address = post.street_address.toString();
                state = post.state.toString();
                zip_code = Long.valueOf(post.zip_code);
                if (Long.valueOf(attempts) >= 3) {
                    alertMessage("Account is Banned", "Too many log in attempts.");
                } else {
                    // if could not find key in users database
                    if (dataSnapshot.getValue() == null) {
                        alertMessage("Incorrect Username", "Couldn't find account. Please try again.");
                    } else {
                        // check password in database and if equal to input, then send to Homepageactivity
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
                                Log.d("Sup", "here0");
                                Intent i = new Intent(LoginActivity.this, HomePageActivity_Admin.class);
                                i.putExtra("username", insertUsername);
                                startActivity(i);
                            } else {

                                Log.d("Login Error", "There was a problem with the user type login attempt.");
                            }

                            // wrong password
                        } else {
                            if (counter < 1) {
                                Long attempts1 = Long.valueOf(attempts) + 1;
                                Users user = new Users(username1, password1, account_type,
                                        email, street_address, city, state,
                                        Long.valueOf(zip_code), Long.valueOf(attempts1), false);
                                mDatabase2.child(username.getText().toString()).setValue(user);
                                alertMessage("Incorrect Password", "Couldn't find account. Please try again.");
                            }
                            counter = 1;
                        }
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

