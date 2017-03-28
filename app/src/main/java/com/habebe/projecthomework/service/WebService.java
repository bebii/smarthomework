package com.habebe.projecthomework.service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.activity.LauncherActivity;
import com.habebe.projecthomework.activity.MainActivity;
import com.habebe.projecthomework.dao.ChapterDao;
import com.habebe.projecthomework.dao.CorrectAnswer;
import com.habebe.projecthomework.dao.ExamDateItemDao;
import com.habebe.projecthomework.dao.Exercise;
import com.habebe.projecthomework.dao.Subject;
import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.dao.homeworkset.Answer;
import com.habebe.projecthomework.dao.homeworkset.Homeworkset;
import com.habebe.projecthomework.dao.homeworkset.Question;
import com.habebe.projecthomework.database.ANSWER_TABLE;
import com.habebe.projecthomework.database.CHAPTER_TABLE;
import com.habebe.projecthomework.database.CORRECTANSWER_TABLE;
import com.habebe.projecthomework.database.EXAMINATION_TABLE;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.QUESTION_TABLE;
import com.habebe.projecthomework.database.SUBJECT_TABLE;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.util.ConstanstURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.habebe.projecthomework.R.string.question;
import static com.habebe.projecthomework.R.string.username;

public class WebService {

    private Object answer;
    private Object student;
    private Object exercise;
    private Object questionCorrect;
    private Object chapter;
    private Object homeworkset;
    private Object data;

