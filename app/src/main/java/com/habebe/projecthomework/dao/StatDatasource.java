package com.habebe.projecthomework.dao;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;

import com.habebe.projecthomework.dao.homeworkset.Question;
import com.habebe.projecthomework.database.CHAPTER_TABLE;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.SUBJECT_TABLE;
import com.habebe.projecthomework.manager.LoginManager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class StatDatasource {
    private Context context;
    private static StatDatasource instance;
    private ArrayList<StatModel> statModels = new ArrayList<StatModel>();

    public StatDatasource(Context context) {
        this.context = context;
        statModels = new ArrayList<StatModel>();
    }

    public int getCount() {
        return statModels.size();
    }

    public StatModel getItem(int position) {
        return statModels.get(position);
    }

    public void getItemFromDummy() {
        statModels.clear();

        try {
            Cursor cSubject = this.context.getContentResolver().query(MyContentProvider.SUBJECT_CONTENT_URI, null, null, null, null);
            if (cSubject.getCount() > 0) {
                cSubject.moveToFirst();
                do {
                    int total = 0;
                    int correct = 0;
                    int wrong = 0;

                    StatModel statModel = new StatModel();
                    statModel.setSubject(cSubject.getString(cSubject.getColumnIndex(SUBJECT_TABLE.SUBJECTNAME)));

                    String w0 = CHAPTER_TABLE.SubjectID + " = '" + cSubject.getString(cSubject.getColumnIndex(SUBJECT_TABLE.SUBJECTID)) + "'";
                    Cursor cChapter = this.context.getContentResolver().query(MyContentProvider.CHAPTER_CONTENT_URI, null, w0, null, null);
                    if (cChapter.getCount() > 0) {
                        cChapter.moveToFirst();
                        do {
                            String w1 =  EXERCISE_TABLE.CHARPTERID + " = '" + cChapter.getString(cChapter.getColumnIndex(CHAPTER_TABLE.ChapterID)) + "'";
                            Cursor cExercise = this.context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, w1, null, null);
                            if (cExercise.getCount() > 0) {
                                cExercise.moveToFirst();
                                do {
                                    String w2 = HOMEWORKSET_TABLE.EXERCISEID + " = '" + cExercise.getString(cExercise.getColumnIndex(EXERCISE_TABLE.EXERCISEID)) + "'";
                                    Cursor cHomework = this.context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, w2, null, null);
                                    if (cHomework.getCount() > 0) {
                                        cHomework.moveToFirst();
                                        do {
                                            try {
                                                total += Integer.parseInt(cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.AMOUNT)));
                                                correct += Integer.parseInt(cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.AMOUNTCORRECT)));
                                                wrong += Integer.parseInt(cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.AMOUNTWRONG)));
                                            }catch (Exception e){
                                                e.toString();
                                            }

                                        } while (cHomework.moveToNext());
                                    }
                                    cHomework.close();

                                } while (cExercise.moveToNext());
                            }

                        } while (cChapter.moveToNext());

                    }
                    cChapter.close();

                    statModel.setExercise_Total(String.valueOf(total));
                    statModel.setExercise_Correct(String.valueOf(correct));
                    statModel.setExercise_Wrong(String.valueOf(wrong));
                    statModel.setExercise_Donot(String.valueOf(total - correct - wrong));
                    statModels.add(statModel);
                } while (cSubject.moveToNext());

            }
            cSubject.close();
        }catch (Exception e){
            e.toString();
        }

    }

}
