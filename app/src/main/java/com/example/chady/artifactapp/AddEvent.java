package com.example.chady.artifactapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    private static final String TAG = "AddEvent";

    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private EditText editEventName, editLocation, editTime;
    private Date d;
    private String date;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        displayDate = (TextView) findViewById(R.id.editDate);
        editEventName = (EditText) findViewById(R.id.editEventName);
        editLocation = (EditText)findViewById(R.id.editLocation);
        editTime = (EditText)findViewById(R.id.editTime);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("events");

        displayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month+1;

                date = month + "/" + day + "/" + year;
                displayDate.setText(date);
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Added Info to database: \n +" +
                        dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void submitEvent(View view){

        String eventName = editEventName.getText().toString().trim();
        String locationValue = editLocation.getText().toString().trim();
        String time = editTime.getText().toString().trim();

        if(TextUtils.isEmpty(date) || TextUtils.isEmpty(locationValue) || TextUtils.isEmpty(time) || TextUtils.isEmpty(eventName)){
            Toast.makeText(AddEvent.this, "Name, Date, Time and Location Required", Toast.LENGTH_SHORT).show();
        }
        else{
            Events newEvents = new Events();
            newEvents.setDate(date);
            newEvents.setName(eventName);
            newEvents.setTime(time);
            newEvents.setLocation(locationValue);

            databaseReference.push().setValue(newEvents);
            Intent i = new Intent(AddEvent.this, CalendarActivity.class);
            startActivity(i);
        }
    }
    public void cancelEvent(View view){
        Intent i = new Intent(AddEvent.this, CalendarActivity.class);
        startActivity(i);
    }
}
