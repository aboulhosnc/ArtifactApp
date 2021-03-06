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
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ArtifactsMainPage extends AppCompatActivity {

    private RecyclerView mArtifactList;
    private DatabaseReference mDatabase;
    private Query mQuery;
    //private DatabaseReference mDatabaseOrdered;
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
        // finds reference in database called Artifacts
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Artifacts");
        //sorted by default to newest
        mQuery = mDatabase.orderByChild("timestamp");
       // mDatabaseOrdered = mDatabase;
        mAuth = FirebaseAuth.getInstance();

        Bundle bundle= getIntent().getExtras();

        if(bundle != null) {
            int sortKeyTest = bundle.getInt("sortKey");

            // sort by type
            if(sortKeyTest == 1){
                //mDatabase = FirebaseDatabase.getInstance().getReference().child("Artifacts").orderByChild("timestamp");
                mQuery = mDatabase.orderByChild("toolType");


            }
            //sort by oldest
            else if(sortKeyTest == 4){
                //mDatabase = FirebaseDatabase.getInstance().getReference().child("Artifacts").orderByChild("toolType");
                mQuery = mDatabase;

            }
            else if(sortKeyTest == 3) {
                mQuery = mDatabase.orderByChild("price");
            }
            else if(sortKeyTest == 2) {
                mQuery = mDatabase.orderByChild("timestamp");
            }


        }
        //mDatabase.orderByChild("toolType");




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

                mQuery
        ) {
            @Override
            protected void populateViewHolder(ArtifactViewHolder viewHolder, Artifact model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setToolType(model.getToolType());

                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUserName(model.getUsername());

                // position of the post in the recyler view
                final String post_key = getRef(position).getKey().toString();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent artifactView = new Intent(ArtifactsMainPage.this, ArtifactViewPage.class);
                        artifactView.putExtra("PostId",post_key);
                        startActivity(artifactView);


                    }
                });

            }
        };
        mArtifactList.setAdapter(FBRA);
    }
    ///Need this
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public ArtifactViewHolder(View itemView) {
            super(itemView);
             mView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView) mView.findViewById(R.id.textTitle);
            post_title.setText(title);
        }
        public void setPrice(Long price){
            TextView post_price = (TextView) mView.findViewById(R.id.artifactPrice);
            post_price.setText(String.valueOf(price));
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.get().load(image).into(post_image);
        }

        public void setToolType(String toolType){
            TextView post_title = (TextView) mView.findViewById(R.id.textToolType);
            post_title.setText(toolType);
        }
        public void setUserName(String userName) {
            TextView post_username = (TextView) mView.findViewById(R.id.textUsername);
            post_username.setText(userName);
        }

    }
    //Need this
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
            String postkey = null;
            Intent intent = new Intent(ArtifactsMainPage.this, AddArtifact.class);
            intent.putExtra("postkey",postkey);
            startActivity(intent);
        }
        else if(id == R.id.logout)
        {
            mAuth.signOut();
        }
        else if(id ==R.id.sort_Type)
        {
            //boolean sortKey = true;
            //sortType();

            Intent sortRefreshIntent = new Intent(ArtifactsMainPage.this, ArtifactsMainPage.class);
            this.finish();
            sortRefreshIntent.putExtra("sortKey",1);
            startActivity(sortRefreshIntent);

        }
        else if(id == R.id.sort_New)
        {

            Intent sortRefreshIntent = new Intent(ArtifactsMainPage.this, ArtifactsMainPage.class);
            finish();
            //sortRefreshIntent.putExtra("sortKey",2);
            startActivity(sortRefreshIntent);

        }
        else if(id == R.id.sort_Last)
        {

            Intent sortRefreshIntent = new Intent(ArtifactsMainPage.this, ArtifactsMainPage.class);
            finish();
            sortRefreshIntent.putExtra("sortKey",4);
            startActivity(sortRefreshIntent);

        }
        else if(id == R.id.sort_Price)
        {

            Intent sortRefreshIntent = new Intent(ArtifactsMainPage.this, ArtifactsMainPage.class);
            finish();
            sortRefreshIntent.putExtra("sortKey",3);
            startActivity(sortRefreshIntent);

        }

        else if( id == R.id.Homescreen)
        {
            Intent homescreenIntent = new Intent(ArtifactsMainPage.this , HomeScreen.class);
            startActivity(homescreenIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    public void artifactPageClicked (View view) {
        Intent artifactView = new Intent(ArtifactsMainPage.this, ArtifactViewPage.class);
        startActivity(artifactView);
    }
    */
    public void sortType() {
        //final String user_id = mAuth.getCurrentUser().getUid();

          mDatabase.orderByChild("toolType");



    }


}
