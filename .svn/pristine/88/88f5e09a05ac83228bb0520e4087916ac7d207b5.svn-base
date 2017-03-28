package com.habebe.projecthomework.dao;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.activity.CalExamActivity;
import com.habebe.projecthomework.dao.homeworkset.Answer;
import com.habebe.projecthomework.dao.homeworkset.Question;
import com.habebe.projecthomework.database.ANSWER_TABLE;
import com.habebe.projecthomework.database.CHAPTER_TABLE;
import com.habebe.projecthomework.database.CORRECTANSWER_TABLE;
import com.habebe.projecthomework.database.EXAMINATION_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.QUESTION_TABLE;
import com.habebe.projecthomework.database.SUBJECT_TABLE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CalExamDatasource {
    private Context context;
    private static CalExamDatasource instance;
    private ArrayList<CalExamModel> calExamDataSource = new ArrayList<CalExamModel>();

    public CalExamDatasource(Context context) {
        this.context = context;
        calExamDataSource = new ArrayList<CalExamModel>();
    }

    public int getCount() {
        return calExamDataSource.size();
    }

    public CalExamModel getItem(int position) {
        return calExamDataSource.get(position);
    }

    public void getItemFromDummy() {
        calExamDataSource.clear();
        HashMap<String, String> hashMap = new HashMap<>();

        Cursor cursor = context.getContentResolver().query(MyContentProvider.EXAMINATION_CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                CalExamModel calExamModel = new CalExamModel();
                try {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(cursor.getString(cursor.getColumnIndex(EXAMINATION_TABLE.EXAMDATE)));


                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if(!hashMap.containsKey(cursor.getString(cursor.getColumnIndex(EXAMINATION_TABLE.EXAMDATE)))){
                        hashMap.put(cursor.getString(cursor.getColumnIndex(EXAMINATION_TABLE.EXAMDATE)), "");
                        CalExamModel cal = new CalExamModel();
                        cal.setMHeader(true);
                        cal.setMontYear((String) DateFormat.format("MMM/yyyy", convertedDate));
                        calExamDataSource.add(cal);

                    }
                    calExamModel.setChapter(cursor.getString(cursor.getColumnIndex(EXAMINATION_TABLE.CHAPTERID)));
                    calExamModel.setExercise(cursor.getString(cursor.getColumnIndex(EXAMINATION_TABLE.DESCRIPTION)));
                    calExamModel.setMontYear(cursor.getString(cursor.getColumnIndex(EXAMINATION_TABLE.EXAMDATE)));
                    calExamModel.setMHeader(false);

                    String w = CHAPTER_TABLE.ChapterID +" = " + "'" + calExamModel.getChapter() + "'";
                    Cursor c = context.getContentResolver().query(MyContentProvider.CHAPTER_CONTENT_URI, null, w, null, null);

                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        calExamModel.setChapter(c.getString(c.getColumnIndex(CHAPTER_TABLE.ChapterName)));
                        calExamModel.setSubject(c.getString(c.getColumnIndex(CHAPTER_TABLE.SubjectID )));

                        String w2 = SUBJECT_TABLE.SUBJECTID +" = " + "'" + c.getString(c.getColumnIndex(CHAPTER_TABLE.SubjectID )) + "'";
                        Cursor c2 = context.getContentResolver().query(MyContentProvider.SUBJECT_CONTENT_URI, null, w2, null, null);
                        if (c2.getCount() > 0) {
                            c2.moveToFirst();
                            calExamModel.setSubject(c2.getString(c2.getColumnIndex(SUBJECT_TABLE.SUBJECTNAME )));
                        }
                        c2.close();
                    }
                    c.close();

                    try {
                        calExamModel.setNumDate((String) DateFormat.format("dd", convertedDate));
                        calExamModel.setNameDate((String) DateFormat.format("EEE", convertedDate));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //calendar.set(t_year, t_month - 1, t_day, t_hour, t_min, t_sec);

                    calExamDataSource.add(calExamModel);
                }catch (Exception e){
                    e.toString();
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = 0; i <= 15; i++) {

            /*if (i == 0) {
                calExamModel.setMHeader(true);
                calExamModel.setMontYear("Jan,2016");

            } else if (i == 8) {
                calExamModel.setMHeader(true);
                calExamModel.setMontYear("Feb,2016");

            }  else {
                calExamModel.setMHeader(false);
                calExamModel.setChapter(context.getString(R.string.charpter) + String.valueOf(i % 5));
                calExamModel.setExercise(context.getString(R.string.cal_exam) + String.valueOf(i));
                calExamModel.setSubject(context.getString(R.string.subjectE));
                calExamModel.setNumDate("10");
                calExamModel.setNameDate("Mon");
            }*/


        }
    }

}