    public void CheckStatusLogin() {

        RequestBody body = new FormBody.Builder()
                .add("username", "bebe")
                .add("password", "99999")
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(ConstanstURL.apiDomainName + "/login.php")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result;
                try {
                    result = response.body().string();
                    /*Gson gson = new Gson();
                    LoginResponseDao dao = gson.fromJson(result, LoginResponseDao.class);
                    if (dao.isSuccess()) {
                        *//*createUserSession(dao);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();*//*
                    } else {
                        //Toast.makeText(getApplicationContext(), dao.getMessage(), Toast.LENGTH_SHORT).show();
                    }*/
                    Log.d("result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void SubjectOfTeacher() {

    }

    public void SetStudentAnswer() {
    }

    public Object getQuestion() {
        return question;
    }

    public Object getAnswer() {
        return answer;
    }

    public Object getStudent() {
        return student;
    }

    public Object getExercise() {
        return exercise;
    }

    public Object getCorrectAnswer() {
        return questionCorrect;
    }

    public Object getChapter() {
        return chapter;
    }

    public Object getHomeworkset() {
        return homeworkset;
    }

    public Object getDataForStudent(final Context context) {
        RequestBody body = new FormBody.Builder()
                .add("username", "bebe")
                .add("password", "99999")
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(ConstanstURL.apiDomainName + "/GetItemForStudent")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result;
                try {
                    ContentResolver cr = context.getContentResolver();
                    result = response.body().string();

                    //Subject
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Subject");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Subject subject = new Subject();
                            subject.setSUBJECTID(jsonObject.getString("SubjectID"));
                            subject.setSUBJECTNAME(jsonObject.getString("SubjectName"));

                            String w = SUBJECT_TABLE.SUBJECTID + " = " + "'" + subject.getSUBJECTID() + "'";
                            Cursor cursor = cr.query(MyContentProvider.SUBJECT_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(SUBJECT_TABLE.SUBJECTID, subject.getSUBJECTID());
                            values.put(SUBJECT_TABLE.SUBJECTNAME, subject.getSUBJECTNAME());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.SUBJECT_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.SUBJECT_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Chapter
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Chapter");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ChapterDao chapterDao = new ChapterDao();
                            chapterDao.setId(jsonObject.getString("ChapterID"));
                            chapterDao.setChaptername(jsonObject.getString("ChapterName"));
                            chapterDao.setSubjectid(jsonObject.getString("Subjectfk"));

                            String w = CHAPTER_TABLE.ChapterID + " = " + "'" + chapterDao.getId() + "'";
                            Cursor cursor = cr.query(MyContentProvider.CHAPTER_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(CHAPTER_TABLE.ChapterID, chapterDao.getId());
                            values.put(CHAPTER_TABLE.ChapterName, chapterDao.getChaptername());
                            values.put(CHAPTER_TABLE.SubjectID, chapterDao.getSubjectid());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.CHAPTER_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.CHAPTER_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Answer
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Answer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Answer answer = new Answer();
                            answer.setAnswerId(jsonObject.getString("AnswerID"));
                            answer.setChoice(jsonObject.getString("Choice"));
                            answer.setText(jsonObject.getString("Answer"));
                            answer.setQuestionfk(jsonObject.getString("Questionfk"));

                            String w = ANSWER_TABLE.ANSWERID + " = " + "'" + answer.getAnswerId() + "'";
                            Cursor cursor = cr.query(MyContentProvider.ANSWER_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(ANSWER_TABLE.ANSWERID, answer.getAnswerId());
                            values.put(ANSWER_TABLE.ANSWER, answer.getText());
                            values.put(ANSWER_TABLE.CHOICENAME, answer.getChoice());
                            values.put(ANSWER_TABLE.QUESTIONID, answer.getQuestionfk());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.ANSWER_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.ANSWER_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //CorrectAnswer
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("CorrectAnswer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            CorrectAnswer correctAnswer = new CorrectAnswer();
                            correctAnswer.setCORRECTID(jsonObject.getString("CorrectID"));
                            correctAnswer.setANSWERID(jsonObject.getString("Answerfk"));
                            correctAnswer.setANSDESC(jsonObject.getString("AnsDesc"));

                            String w = CORRECTANSWER_TABLE.ANSWERID + " = " + "'" + correctAnswer.getCORRECTID() + "'";
                            Cursor cursor = cr.query(MyContentProvider.CORRECTANSWER_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(CORRECTANSWER_TABLE.CORRECTID, correctAnswer.getCORRECTID());
                            values.put(CORRECTANSWER_TABLE.ANSWERID, correctAnswer.getANSWERID());
                            values.put(CORRECTANSWER_TABLE.ANSDESC, correctAnswer.getANSDESC());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.CORRECTANSWER_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.CORRECTANSWER_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Question
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Question");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Question question = new Question();
                            question.setQuestionId(jsonObject.getString("QuestionID"));
                            question.setProposition(jsonObject.getString("Proposition"));
                            question.setExerciseID(jsonObject.getString("Exercisefk"));
                            question.setCorrectID(jsonObject.getString("Correct"));
                            question.setAnsDesc(jsonObject.getString("AnsDesc"));

                            String w = QUESTION_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "'";
                            Cursor cursor = cr.query(MyContentProvider.QUESTION_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(QUESTION_TABLE.QUESTIONID, question.getQuestionId());
                            values.put(QUESTION_TABLE.PROPOSITION, question.getProposition());
                            values.put(QUESTION_TABLE.EXERCISEID, question.getExerciseID());
                            values.put(QUESTION_TABLE.CORRECT, question.getCorrect());
                            values.put(QUESTION_TABLE.ANSDESC, question.getAnsDesc());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.QUESTION_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.QUESTION_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Exercise
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Exercise");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Exercise exercise = new Exercise();
                            exercise.setEXERCISEID(jsonObject.getString("ExerID"));
                            exercise.setCHARPTERID(jsonObject.getString("ChapterID"));
                            exercise.setDODATE(jsonObject.getString("DoDate"));
                            exercise.setEXPDATE(jsonObject.getString("ExpDate"));
                            exercise.setAMOUNT(jsonObject.getString("AmountDo"));
                            exercise.setScore(jsonObject.getString("Score"));
                            exercise.setLevel(jsonObject.getString("Level"));

                            String w = EXERCISE_TABLE.EXERCISEID + " = " + "'" + exercise.getEXERCISEID() + "'";
                            Cursor cursor = cr.query(MyContentProvider.EXERCISE_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(EXERCISE_TABLE.EXERCISEID, exercise.getEXERCISEID());
                            values.put(EXERCISE_TABLE.CHARPTERID, exercise.getCHARPTERID());
                            values.put(EXERCISE_TABLE.DODATE, exercise.getDODATE());
                            values.put(EXERCISE_TABLE.EXPDATE, exercise.getEXPDATE());
                            values.put(EXERCISE_TABLE.AMOUNT, exercise.getAMOUNT());
                            values.put(EXERCISE_TABLE.Score, exercise.getScore());
                            values.put(EXERCISE_TABLE.Level, exercise.getLevel());

                            if (cursor.getCount() == 0) {
                                try {
                                    context.sendBroadcast(new Intent(MainActivity.UpdateGraph));

                                    cr.insert(MyContentProvider.EXERCISE_CONTENT_URI, values);

                                    String selection = QUESTION_TABLE.EXERCISEID + " = " + "'" + exercise.getEXERCISEID() + "'";
                                    ArrayList<Question> list = new ArrayList<>();
                                    Cursor cQuestion = context.getContentResolver().query(MyContentProvider.QUESTION_CONTENT_URI, null, selection, null, null);
                                    if (cQuestion != null) {
                                        if (cQuestion.getCount() > 0) {
                                            cQuestion.moveToFirst();
                                            do {
                                                Question question = new Question();
                                                question.setQuestionId(cQuestion.getString(cQuestion.getColumnIndex(QUESTION_TABLE.QUESTIONID)));
                                                question.setProposition(cQuestion.getString(cQuestion.getColumnIndex(QUESTION_TABLE.PROPOSITION)));
                                                question.setExerciseID(cQuestion.getString(cQuestion.getColumnIndex(QUESTION_TABLE.EXERCISEID)));
                                                question.setCorrectID(cQuestion.getString(cQuestion.getColumnIndex(QUESTION_TABLE.CORRECT)));
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

                                                list.add(question);

                                            } while (cQuestion.moveToNext());
                                        }
                                        cQuestion.close();


                                        // Random Question
                                        Collections.shuffle(list);

                                        int HID = 1;
                                        // Insert Homeworkset Data
                                        Cursor cGetIDHomework = context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, null, null, null);
                                        if (cGetIDHomework.getCount() > 0) {
                                            cGetIDHomework.moveToLast();
                                            HID = Integer.parseInt(cGetIDHomework.getString(cGetIDHomework.getColumnIndex(HOMEWORKSET_TABLE.HSETID))) + 1;
                                        }

                                        ContentValues values1 = new ContentValues();
                                        values1.put(HOMEWORKSET_TABLE.EXERCISEID, exercise.getEXERCISEID());
                                        values1.put(HOMEWORKSET_TABLE.HSETID, HID);
                                        values1.put(HOMEWORKSET_TABLE.COMMITED, "N");
                                        values1.put(HOMEWORKSET_TABLE.AMOUNT, exercise.getAMOUNT());

                                        context.getContentResolver().insert(MyContentProvider.HOMEWORKSET_CONTENT_URI, values1);
                                        cGetIDHomework.close();

                                        String exercisseselect = EXERCISE_TABLE.EXERCISEID + " = " + "'" + exercise.getEXERCISEID() + "'";
                                        Cursor cursorexercise = context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, exercisseselect, null, null);
                                        if (cursorexercise.getCount() > 0) {
                                            cursorexercise.moveToFirst();
                                            int amount = Integer.parseInt(cursorexercise.getString(cursorexercise.getColumnIndex(EXERCISE_TABLE.AMOUNT)));
                                            cursorexercise.close();

                                            for (int j = 0; j < amount; j++) {
                                                Question question = list.get(j);

                                                String updatequestion = QUESTION_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "'";
                                                ContentValues values_question = new ContentValues();
                                                values_question.put(QUESTION_TABLE.HSETID, HID);
                                                context.getContentResolver().update(MyContentProvider.QUESTION_CONTENT_URI, values_question, updatequestion, null);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.EXERCISE_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Exam
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Exam");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            ExamDateItemDao examDateItemDao = new ExamDateItemDao();
                            examDateItemDao.setExamid(jsonObject.getString("ExamID"));
                            examDateItemDao.setChapter(jsonObject.getString("Chapterfk"));
                            examDateItemDao.setDetail(jsonObject.getString("Description"));
                            examDateItemDao.setExamSent(jsonObject.getString("ExamDate"));

                            String w = EXAMINATION_TABLE.EXAMID + " = " + "'" + examDateItemDao.getExamid() + "'";
                            Cursor cursor = cr.query(MyContentProvider.EXAMINATION_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(EXAMINATION_TABLE.EXAMID, examDateItemDao.getExamid());
                            values.put(EXAMINATION_TABLE.CHAPTERID, examDateItemDao.getChapter());
                            values.put(EXAMINATION_TABLE.DESCRIPTION, examDateItemDao.getDetail());
                            values.put(EXAMINATION_TABLE.EXAMDATE, examDateItemDao.getExamdate());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.EXAMINATION_CONTENT_URI, values);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.EXAMINATION_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            String w2 = EXAMINATION_TABLE.EXAMID + " = " + "'" + examDateItemDao.getExamid() + "' AND " + EXAMINATION_TABLE.ADDEVENTED + " = 'Y'";
                            Cursor cursor2 = cr.query(MyContentProvider.EXAMINATION_CONTENT_URI, null, w2, null, null);
                            if (cursor2.getCount() == 0) {
                                try {
                                    try {
                                        // get calendar
                                        Calendar cal = Calendar.getInstance();
                                        Uri EVENTS_URI = LauncherActivity.EVENTS_URI;

                                        Calendar calendar = Calendar.getInstance();
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        Date convertedDate = new Date();
                                        try {
                                            convertedDate = dateFormat.parse(examDateItemDao.getExamdate());
                                            //calExamModel.setNumDate((String) DateFormat.format("dd", convertedDate));
                                        } catch (ParseException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        // event insert
                                        ContentValues values2 = new ContentValues();
                                        values2.put("calendar_id", 1);
                                        values2.put("title", examDateItemDao.getDetail());
                                        values2.put("allDay", 0);
                                        values2.put("dtstart", convertedDate.getTime() + 11 * 60 * 1000); // event starts at 11 minutes from now
                                        values2.put("dtend", convertedDate.getTime() + 60 * 60 * 1000); // ends 60 minutes from now
                                        values2.put("description", examDateItemDao.getSubject());
                                        values2.put("hasAlarm", 1);

                                        TimeZone timeZone = TimeZone.getDefault();
                                        values2.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                                        Uri event = cr.insert(EVENTS_URI, values2);

                                        // reminder insert
                                        Uri REMINDERS_URI = LauncherActivity.REMINDERS_URI;
                                        values2 = new ContentValues();
                                        values2.put("event_id", Long.parseLong(event.getLastPathSegment()));
                                        values2.put("method", 1);
                                        values2.put("minutes", 10);
                                        cr.insert(REMINDERS_URI, values2);

                                        values.put(EXAMINATION_TABLE.ADDEVENTED, "Y");
                                        cr.update(MyContentProvider.EXAMINATION_CONTENT_URI, values, w, null);

                                    } catch (Exception e) {
                                        e.toString();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            cursor2.close();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }
                    Log.d("result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return data;
    }

    public void getDataForTeacher(final Context context) {
        User user = LoginManager.getInstance().getUser(context);
        RequestBody body = new FormBody.Builder()
                .add("teacherid", user.getUserID())
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(ConstanstURL.apiDomainName + "/GetItemForTeacher")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result;
                try {
                    ContentResolver cr = context.getContentResolver();
                    result = response.body().string();

                    //Subject
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Subject");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Subject subject = new Subject();
                            subject.setSUBJECTID(jsonObject.getString("SubjectID"));
                            subject.setSUBJECTNAME(jsonObject.getString("SubjectName"));

                            String w = SUBJECT_TABLE.SUBJECTID + " = " + "'" + subject.getSUBJECTID() + "'";
                            Cursor cursor = cr.query(MyContentProvider.SUBJECT_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(SUBJECT_TABLE.SUBJECTID, subject.getSUBJECTID());
                            values.put(SUBJECT_TABLE.SUBJECTNAME, subject.getSUBJECTNAME());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.SUBJECT_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.SUBJECT_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Chapter
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Chapter");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ChapterDao chapterDao = new ChapterDao();
                            chapterDao.setId(jsonObject.getString("ChapterID"));
                            chapterDao.setChaptername(jsonObject.getString("ChapterName"));
                            chapterDao.setSubjectid(jsonObject.getString("Subjectfk"));

                            String w0 = SUBJECT_TABLE.SUBJECTID + " = " + "'" + chapterDao.getSubjectid() + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.SUBJECT_CONTENT_URI, null, w0, null, null);

                            if (cursor0.getCount() > 0) {
                                String w1 = CHAPTER_TABLE.ChapterID + " = " + "'" + chapterDao.getId() + "'";
                                Cursor cursor1 = cr.query(MyContentProvider.CHAPTER_CONTENT_URI, null, w1, null, null);

                                ContentValues values = new ContentValues();
                                values.put(CHAPTER_TABLE.ChapterID, chapterDao.getId());
                                values.put(CHAPTER_TABLE.ChapterName, chapterDao.getChaptername());
                                values.put(CHAPTER_TABLE.SubjectID, chapterDao.getSubjectid());

                                if (cursor1.getCount() == 0) {
                                    try {
                                        cr.insert(MyContentProvider.CHAPTER_CONTENT_URI, values);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        cr.update(MyContentProvider.CHAPTER_CONTENT_URI, values, w1, null);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            cursor0.close();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Exercise
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Exercise");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Exercise exercise = new Exercise();
                            exercise.setEXERCISEID(jsonObject.getString("ExerID"));
                            exercise.setCHARPTERID(jsonObject.getString("ChapterID"));
                            exercise.setDODATE(jsonObject.getString("DoDate"));
                            exercise.setEXPDATE(jsonObject.getString("ExpDate"));
                            exercise.setAMOUNT(jsonObject.getString("AmountDo"));
                            exercise.setScore(jsonObject.getString("Score"));
                            exercise.setLevel(jsonObject.getString("Level"));

                            String w0 = CHAPTER_TABLE.ChapterID + " = " + "'" + exercise.getCHARPTERID() + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.CHAPTER_CONTENT_URI, null, w0, null, null);

                            if (cursor0.getCount() > 0) {
                                String w = EXERCISE_TABLE.EXERCISEID + " = " + "'" + exercise.getEXERCISEID() + "'";
                                Cursor cursor = cr.query(MyContentProvider.EXERCISE_CONTENT_URI, null, w, null, null);

                                ContentValues values = new ContentValues();
                                values.put(EXERCISE_TABLE.EXERCISEID, exercise.getEXERCISEID());
                                values.put(EXERCISE_TABLE.CHARPTERID, exercise.getCHARPTERID());
                                values.put(EXERCISE_TABLE.DODATE, exercise.getDODATE());
                                values.put(EXERCISE_TABLE.EXPDATE, exercise.getEXPDATE());
                                values.put(EXERCISE_TABLE.AMOUNT, exercise.getAMOUNT());
                                values.put(EXERCISE_TABLE.Score, exercise.getScore());
                                values.put(EXERCISE_TABLE.Level, exercise.getLevel());

                                if (cursor.getCount() == 0) {
                                    try {
                                        context.sendBroadcast(new Intent(MainActivity.UpdateGraph));

                                        cr.insert(MyContentProvider.EXERCISE_CONTENT_URI, values);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        cr.update(MyContentProvider.EXERCISE_CONTENT_URI, values, w, null);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                            cursor0.close();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Question
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Question");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Question question = new Question();
                            question.setQuestionId(jsonObject.getString("QuestionID"));
                            question.setProposition(jsonObject.getString("Proposition"));
                            question.setExerciseID(jsonObject.getString("Exercisefk"));
                            question.setCorrectID(jsonObject.getString("Correct"));
                            question.setAnsDesc(jsonObject.getString("AnsDesc"));

                            String w0 = EXERCISE_TABLE.EXERCISEID + " = " + "'" + question.getExerciseID() + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.EXERCISE_CONTENT_URI, null, w0, null, null);

                            if (cursor0.getCount() > 0) {
                                String w = QUESTION_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "'";
                                Cursor cursor = cr.query(MyContentProvider.QUESTION_CONTENT_URI, null, w, null, null);

                                ContentValues values = new ContentValues();
                                values.put(QUESTION_TABLE.QUESTIONID, question.getQuestionId());
                                values.put(QUESTION_TABLE.PROPOSITION, question.getProposition());
                                values.put(QUESTION_TABLE.EXERCISEID, question.getExerciseID());
                                values.put(QUESTION_TABLE.CORRECT, question.getCorrect());
                                values.put(QUESTION_TABLE.ANSDESC, question.getAnsDesc());

                                if (cursor.getCount() == 0) {
                                    try {
                                        cr.insert(MyContentProvider.QUESTION_CONTENT_URI, values);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        cr.update(MyContentProvider.QUESTION_CONTENT_URI, values, w, null);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            cursor0.close();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Homeworkset
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Homeworkset");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Homeworkset homeworkset = new Homeworkset();
                            homeworkset.setHomeworksetId(jsonObject.getString("HsetID"));
                            homeworkset.setScore(jsonObject.getString("Score"));

                            // update hsetid on question
                            String w0 = QUESTION_TABLE.QUESTIONID + " = " + "'" + jsonObject.getString("Questionfk") + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.QUESTION_CONTENT_URI, null, w0, null, null);

                            ContentValues values0 = new ContentValues();
                            values0.put(QUESTION_TABLE.HSETID, homeworkset.getHomeworksetId());

                            if (cursor0.getCount() > 0) {
                                try {
                                    cr.update(MyContentProvider.QUESTION_CONTENT_URI, values0, w0, null);

                                    cursor0.moveToFirst();

                                    String w = HOMEWORKSET_TABLE.HSETID + " = " + "'" + homeworkset.getHomeworksetId() + "'";
                                    Cursor cursor = cr.query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, w, null, null);

                                    ContentValues values1 = new ContentValues();
                                    values1.put(HOMEWORKSET_TABLE.HSETID, homeworkset.getHomeworksetId());
                                    values1.put(HOMEWORKSET_TABLE.EXERCISEID, cursor0.getString(cursor0.getColumnIndex(QUESTION_TABLE.EXERCISEID)));
                                    values1.put(HOMEWORKSET_TABLE.SCORE, homeworkset.getScore());

                                    if (cursor.getCount() == 0) {
                                        try {
                                            cr.insert(MyContentProvider.HOMEWORKSET_CONTENT_URI, values1);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            cr.update(MyContentProvider.HOMEWORKSET_CONTENT_URI, values1, w, null);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            cursor0.close();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Answer
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Answer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Answer answer = new Answer();
                            answer.setAnswerId(jsonObject.getString("AnswerID"));
                            answer.setChoice(jsonObject.getString("Choice"));
                            answer.setText(jsonObject.getString("Answer"));
                            answer.setQuestionfk(jsonObject.getString("Questionfk"));

                            String w0 = QUESTION_TABLE.QUESTIONID + " = " + "'" + answer.getQuestionfk() + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.QUESTION_CONTENT_URI, null, w0, null, null);

                            if (cursor0.getCount() > 0) {
                                String w = ANSWER_TABLE.ANSWERID + " = " + "'" + answer.getAnswerId() + "'";
                                Cursor cursor = cr.query(MyContentProvider.ANSWER_CONTENT_URI, null, w, null, null);

                                ContentValues values = new ContentValues();
                                values.put(ANSWER_TABLE.ANSWERID, answer.getAnswerId());
                                values.put(ANSWER_TABLE.ANSWER, answer.getText());
                                values.put(ANSWER_TABLE.CHOICENAME, answer.getChoice());
                                values.put(ANSWER_TABLE.QUESTIONID, answer.getQuestionfk());

                                if (cursor.getCount() == 0) {
                                    try {
                                        cr.insert(MyContentProvider.ANSWER_CONTENT_URI, values);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        cr.update(MyContentProvider.ANSWER_CONTENT_URI, values, w, null);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            cursor0.close();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //CorrectAnswer
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("CorrectAnswer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            CorrectAnswer correctAnswer = new CorrectAnswer();
                            correctAnswer.setCORRECTID(jsonObject.getString("CorrectID"));
                            correctAnswer.setANSWERID(jsonObject.getString("Answerfk"));
                            correctAnswer.setANSDESC(jsonObject.getString("AnsDesc"));

                            String w0 = ANSWER_TABLE.ANSWERID + " = " + "'" + correctAnswer.getANSWERID() + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.ANSWER_CONTENT_URI, null, w0, null, null);

                            if (cursor0.getCount() > 0) {
                                String w = CORRECTANSWER_TABLE.ANSWERID + " = " + "'" + correctAnswer.getCORRECTID() + "'";
                                Cursor cursor = cr.query(MyContentProvider.CORRECTANSWER_CONTENT_URI, null, w, null, null);

                                ContentValues values = new ContentValues();
                                values.put(CORRECTANSWER_TABLE.CORRECTID, correctAnswer.getCORRECTID());
                                values.put(CORRECTANSWER_TABLE.ANSWERID, correctAnswer.getANSWERID());
                                values.put(CORRECTANSWER_TABLE.ANSDESC, correctAnswer.getANSDESC());

                                if (cursor.getCount() == 0) {
                                    try {
                                        cr.insert(MyContentProvider.CORRECTANSWER_CONTENT_URI, values);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        cr.update(MyContentProvider.CORRECTANSWER_CONTENT_URI, values, w, null);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            cursor0.close();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Exam
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Exam");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            ExamDateItemDao examDateItemDao = new ExamDateItemDao();
                            examDateItemDao.setExamid(jsonObject.getString("ExamID"));
                            examDateItemDao.setChapter(jsonObject.getString("Chapterfk"));
                            examDateItemDao.setDetail(jsonObject.getString("Description"));
                            examDateItemDao.setExamSent(jsonObject.getString("ExamDate"));

                            String w0 = CHAPTER_TABLE.ChapterID + " = " + "'" + examDateItemDao.getChapter() + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.CHAPTER_CONTENT_URI, null, w0, null, null);

                            if (cursor0.getCount() > 0) {
                                String w = EXAMINATION_TABLE.EXAMID + " = " + "'" + examDateItemDao.getExamid() + "'";
                                Cursor cursor = cr.query(MyContentProvider.EXAMINATION_CONTENT_URI, null, w, null, null);

                                ContentValues values = new ContentValues();
                                values.put(EXAMINATION_TABLE.EXAMID, examDateItemDao.getExamid());
                                values.put(EXAMINATION_TABLE.CHAPTERID, examDateItemDao.getChapter());
                                values.put(EXAMINATION_TABLE.DESCRIPTION, examDateItemDao.getDetail());
                                values.put(EXAMINATION_TABLE.EXAMDATE, examDateItemDao.getExamdate());

                                if (cursor.getCount() == 0) {
                                    try {
                                        cr.insert(MyContentProvider.EXAMINATION_CONTENT_URI, values);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        cr.update(MyContentProvider.EXAMINATION_CONTENT_URI, values, w, null);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                String w2 = EXAMINATION_TABLE.EXAMID + " = " + "'" + examDateItemDao.getExamid() + "' AND " + EXAMINATION_TABLE.ADDEVENTED + " = 'Y'";
                                Cursor cursor2 = cr.query(MyContentProvider.EXAMINATION_CONTENT_URI, null, w2, null, null);
                                if (cursor2.getCount() == 0) {
                                    try {
                                        try {
                                            // get calendar
                                            Calendar cal = Calendar.getInstance();
                                            Uri EVENTS_URI = LauncherActivity.EVENTS_URI;

                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            Date convertedDate = new Date();
                                            try {
                                                convertedDate = dateFormat.parse(examDateItemDao.getExamdate());
                                                //calExamModel.setNumDate((String) DateFormat.format("dd", convertedDate));
                                            } catch (ParseException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }

                                            // event insert
                                            ContentValues values2 = new ContentValues();
                                            values2.put("calendar_id", 1);
                                            values2.put("title", examDateItemDao.getDetail());
                                            values2.put("allDay", 0);
                                            values2.put("dtstart", convertedDate.getTime() + 11 * 60 * 1000); // event starts at 11 minutes from now
                                            values2.put("dtend", convertedDate.getTime() + 60 * 60 * 1000); // ends 60 minutes from now
                                            values2.put("description", examDateItemDao.getSubject());
                                            values2.put("hasAlarm", 1);

                                            TimeZone timeZone = TimeZone.getDefault();
                                            values2.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                                            Uri event = cr.insert(EVENTS_URI, values2);

                                            // reminder insert
                                            Uri REMINDERS_URI = LauncherActivity.REMINDERS_URI;
                                            values2 = new ContentValues();
                                            values2.put("event_id", Long.parseLong(event.getLastPathSegment()));
                                            values2.put("method", 1);
                                            values2.put("minutes", 10);
                                            cr.insert(REMINDERS_URI, values2);

                                            values.put(EXAMINATION_TABLE.ADDEVENTED, "Y");
                                            cr.update(MyContentProvider.EXAMINATION_CONTENT_URI, values, w, null);

                                        } catch (Exception e) {
                                            e.toString();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                cursor2.close();

                            }
                            cursor0.close();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }
                    Log.d("result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Object getDataForParent(final Context context) {
        SharedPreferences pref = context.getSharedPreferences("login", 1);
        RequestBody body = new FormBody.Builder()
                .add("studentid", pref.getString("studentid", ""))
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(ConstanstURL.apiDomainName + "/GetItemForParent")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result;
                try {
                    ContentResolver cr = context.getContentResolver();
                    result = response.body().string();

                    //Subject
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Subject");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Subject subject = new Subject();
                            subject.setSUBJECTID(jsonObject.getString("SubjectID"));
                            subject.setSUBJECTNAME(jsonObject.getString("SubjectName"));

                            String w = SUBJECT_TABLE.SUBJECTID + " = " + "'" + subject.getSUBJECTID() + "'";
                            Cursor cursor = cr.query(MyContentProvider.SUBJECT_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(SUBJECT_TABLE.SUBJECTID, subject.getSUBJECTID());
                            values.put(SUBJECT_TABLE.SUBJECTNAME, subject.getSUBJECTNAME());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.SUBJECT_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.SUBJECT_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Chapter
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Chapter");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ChapterDao chapterDao = new ChapterDao();
                            chapterDao.setId(jsonObject.getString("ChapterID"));
                            chapterDao.setChaptername(jsonObject.getString("ChapterName"));
                            chapterDao.setSubjectid(jsonObject.getString("Subjectfk"));

                            String w = CHAPTER_TABLE.ChapterID + " = " + "'" + chapterDao.getId() + "'";
                            Cursor cursor = cr.query(MyContentProvider.CHAPTER_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(CHAPTER_TABLE.ChapterID, chapterDao.getId());
                            values.put(CHAPTER_TABLE.ChapterName, chapterDao.getChaptername());
                            values.put(CHAPTER_TABLE.SubjectID, chapterDao.getSubjectid());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.CHAPTER_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.CHAPTER_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Answer
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Answer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Answer answer = new Answer();
                            answer.setAnswerId(jsonObject.getString("AnswerID"));
                            answer.setChoice(jsonObject.getString("Choice"));
                            answer.setText(jsonObject.getString("Answer"));
                            answer.setQuestionfk(jsonObject.getString("Questionfk"));

                            String w = ANSWER_TABLE.ANSWERID + " = " + "'" + answer.getAnswerId() + "'";
                            Cursor cursor = cr.query(MyContentProvider.ANSWER_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(ANSWER_TABLE.ANSWERID, answer.getAnswerId());
                            values.put(ANSWER_TABLE.ANSWER, answer.getText());
                            values.put(ANSWER_TABLE.CHOICENAME, answer.getChoice());
                            values.put(ANSWER_TABLE.QUESTIONID, answer.getQuestionfk());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.ANSWER_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.ANSWER_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //CorrectAnswer
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("CorrectAnswer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            CorrectAnswer correctAnswer = new CorrectAnswer();
                            correctAnswer.setCORRECTID(jsonObject.getString("CorrectID"));
                            correctAnswer.setANSWERID(jsonObject.getString("Answerfk"));
                            correctAnswer.setANSDESC(jsonObject.getString("AnsDesc"));

                            String w = CORRECTANSWER_TABLE.ANSWERID + " = " + "'" + correctAnswer.getCORRECTID() + "'";
                            Cursor cursor = cr.query(MyContentProvider.CORRECTANSWER_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(CORRECTANSWER_TABLE.CORRECTID, correctAnswer.getCORRECTID());
                            values.put(CORRECTANSWER_TABLE.ANSWERID, correctAnswer.getANSWERID());
                            values.put(CORRECTANSWER_TABLE.ANSDESC, correctAnswer.getANSDESC());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.CORRECTANSWER_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.CORRECTANSWER_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Question
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Question");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Question question = new Question();
                            question.setQuestionId(jsonObject.getString("QuestionID"));
                            question.setProposition(jsonObject.getString("Proposition"));
                            question.setExerciseID(jsonObject.getString("Exercisefk"));
                            question.setCorrectID(jsonObject.getString("Correct"));
                            question.setAnsDesc(jsonObject.getString("AnsDesc"));

                            String w = QUESTION_TABLE.QUESTIONID + " = " + "'" + question.getQuestionId() + "'";
                            Cursor cursor = cr.query(MyContentProvider.QUESTION_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(QUESTION_TABLE.QUESTIONID, question.getQuestionId());
                            values.put(QUESTION_TABLE.PROPOSITION, question.getProposition());
                            values.put(QUESTION_TABLE.EXERCISEID, question.getExerciseID());
                            values.put(QUESTION_TABLE.CORRECT, question.getCorrect());
                            values.put(QUESTION_TABLE.ANSDESC, question.getAnsDesc());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.QUESTION_CONTENT_URI, values);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.QUESTION_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Homeworkset
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Homeworkset");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Homeworkset homeworkset = new Homeworkset();
                            homeworkset.setHomeworksetId(jsonObject.getString("HsetID"));
                            homeworkset.setScore(jsonObject.getString("Score"));

                            String w = HOMEWORKSET_TABLE.HSETID + " = " + "'" + homeworkset.getHomeworksetId() + "'";
                            Cursor cursor = cr.query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(HOMEWORKSET_TABLE.HSETID, homeworkset.getHomeworksetId());
                            values.put(HOMEWORKSET_TABLE.SCORE, homeworkset.getScore());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.HOMEWORKSET_CONTENT_URI, values);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.HOMEWORKSET_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            // update hsetid on question
                            String w0 = QUESTION_TABLE.QUESTIONID + " = " + "'" + jsonObject.getString("Questionfk") + "'";
                            Cursor cursor0 = cr.query(MyContentProvider.QUESTION_CONTENT_URI, null, w0, null, null);

                            ContentValues values0 = new ContentValues();
                            values0.put(QUESTION_TABLE.HSETID, homeworkset.getHomeworksetId());

                            if (cursor0.getCount() > 0) {
                                try {
                                    cr.update(MyContentProvider.QUESTION_CONTENT_URI, values0, w0, null);

                                    cursor0.moveToFirst();
                                    ContentValues values1 = new ContentValues();
                                    values1.put(HOMEWORKSET_TABLE.HSETID, homeworkset.getHomeworksetId());
                                    values1.put(HOMEWORKSET_TABLE.EXERCISEID, cursor0.getString(cursor0.getColumnIndex(QUESTION_TABLE.EXERCISEID)));

                                    cr.update(MyContentProvider.HOMEWORKSET_CONTENT_URI, values1, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            cursor0.close();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Exercise
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Exercise");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Exercise exercise = new Exercise();
                            exercise.setEXERCISEID(jsonObject.getString("ExerID"));
                            exercise.setCHARPTERID(jsonObject.getString("ChapterID"));
                            exercise.setDODATE(jsonObject.getString("DoDate"));
                            exercise.setEXPDATE(jsonObject.getString("ExpDate"));
                            exercise.setAMOUNT(jsonObject.getString("AmountDo"));
                            exercise.setScore(jsonObject.getString("Score"));
                            exercise.setLevel(jsonObject.getString("Level"));

                            String w = EXERCISE_TABLE.EXERCISEID + " = " + "'" + exercise.getEXERCISEID() + "'";
                            Cursor cursor = cr.query(MyContentProvider.EXERCISE_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(EXERCISE_TABLE.EXERCISEID, exercise.getEXERCISEID());
                            values.put(EXERCISE_TABLE.CHARPTERID, exercise.getCHARPTERID());
                            values.put(EXERCISE_TABLE.DODATE, exercise.getDODATE());
                            values.put(EXERCISE_TABLE.EXPDATE, exercise.getEXPDATE());
                            values.put(EXERCISE_TABLE.AMOUNT, exercise.getAMOUNT());
                            values.put(EXERCISE_TABLE.Score, exercise.getScore());
                            values.put(EXERCISE_TABLE.Level, exercise.getLevel());

                            if (cursor.getCount() == 0) {
                                try {
                                    context.sendBroadcast(new Intent(MyReceiver.ACTION_NOTIFICATION));
                                    context.sendBroadcast(new Intent(MainActivity.UpdateGraph));

                                    cr.insert(MyContentProvider.EXERCISE_CONTENT_URI, values);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.EXERCISE_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            cursor.close();


                            // Check child do not homework

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date date1 = new Date();
                            try {
                                date1 = dateFormat.parse(exercise.getEXPDATE());
                                long diff = MinDifferentDateTime(date1, new Date());
                                if (diff > 0) {
                                    String selecthomeworkset = HOMEWORKSET_TABLE.EXERCISEID + " = " + "'" + exercise.getEXERCISEID() + "'";
                                    Cursor cHomeworkset = context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, selecthomeworkset, null, null);
                                    if (cHomeworkset.getCount() == 0) {
                                        String w0 = EXERCISE_TABLE.EXERCISEID + " = " + "'" + exercise.getEXERCISEID() + "' AND " + EXERCISE_TABLE.SENDNOTIFICATIONED + " = 'Y'";
                                        Cursor cursor0 = context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, w0, null, null);
                                        if (cursor0.getCount() == 0) {
                                            //Send notification
                                            context.sendBroadcast(new Intent(MyReceiver.ACTION_NOTIFICATION_DONOTHOMEWORK));

                                            ContentValues values0 = new ContentValues();
                                            values0.put(EXERCISE_TABLE.SENDNOTIFICATIONED, "Y");
                                            cr.update(MyContentProvider.EXERCISE_CONTENT_URI, values0, w, null);
                                        }
                                        cursor0.close();

                                    }

                                    cHomeworkset.close();
                                }
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }

                    //Exam
                    try {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("Exam");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            ExamDateItemDao examDateItemDao = new ExamDateItemDao();
                            examDateItemDao.setExamid(jsonObject.getString("ExamID"));
                            examDateItemDao.setChapter(jsonObject.getString("Chapterfk"));
                            examDateItemDao.setDetail(jsonObject.getString("Description"));
                            examDateItemDao.setExamSent(jsonObject.getString("ExamDate"));

                            String w = EXAMINATION_TABLE.EXAMID + " = " + "'" + examDateItemDao.getExamid() + "'";
                            Cursor cursor = cr.query(MyContentProvider.EXAMINATION_CONTENT_URI, null, w, null, null);

                            ContentValues values = new ContentValues();
                            values.put(EXAMINATION_TABLE.EXAMID, examDateItemDao.getExamid());
                            values.put(EXAMINATION_TABLE.CHAPTERID, examDateItemDao.getChapter());
                            values.put(EXAMINATION_TABLE.DESCRIPTION, examDateItemDao.getDetail());
                            values.put(EXAMINATION_TABLE.EXAMDATE, examDateItemDao.getExamdate());

                            if (cursor.getCount() == 0) {
                                try {
                                    cr.insert(MyContentProvider.EXAMINATION_CONTENT_URI, values);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cr.update(MyContentProvider.EXAMINATION_CONTENT_URI, values, w, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            String w2 = EXAMINATION_TABLE.EXAMID + " = " + "'" + examDateItemDao.getExamid() + "' AND " + EXAMINATION_TABLE.ADDEVENTED + " = 'Y'";
                            Cursor cursor2 = cr.query(MyContentProvider.EXAMINATION_CONTENT_URI, null, w2, null, null);
                            if (cursor2.getCount() == 0) {
                                try {
                                    try {
                                        // get calendar
                                        Calendar cal = Calendar.getInstance();
                                        Uri EVENTS_URI = LauncherActivity.EVENTS_URI;

                                        Calendar calendar = Calendar.getInstance();
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        Date convertedDate = new Date();
                                        try {
                                            convertedDate = dateFormat.parse(examDateItemDao.getExamdate());
                                            //calExamModel.setNumDate((String) DateFormat.format("dd", convertedDate));
                                        } catch (ParseException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        // event insert
                                        ContentValues values2 = new ContentValues();
                                        values2.put("calendar_id", 1);
                                        values2.put("title", examDateItemDao.getDetail());
                                        values2.put("allDay", 0);
                                        values2.put("dtstart", convertedDate.getTime() + 11 * 60 * 1000); // event starts at 11 minutes from now
                                        values2.put("dtend", convertedDate.getTime() + 60 * 60 * 1000); // ends 60 minutes from now
                                        values2.put("description", examDateItemDao.getSubject());
                                        values2.put("hasAlarm", 1);

                                        TimeZone timeZone = TimeZone.getDefault();
                                        values2.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                                        Uri event = cr.insert(EVENTS_URI, values2);

                                        // reminder insert
                                        Uri REMINDERS_URI = LauncherActivity.REMINDERS_URI;
                                        values2 = new ContentValues();
                                        values2.put("event_id", Long.parseLong(event.getLastPathSegment()));
                                        values2.put("method", 1);
                                        values2.put("minutes", 10);
                                        cr.insert(REMINDERS_URI, values2);

                                        values.put(EXAMINATION_TABLE.ADDEVENTED, "Y");
                                        cr.update(MyContentProvider.EXAMINATION_CONTENT_URI, values, w, null);

                                    } catch (Exception e) {
                                        e.toString();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            cursor2.close();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.toString();
                    }
                    Log.d("result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return data;
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
