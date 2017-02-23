/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.fourandahalfmen.m4.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"EMAIL  text,PASSWORD text, TYPE text); ";

    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DbHelper dbHelper;

    public  LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public  LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String email,String password, String type) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD",password);
        newValues.put("TYPE",type);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
    }

    public int deleteEntry(String email) {
        //String id=String.valueOf(ID);
        String where="EMAIL=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{email}) ;
        return numberOFEntriesDeleted;
    }

    public String getSingleEntry(String email) {
        Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) {
            // email Not Exist
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public String getSingleEntry2(String email) {
        Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        cursor.moveToFirst();
        String type = cursor.getString(cursor.getColumnIndex("TYPE"));
        cursor.close();
        return type;
    }

    public void  updateEntry(String email,String password, String type) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("EMAIL", email);
        updatedValues.put("PASSWORD",password);
        updatedValues.put("TYPE",type);
        String where ="EMAIL ='"+email+"'";
        db.update("LOGIN",updatedValues, where, null);
    }
}