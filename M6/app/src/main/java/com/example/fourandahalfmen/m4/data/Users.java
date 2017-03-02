package com.example.fourandahalfmen.m4.data;

public final class Users {

    public String username;
    public String password;
    public String account_type;
    public String email;
    public String street_address;
    public String city;
    public String state;
    public int zip_code;
    public int attempts;
    public boolean locked;

    public Users() {
        this(null, null, null, null, null, null, null, 0, 0, false);
    }

    public Users(String username, String password, String account_type,
                 String email, String street_address, String city,
                 String state, int zip_code, int attempts, boolean locked) {

        this.username = username;
        this.password = password;
        this.account_type = account_type;
        this.email = email;
        this.street_address = street_address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.attempts = attempts;
        this.locked = locked;

    }
}

