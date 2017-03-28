package com.habebe.projecthomework.database;

public class QUESTION_TABLE {
    public static final String TABLE = "QUESTION_TABLE";
    public static final String PROPOSITION = "PROPOSITION";
    public static final String QUESTIONNAME = "QUESTIONNAME";
    public static final String QUESTIONID = "QUESTIONID";
    public static final String HSETID = "HSETID";
    public static final String EXERCISEID = "EXERCISEID";
    public static final String CORRECT = "CORRECT";
    public static final String ANSDESC = "ANSDESC";

    public static String onCreate() {
        String DATABASE_CREATE = "create table "
                + TABLE
                + " ("
                + "_id" + " TEXT, "
                + HSETID + " TEXT, "
                + CORRECT + " TEXT, "
                + ANSDESC + " TEXT, "

                + EXERCISEID + " TEXT, "
                + PROPOSITION + " TEXT, "
                + QUESTIONNAME + " TEXT, "
                + QUESTIONID + " TEXT);";
        return DATABASE_CREATE;
    }

}
