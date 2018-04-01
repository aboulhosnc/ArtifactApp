package com.example.chady.artifactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onLoginClick (View v)
    {
        if(v.getId() == R.id.BLogin)
        {
            Intent i = new Intent(MainActivity.this, Login_Page.class);
            startActivity(i);

        }
    }

    public void onSignUpClick (View v)
    {
        if(v.getId() == R.id.BSignUp)
        {
            Intent i = new Intent(MainActivity.this, Signup.class);
            startActivity(i);

        }
    }


}
