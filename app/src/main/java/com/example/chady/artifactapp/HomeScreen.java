package com.example.chady.artifactapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by michaelpoblacion1 on 3/24/18.
import android.os.Bundle;

/**
 * Created by Chady on 3/25/2018.
 */

public class HomeScreen extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(HomeScreen.this, MainActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);
    }


    public void onArtifactsClick(View view) {
        Intent i = new Intent(HomeScreen.this, ArtifactsMainPage.class);
        startActivity(i);

    }

    public void onCollectorsClick(View view) {

        Intent i = new Intent(HomeScreen.this, Collectors.class);
        startActivity(i);
    }

    public void onProfileClick(View view) {
        System.out.println("Button Clicked");
    }

    public void onMapsClick(View view) {
        System.out.println("Button Clicked");
    }

    public void onChatClick(View view) {
        System.out.println("Button Clicked");
    }

    public void onCalendarClick(View view) {
        System.out.println("Button Clicked");
    }

    public void onNotificationsClick(View view) {
        System.out.println("Button Clicked");
    }

    public void onSettingsClick(View view) {
        System.out.println("Button Clicked");
    }

    public void onLogoutClick(View view) {
        mAuth.signOut();
        //startActivity(new Intent(HomeScreen.this, LoginActivity.class));

    }
}
