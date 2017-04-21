package com.example.appla.iattended;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Timer;
import java.util.TimerTask;

public class StudentActivity extends Activity{

    MyReceiver myReceiver;
    BeaconService beaconService;
    static Timer T=new Timer();
    int seconds=0;
    int minutes=0;
    int hours =0;
    static Boolean timer=true;
    String intendedRoom = "";
    String courseName,startTime,tutorialNr;
    TextView tv_counter;
    Button btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Firebase.setAndroidContext(this);
        Bundle bundle = getIntent().getExtras();
        final Intent myIntent = new Intent(StudentActivity.this, attendedActivity.class);
        intendedRoom = bundle.getString("IntendedRoom");
        courseName = bundle.getString("CourseName");
        startTime = bundle.getString("StartTime");
        tutorialNr = bundle.getString("TutorialNr");
        Log.d("Rana",intendedRoom);
        tv_counter = (TextView) findViewById(R.id.tv_st_counter);
        tv_counter.setText("00:00:00");
        btn_done = (Button) findViewById(R.id.bt_student_endSession);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myIntent.putExtra("IntendedRoom", intendedRoom);
                myIntent.putExtra("CourseName", courseName);
                myIntent.putExtra("TutorialNr", tutorialNr);
                myIntent.putExtra("StartTime", startTime);
                StudentActivity.this.startActivity(myIntent);
            }
        });
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
                            if (myReceiver.str_major.equals(intendedRoom) && myReceiver.distance<3){
                                seconds++;
                            }
                        tv_counter.setText(hours+":"+minutes+":"+seconds);
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

        double distance;
        String str_major = "";

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            if (arg1.getDoubleExtra("Distance", 0)!=0){
                distance = arg1.getDoubleExtra("D1", 0);
                Toast.makeText(StudentActivity.this,"Distance = "+distance,
                    Toast.LENGTH_LONG).show();
            }else{
                distance = 5;
            }
            if (!arg1.getStringExtra("Major").equals("")){
                str_major= arg1.getStringExtra("Major");
                Toast.makeText(StudentActivity.this,"Major = "+str_major,
                        Toast.LENGTH_LONG).show();
            }else{
                str_major = "";
            }
            Log.d("Rana",distance+" , major:  "+str_major+" , ");

        }

    }

}
