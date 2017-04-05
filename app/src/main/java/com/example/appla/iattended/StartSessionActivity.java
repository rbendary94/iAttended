package com.example.appla.iattended;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by Yasser on 28/3/17.
 */

public class StartSessionActivity  extends AppCompatActivity {

    EditText courseName, roomNr,tutorialNr;
    String str_courseName, str_roomNr,str_tutorialNr;
    Button startSession;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startsession);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://iattended-bd60c.firebaseio.com/");

        courseName = (EditText)  findViewById(R.id.txt_ta_courseName);
        roomNr = (EditText)  findViewById(R.id.txt_ta_roomNr);
        tutorialNr = (EditText)  findViewById(R.id.txt_ta_tutorialNr);
        startSession = (Button) findViewById(R.id.bt_startsession);
        startSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------generate strings------------
                str_courseName = courseName.getText().toString();
                str_roomNr = roomNr.getText().toString();
                str_tutorialNr = tutorialNr.getText().toString();

                //------------conditions 3alehom------------

                //PASSWORD CONDITIONS
                if(!str_courseName.equals("") && !str_roomNr.equals("") && !str_tutorialNr.equals("")){
                    //insert to db
                    Firebase newRef = ref.child("Sessions").push();
                    Session session = new Session(str_courseName, str_roomNr,str_tutorialNr);
                    newRef.setValue(session);
                    Toast.makeText(StartSessionActivity.this,
                            "Session " +session.str_courseName + " started!", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(StartSessionActivity.this, TaActivity.class);
                    myIntent.putExtra("SessionName", session.str_courseName);
                    myIntent.putExtra("SessionStartTime", session.startTime);
                    myIntent.putExtra("SessionRoomNr", session.str_roomNr);
                    myIntent.putExtra("SessionTutorialNr", session.str_tutorialNr);

                    StartSessionActivity.this.startActivity(myIntent);

                }else{
                    Toast.makeText(StartSessionActivity.this,
                            "Please make sure all fields are filled!", Toast.LENGTH_LONG).show();
                }
            }
        });
        //call service that is continuously detecting beacons around
        //Once it detects 4 beacons with same UUID
        //           it get from Front End the course name, room number and tutorial number and UUID
        //           start counter/timestamp
        //           insert in db
        //On End button click: insert total duration or timestamp -> sessionmarked as ended.



    }
}
