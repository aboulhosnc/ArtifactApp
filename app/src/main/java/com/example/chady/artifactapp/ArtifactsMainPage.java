package com.example.chady.artifactapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ArtifactsMainPage extends AppCompatActivity {

    private RecyclerView mArtifactList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifacts_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mArtifactList = (RecyclerView) findViewById(R.id.artifact_list);
        mArtifactList.setHasFixedSize(true);
        mArtifactList.setLayoutManager(new LinearLayoutManager(this));
        // finds reference in database called InstaApp
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Artifacts");
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            // if no user is logged in take them to the log in screen
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent loginIntent = new Intent(ArtifactsMainPage.this, MainActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }

            }
        };




    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);



        FirebaseRecyclerAdapter<Artifact,ArtifactViewHolder> FBRA = new FirebaseRecyclerAdapter<Artifact, ArtifactViewHolder>(
                Artifact.class,
                R.layout.artifact_row,
                ArtifactViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(ArtifactViewHolder viewHolder, Artifact model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setToolType(model.getToolType());

                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());

            }
        };
        mArtifactList.setAdapter(FBRA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        public ArtifactViewHolder(View itemView) {
            super(itemView);
            View mView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView) itemView.findViewById(R.id.textTitle);
            post_title.setText(title);
        }
        public void setPrice(int price){
            TextView post_price = (TextView) itemView.findViewById(R.id.artifactPrice);
            post_price.setText(String.valueOf(price));
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image);
            Picasso.get().load(image).into(post_image);
        }

        public void setToolType(String toolType){
            TextView post_title = (TextView) itemView.findViewById(R.id.textToolType);
            post_title.setText(toolType);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.addIcon)
        {
            Intent intent = new Intent(ArtifactsMainPage.this, AddArtifact.class);
            startActivity(intent);
        }
        else if(id == R.id.logout)
        {
            mAuth.signOut();
        }

        return super.onOptionsItemSelected(item);
    }

}
