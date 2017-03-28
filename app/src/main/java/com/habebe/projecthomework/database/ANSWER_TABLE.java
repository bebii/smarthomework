package com.habebe.projecthomework.database;

public class ANSWER_TABLE {
    public static final String TABLE = "ANSWER_TABLE";
    public static final String ANSWERID = "ANSWERID";
    public static final String CHOICENAME = "CHOICENAME";
    public static final String ANSWER = "ANSWER";
    public static final String QUESTIONID = "QUESTIONID";

    public static String onCreate() {
        String DATABASE_CREATE = "create table "
                + TABLE
                + " ("
                + "_id" + " TEXT, "
                + ANSWERID + " TEXT, "
                + CHOICENAME + " TEXT, "
                + ANSWER + " TEXT, "
                + QUESTIONID + " TEXT);";
        return DATABASE_CREATE;
    }

}
