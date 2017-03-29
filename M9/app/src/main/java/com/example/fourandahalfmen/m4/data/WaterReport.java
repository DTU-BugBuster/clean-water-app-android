package com.example.fourandahalfmen.m4.data;
import java.util.Date;

/**
 * Created by varunballari on 3/1/17.
 */

public class WaterReport {

    public String location;
    public String user;
    public String waterType;
    public String waterCondition;
    public String date;
    public Double llat;
    public Double llong;

    /**
     * Constructor with no parameters
     */
    public WaterReport() {
        this("null", "null", "null", "null", 0.0, 0.0);
        this.date = new Date().toString();
    }

    /**
     * Constructor will all parameters
     * @param location          location of water source
     * @param user              user who inputs the data
     * @param waterType         type of water
     * @param waterCondition    condition of water
     */
    public WaterReport(String location, String user, String waterType, String waterCondition, Double llat, Double llong) {
        this.location = location;
        this.user = user;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.date = new Date().toString();
        this.llat = llat;
        this.llong = llong;
    }
}
