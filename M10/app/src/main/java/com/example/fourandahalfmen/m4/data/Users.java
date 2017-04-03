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

    /**
     * Constructor with no parameters
     */
    public Users() {
        this(null, null, null, null, null, null, null, 0, 0, false);
    }

    /**
     * Constructor will all parameters
     *
     * @param username          username of user
     * @param password          password of user
     * @param account_type      account type of user (etc. admin)
     * @param email             email of user
     * @param street_address    street address of user
     * @param city              city of address
     * @param state             state of address
     * @param zip_code          zip code of address
     * @param attempts          login attempts if wrong
     * @param locked            if account is locked
     */
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

