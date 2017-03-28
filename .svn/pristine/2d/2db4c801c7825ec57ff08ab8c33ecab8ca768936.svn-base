package com.habebe.projecthomework.piechart;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.util.Position;

public class PieModel {

    private int total;
    private int answercorrect;
    private int donothomework;
    private int answerwrong;

    private Context context;
    private int question;

    public PieModel(Context context) {
        this.context = context;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAnswercorrect() {
        return answercorrect;
    }

    public void setAnswercorrect(int answercorrect) {
        this.answercorrect = answercorrect;
    }

    public int getDonothomework() {
        return donothomework;
    }

    public void setDonothomework(int donothomework) {
        this.donothomework = donothomework;
    }

    public int getAnswerwrong() {
        return answerwrong;
    }

    public void setAnswerwrong(int answerwrong) {
        this.answerwrong = answerwrong;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void notifyDataSetChanged() {
        User user = LoginManager.getInstance().getUser(context);
        if (user.getPosition().equalsIgnoreCase(Position.STATUS_STUDENT)) {
            GetDataForStudent();

        } else if (user.getPosition().equalsIgnoreCase(Position.STATUS_PARENT)) {
            GetDataForTeacher();

        }


    }

    private void GetDataForTeacher() {
        int total = 0;
        int correct = 0;
        int wrong = 0;

        try {
            Cursor cHomework = this.context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, null, null, null);
            if (cHomework.getCount() > 0) {
                cHomework.moveToFirst();
                do {
                    String w = EXERCISE_TABLE.EXERCISEID + " = '" + cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.EXERCISEID)) + "'";
                    Cursor cursor0 = this.context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, w, null, null);
                    if (cursor0.getCount() > 0) {
                        cursor0.moveToFirst();
                        total += Integer.parseInt(cursor0.getString(cursor0.getColumnIndex(EXERCISE_TABLE.AMOUNT)));
                        correct += Integer.parseInt(cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.SCORE))) / Integer.parseInt(cursor0.getString(cursor0.getColumnIndex(EXERCISE_TABLE.Level)));

                    }
                    cursor0.close();


                    wrong = total - correct;

                } while (cHomework.moveToNext());
            }

            cHomework.close();


        } catch (Exception e) {

        }

        setTotal(total);
        setAnswercorrect(correct);
        setAnswerwrong(wrong);
        setDonothomework(total - correct - wrong);

    }

    private void GetDataForStudent() {
        int total = 0;
        int correct = 0;
        int wrong = 0;

        try {
            Cursor cHomework = this.context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, null, null, null);
            if (cHomework.getCount() > 0) {
                cHomework.moveToFirst();
                do {
                    total += Integer.parseInt(cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.AMOUNT)));
                    correct += Integer.parseInt(cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.AMOUNTCORRECT)));
                    wrong += Integer.parseInt(cHomework.getString(cHomework.getColumnIndex(HOMEWORKSET_TABLE.AMOUNTWRONG)));

                } while (cHomework.moveToNext());
            }

            cHomework.close();


        } catch (Exception e) {

        }

        setTotal(total);
        setAnswercorrect(correct);
        setAnswerwrong(wrong);
        setDonothomework(total - correct - wrong);
    }

}
