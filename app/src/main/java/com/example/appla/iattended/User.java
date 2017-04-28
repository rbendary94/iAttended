package com.example.appla.iattended;

/**
 * Created by Yasser on 27/3/17.
 */

public class User {
    public     String email, password,id;
    public     Boolean isTa;

    public User(String email, String password, String id,Boolean isTa){
        this.email=email;
        this.password=password;
        this.id=id;
        this.isTa=isTa;
    }
    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                ", isTa=" + isTa +
                '}';
    }
}
