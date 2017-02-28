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


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton, mCancel;
    private LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mCancel = (Button) findViewById(R.id.cancel_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void cancel() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(i);
    }

    /**
     * method to attempt a login by getting email and password
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        boolean cancel = false;

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String storedPassword= loginDataBaseAdapter.getSingleEntry(email);
        if(password.equals(storedPassword)) {
            Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
            AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.email);
            EditText textView2 = (EditText) findViewById(R.id.password);
            String getrec = textView.getText().toString();
            String getrec2 = textView2.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("stuff", getrec);
            bundle.putString("stuff2", getrec2);
            i.putExtras(bundle);
            startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}

