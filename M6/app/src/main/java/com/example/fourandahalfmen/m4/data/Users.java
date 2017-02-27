package com.example.fourandahalfmen.m4.data;

import android.provider.BaseColumns;

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


//    public static final class UserEntry implements BaseColumns {
//
//        public final static String TABLE_NAME = "Users";
//
//        public final static String _ID = BaseColumns._ID;
//
//        public final static String COLUMN_EMAIL = "email";
//
//        public final static String COLUMN_PASSWORD = "password";
//
//        public final static String COLUMN_TYPE = "type";
//        /**
//         * Possible values for the type of user.
//         */
//        public static final String TYPE_USER = "User";
//        public static final String TYPE_WORKER = "Worker";
//        public static final String TYPE_MANAGER = "Manager";
//        public static final String TYPE_ADMIN = "Admin";
//    }

}

