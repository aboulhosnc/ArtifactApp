package com.example.chady.artifactapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private FirebaseDatabase mData;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference ref;

    EventListAdapter adapter;
    TextView textView;
    private ListView listView;
    private ArrayList<String> array;
    private ArrayList<Events> arrayEvent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        array = new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        adapter = new EventListAdapter(this,R.layout.customlayout,arrayEvent);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(CalendarActivity.this, MainActivity.class);
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }
    public void addCalendarEvent(View view){
        Intent i = new Intent(CalendarActivity.this, AddEvent.class);
        startActivity(i);
    }
    private void showData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            Events event = new Events();
            event.setName(ds.getValue(Events.class).getName());
            event.setLocation(ds.getValue(Events.class).getLocation());
            event.setTime(ds.getValue(Events.class).getTime());
            event.setDate(ds.getValue(Events.class).getDate());

            array.add(event.getName());
            arrayEvent.add(event);

        }
        listView.setAdapter(adapter);
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
        else if(id == R.id.homescreen_action)
        {
            Intent intent = new Intent(CalendarActivity.this, HomeScreen.class);
            startActivity(intent);
        }
        else if(id == R.id.logout)
        {
            mAuth.signOut();
        }

        return super.onOptionsItemSelected(item);
    }
}
