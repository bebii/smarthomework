package com.habebe.projecthomework.dao;

import android.content.Context;
import android.database.Cursor;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.homeworkset.Homeworkset;
import com.habebe.projecthomework.database.CHAPTER_TABLE;
import com.habebe.projecthomework.database.EXAMINATION_TABLE;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.SUBJECT_TABLE;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ShowScoreDatasource {
    private static ShowScoreDatasource instance;
    private ArrayList<ScoreDatasource> scoreDatasources = new ArrayList<ScoreDatasource>();

    public static final synchronized ShowScoreDatasource getInstance() {
        if (instance == null) instance = new ShowScoreDatasource();
        return instance;
    }

    public ArrayList<ScoreDatasource> getItem(Context context) {
        scoreDatasources.clear();
        User user = LoginManager.getInstance().getUser(context);

        Cursor cursor = context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, null, null, null);

        HashMap<String, String> hashMap = new HashMap<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ScoreDatasource scoreDatasource = new ScoreDatasource();
                scoreDatasource.setHeader(false);
                scoreDatasource.setSumScore(false);

                try {
                    String w0 = CHAPTER_TABLE.ChapterID + " = " + "'" + cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.CHARPTERID)) + "'";
                    Cursor c0 = context.getContentResolver().query(MyContentProvider.CHAPTER_CONTENT_URI, null, w0, null, null);
                    String subjectid = "";
                    if (c0.getCount() > 0) {
                        c0.moveToFirst();
                        subjectid = (c0.getString(c0.getColumnIndex(CHAPTER_TABLE.SubjectID)));
                        scoreDatasource.setChapter(c0.getString(c0.getColumnIndex(CHAPTER_TABLE.ChapterName)));
                    }
                    c0.close();

                    String w1 = SUBJECT_TABLE.SUBJECTID + " = " + "'" + subjectid + "'";
                    Cursor c1 = context.getContentResolver().query(MyContentProvider.SUBJECT_CONTENT_URI, null, w1, null, null);

                    if (c1.getCount() > 0) {
                        c1.moveToFirst();
                        String subjectname = (c1.getString(c1.getColumnIndex(SUBJECT_TABLE.SUBJECTNAME)));

                        if (!hashMap.containsKey(subjectid)) {
                            ScoreDatasource datasource = new ScoreDatasource();
                            datasource.setHeader(true);
                            datasource.setSubject(subjectname);
                            hashMap.put(subjectid, subjectname);
                            scoreDatasources.add(datasource);
                        }
                    }
                    c1.close();

                    String w2 = HOMEWORKSET_TABLE.EXERCISEID + " = " + "'" + cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.EXERCISEID)) + "'";
                    Cursor c2 = context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, w2, null, null);

                    float total = 0;
                    float score = 0;

                    if (c2.getCount() > 0) {
                        c2.moveToFirst();

                        do {
                            if (user.getPosition().equalsIgnoreCase(Position.STATUS_TEACHER)) {
                                total += (Float.parseFloat(cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.AMOUNT))) * Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.Score))));
                                score += Float.parseFloat(c2.getString(c2.getColumnIndex(HOMEWORKSET_TABLE.SCORE)));
                                scoreDatasource.setScore(String.valueOf((score/total)*100));

                            } else {
                                total += (Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.AMOUNT))) * Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.Score))));
                                score += Integer.parseInt(c2.getString(c2.getColumnIndex(HOMEWORKSET_TABLE.SCORE)));
                                scoreDatasource.setScore(c2.getString(c2.getColumnIndex(HOMEWORKSET_TABLE.SCORE)) + " / " + String.valueOf(total));

                                try {
                                    if (scoreDatasource.getScore() == null) {
                                        scoreDatasource.setScore("-");
                                    }
                                } catch (Exception e) {
                                    e.toString();
                                }
                            }
                        }while (c2.moveToNext());

                    }
                    c2.close();

                    scoreDatasource.setExercise(context.getText(R.string.label_exercise) + " " + cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.EXERCISEID)));
                    scoreDatasources.add(scoreDatasource);
                } catch (Exception e) {
                    e.toString();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return scoreDatasources;
    }

    public ArrayList<ScoreDatasource> getScoreDatasources() {
        return scoreDatasources;
    }

    public void setScoreDatasources(ArrayList<ScoreDatasource> scoreDatasources) {
        this.scoreDatasources = scoreDatasources;
    }
}
