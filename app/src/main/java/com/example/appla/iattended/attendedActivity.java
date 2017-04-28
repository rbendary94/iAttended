package com.example.appla.iattended;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class attendedActivity extends AppCompatActivity {

    String roomNr,courseName,startTime,tutorialNr,endTime;
    TextView tv_roomNr,tv_courseName,tv_attendance,tv_tutorialNr;
    Boolean attended;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended);
        final Bundle bundle = getIntent().getExtras();
        roomNr = bundle.getString("IntendedRoom");
        courseName = bundle.getString("CourseName");
        startTime = bundle.getString("StartTime");
        tutorialNr = bundle.getString("TutorialNr");
        attended = bundle.getBoolean("Attended");

        tv_attendance = (TextView) findViewById(R.id.tv_attendance);
        tv_roomNr = (TextView) findViewById(R.id.tv_roomNr);
        tv_courseName = (TextView) findViewById(R.id.tv_courseName);
        tv_tutorialNr = (TextView) findViewById(R.id.tv_tutorialNr);
        done = (Button) findViewById(R.id.btn_attendedDone);

        tv_tutorialNr.setText(tutorialNr);
        tv_courseName.setText(courseName);
        tv_roomNr.setText(roomNr);
        if (attended){
            tv_attendance.setText("Attended");
        }else{
            tv_attendance.setText("No attendance");
        }


        DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://iattended-bd60c.firebaseio.com/");
        DatabaseReference dbref2 =  dbref.child("Attendance").push();

        dbref2.setValue(new Attendance(new User(bundle.getString("User"),null,null,null),attended, new Session(courseName, roomNr,tutorialNr)), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Intent myIntent = new Intent(attendedActivity.this, StudentStartActivity.class);
                        myIntent.putExtra("User",bundle.getString("User"));
                        attendedActivity.this.startActivity(myIntent);

                    }
                });
            }
        });

    }
}
