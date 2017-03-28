package com.habebe.projecthomework.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.habebe.projecthomework.dao.homeworkset.Answer;
import com.habebe.projecthomework.dao.homeworkset.Homeworkset;
import com.habebe.projecthomework.dao.homeworkset.Question;
import com.habebe.projecthomework.database.ANSWER_TABLE;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.HomeworkDatabaseHelper;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.QUESTION_TABLE;
import com.habebe.projecthomework.database.STUDENTANSWER_TABLE;
import com.habebe.projecthomework.database.USER_TABLE;
import com.habebe.projecthomework.service.MyReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import static android.R.attr.id;
import static android.R.attr.track;

public class DatahomeworkDatasource {
    private static DatahomeworkDatasource instance;
    private ArrayList<String> questionIds = new ArrayList<String>();
    private HashMap<String, String> map_selection = new HashMap<>();
    private Context context;

    private HashMap<String, Integer> scores = new HashMap<>();
    private HashMap<String, String> answerid = new HashMap<>();

    public static final synchronized DatahomeworkDatasource getInstance() {
        if (instance == null) instance = new DatahomeworkDatasource();
        return instance;
    }

    public Homeworkset getHomeworkset(Context context, String exerciseID) {
        this.context = context;
        Homeworkset homeworkset = new Homeworkset();
        HashMap<String, Question> questionHashMap = new HashMap<>();
        questionIds.clear();

        String selecthomeworkset = HOMEWORKSET_TABLE.EXERCISEID + " = " + "'" + exerciseID + "'";
        Cursor cHomeworkset = context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, selecthomeworkset, null, null);
        if (cHomeworkset.getCount() > 0) {
            cHomeworkset.moveToFirst();

            try {
                String w = QUESTION_TABLE.HSETID + " = " + "'" + cHomeworkset.getString(cHomeworkset.getColumnIndex(HOMEWORKSET_TABLE.HSETID)) + "'";
                ArrayList<Question> list = new ArrayList<>();
                Cursor cursor = context.getContentResolver().query(MyContentProvider.QUESTION_CONTENT_URI, null, w, null, null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            Question question = new Question();
                            question.setQuestionId(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.QUESTIONID)));
                            question.setProposition(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.PROPOSITION)));
                            question.setExerciseID(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.EXERCISEID)));
                            question.setCorrectID(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.CORRECT)));
                            question.setQuestion(true);

                            ArrayList<Answer> answerArrayList = new ArrayList<Answer>();
                            String answerselect = ANSWER_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "'";
                            Cursor answercursor = context.getContentResolver().query(MyContentProvider.ANSWER_CONTENT_URI, null, answerselect, null, null);
                            if (answercursor.getCount() > 0) {
                                answercursor.moveToFirst();
                                do {
                                    Answer answer = new Answer();
                                    answer.setAnswerId(answercursor.getString(answercursor.getColumnIndex(ANSWER_TABLE.ANSWERID)));
                                    answer.setChoice(answercursor.getString(answercursor.getColumnIndex(ANSWER_TABLE.CHOICENAME)));
                                    answer.setText(answercursor.getString(answercursor.getColumnIndex(ANSWER_TABLE.ANSWER)));

                                    answerArrayList.add(answer);

                                } while (answercursor.moveToNext());
                            }
                            answercursor.close();
                            question.setAnswer(answerArrayList);

                            String w0 = EXERCISE_TABLE.EXERCISEID + " = " + "'" + question.getExerciseID() + "'";
                            Cursor cExercise = context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, w0, null, null);
                            if (cExercise.getCount() > 0) {
                                cExercise.moveToFirst();
                                try {
                                    question.setScore(Integer.parseInt(cExercise.getString(cExercise.getColumnIndex(EXERCISE_TABLE.Score))));
                                } catch (Exception e) {
                                    e.toString();
                                }
                            }
                            cExercise.close();

                            if (!map_selection.containsKey(question.getQuestionId())) {

                                String select_answer = STUDENTANSWER_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "'";
                                Cursor cStudent_answer = context.getContentResolver().query(MyContentProvider.STUDENTANSWER_CONTENT_URI, null, select_answer, null, null);
                                if (cStudent_answer.getCount() > 0) {
                                    cStudent_answer.moveToFirst();
                                    try {
                                        String answerid = cStudent_answer.getString(cStudent_answer.getColumnIndex(STUDENTANSWER_TABLE.ANSWERID));
                                        map_selection.put(question.getQuestionId(), answerid);
                                    } catch (Exception e) {
                                        e.toString();
                                    }
                                } else {
                                    map_selection.put(question.getQuestionId(), "-1");
                                }
                                cStudent_answer.close();
                            }

                            list.add(question);

                        } while (cursor.moveToNext());
                    }
                    cursor.close();

                    for (int i = 0; i < list.size(); i++) {
                        Question question = list.get(i);
                        questionIds.add(question.getQuestionId());
                        questionHashMap.put(question.getQuestionId(), question);
                    }
                }
            } catch (Exception e) {
                e.toString();
            }
        }

        cHomeworkset.close();

        Question customquestion = new Question();
        customquestion.setQuestionId("-1");
        questionIds.add(customquestion.getQuestionId());
        customquestion.setQuestion(false);

        map_selection.put(customquestion.getQuestionId(), "-1");
        questionHashMap.put(customquestion.getQuestionId(), customquestion);

        homeworkset.setQuestionHashMap(questionHashMap);

        return homeworkset;
    }

    public Homeworkset getHomeworksetForTeacher(Context context, String exerciseID) {
        this.context = context;
        Homeworkset homeworkset = new Homeworkset();
        HashMap<String, Question> questionHashMap = new HashMap<>();
        questionIds.clear();

        try {
            ArrayList<Question> list = new ArrayList<>();
            Cursor cursor = context.getContentResolver().query(MyContentProvider.QUESTION_CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        Question question = new Question();
                        question.setQuestionId(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.QUESTIONID)));
                        question.setProposition(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.PROPOSITION)));
                        question.setExerciseID(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.EXERCISEID)));
                        question.setCorrectID(cursor.getString(cursor.getColumnIndex(QUESTION_TABLE.CORRECT)));
                        question.setQuestion(true);

                        ArrayList<Answer> answerArrayList = new ArrayList<Answer>();
                        String answerselect = ANSWER_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "'";
                        Cursor answercursor = context.getContentResolver().query(MyContentProvider.ANSWER_CONTENT_URI, null, answerselect, null, null);
                        if (answercursor.getCount() > 0) {
                            answercursor.moveToFirst();
                            do {
                                Answer answer = new Answer();
                                answer.setAnswerId(answercursor.getString(answercursor.getColumnIndex(ANSWER_TABLE.ANSWERID)));
                                answer.setChoice(answercursor.getString(answercursor.getColumnIndex(ANSWER_TABLE.CHOICENAME)));
                                answer.setText(answercursor.getString(answercursor.getColumnIndex(ANSWER_TABLE.ANSWER)));

                                answerArrayList.add(answer);

                            } while (answercursor.moveToNext());
                        }
                        answercursor.close();
                        question.setAnswer(answerArrayList);

                        String w0 = EXERCISE_TABLE.EXERCISEID + " = " + "'" + question.getExerciseID() + "'";
                        Cursor cExercise = context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, w0, null, null);
                        if (cExercise.getCount() > 0) {
                            cExercise.moveToFirst();
                            try {
                                question.setScore(Integer.parseInt(cExercise.getString(cExercise.getColumnIndex(EXERCISE_TABLE.Score))));
                            } catch (Exception e) {
                                e.toString();
                            }
                        }
                        cExercise.close();

                        if (!map_selection.containsKey(question.getQuestionId())) {
                            map_selection.put(question.getQuestionId(), "-1");
                        }

                        list.add(question);

                    } while (cursor.moveToNext());
                }
                cursor.close();

                for (int i = 0; i < list.size(); i++) {
                    Question question = list.get(i);
                    questionIds.add(question.getQuestionId());
                    questionHashMap.put(question.getQuestionId(), question);
                }
            }
        } catch (Exception e) {
            e.toString();
        }

        Question customquestion = new Question();
        customquestion.setQuestionId("-1");
        questionIds.add(customquestion.getQuestionId());
        customquestion.setQuestion(false);

        map_selection.put(customquestion.getQuestionId(), "-1");
        questionHashMap.put(customquestion.getQuestionId(), customquestion);

        homeworkset.setQuestionHashMap(questionHashMap);

        return homeworkset;
    }

    private void createHomeworket(LoginResponseDao loginResponseDao) {
        ContentResolver cr = context.getContentResolver();
        try {
            ContentValues values = new ContentValues();
            /*values.put(HOMEWORKSET_TABLE.USERID, loginResponseDao.getId());
            values.put(HOMEWORKSET_TABLE.NAME, loginResponseDao.getFullName());
            values.put(HOMEWORKSET_TABLE.POSITION, loginResponseDao.getStatus());

            cr.insert(MyContentProvider.HOMEWORKSET_CONTENT_URI, values);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(ArrayList<String> questionIds) {
        this.questionIds = questionIds;
    }

    public HashMap<String, String> getMap_selection() {
        return map_selection;
    }

    public void setMap_selection(HashMap<String, String> map_selection) {
        this.map_selection = map_selection;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }

    public void setScores(HashMap<String, Integer> scores) {
        this.scores = scores;
    }

    public HashMap<String, String> getAnswerid() {
        return answerid;
    }

    public void setAnswerid(HashMap<String, String> answerid) {
        this.answerid = answerid;
    }

    public Homeworkset getHomeworksetForParrent(Context applicationContext, String exerciseID) {

        return null;
    }
}
