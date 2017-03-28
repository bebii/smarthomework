package com.habebe.projecthomework.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.habebe.projecthomework.dao.homeworkset.Answer;

public class MyContentProvider extends ContentProvider {
    public HomeworkDatabaseHelper databaseHelper;
    // Create Uri for query
    public static final String authorities = "com.habebe.homeworkprovider";
    public static final Uri ANSWER_CONTENT_URI = Uri.parse("content://" + authorities + "/answer");
    public static final Uri CORRECTANSWER_CONTENT_URI = Uri.parse("content://" + authorities + "/correctanswer");
    public static final Uri EXAMINATION_CONTENT_URI = Uri.parse("content://" + authorities + "/examination");
    public static final Uri EXERCISE_CONTENT_URI = Uri.parse("content://" + authorities + "/exercise");
    public static final Uri HOMEWORKSET_CONTENT_URI = Uri.parse("content://" + authorities + "/homeworkset");
    public static final Uri QUESTION_CONTENT_URI = Uri.parse("content://" + authorities + "/question");
    public static final Uri REGISTER_CONTENT_URI = Uri.parse("content://" + authorities + "/register");
    public static final Uri STUDENTANSWER_CONTENT_URI = Uri.parse("content://" + authorities + "/studentanswer");
    public static final Uri SUBJECT_CONTENT_URI = Uri.parse("content://" + authorities + "/subject");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + authorities + "/user");
    public static final Uri CHAPTER_CONTENT_URI = Uri.parse("content://" + authorities + "/chapter");

    public static final int ANSWER = 1;
    public static final int CORRECTANSWER = 2;
    public static final int EXAMINATION = 3;
    public static final int EXERCISE = 4;
    public static final int HOMEWORKSET = 5;
    public static final int QUESTION = 6;
    public static final int REGISTER = 7;
    public static final int STUDENTANSWER = 8;
    public static final int SUBJECT = 9;
    public static final int USER = 10;
    public static final int CHAPTER = 11;

    private static final UriMatcher uriMatcher;

    // Allocate the UriMatcher object, where a URI ending in 'earthquakes' will
    // correspond to a request for all earthquakes, and 'earthquakes' with a
    // trailing '/[rowID]' will represent a single earthquake row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authorities, "answer", ANSWER);
        uriMatcher.addURI(authorities, "correctanswer", CORRECTANSWER);
        uriMatcher.addURI(authorities, "examination", EXAMINATION);
        uriMatcher.addURI(authorities, "exercise", EXERCISE);
        uriMatcher.addURI(authorities, "homeworkset", HOMEWORKSET);
        uriMatcher.addURI(authorities, "question", QUESTION);
        uriMatcher.addURI(authorities, "register", REGISTER);
        uriMatcher.addURI(authorities, "studentanswer", STUDENTANSWER);
        uriMatcher.addURI(authorities, "subject", SUBJECT);
        uriMatcher.addURI(authorities, "user", USER);
        uriMatcher.addURI(authorities, "chapter", CHAPTER);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int count;
        switch (uriMatcher.match(uri)) {
            case ANSWER:
                count = database.delete(ANSWER_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case CORRECTANSWER:
                count = database.delete(CORRECTANSWER_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case EXAMINATION:
                count = database.delete(EXAMINATION_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case EXERCISE:
                count = database.delete(EXERCISE_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case HOMEWORKSET:
                count = database.delete(HOMEWORKSET_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case QUESTION:
                count = database.delete(QUESTION_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case REGISTER:
                count = database.delete(REGISTER_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case STUDENTANSWER:
                count = database.delete(STUDENTANSWER_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case SUBJECT:
                count = database.delete(SUBJECT_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case USER:
                count = database.delete(USER_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            case CHAPTER:
                count = database.delete(CHAPTER_TABLE.TABLE, selection,
                        selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case ANSWER:
                return "vnd.android.cursor.dir/vnd.projecthomework.answer";
            case CORRECTANSWER:
                return "vnd.android.cursor.dir/vnd.projecthomework.correctanswer";
            case EXAMINATION:
                return "vnd.android.cursor.dir/vnd.projecthomework.examination";
            case EXERCISE:
                return "vnd.android.cursor.dir/vnd.projecthomework.exercise";
            case HOMEWORKSET:
                return "vnd.android.cursor.dir/vnd.projecthomework.homeworkset";
            case QUESTION:
                return "vnd.android.cursor.dir/vnd.projecthomework.question";
            case REGISTER:
                return "vnd.android.cursor.dir/vnd.projecthomework.register";
            case STUDENTANSWER:
                return "vnd.android.cursor.dir/vnd.projecthomework.studentanswer";
            case SUBJECT:
                return "vnd.android.cursor.dir/vnd.projecthomework.subject";
            case USER:
                return "vnd.android.cursor.dir/vnd.projecthomework.user";
            case CHAPTER:
                return "vnd.android.cursor.dir/vnd.projecthomework.chapter";
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case ANSWER:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long answerrowID = database.insert(ANSWER_TABLE.TABLE,
                        "answer", values);

                // Return a URI to the newly inserted row on success.
                if (answerrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(ANSWER_CONTENT_URI,
                            answerrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case CORRECTANSWER:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long correctanswerrowID = database.insert(CORRECTANSWER_TABLE.TABLE,
                        "correctanswer", values);

                // Return a URI to the newly inserted row on success.
                if (correctanswerrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(CORRECTANSWER_CONTENT_URI,
                            correctanswerrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case EXAMINATION:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long EXAMINATIONrowID = database.insert(EXAMINATION_TABLE.TABLE,
                        "examination", values);

                // Return a URI to the newly inserted row on success.
                if (EXAMINATIONrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(EXAMINATION_CONTENT_URI,
                            EXAMINATIONrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case EXERCISE:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long EXERCISErowID = database.insert(EXERCISE_TABLE.TABLE,
                        "exercise", values);

                // Return a URI to the newly inserted row on success.
                if (EXERCISErowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(EXERCISE_CONTENT_URI,
                            EXERCISErowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case HOMEWORKSET:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long HOMEWORKSETrowID = database.insert(HOMEWORKSET_TABLE.TABLE,
                        "homeworkset", values);

                // Return a URI to the newly inserted row on success.
                if (HOMEWORKSETrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(HOMEWORKSET_CONTENT_URI,
                            HOMEWORKSETrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case QUESTION:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long QUESTIONrowID = database.insert(QUESTION_TABLE.TABLE,
                        "question", values);

                // Return a URI to the newly inserted row on success.
                if (QUESTIONrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(QUESTION_CONTENT_URI,
                            QUESTIONrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case REGISTER:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long REGISTERrowID = database.insert(REGISTER_TABLE.TABLE,
                        "register", values);

                // Return a URI to the newly inserted row on success.
                if (REGISTERrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(REGISTER_CONTENT_URI,
                            REGISTERrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case STUDENTANSWER:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long STUDENTANSWERrowID = database.insert(STUDENTANSWER_TABLE.TABLE,
                        "studentanswer", values);

                // Return a URI to the newly inserted row on success.
                if (STUDENTANSWERrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(STUDENTANSWER_CONTENT_URI,
                            STUDENTANSWERrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case SUBJECT:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long SUBJECTrowID = database.insert(SUBJECT_TABLE.TABLE,
                        "subject", values);

                // Return a URI to the newly inserted row on success.
                if (SUBJECTrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(SUBJECT_CONTENT_URI,
                            SUBJECTrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case USER:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long USERrowID = database.insert(USER_TABLE.TABLE,
                        "user", values);

                // Return a URI to the newly inserted row on success.
                if (USERrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(USER_CONTENT_URI,
                            USERrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;

            case CHAPTER:
                // Insert the new row. The call to database.insert will return the
                // row number
                // if it is successful.
                long CHAPTERrowID = database.insert(CHAPTER_TABLE.TABLE,
                        "chapter", values);

                // Return a URI to the newly inserted row on success.
                if (CHAPTERrowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(CHAPTER_CONTENT_URI,
                            CHAPTERrowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return uri;
                }
                break;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();

        databaseHelper = new HomeworkDatabaseHelper(context,
                databaseHelper.DATABASE_NAME, null,
                databaseHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // If this is a row query, limit the result set to the passed in row.
        switch (uriMatcher.match(uri)) {
            case ANSWER:
                qb.setTables(ANSWER_TABLE.TABLE);
                break;

            case CORRECTANSWER:
                qb.setTables(CORRECTANSWER_TABLE.TABLE);
                break;

            case EXAMINATION:
                qb.setTables(EXAMINATION_TABLE.TABLE);
                break;

            case EXERCISE:
                qb.setTables(EXERCISE_TABLE.TABLE);
                break;

            case HOMEWORKSET:
                qb.setTables(HOMEWORKSET_TABLE.TABLE);
                break;

            case QUESTION:
                qb.setTables(QUESTION_TABLE.TABLE);
                break;

            case REGISTER:
                qb.setTables(REGISTER_TABLE.TABLE);
                break;

            case STUDENTANSWER:
                qb.setTables(STUDENTANSWER_TABLE.TABLE);
                break;

            case SUBJECT:
                qb.setTables(SUBJECT_TABLE.TABLE);
                break;

            case USER:
                qb.setTables(USER_TABLE.TABLE);
                break;

            case CHAPTER:
                qb.setTables(CHAPTER_TABLE.TABLE);
                break;
            default:
                break;
        }

        // Apply the query to the underlying database.
        Cursor c = qb.query(database, projection, selection, selectionArgs,
                null, null, sortOrder);

        // Cursor c =
        // database.rawQuery("select * from "+SkyFrogDatabaseHelper.JOBH_TABLE,
        // null);

        // Register the contexts ContentResolver to be notified if
        // the cursor result set changes.
        c.setNotificationUri(getContext().getContentResolver(), uri);

        // Return a cursor to the query result.
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int count;
        switch (uriMatcher.match(uri)) {
            case ANSWER:
                count = database.update(ANSWER_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case CORRECTANSWER:
                count = database.update(CORRECTANSWER_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case EXAMINATION:
                count = database.update(EXAMINATION_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case EXERCISE:
                count = database.update(EXERCISE_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case HOMEWORKSET:
                count = database.update(HOMEWORKSET_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case QUESTION:
                count = database.update(QUESTION_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case REGISTER:
                count = database.update(REGISTER_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case STUDENTANSWER:
                count = database.update(STUDENTANSWER_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case SUBJECT:
                count = database.update(SUBJECT_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            case USER:
                count = database.update(USER_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;

            case CHAPTER:
                count = database.update(CHAPTER_TABLE.TABLE, values,
                        selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        //Update uri
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
