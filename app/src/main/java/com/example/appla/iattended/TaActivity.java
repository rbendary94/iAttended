package com.example.appla.iattended;


import android.app.Activity;
import android.os.Bundle;

public class TaActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta);


        //call service that is continuously detecting beacons around
        //Once it detects 4 beacons with same UUID
        //           it get from Front End the course name, room number and tutorial number and UUID
        //           start counter/timestamp
        //           insert in db
        //On End button click: insert total duration or timestamp -> sessionmarked as ended.



    }

}
