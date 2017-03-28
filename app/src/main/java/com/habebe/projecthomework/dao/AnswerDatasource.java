package com.habebe.projecthomework.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.homeworkset.Answer;
import com.habebe.projecthomework.dao.homeworkset.Question;
import com.habebe.projecthomework.database.ANSWER_TABLE;
import com.habebe.projecthomework.database.CORRECTANSWER_TABLE;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.QUESTION_TABLE;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.util.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnswerDatasource {
    private Context context;
    private static AnswerDatasource instance;
    private ArrayList<CorrectAnswer> mDataSource = new ArrayList<>();

    public AnswerDatasource(Context context) {
        this.context = context;
    }

    public int getCount() {
        return mDataSource.size();
    }

    public CorrectAnswer getItem(int position) {
        return mDataSource.get(position);
    }

    public void getItemFromDummy(String exerciseId) {
        mDataSource.clear();

        String selec = EXERCISE_TABLE.EXERCISEID + " = " + "'" + exerciseId + "'";
        Cursor c1 = context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, selec, null, null);
        if (c1.getCount() > 0) {
            c1.moveToFirst();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = new Date();
            try {
                date1 = dateFormat.parse(c1.getString(c1.getColumnIndex(EXERCISE_TABLE.EXPDATE)));
                long diff = MinDifferentDateTime(date1, new Date());
                User user = LoginManager.getInstance().getUser(context);

                if (diff > 0 || user.getPosition().equalsIgnoreCase(Position.STATUS_TEACHER)) {


                    String questionselection = QUESTION_TABLE.EXERCISEID + " = " + "'" + c1.getString(c1.getColumnIndex(EXERCISE_TABLE.EXERCISEID)) + "'";
                    Cursor cursorq = context.getContentResolver().query(MyContentProvider.QUESTION_CONTENT_URI, null, questionselection, null, null);

                    if (cursorq.getCount() > 0) {
                        cursorq.moveToFirst();
                        do {
                            CorrectAnswer correctAnswer = new CorrectAnswer();

                            Question question = new Question();
                            question.setExerciseID(cursorq.getString(cursorq.getColumnIndex(QUESTION_TABLE.EXERCISEID)));
                            question.setProposition(cursorq.getString(cursorq.getColumnIndex(QUESTION_TABLE.PROPOSITION)));
                            question.setQuestionId(cursorq.getString(cursorq.getColumnIndex(QUESTION_TABLE.QUESTIONID)));
                            question.setCorrectID(cursorq.getString(cursorq.getColumnIndex(QUESTION_TABLE.CORRECT)));
                            correctAnswer.setQuestion(question);

                            String select_answer = ANSWER_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "' AND "
                                    + ANSWER_TABLE.CHOICENAME + " = '"+ question.getCorrect() + "'" ;
                            Cursor cursoranswer = context.getContentResolver().query(MyContentProvider.ANSWER_CONTENT_URI, null, select_answer, null, null);

                            if (cursoranswer.getCount() > 0) {
                                cursoranswer.moveToFirst();
                                Answer answer = new Answer();
                                answer.setAnswerId(cursoranswer.getString(cursoranswer.getColumnIndex(ANSWER_TABLE.ANSWERID)));
                                answer.setText(cursoranswer.getString(cursoranswer.getColumnIndex(ANSWER_TABLE.ANSWER)));
                                answer.setChoice(cursoranswer.getString(cursoranswer.getColumnIndex(ANSWER_TABLE.CHOICENAME)));
                                answer.setQuestionfk(cursoranswer.getString(cursoranswer.getColumnIndex(ANSWER_TABLE.QUESTIONID)));
                                correctAnswer.setAnswer(answer);

                                String select_correct = CORRECTANSWER_TABLE.ANSWERID + " = " + "'" + answer.getAnswerId() + "'";
                                Cursor cursor = context.getContentResolver().query(MyContentProvider.CORRECTANSWER_CONTENT_URI, null, select_correct, null, null);

                                if (cursor.getCount() > 0) {
                                    cursor.moveToFirst();
                                    do {
                                        try {

                                            correctAnswer.setCORRECTID(cursor.getString(cursor.getColumnIndex(CORRECTANSWER_TABLE.CORRECTID)));
                                            correctAnswer.setANSDESC(cursor.getString(cursor.getColumnIndex(CORRECTANSWER_TABLE.ANSDESC)));
                                            correctAnswer.setANSWERID(cursor.getString(cursor.getColumnIndex(CORRECTANSWER_TABLE.ANSWERID)));

                                        } catch (Exception e) {
                                            e.toString();
                                        }
                                    } while (cursor.moveToNext());
                                }
                                cursor.close();

                            }
                            cursoranswer.close();

                            mDataSource.add(correctAnswer);

                        }while (cursorq.moveToNext());
                    }
                    cursorq.close();
                }

                Log.d("", "");

                //calExamModel.setNumDate((String) DateFormat.format("dd", convertedDate));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        c1.close();

    }

    public long MinDifferentDateTime(Date LastLocationUpdate, Date CurrentDate) {
        try {

            // in milliseconds
            long diff = CurrentDate.getTime() - LastLocationUpdate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            return diffDays;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

}
