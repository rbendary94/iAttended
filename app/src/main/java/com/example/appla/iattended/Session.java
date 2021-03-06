package com.example.appla.iattended;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yasser on 28/3/17.
 */

public class Session {
    public String str_courseName, str_roomNr,str_tutorialNr,startTime , endTime;

    public Session(String str_courseName, String str_roomNr, String str_tutorialNr){
        this.str_courseName=str_courseName;
        this.str_roomNr=str_roomNr;
        this.str_tutorialNr=str_tutorialNr;
        this.startTime= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        this.endTime = "";
    }


    @Override
    public String toString() {
        return "Session{" +
                "str_courseName='" + str_courseName + '\'' +
                ", str_roomNr='" + str_roomNr + '\'' +
                ", str_tutorialNr='" + str_tutorialNr + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
