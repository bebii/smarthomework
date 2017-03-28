package com.habebe.projecthomework.database;

public class CORRECTANSWER_TABLE {
    public static final String TABLE = "CORRECTANSWER_TABLE";
    public static final String CORRECTID = "CORRECTID";
    public static final String ANSWERID = "ANSWERID";
    public static final String ANSDESC = "ANSDESC";

    public static String onCreate() {
        String DATABASE_CREATE = "create table "
                + TABLE
                + " ("
                + "_id" + " TEXT, "
                + CORRECTID + " TEXT, "
                + ANSWERID + " TEXT, "
                + ANSDESC + " TEXT);";
        return DATABASE_CREATE;
    }

}
