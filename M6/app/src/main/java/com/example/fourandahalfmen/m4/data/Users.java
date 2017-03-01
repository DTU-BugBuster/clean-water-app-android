package com.example.fourandahalfmen.m4.data;

public final class Users {

    public String username;
    public String password;
    public String account_type;
    public String email;
    public int house_num;
    public String street_address;
    public String city;
    public String state;
    public String zip_code;

    public Users() {
        this(null, null, null, null, 0, null, null, null, null);
    }

    public Users(String username, String password, String account_type,
                 String email, int house_num, String street_address, String city,
                 String state, String zip_code) {

        this.username = username;
        this.password = password;
        this.account_type = account_type;
        this.email = email;
        this.house_num = house_num;
        this.street_address = street_address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;

    }
}

