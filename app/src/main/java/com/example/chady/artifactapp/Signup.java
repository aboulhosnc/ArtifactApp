package com.example.chady.artifactapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Chady on 3/24/2018.
 */

public class Signup extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private Button registerButton;
    private EditText ETName;
    private EditText ETEmail;
    private EditText ETPass;
    private EditText ETPassV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();
        registerButton = (Button) findViewById(R.id.BuserSignUp);
        ETName = (EditText) findViewById(R.id.TFname);
        ETEmail = (EditText) findViewById(R.id.TFemail);
        ETPass = (EditText) findViewById(R.id.TFpassword);
        ETPassV = (EditText) findViewById(R.id.TFconfirmPassword);
        progressDialog = new ProgressDialog(this);
        registerButton.setOnClickListener(this);
    }

    public void registerUser(){
        String name = ETName.getText().toString().trim();
        String email = ETEmail.getText().toString().trim();
        String password = ETPass.getText().toString().trim();
        String passwordV = ETPassV.getText().toString().trim();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ){
            Toast.makeText(this, "Please Fill In All Fields", Toast.LENGTH_LONG).show();
            return;
        }
        if(!TextUtils.equals(password, passwordV)) {
            Toast.makeText(this,"Please make sure passwords match",Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length() < 6 ){
            Toast.makeText(this,"Password must be 6 characters long",Toast.LENGTH_LONG).show();
            return;

        }


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Signup.this, LoginActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Signup.this, "Failed To Register!", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();

                        }
                    }
                });

    }
    public void goToMain(View view){
        Intent j = new Intent(Signup.this, MainActivity.class);
        startActivity(j);
    }

    @Override
    public void onClick(View view) {
        if(view == registerButton){
            registerUser();
        }
    }
}
