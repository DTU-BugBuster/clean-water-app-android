package com.example.fourandahalfmen.m4.data;
import java.util.Date;


public class securityReport {

    public String viewLoginTimeStamp;
    public boolean success_status;
    public String user;


    /**
     * Constructor with no parameters
     */
    public securityReport() {
        this("", false, "");
    }

    /**
     * @param viewLoginTimeStamp Login Attempt meta data
     * @param success_status whether the login attempt was successful
     * @param user current user attempting to login
     */
    public securityReport(String viewLoginTimeStamp, boolean success_status, String user) {

        this.viewLoginTimeStamp = viewLoginTimeStamp;
        this.success_status = success_status;
        this.user = user;
    }
}