package com.example.appla.iattended;

/**
 * Created by Rana on 4/28/17.
 */

public class Attendance {
    public Boolean attendance;
    public User user;
    public Session session;

    public Attendance(User user, Boolean attendance, Session session){
        this.user=user;
        this.attendance=attendance;
        this.session = session;
    }
}
