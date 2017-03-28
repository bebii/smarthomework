package com.habebe.projecthomework.database;

public class REGISTER_TABLE {
    public static final String TABLE = "REGISTER_TABLE";
    public static final String SUBJECTID = "SUBJECTID";
    public static final String YEAR = "YEAR";
    public static final String SEMESTER = "SEMESTER";
    public static final String STUDENTID = "STUDENTID";

    public static String onCreate() {
        String DATABASE_CREATE = "create table "
                + TABLE
                + " ("
                + "_id" + " TEXT, "
                + SUBJECTID + " TEXT, "
                + YEAR + " TEXT, "
                + SEMESTER + " TEXT, "
                + STUDENTID + " TEXT);";
        return DATABASE_CREATE;
    }

}
