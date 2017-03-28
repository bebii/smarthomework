package com.habebe.projecthomework.database;

public class HOMEWORKSET_TABLE {
    public static final String TABLE = "HOMEWORKSET_TABLE";
    public static final String HSETID = "HSETID";
    public static final String EXERCISEID = "EXERCISEID";
    public static final String DODATE = "DODATE";
    public static final String EXPDATE = "EXPDATE";
    public static final String SCORE = "SCORE";
    public static final String COMMITED = "COMMITED";

    public static final String AMOUNT = "AMOUNT";
    public static final String AMOUNTCORRECT = "AMOUNTCORRECT";
    public static final String AMOUNTWRONG = "AMOUNTWRONG";

    public static String onCreate() {
        String DATABASE_CREATE = "create table "
                + TABLE
                + " ("
                + "_id" + " TEXT, "
                + HSETID + " TEXT, "
                + EXERCISEID + " TEXT, "

                + AMOUNT + " TEXT, "
                + AMOUNTCORRECT + " TEXT, "
                + AMOUNTWRONG + " TEXT, "

                + DODATE + " TEXT, "
                + COMMITED + " TEXT, "
                + EXPDATE + " TEXT, "
                + SCORE + " TEXT);";
        return DATABASE_CREATE;
    }

}
