package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomePageActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Button mBtn2 = (Button) findViewById(R.id.logout_button);
        mBtn2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Log.i("clicks","You Clicked B1");
        Intent i=new Intent(HomePageActivity.this, WelcomeActivity.class);
        startActivity(i);
    }
}