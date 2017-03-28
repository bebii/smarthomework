package com.habebe.projecthomework.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.SUBJECT_TABLE;

import java.util.ArrayList;

public class SubjectDatasource {
    private static SubjectDatasource instance;
    private Context context;
    private ArrayList<Subject> subjectArrayList = new ArrayList<Subject>();

    public SubjectDatasource(Context context) {
        this.context = context;
    }

    public void getItemFromDummy() {
        subjectArrayList.clear();

        try {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(MyContentProvider.SUBJECT_CONTENT_URI, null, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Subject subject = new Subject();
                    subject.setSUBJECTID(cursor.getString(cursor.getColumnIndex(SUBJECT_TABLE.SUBJECTID)));
                    subject.setSUBJECTNAME(cursor.getString(cursor.getColumnIndex(SUBJECT_TABLE.SUBJECTNAME)));

                    subjectArrayList.add(subject);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.toString();
        }
    }

    public Subject getItem(int position) {
        return subjectArrayList.get(position);
    }

    public int getCount() {
        return subjectArrayList.size();
    }
}
