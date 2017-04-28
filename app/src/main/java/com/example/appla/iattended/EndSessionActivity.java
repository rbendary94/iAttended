package com.example.appla.iattended;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.firebase.client.Firebase;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yasser on 28/3/17.
 */

public class EndSessionActivity extends AppCompatActivity {

    TextView courseName, startTime, endTime, room, tutorialNr;
    String str_courseName, str_startTime,str_endTime, str_room, str_tutorialNr;
    Session s;
    Button redirectToStartSession;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endsession);
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://iattended-bd60c.firebaseio.com/");

        courseName = (TextView) findViewById(R.id.tv_endSession_courseName);
        startTime = (TextView) findViewById(R.id.tv_tv_endSession_startTime);//
        endTime = (TextView) findViewById(R.id.tv_tv_endSession_endTime);//
        tutorialNr= (TextView) findViewById(R.id.tv_endSession_tutorialNr);
        room = (TextView) findViewById(R.id.tv_tv_endSession_RoomNr);

        Bundle bundle = getIntent().getExtras();

        str_courseName = bundle.getString("SessionCourseName");
        str_room = bundle.getString("SessionRoomNr");
        str_tutorialNr = bundle.getString("SessionTutorialNr");
        str_startTime= bundle.getString("SessionStartTime");
        str_endTime =bundle.getString("SessionEndTime");

        courseName.setText(str_courseName);
        startTime.setText(str_startTime);
        endTime.setText(str_endTime);
        tutorialNr.setText(str_tutorialNr);
        room.setText(str_room);

        s = new Session(str_courseName,str_room, str_tutorialNr );
        s.startTime = str_startTime;
        s.endTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        DatabaseReference dbref2 =  dbref.child("FinishedSessions").push();

        dbref2.setValue(s, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });


        redirectToStartSession = (Button)  findViewById(R.id.bt_redirect_endsession);
        redirectToStartSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EndSessionActivity.this, StartSessionActivity.class);
                EndSessionActivity.this.startActivity(myIntent);
            }
        });
    }

}
