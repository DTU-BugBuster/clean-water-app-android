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
        public static final int TYPE_USER = 0;
        public static final int TYPE_WORKER = 1;
        public static final int TYPE_MANAGER = 2;
        public static final int TYPE_ADMIN = 3;
    }

}

