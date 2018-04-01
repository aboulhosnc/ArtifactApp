package com.example.chady.artifactapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by michaelpoblacion1 on 3/24/18.
import android.os.Bundle;

/**
 * Created by Chady on 3/25/2018.
 */

public class ArtifactsPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artifacts);
    }

    public void homeButtonClicked(View view) {
        Intent i = new Intent(ArtifactsPage.this, HomeScreen.class);
        startActivity(i);
      //This added?
        setContentView(R.layout.artifactspage);
    }
}
