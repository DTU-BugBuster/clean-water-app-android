package com.example.fourandahalfmen.m4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.fourandahalfmen.m4.data.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.sign_in_button);

        /**
         * Login Button Listerner
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
                Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(i);
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
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Incorrect Username");
                    dialog.setMessage("Couldn't find account. Please try again." );
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    final AlertDialog alert = dialog.create();
                    alert.show();
                } else {
                    Users post = dataSnapshot.getValue(Users.class);
                    System.out.println(post);
                    if (insertPassword.equals(post.password)) {
                        Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                        i.putExtra("username", insertUsername);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(LoginActivity.this);
                        dialog2.setCancelable(false);
                        dialog2.setTitle("Incorrect Password");
                        dialog2.setMessage("Couldn't find account. Please try again.");
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

