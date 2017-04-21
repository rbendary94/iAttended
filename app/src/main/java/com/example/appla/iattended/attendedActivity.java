package com.example.appla.iattended;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class attendedActivity extends AppCompatActivity {

    String roomNr,courseName,startTime,tutorialNr,endTime;
    TextView tv_roomNr,tv_courseName,tv_startTime,tv_tutorialNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended);
        Firebase.setAndroidContext(this);
        Bundle bundle = getIntent().getExtras();
        roomNr = bundle.getString("IntendedRoom");
        courseName = bundle.getString("CourseName");
        startTime = bundle.getString("StartTime");
        tutorialNr = bundle.getString("TutorialNr");

        Firebase ref = new Firebase("https://iattended-bd60c.firebaseio.com/");
        final Firebase newRef = ref.child("FinishedSessions");
        Query queryRef = newRef.orderByChild("str_roomNr").equalTo(roomNr);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                    Toast.makeText(StudentActivity.this,
//                            "" + dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();

                if(dataSnapshot.getValue() !=null){
                    String temp = dataSnapshot.getValue().toString();
                    Log.d("Rana4",temp);
                    courseName= temp.substring(temp.indexOf("str_courseName=")+15,temp.indexOf(", str_roomNr"));
                    tutorialNr= temp.substring(temp.indexOf("str_tutorialNr=")+15,temp.length()-2);
                    startTime= temp.substring(temp.indexOf("startTime=")+10,temp.indexOf(", str_courseName"));
                    endTime=temp.substring(temp.indexOf("endTime=")+8,temp.length()-1);
                    Log.d("Rana4",courseName);
                    Log.d("Rana4",startTime);
                    Log.d("Rana4",tutorialNr);
                    Log.d("Rana4",endTime);
                    //Session fetched

                    //Calculate duration and insert in Attendance Table f FireBase.


                }else{
                    Toast.makeText(attendedActivity.this, "Failed" , Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
