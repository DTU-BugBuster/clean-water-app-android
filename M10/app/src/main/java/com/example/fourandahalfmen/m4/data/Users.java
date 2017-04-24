package com.example.fourandahalfmen.m4.data;

public final class Users {

    public String username;
    public String password;
    public String account_type;
    public String email;
    public String street_address;
    public String city;
    public String state;
    public Long zip_code;
    public Long attempts;
    public boolean locked;

    /**
     * Constructor with no parameters
     */
    public Users() {
        this(null, null, null, null, null, null, null, Long.valueOf(0), Long.valueOf(0), false);
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
                 String state, Long zip_code, Long attempts, boolean locked) {

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

    /** A method to ensure the password is valid based on our requirements
     *
     * @param password password retrieved from user and getting checked if valid
     * @return a boolean of whether the password passed in is valid or not
     */
    public boolean setPassword(String password) {

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null. Please pass in a valid" +
                    " password.");
        }

        if (password.equals("")) {
            throw new IllegalArgumentException("Password field cannot be empty. Please pass in a" +
                    " valid password.");
        }

        int validSymbols = 0;
        int capitalLetters = 0;
        boolean invalidPassowrd = true;

        if (password.length() < 7) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            char cur = password.charAt(i);
            if (cur == '!' || cur == '$' || cur == '#' || cur == '@')  {
                validSymbols++;
            } else if (Character.isUpperCase(cur)) {
                capitalLetters++;
            } else if (cur == '+' || cur == '-' || cur == '%') {
                return false;
            }
        }

        if (validSymbols > 0 && capitalLetters > 0) {
            this.password = password;
            return true;
        } else {
            return false;
        }

    }

    /** A method to ensure the username is valid based on our requirements
     *
     * @param username username retrieved from user and getting checked if valid
     * @return a boolean of whether the username passed in is valid or not
     */
    public boolean setUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null. Please pass in a valid" +
                    " username.");
        }

        if (username.equals("")) {
            throw new IllegalArgumentException("Username field cannot be empty. Please pass in a" +
                    " valid username.");
        }

        int capitalLetters = 0;

        if (username.length() < 5) {
            return false;
        }
        for (int i = 0; i < username.length(); i++) {
            if (!Character.isLetter(username.charAt(i))) {
                return false;
            }

            if (Character.isUpperCase(username.charAt(i))) {
                capitalLetters++;
            }
        }

        if (capitalLetters == 2) {
            this.username = username;
            return true;
        } else {
            return false;
        }
    }


    /** A method to ensure the email is valid based on our requirements
     *
     * @param email username retrieved from email and getting checked if valid
     * @return a boolean of whether the email passed in is valid or not
     */
    public boolean setEmail(String email) {

        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null. Please pass in a valid" +
                    " email.");
        }

        if (email.equals("")) {
            throw new IllegalArgumentException("Email field cannot be empty. Please pass in a" +
                    " valid email.");
        }


        if (email.length() < 5) {
            return false;
        }

        if (!email.contains(".")) {
            return false;
        } else if (!email.contains("@")) {
            return false;
        } else if (!email.contains("com") && !email.contains("edu") && !email.contains("org") ) {
            return false;
        } else {
            return true;
        }



    }
}

