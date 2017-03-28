package com.habebe.projecthomework.database;

public class EXERCISE_TABLE {
    public static final String TABLE = "EXERCISE_TABLE";
    public static final String EXERCISEID = "EXERCISEID";
    public static final String SUBJECTID = "SUBJECTID";
    public static final String CHARPTERID = "CHARPTERID";
    public static final String CHARPTERNAME = "CHARPTERNAME";
    public static final String DODATE = "DODATE";
    public static final String EXPDATE = "EXPDATE";
    public static final String AMOUNT = "AMOUNT";
    public static final String Score = "Score";
    public static final String Level = "Level";
    public static final String SENDNOTIFICATIONED = "SENDNOTIFICATIONED";

    public static String onCreate() {
        String DATABASE_CREATE = "create table "
                + TABLE
                + " ("
                + "_id" + " TEXT, "
                + EXERCISEID + " TEXT, "
                + SUBJECTID + " TEXT, "
                + CHARPTERID + " TEXT, "
                + CHARPTERNAME + " TEXT, "
                + DODATE + " TEXT, "
                + EXPDATE + " TEXT, "
                + Score + " TEXT, "
                + Level + " TEXT, "
                + SENDNOTIFICATIONED + " TEXT, "
                + AMOUNT + " TEXT);";
        return DATABASE_CREATE;
    }

}
