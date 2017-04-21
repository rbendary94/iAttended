package com.example.appla.iattended;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

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
        et_intendedRoomNr = (EditText) findViewById(R.id.et_IntendedRoom);
        btn_confirm = (Button) findViewById(R.id.btn_studentStart);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intendedRoom = et_intendedRoomNr.getText().toString();
                //fireball check

            Firebase ref = new Firebase("https://iattended-bd60c.firebaseio.com/");
            final Firebase newRef = ref.child("Sessions");
            Query queryRef = newRef.orderByChild("str_roomNr").equalTo(intendedRoom);

            queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Toast.makeText(StudentActivity.this,
//                            "" + dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();

                    if(dataSnapshot.getValue() !=null){
                        String temp = dataSnapshot.getValue().toString();
                        courseName= temp.substring(temp.indexOf("str_courseName=")+15,temp.indexOf(", str_roomNr"));
                        tutorialNr= temp.substring(temp.indexOf("str_tutorialNr=")+15,temp.length()-2);
                        startTime= temp.substring(temp.indexOf("startTime=")+10,temp.indexOf(", str_courseName"));
                        Log.d("rana2",courseName);
                        Log.d("rana2",startTime);
                        Log.d("rana2",tutorialNr);
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
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            }
        });
    }
}