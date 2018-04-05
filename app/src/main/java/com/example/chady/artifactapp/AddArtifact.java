package com.example.chady.artifactapp;

import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddArtifact extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 2;
    private Uri uri = null; // store image value
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            uri = data.getData();
            imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setImageURI(uri); //replaces stock image with image of choice



        }
    }

    public void addArtifactButtonClicked (View v){

        final String titleValue = editArtifactName.getText().toString().trim();
        final String descValue = editDesc.getText().toString().trim();
        final String costValue = editPrice.getText().toString().trim();
        final String locationValue = editLocation.getText().toString().trim();
        final int priceValue =  Integer.parseInt(costValue);
        final String toolValue =  spinner.getSelectedItem().toString();


        if(!TextUtils.isEmpty(titleValue) && !TextUtils.isEmpty(descValue)){
            StorageReference filePath = storageReference.child("PostImage").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    DatabaseReference newPost = databaseReference.push();
                    Uri downloadurl = taskSnapshot.getDownloadUrl();


                    newPost.child("title").setValue(titleValue);
                    newPost.child("description").setValue(descValue);
                    newPost.child("image").setValue(downloadurl.toString());
                    newPost.child("price").setValue(priceValue);
                    newPost.child("toolType").setValue(toolValue);
                    newPost.child("location").setValue(locationValue);
                    Toast.makeText(AddArtifact.this,"Upload Complete",Toast.LENGTH_LONG).show();
                    clearScreen();



                    Intent i = new Intent(AddArtifact.this, ArtifactsMainPage.class);
                    startActivity(i);


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
    }

}
