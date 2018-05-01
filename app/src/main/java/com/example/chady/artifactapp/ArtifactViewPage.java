package com.example.chady.artifactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ArtifactViewPage extends AppCompatActivity {

    private String post_key = null;
    private DatabaseReference mDatabase;

    // initialize items on screen
    private ImageView singlePostArtifactImage;
    private TextView singlePostArtifactName;
    private TextView singlePostArtifactDescription;
    private TextView singlePostArtifactLocation;
    private TextView singlePostArtifactPrice;
    private TextView singlePostArtifactToolType;
    private TextView singlePostArtifactUsername;

    //test for postkey
    private TextView singlePostKey;

    private Button deleteButton;
    private FirebaseAuth mAuth;
    private Button editButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_view_page);


         post_key = getIntent().getExtras().getString("PostId");
         mDatabase = FirebaseDatabase.getInstance().getReference().child("Artifacts");

        singlePostArtifactImage = (ImageView)findViewById(R.id.singleImageView);
        singlePostArtifactName = (TextView) findViewById(R.id.singleArtifactName);
        singlePostArtifactDescription = (TextView) findViewById(R.id.singleArtifactDescription);
        singlePostArtifactLocation = (TextView) findViewById(R.id.singleArtifactLocation);
        singlePostArtifactPrice = (TextView) findViewById(R.id.singleArtifactPrice);
        singlePostArtifactToolType = (TextView) findViewById(R.id.singleArtifactToolType);
        singlePostArtifactUsername = (TextView) findViewById(R.id.singleArtifactUploadUser);

        //test for postkey
        //singlePostKey = (TextView) findViewById(R.id.singleArtifactPostKey);

        mAuth = FirebaseAuth.getInstance();
        deleteButton =(Button) findViewById(R.id.singleArtifactDelete);
        deleteButton.setVisibility(View.INVISIBLE);
        editButton = (Button) findViewById(R.id.singleArtifactEdit);
        editButton.setVisibility(View.INVISIBLE);



        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_name = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("description").getValue();
                String post_location = (String) dataSnapshot.child("location").getValue();
                Long post_price = (Long) dataSnapshot.child("price").getValue();
                String post_tooltype = (String) dataSnapshot.child("toolType").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();
                String post_username = (String) dataSnapshot.child("username").getValue();



                singlePostArtifactName.setText(post_name);
                singlePostArtifactDescription.setText(post_desc);
                singlePostArtifactLocation.setText(post_location);
                singlePostArtifactPrice.setText(String.valueOf(post_price));
                singlePostArtifactToolType.setText(post_tooltype);
                singlePostArtifactUsername.setText(post_username);


                //Test for postkey
               // singlePostKey.setText(post_key);

                // get image from  database
                Picasso.get().load(post_image).into(singlePostArtifactImage);

                // only show buttons visible if user created post

                if(mAuth.getCurrentUser().getUid().equals(post_uid)){
                    editButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public void deleteButtonClicked (View view) {
        mDatabase.child(post_key).removeValue();
        Toast.makeText(ArtifactViewPage.this,"Artifact Deleted",Toast.LENGTH_LONG).show();

        Intent mainActivityIntent = new Intent(ArtifactViewPage.this, ArtifactsMainPage.class);
        startActivity(mainActivityIntent);

    }
    public void editArtifactClicked (View view) {
        //check if edit button was clicked
        boolean editClick = true;

        Intent editArtifact = new Intent(ArtifactViewPage.this, EditArtifact.class);

        editArtifact.putExtra("PostId",post_key );
        editArtifact.putExtra("editIntent",editClick);
        startActivity(editArtifact);
    }


}
