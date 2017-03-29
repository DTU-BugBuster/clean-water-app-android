package com.example.fourandahalfmen.m4.data;
import java.util.Date;


public class WaterPurityReport {

    public String date;
    public int reportNumber;
    public String user;
    public String location;
    public Double llat;
    public Double llong;
    public String waterCondition;
    public Double virusPPM;
    public Double contaminantPPM;


    /**
     * Constructor with no parameters
     */
    public WaterPurityReport() {
        this(0 ,"null", "null", 0.0, 0.0 ,"null", 0.0, 0.0);
        this.date = new Date().toString();
    }

    /**
     *
     * @param reportNumber report number
     * @param user workers name
     * @param location location of water source
     * @param llat latitude of location
     * @param llong longitude of location
     * @param waterCondition condition of water
     * @param virusPPM virusPPM of water - Parts Per Million
     * @param contaminantPPM contaminantPPM  - Parts Per Million
     */
    public WaterPurityReport(int reportNumber,
                             String user, String location, Double llat, Double llong,
                             String waterCondition, Double virusPPM, Double contaminantPPM) {

        this.date = new Date().toString();
        this.reportNumber = reportNumber;
        this.user = user;
        this.location = location;
        this.llat = llat;
        this.llong = llong;
        this.waterCondition = waterCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }
}
