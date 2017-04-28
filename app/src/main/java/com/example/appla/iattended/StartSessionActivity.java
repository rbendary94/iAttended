package com.example.appla.iattended;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Yasser on 28/3/17.
 */

public class StartSessionActivity  extends AppCompatActivity {

    EditText courseName, roomNr,tutorialNr;
    String str_courseName, str_roomNr,str_tutorialNr;
    Button startSession;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://iattended-bd60c.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startsession);

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

                //------------conditions ------------

                //PASSWORD CONDITIONS
                if(!str_courseName.equals("") && !str_roomNr.equals("") && !str_tutorialNr.equals("")){
                    //insert to db
                    DatabaseReference dbref2 =  dbref.child("Sessions").push();
                    Session session = new Session(str_courseName, str_roomNr,str_tutorialNr);

                    dbref2.setValue(session, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");
                            }
                        }
                    });

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
    }
}
