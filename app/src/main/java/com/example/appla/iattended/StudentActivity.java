package com.example.appla.iattended;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class StudentActivity extends Activity{

    MyReceiver myReceiver;
    BeaconService beaconService;
    static Timer T=new Timer();
    int seconds=0;
    int minutes=0;
    int hours =0;
    static Boolean timer=false;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Firebase.setAndroidContext(this);

        //call service that is continuosly detecting beacons around

        //Once it detects 4 beacons with same UUID
        //           it fetches from db the course name, room number and tutorial number
        //           start counter
        //As long as the same 4 beacons are detected increment the counter
        //On Done button click: check if session ended (through firebase db) then add the attendance for this student
        //                            else if session is not done: display warning.


        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (seconds==60){
                            seconds=0;
                            minutes++;
                        }
                        if(minutes==60){
                            minutes =0;
                            hours++;
                        }
//                        timeCounter.setText(hours+":"+minutes+":"+seconds);
                        if(timer)
                            seconds++;
                        Log.d("Rana2",seconds+"");
                    }
                });
            }
        }, 1000, 1000);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver = new MyReceiver();
        beaconService = new BeaconService();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BeaconService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service
        Intent intent = new Intent(StudentActivity.this,
                BeaconService.class);
        startService(intent);

        super.onStart();
    }

    private class MyReceiver extends BroadcastReceiver {

        double d1,d2,d3,d4;
        String RoomNr="";
        String str_courseName,str_TutorialNr,str_startTime;

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            if (arg1.getDoubleExtra("D1", 0)!=0){
                d1 = arg1.getDoubleExtra("D1", 0);
                Toast.makeText(StudentActivity.this,"D1= "+d1,
                    Toast.LENGTH_LONG).show();
            }
            if (arg1.getDoubleExtra("D2", 0)!=0){
                d2 = arg1.getDoubleExtra("D2", 0);
                Toast.makeText(StudentActivity.this,"D2= "+d2,
                        Toast.LENGTH_LONG).show();
            }
            if (arg1.getDoubleExtra("D3", 0)!=0){
                d3 = arg1.getDoubleExtra("D3", 0);
            }
            if (arg1.getDoubleExtra("D4", 0)!=0){
                d4 = arg1.getDoubleExtra("D4", 0);
            }
            Log.d("Rana",d1+" , "+d2+" , "+d3);
            double va = (Math.pow(d2, 2) - Math.pow(d3, 2) - (Math.pow(0, 2) - Math.pow(5, 2) ) - (Math.pow(5, 2) - Math.pow(0,2 )) ) /2;
            double vb = (Math.pow(d2, 2) - Math.pow(d1, 2) - (Math.pow(0, 2) - Math.pow(0, 2) ) - (Math.pow(5, 2) - Math.pow(0,2 )) ) /2;


            RoomNr=arg1.getStringExtra("RoomNr");
            if (RoomNr.equals("")){
                StudentActivity.T.cancel();
            }
            Log.d("Rana1", RoomNr);
            Firebase ref = new Firebase("https://iattended-bd60c.firebaseio.com/");
            final Firebase newRef = ref.child("Sessions");
            Query queryRef = newRef.orderByChild("str_roomNr").equalTo(RoomNr);

            queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Toast.makeText(StudentActivity.this,
//                            "" + dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();

                    if(dataSnapshot.getValue() !=null){
                        Log.d("Rana1",dataSnapshot.getValue().toString() );
                        String temp = dataSnapshot.getValue().toString();
                        str_courseName= temp.substring(temp.indexOf("str_courseName=")+15,temp.indexOf(", str_roomNr"));
                        str_TutorialNr= temp.substring(temp.indexOf("str_tutorialNr=")+15,temp.length()-2);
                        str_startTime= temp.substring(temp.indexOf("startTime=")+10,temp.indexOf(", str_courseName"));
                        Log.d("rana2",str_courseName);
                        Log.d("rana2",str_startTime);
                        Log.d("rana2",str_TutorialNr);
                        //Session fetched
                        String fetchedStartTime = dataSnapshot.getValue().toString();

                        //start Counter
                        StudentActivity.timer=true;

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

    }

}
