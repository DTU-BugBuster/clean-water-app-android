package com.example.fourandahalfmen.m4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fourandahalfmen.m4.data.LoginDataBaseAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

//    private AutoCompleteTextView mEmailView;
//    private EditText mPasswordView;
//    private Button mEmailSignInButton, mCancel;
//    private LoginDataBaseAdapter loginDataBaseAdapter;

    /* instance variables */
    private EditText username;
    private EditText password;

    /* database instance */
    private GoogleApiClient client;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDatabase = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });

//        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
//        loginDataBaseAdapter=loginDataBaseAdapter.open();
//
//        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mCancel = (Button) findViewById(R.id.cancel_button);
//
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//            }
//        });
//        mCancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cancel();
//            }
//        });
    }

    /**
     * If cancel button is clicked, goes back to welcome screen.
     */
    public void onLoginClick(View v) {
        attemptLogin();
    }

    /**
     * If cancel button is clicked, goes back to welcome screen.
     */
    public void onCancelClick(View v) {
//        mEmailView.setError(null);
//        mPasswordView.setError(null);
        Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(i);
    }

    /**
     * method to attempt a login by getting username and password
     */
    private void attemptLogin() {
//        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);
//        boolean cancel = false;

        // Store values at the time of the login attempt.
        String insertUsername = username.getText().toString();
        String insertPassword = password.getText().toString();
        String storedPassword = userDatabase.child(insertUsername).child("password");
//        String storedPassword= loginDataBaseAdapter.getSingleEntry(email);
        if(insertPassword.equals(storedPassword)) {
            Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
            i.putExtra("username", insertUsername);
//            AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.email);
//            EditText textView2 = (EditText) findViewById(R.id.password);
//            String getrec = textView.getText().toString();
//            String getrec2 = textView2.getText().toString();
//            Bundle bundle = new Bundle();
//            bundle.putString("stuff", getrec);
//            bundle.putString("stuff2", getrec2);
//            i.putExtras(bundle);
            startActivity(i);
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        // Close The Database
////        loginDataBaseAdapter.close();
//    }
}

