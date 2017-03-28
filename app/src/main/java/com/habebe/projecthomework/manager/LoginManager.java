package com.habebe.projecthomework.manager;

import android.content.Context;
import android.database.Cursor;

import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.USER_TABLE;

/**
 * Created by habebe on 21-Nov-16.
 */

public class LoginManager {
    private static LoginManager instance;

    public static final synchronized LoginManager getInstance() {
        if (instance == null){
            instance = new LoginManager();
        }
        return instance;
    }

    public User getUser(Context context) {
        User user = new User();
        Cursor cursor = context.getContentResolver().query(MyContentProvider.USER_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                user.setFName(cursor.getString(cursor.getColumnIndex(USER_TABLE.NAME)));
                user.setUserID(cursor.getString(cursor.getColumnIndex(USER_TABLE.USERID)));
                user.setPosition(cursor.getString(cursor.getColumnIndex(USER_TABLE.POSITION)));
            }
        }
        return user;
    }
}
