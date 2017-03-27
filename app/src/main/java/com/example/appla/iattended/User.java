package com.example.appla.iattended;

/**
 * Created by Yasser on 27/3/17.
 */

public class User {
     String email, password,id;
     Boolean isTa;

    public User(String email, String password, String id,Boolean isTa){
        this.email=email;
        this.password=password;
        this.id=id;
        this.isTa=isTa;
    }
}
