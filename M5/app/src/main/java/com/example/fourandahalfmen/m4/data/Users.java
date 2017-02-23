package com.example.fourandahalfmen.m4.data;

import android.provider.BaseColumns;

public final class Users {

    private Users() {}


    public static final class UserEntry implements BaseColumns {

        public final static String TABLE_NAME = "Users";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_EMAIL ="email";

        public final static String COLUMN_PASSWORD = "password";

        public final static String COLUMN_TYPE = "type";
        /**
         * Possible values for the type of user.
         */
        public static final String TYPE_USER = "User";
        public static final String TYPE_WORKER = "Worker";
        public static final String TYPE_MANAGER = "Manager";
        public static final String TYPE_ADMIN = "Admin";
    }

}

