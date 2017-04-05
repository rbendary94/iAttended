package com.example.appla.iattended;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    Button redirectTosignUp;
    Button login;

    EditText email, password;
    String str_email, str_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

                queryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getValue() !=null){

                            String fetchedPassword= dataSnapshot.getValue().toString().substring(dataSnapshot.getValue().toString().indexOf("password")+9,dataSnapshot.getValue().toString().indexOf(", isTa") );

                            //Email is not null then compare passwords
                            if(fetchedPassword.equals(str_password)){
                                String fetchedIsTa =dataSnapshot.getValue().toString().substring(dataSnapshot.getValue().toString().indexOf("isTa")+5,dataSnapshot.getValue().toString().length()-2 ); ;
                                Toast.makeText(LoginActivity.this,
                                        ""+fetchedIsTa, Toast.LENGTH_LONG).show();
                                if(fetchedIsTa.contains("true")){
                                    //Redirect to TA's page
//                                    Toast.makeText(MainActivity.this,
//                                            "GOWA EL CONDITION  "+fetchedIsTa, Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(LoginActivity.this, StartSessionActivity.class);
                                    LoginActivity.this.startActivity(myIntent);
                                }else{
                                    //Redirect to Student Page
                                    Intent myIntent = new Intent(LoginActivity.this, StudentActivity.class);
                                    LoginActivity.this.startActivity(myIntent);
                                }
                            }else{
                                Toast.makeText(LoginActivity.this,
                                        "Incorrect Password", Toast.LENGTH_LONG).show();
                            }
                        }else{

                            Toast.makeText(LoginActivity.this,
                                    "Email not found. please recheck email or Register", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(LoginActivity.this,
                                "booooooo", Toast.LENGTH_LONG).show();
                    }

                });
            }}
        );
        redirectTosignUp = (Button) findViewById(R.id.btn_redirectToSignUp);
        redirectTosignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }}
        );

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service

        //Start our own service

        super.onStart();
    }
}
