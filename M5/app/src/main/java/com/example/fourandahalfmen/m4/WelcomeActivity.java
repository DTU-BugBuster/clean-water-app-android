package com.example.fourandahalfmen.m4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.content.Intent;


public class WelcomeActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button mBtn1 = (Button) findViewById(R.id.login_button);
        mBtn1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Log.i("clicks","You Clicked B1");
        Intent i=new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(i);
    }
}