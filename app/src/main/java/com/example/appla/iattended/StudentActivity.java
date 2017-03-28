package com.example.appla.iattended;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class StudentActivity extends Activity{

    MyReceiver myReceiver;
    BeaconService beaconService;

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

//            Toast.makeText(MainActivity.this,
//                    "Triggered by Service!\n"
//                            + "Data passed: " + String.valueOf(x)+" , "+String.valueOf(y),
//                    Toast.LENGTH_LONG).show();

        }

    }

}
