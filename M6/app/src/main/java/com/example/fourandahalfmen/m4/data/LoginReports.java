package com.example.fourandahalfmen.m4.data;

import java.util.Date;

/**
 * Created by varunballari on 3/1/17.
 */

public class LoginReports {
    public boolean success_status;
    public String user;
    public String login_timestamp;

    public LoginReports() {
        this(false, "None");
        this.login_timestamp = new Date().toString();
    }

    public LoginReports(boolean success_status, String user) {
        this.success_status = success_status;
        this.user = user;
        this.login_timestamp = new Date().toString();
    }


}
