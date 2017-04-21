package com.example.appla.iattended;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TaActivity extends Activity{

    TextView courseName, timeCounter;
    String str_courseName, str_timeCounter, str_roomNr, str_tutorialNr;
    Button btn_endSession;
    int seconds=0;
    int minutes=0;
    int hours =0;
    Timer T=new Timer();
    Session s;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://iattended-bd60c.firebaseio.com/");
        courseName = (TextView) findViewById(R.id.tv_ta_courseName);
        timeCounter = (TextView) findViewById(R.id.tv_st_counter);
        btn_endSession = (Button) findViewById(R.id.bt_ta_endSession);
        Bundle bundle = getIntent().getExtras();
        str_courseName = bundle.getString("SessionName");
        str_timeCounter= bundle.getString("SessionStartTime");
        str_roomNr = bundle.getString("SessionRoomNr");
        str_tutorialNr = bundle.getString("SessionTutorialNr");

        courseName.setText(str_courseName);

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
                        timeCounter.setText(hours+":"+minutes+":"+seconds);
                        seconds++;
                    }
                });
            }
        }, 1000, 1000);

        btn_endSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.cancel();
                final Firebase newRef = ref.child("Sessions");
                final Query queryRef = newRef.orderByChild("startTime").equalTo(str_timeCounter);
                queryRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if(dataSnapshot.getValue() !=null){
                            Toast.makeText(TaActivity.this,
                                    "HI " + dataSnapshot.getValue(), Toast.LENGTH_LONG).show();
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                break;
                            }



                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                //redirect to another activity
                Intent myIntent = new Intent(TaActivity.this, EndSessionActivity.class);
                myIntent.putExtra("SessionCourseName", str_courseName);
                myIntent.putExtra("SessionRoomNr", str_roomNr);
                myIntent.putExtra("SessionTutorialNr", str_tutorialNr);
                myIntent.putExtra("SessionStartTime", str_timeCounter);
                String endTime = DateFormat.getDateTimeInstance().format(new Date());
                myIntent.putExtra("SessionEndTime", endTime);
                TaActivity.this.startActivity(myIntent);

            }
        });
    }//method OnCreate

}
