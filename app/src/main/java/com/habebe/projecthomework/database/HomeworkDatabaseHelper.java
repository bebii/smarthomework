package com.habebe.projecthomework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HomeworkDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "projecthomework.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TAG = "Upgrage";
    private Context mContext;

    public HomeworkDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    public HomeworkDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ANSWER_TABLE.onCreate());
        db.execSQL(CORRECTANSWER_TABLE.onCreate());
        db.execSQL(EXAMINATION_TABLE.onCreate());
        db.execSQL(EXERCISE_TABLE.onCreate());
        db.execSQL(HOMEWORKSET_TABLE.onCreate());
        db.execSQL(QUESTION_TABLE.onCreate());
        db.execSQL(REGISTER_TABLE.onCreate());
        db.execSQL(STUDENTANSWER_TABLE.onCreate());
        db.execSQL(SUBJECT_TABLE.onCreate());
        db.execSQL(CHAPTER_TABLE.onCreate());
        db.execSQL(USER_TABLE.onCreate());

        Log.d("homework_db", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        for (int i = oldVersion + 1; i <= newVersion; i++) {
            try {
                switch (i) {
                    case 2:
                        try {
                            /*Log.d(TAG, "Adding JOB Table");

                            // Update JobD Table
                            String sqljobd1 = "ALTER TABLE " + MY_TABLE + " ADD COLUMN " +
                                    "U_STATUSRECEIVE" + " TEXT";
                            db.execSQL(sqljobd1);

                            // Update other Table
                            db.execSQL(NEWDATABASE_CREATE);*/
                        } catch (Exception e) {

                        }
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
                droptable(db);
            }
        }
    }

    private void droptable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + ANSWER_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CORRECTANSWER_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXAMINATION_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HOMEWORKSET_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REGISTER_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTANSWER_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CHAPTER_TABLE.TABLE);

        onCreate(db);

    }
}
