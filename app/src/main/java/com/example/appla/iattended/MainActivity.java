package com.example.appla.iattended;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Button redirectTosignUp;
    Button login;
    BeaconService beaconService;

    EditText email, password;
    String str_email, str_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://iattended-bd60c.firebaseio.com/");
        final Firebase newRef = ref.child("Users");
        email = (EditText)  findViewById(R.id.txt_login_email);
        password = (EditText)  findViewById(R.id.txt_login_password);
        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //generate el strings el awel
                str_email = email.getText().toString();
                str_password = password.getText().toString();
                //fetch mn el db
                Query queryRef = newRef.orderByChild("email").equalTo(str_email);
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                        System.out.println(dataSnapshot.getValue());
                        Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();

                        String email1 = String.valueOf(value.get("email"));
                        String password1 = String.valueOf(value.get("password"));
                        //System.out.println(dataSnapshot.getKey() + "is" + value.get("fullName").toString());
                        Toast.makeText(MainActivity.this,
                                email1+"     "+password1, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                //redirect
            }}
        );
        redirectTosignUp = (Button) findViewById(R.id.btn_redirectToSignUp);
        redirectTosignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SignUpActivity.class);
                MainActivity.this.startActivity(myIntent);
            }}
        );

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service
        beaconService = new BeaconService();

        //Start our own service
        Intent intent = new Intent(MainActivity.this,
                BeaconService.class);
        startService(intent);

        super.onStart();
    }
}
