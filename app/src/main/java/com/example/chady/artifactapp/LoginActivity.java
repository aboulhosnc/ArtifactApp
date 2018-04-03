package com.example.chady.artifactapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Chady on 3/24/2018.
 * Edited by Michael on 3/24/2018.
 *
 */



public class LoginActivity extends Activity {


    private EditText loginEmail;
    private EditText loginPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference  mDatabase;
    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


    }

    public void onUserLoginClick(View v){


        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Make sure to write the Email!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Make sure to write the Password!", Toast.LENGTH_SHORT).show();

        }
        else {
            progressDialog.setMessage("Logging In...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkUserExists();
                        Intent loginIntent = new Intent(LoginActivity.this, HomeScreen.class);
                        startActivity(loginIntent);
                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();

                    }


                }
            });
        }


    }

    public void testCLick () {
        Intent i = new Intent(LoginActivity.this, HomeScreen.class);
        startActivity(i);
    }

    public void checkUserExists() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //public boolean validateLogin();

}


