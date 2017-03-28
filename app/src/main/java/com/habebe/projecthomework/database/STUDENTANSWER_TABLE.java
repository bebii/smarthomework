package com.habebe.projecthomework.database;

public class STUDENTANSWER_TABLE {
    public static final String TABLE = "STUDENTANSWER_TABLE";
    public static final String STANSWERID = "STANSWERID";
    public static final String ANSWERID = "ANSWERID";
    public static final String QUESTIONID = "QUESTIONID";

    public static String onCreate() {
        String DATABASE_CREATE = "create table "
                + TABLE
                + " ("
                + "_id" + " TEXT, "
                + STANSWERID + " TEXT, "
                + ANSWERID + " TEXT, "
                + QUESTIONID + " TEXT);";
        return DATABASE_CREATE;
    }

}
