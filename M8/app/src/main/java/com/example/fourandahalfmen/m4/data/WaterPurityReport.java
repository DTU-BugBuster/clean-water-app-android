package com.example.fourandahalfmen.m4.data;
import java.util.Date;


public class WaterPurityReport {

    private String date;
    private int reportNumber;
    private String user;
    private String location;
    private Double llat;
    private Double llong;
    private String waterCondition;
    private double virusPPM;
    private double contaminantPPM;


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
                             String user, String location, double llat, double llong,
                             String waterCondition, double virusPPM, double contaminantPPM) {

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
