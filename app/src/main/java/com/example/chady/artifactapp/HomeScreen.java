package com.example.chady.artifactapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        mAuth = FirebaseAuth.getInstance();

       /*
       Need listener?
       if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(HomeScreen.this, LoginActivity.class));
        }
        */
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
        startActivity(new Intent(HomeScreen.this, LoginActivity.class));
    }
}
