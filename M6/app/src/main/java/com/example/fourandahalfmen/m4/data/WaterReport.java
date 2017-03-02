package com.example.fourandahalfmen.m4.data;
import java.util.Date;

/**
 * Created by varunballari on 3/1/17.
 */

public class WaterReport {

    String location;
    String user;
    String waterType;
    String waterCondition;
    String date;

    public WaterReport() {
        this("null", "null", "null", "null");
        this.date = new Date().toString();
    }

    public WaterReport(String location, String user, String waterType, String waterCondition) {
        this.location = location;
        this.user = user;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.date = new Date().toString();
    }
}
