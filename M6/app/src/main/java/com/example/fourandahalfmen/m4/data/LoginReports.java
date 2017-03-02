package com.example.fourandahalfmen.m4.data;

import java.util.Date;

/**
 * Created by varunballari on 3/1/17.
 */

public class LoginReports {
    public boolean success_status;
    public String user;
    public String login_timestamp;

    /**
     * Constructor with no parameters
     */
    public LoginReports() {
        this(false, "None");
        this.login_timestamp = new Date().toString();
    }

    /**
     * Constructor will all parameters
     * @param success_status    status of login
     * @param user              user who tried to login
     */
    public LoginReports(boolean success_status, String user) {
        this.success_status = success_status;
        this.user = user;
        this.login_timestamp = new Date().toString();
    }


}
