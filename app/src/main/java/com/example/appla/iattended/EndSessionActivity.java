package com.example.appla.iattended;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Yasser on 28/3/17.
 */

public class EndSessionActivity extends AppCompatActivity {

    Firebase ref;
    TextView courseName, startTime, endTime, room, tutorialNr;
    String str_courseName, str_startTime,str_endTime, str_room, str_tutorialNr;
    Session s;
    Button redirectToStartSession;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endsession);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://iattended-bd60c.firebaseio.com/");
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
        s.endTime = DateFormat.getDateTimeInstance().format(new Date());
        Firebase newRef2 = ref.child("FinishedSessions").push();
        newRef2.setValue(s);


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
