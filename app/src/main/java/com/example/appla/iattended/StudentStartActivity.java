package com.example.appla.iattended;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.start;

public class StudentStartActivity extends AppCompatActivity {

    EditText et_intendedRoomNr;
    Button btn_confirm;
    String intendedRoom,courseName,tutorialNr,startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_start);
        final Intent myIntent = new Intent(StudentStartActivity.this, StudentActivity.class);
        Bundle bundle = getIntent().getExtras();
        myIntent.putExtra("User",bundle.getString("User"));
        et_intendedRoomNr = (EditText) findViewById(R.id.et_IntendedRoom);
        btn_confirm = (Button) findViewById(R.id.btn_studentStart);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        intendedRoom = et_intendedRoomNr.getText().toString();

            DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://iattended-bd60c.firebaseio.com/");
            final DatabaseReference dbref2 =  dbref.child("Sessions");
            Query queryRef = dbref2.orderByChild("str_roomNr").equalTo(intendedRoom);

            queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.getValue() !=null){
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            courseName = (String) messageSnapshot.child("str_courseName").getValue();
                            tutorialNr = (String) messageSnapshot.child("str_tutorialNr").getValue();
                            startTime = (String) messageSnapshot.child("startTime").getValue();
                            break;
                        }

                        //Session fetched
                        myIntent.putExtra("IntendedRoom", intendedRoom);
                        myIntent.putExtra("CourseName", courseName);
                        myIntent.putExtra("TutorialNr", tutorialNr);
                        myIntent.putExtra("StartTime", startTime);
                        StudentStartActivity.this.startActivity(myIntent);

                    }else{
                        Toast.makeText(StudentStartActivity.this, "Theres currently no session in this room make sure the TA started the session and you are in the correct room!" , Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            }
        });
    }
}
