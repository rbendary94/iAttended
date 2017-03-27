package com.example.appla.iattended;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StudentActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        //call service that is continuosly detecting beacons around

        //Once it detects 4 beacons with same UUID
        //           it fetches from db the course name, room number and tutorial number
        //           start counter
        //As long as the same 4 beacons are detected increment the counter
        //On Done button click: check if session ended (through firebase db) then add the attendance for this student
        //                            else if session is not done: display warning.


        Intent i = new Intent(this, BeaconService.class);
        startService(i);

    }

}
