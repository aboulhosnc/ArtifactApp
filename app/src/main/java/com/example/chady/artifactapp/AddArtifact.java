package com.example.chady.artifactapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Date;

public class AddArtifact extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 2;
    private Uri uri = null; // store image value
    private Uri mImageUri = null; //cropped image
    private ImageButton imageButton; // create imagebutton
    private EditText editArtifactName;
    private EditText editDesc;
    private EditText editPrice;
    private EditText editLocation;

    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;


    //for editing artifacts
    private String post_key = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_artifact);
        editArtifactName =(EditText) findViewById(R.id.editArtifactName);
        editDesc = (EditText) findViewById(R.id.editDesc);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editLocation = (EditText) findViewById(R.id.editLocation);
        // add reference for firebase initialiance storage reference
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getInstance().getReference().child("Artifacts");
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        Bundle bundle = getIntent().getExtras();

        //when editing an artifact from the view page




        //initate mauth database and firebaseuser
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        // where the name field is puled from
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrentUser.getUid());

        // drop down list for Tool Types
        spinner = (Spinner)findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this,R.array.tool_types,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String toolValue = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(),toolValue+ " selected",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void imageButtonClicked (View view)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERY_REQUEST);
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            uri = data.getData();
            imageButton.setBackgroundResource(0);

            imageButton.setImageURI(uri); //replaces stock image with image of choice



        }
    }
    */
        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

             uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {
            imageButton.setBackgroundResource(0);
                 mImageUri = result.getUri();
                imageButton.setImageURI(mImageUri);
            }
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void addArtifactButtonClicked (View v){

        final String titleValue = editArtifactName.getText().toString().trim();
        final String descValue = editDesc.getText().toString().trim();
        final String costValue = editPrice.getText().toString().trim();
        final String locationValue = editLocation.getText().toString().trim();
        final Long timestamp = Long.MAX_VALUE - new Date().getTime();

        final String toolValue =  spinner.getSelectedItem().toString();


        if(TextUtils.isEmpty(titleValue)) {
            Toast.makeText(AddArtifact.this,"Artifact Name required",Toast.LENGTH_LONG).show();
            return;
        }

        if( !imageButton.isPressed())
        {
            Toast.makeText(AddArtifact.this,"Image required",Toast.LENGTH_LONG).show();
            return;
        }



        if(!TextUtils.isEmpty(titleValue)  ){
            StorageReference filePath = storageReference.child("PostImage").child(mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // use final so newpost will show up in evenlistener
                    final DatabaseReference newPost = databaseReference.push();
                    final Uri downloadurl = taskSnapshot.getDownloadUrl();

                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(TextUtils.isEmpty(descValue)) {
                                newPost.child("description").setValue("Description not added");
                            }
                            else
                            {
                                newPost.child("description").setValue(descValue);
                            }


                            if(TextUtils.isEmpty(costValue)) {

                                newPost.child("price").setValue(0);
                            }
                            else
                            {
                                Long priceValue =  Long.parseLong(costValue);
                                newPost.child("price").setValue(priceValue);
                            }
                            if( TextUtils.isEmpty(locationValue)){
                                newPost.child("location").setValue("Location not added");
                            }
                            else {
                                newPost.child("location").setValue(locationValue);
                            }



                            newPost.child("title").setValue(titleValue);
                            newPost.child("image").setValue(downloadurl.toString());

                            newPost.child("toolType").setValue(toolValue);
                            newPost.child("uid").setValue(mCurrentUser.getUid());
                            newPost.child("timestamp").setValue(timestamp);
                            newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddArtifact.this,"Upload Complete",Toast.LENGTH_LONG).show();
                                        clearScreen();
                                        Intent i = new Intent(AddArtifact.this, ArtifactsMainPage.class);


                                        startActivity(i);

                                    }

                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });
        }

    }
    public void cancelButtonClicked(View view) {
        clearScreen();

    }
    public void clearScreen () {
        editArtifactName.getText().clear();
        editDesc.getText().clear();
        editPrice.getText().clear();
        editLocation.getText().clear();
        imageButton.setImageResource(R.drawable.image_download_icon);
    }

}