package com.example.appla.iattended;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class SignUpActivity extends AppCompatActivity {

    EditText email, password,id, confirmPassword;
    String str_email, str_password,str_id, str_confirmPassword;
    Boolean email_verified, password_verified, isTA, id_verified;
    Firebase ref = new Firebase("https://iattended-bd60c.firebaseio.com/");

    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Firebase.setAndroidContext(this);
        email = (EditText)  findViewById(R.id.txt_signup_email);
        password = (EditText)  findViewById(R.id.txt_signup_password);
        confirmPassword = (EditText)  findViewById(R.id.txt_signup_confirm_password);
        id = (EditText)  findViewById(R.id.txt_signup_id);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    email.setHint("");
                else
                    email.setHint("GUC email");
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint("Password");
            }
        });
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    confirmPassword.setHint("");
                else
                    confirmPassword.setHint("Confirm Password");
            }
        });
        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    id.setHint("");
                else
                    id.setHint("XX-XXXXX");
            }
        });
        signUp = (Button) findViewById(R.id.btn_login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------generate strings------------
                str_email = email.getText().toString();
                str_password = password.getText().toString();
                str_confirmPassword = confirmPassword.getText().toString();
                str_id = id.getText().toString();

                //------------conditions 3alehom------------

                //PASSWORD CONDITIONS
                if(str_password.equals(str_confirmPassword)){

                    password_verified = true;
                }else{
                    password_verified = false;
                    Toast.makeText(SignUpActivity.this,
                            "Passwords do not match, please try again.", Toast.LENGTH_LONG).show();
                }

                //EMAIL CONDITIONS
                if(str_email.endsWith("@student.guc.edu.eg")){
                    //Student
                    email_verified = true;
                    isTA =false;
                }else if(str_email.endsWith("@guc.edu.eg")) {
                    //TA
                    email_verified = true;
                    isTA=true;

                }else{
                    email_verified = false;
                    Toast.makeText(SignUpActivity.this,
                        "Incorrect Email. Please use your GUC email.", Toast.LENGTH_LONG).show();
                }

                //ID CONDITIONS
                if(str_id.contains("-") && str_id.length() > 4){
                    id_verified =true;
                }else if (str_id.equals("") && isTA) {
                    id_verified = true;
                } else{
                        id_verified =false;
                        Toast.makeText(SignUpActivity.this,
                            "Please insert GUC ID or if TA, leave blank.", Toast.LENGTH_LONG).show();
                    }



                //------------Dump to firebase db in table Users.------------
                if(email_verified && password_verified && id_verified){
                    //Insert
                    User user = new User(str_email,str_password,str_id,isTA);
                    Firebase newRef = ref.child("Users").push();
                    newRef.setValue(user);
                    if(isTA){
                        //Redirect to TA's page
                    }else{
                        //Redirect to students page

                    }
                }

            }
        });


    }
}
