package com.habebe.projecthomework.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.habebe.projecthomework.database.CHAPTER_TABLE;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.SUBJECT_TABLE;

import java.util.ArrayList;

/**
 * Created by yutthanan on 11/5/16.
 */

public class ChapterDatasource {
    private final Context context;
    private ArrayList<ChapterDao> mDataSource = new ArrayList<ChapterDao>();
    private DrawableProvider mProvider;

    public ChapterDatasource(Context context) {
        this.context = context;

    }

    public void getItemFromDummy(String subjectid) {
        mDataSource.clear();

        try {
            ArrayList<String> chapterids = new ArrayList<>();
            String w = CHAPTER_TABLE.SubjectID +" = " + "'" + subjectid + "'";
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(MyContentProvider.CHAPTER_CONTENT_URI, null, w, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String select = EXERCISE_TABLE.CHARPTERID +" = " + "'" + cursor.getString(cursor.getColumnIndex(CHAPTER_TABLE.ChapterID)) + "'";
                    Cursor cursor2 = contentResolver.query(MyContentProvider.EXERCISE_CONTENT_URI, null, select, null, null);
                    if (cursor2.getCount() > 0) {
                        cursor2.moveToFirst();
                        do {
                            ChapterDao chapter = new ChapterDao();
                            chapter.setId(cursor.getString(cursor.getColumnIndex(CHAPTER_TABLE.ChapterID)));
                            chapter.setChaptername(cursor.getString(cursor.getColumnIndex(CHAPTER_TABLE.ChapterName)));
                            chapter.setSubjectid(cursor.getString(cursor.getColumnIndex(CHAPTER_TABLE.SubjectID)));
                            chapter.setExerciseID(cursor2.getString(cursor2.getColumnIndex(EXERCISE_TABLE.EXERCISEID)));
                            chapter.setLevel(cursor2.getString(cursor2.getColumnIndex(EXERCISE_TABLE.Level)));

                            mDataSource.add(chapter);
                        } while (cursor2.moveToNext());
                    }
                    cursor2.close();
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.toString();
        }
    }

    public int getCount() {
        return mDataSource.size();
    }

    public ChapterDao getItem(int position) {
        return mDataSource.get(position);
    }
}
