package com.habebe.projecthomework.adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.activity.AssignmentActivity;
import com.habebe.projecthomework.activity.CharpterDialogFragment;
import com.habebe.projecthomework.activity.ScoreDialogFragment;
import com.habebe.projecthomework.dao.DrawableProvider;
import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.dao.homeworkset.Answer;
import com.habebe.projecthomework.dao.homeworkset.Homeworkset;
import com.habebe.projecthomework.dao.homeworkset.Question;
import com.habebe.projecthomework.dao.DatahomeworkDatasource;
import com.habebe.projecthomework.database.EXERCISE_TABLE;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.QUESTION_TABLE;
import com.habebe.projecthomework.database.STUDENTANSWER_TABLE;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.manager.NetworkConnection;
import com.habebe.projecthomework.util.ConstanstURL;
import com.habebe.projecthomework.util.Position;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Habebe on 8/4/2559.
 */
public class DataHomeworkAdapter extends BaseAdapter implements View.OnClickListener {
    private DrawableProvider mProvider;
    private Activity context;
    private List<String> questionIds;
    private HashMap<String, String> map_selection;
    private User user;

    private Homeworkset homeworkset;
    private String homeworksetID;

    public DataHomeworkAdapter(AssignmentActivity assignmentActivity, Homeworkset homeworkset) {
        this.context = assignmentActivity;

        this.homeworkset = homeworkset;
        this.questionIds = DatahomeworkDatasource.getInstance().getQuestionIds();
        this.map_selection = DatahomeworkDatasource.getInstance().getMap_selection();
        this.user = LoginManager.getInstance().getUser(context);

        mProvider = new DrawableProvider(context);
    }

    @Override
    public int getCount() {
        return homeworkset.getQuestionHashMap().size();
    }

    @Override
    public Question getItem(int position) {
        return homeworkset.getQuestionHashMap().get(questionIds.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Question question = getItem(position);
        final ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.datahomework_row, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.BTN_SEND.setOnClickListener(this);

        holder.BTN_SEND.setVisibility((question.isQuestion()) ? View.GONE : View.VISIBLE);
        holder.BTN_SEND.setEnabled(user.getPosition().equalsIgnoreCase(Position.STATUS_STUDENT) ? true : false);

        holder.Contain_question.setVisibility((question.isQuestion()) ? View.VISIBLE : View.GONE);

        holder.txtNameChapter.setText(context.getText(R.string.label_exercise) + " " + question.getExerciseID());
        holder.txtNameChapter.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        if (position == 0) {
            holder.txtNameChapter.setText(homeworkset.getChapter());
        }

        if (question.getProposition() != null) {

            final Drawable drawable = mProvider.getRoundColorOrange(String.valueOf(position + 1));
            holder.imageView.setImageDrawable(drawable);
            holder.text_question.setText(question.getProposition());

            String answerid = map_selection.get(question.getQuestionId());
            holder.radio_01.setChecked((answerid.equalsIgnoreCase(question.getAnswer().get(0).getAnswerId())) ? true : false);
            holder.radio_02.setChecked((answerid.equalsIgnoreCase(question.getAnswer().get(1).getAnswerId())) ? true : false);
            holder.radio_03.setChecked((answerid.equalsIgnoreCase(question.getAnswer().get(2).getAnswerId())) ? true : false);
            holder.radio_04.setChecked((answerid.equalsIgnoreCase(question.getAnswer().get(3).getAnswerId())) ? true : false);
            holder.radio_05.setChecked((answerid.equalsIgnoreCase(question.getAnswer().get(4).getAnswerId()))? true : false);

            holder.radio_01.setText(question.getAnswer().get(0).getChoice() + ") " + question.getAnswer().get(0).getText());
            holder.radio_02.setText(question.getAnswer().get(1).getChoice() + ") " + question.getAnswer().get(1).getText());
            holder.radio_03.setText(question.getAnswer().get(2).getChoice() + ") " + question.getAnswer().get(2).getText());
            holder.radio_04.setText(question.getAnswer().get(3).getChoice() + ") " + question.getAnswer().get(3).getText());
            holder.radio_05.setText(question.getAnswer().get(4).getChoice() + ") " + question.getAnswer().get(4).getText());

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.radio_01:
                            map_selection.put(question.getQuestionId(), question.getAnswer().get(0).getAnswerId());
                            DatahomeworkDatasource.getInstance().getAnswerid().put(question.getQuestionId(), question.getAnswer().get(0).getAnswerId());
                            SetScore(question, question.getAnswer().get(0));
                            break;
                        case R.id.radio_02:
                            map_selection.put(question.getQuestionId(), question.getAnswer().get(1).getAnswerId());
                            DatahomeworkDatasource.getInstance().getAnswerid().put(question.getQuestionId(), question.getAnswer().get(1).getAnswerId());
                            SetScore(question, question.getAnswer().get(1));
                            break;
                        case R.id.radio_03:
                            map_selection.put(question.getQuestionId(), question.getAnswer().get(2).getAnswerId());
                            DatahomeworkDatasource.getInstance().getAnswerid().put(question.getQuestionId(), question.getAnswer().get(2).getAnswerId());
                            SetScore(question, question.getAnswer().get(2));
                            break;
                        case R.id.radio_04:
                            map_selection.put(question.getQuestionId(), question.getAnswer().get(3).getAnswerId());
                            DatahomeworkDatasource.getInstance().getAnswerid().put(question.getQuestionId(), question.getAnswer().get(3).getAnswerId());
                            SetScore(question, question.getAnswer().get(3));
                            break;
                        case R.id.radio_05:
                            map_selection.put(question.getQuestionId(), question.getAnswer().get(4).getAnswerId());
                            DatahomeworkDatasource.getInstance().getAnswerid().put(question.getQuestionId(), question.getAnswer().get(4).getAnswerId());
                            SetScore(question, question.getAnswer().get(4));
                            break;
                    }

                }
            };

            holder.radio_01.setOnClickListener(onClickListener);
            holder.radio_02.setOnClickListener(onClickListener);
            holder.radio_03.setOnClickListener(onClickListener);
            holder.radio_04.setOnClickListener(onClickListener);
            holder.radio_05.setOnClickListener(onClickListener);

            if (drawable instanceof AnimationDrawable) {
                holder.imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable) drawable).stop();
                        ((AnimationDrawable) drawable).start();
                    }
                });
            }
        }

        return convertView;
    }

    private void SetScore(Question question, Answer answer) {
        if (question.getCorrect().equalsIgnoreCase(answer.getChoice())) {
            DatahomeworkDatasource.getInstance().getScores().put(question.getQuestionId(), question.getScore());
        } else {
            DatahomeworkDatasource.getInstance().getScores().put(question.getQuestionId(), 0);
        }

        ContentResolver cr = context.getContentResolver();
        String w0 = STUDENTANSWER_TABLE.QUESTIONID + " = " + "'" + answer.getQuestionfk() + "'";
        Cursor cursor0 = cr.query(MyContentProvider.STUDENTANSWER_CONTENT_URI, null, w0, null, null);

        ContentValues values0 = new ContentValues();
        values0.put(STUDENTANSWER_TABLE.STANSWERID, user.getUserID());
        values0.put(STUDENTANSWER_TABLE.ANSWERID, answer.getAnswerId());
        values0.put(STUDENTANSWER_TABLE.QUESTIONID, question.getQuestionId());

        if (cursor0.getCount() == 0 ) {
            try {
                cr.insert(MyContentProvider.STUDENTANSWER_CONTENT_URI, values0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            cr.update(MyContentProvider.STUDENTANSWER_CONTENT_URI, values0, w0, null);
        }
        cursor0.close();

    }

    public void setData(Homeworkset homeworkset) {
        this.homeworkset = homeworkset;
        this.questionIds = DatahomeworkDatasource.getInstance().getQuestionIds();
        this.map_selection = DatahomeworkDatasource.getInstance().getMap_selection();

        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_SEND:
                try {
                    if (!NetworkConnection.isAvailable(context)) {
                        Toast.makeText(context, context.getText(R.string.noteinternet), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!CheckSelectItem()) {
                        return;
                    }

                    String selecthomeworkset = HOMEWORKSET_TABLE.COMMITED + " = " + "'" + "Y" + "'";
                    Cursor cHomeworkset = context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, selecthomeworkset, null, null);
                    if (cHomeworkset.getCount() > 0) {
                        Toast.makeText(context, context.getText(R.string.cannotresend), Toast.LENGTH_SHORT).show();

                    } else {
                        Question question = homeworkset.getQuestionHashMap().get(questionIds.get(0));
                        String w = EXERCISE_TABLE.EXERCISEID + " = " + "'" + question.getExerciseID() + "'";
                        Cursor cursor = context.getContentResolver().query(MyContentProvider.EXERCISE_CONTENT_URI, null, w, null, null);
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date date1 = new Date();
                            try {
                                date1 = dateFormat.parse(cursor.getString(cursor.getColumnIndex(EXERCISE_TABLE.EXPDATE)));
                                long diff = MinDifferentDateTime(date1, new Date());
                                if (diff > 0) {
                                    Toast.makeText(context, context.getText(R.string.expdatetosend), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                int countscore = 0;
                                //Send Homework
                                for (int i = 0; i < questionIds.size(); i++) {
                                    if (!questionIds.get(i).equals("-1")) {
                                        countscore += DatahomeworkDatasource.getInstance().getScores().get(questionIds.get(i));
                                    }
                                }

                                String selection = HOMEWORKSET_TABLE.EXERCISEID + " = " + "'" + question.getExerciseID() + "'";
                                ContentValues values = new ContentValues();
                                values.put(HOMEWORKSET_TABLE.SCORE, countscore);
                                values.put(HOMEWORKSET_TABLE.AMOUNTCORRECT, countscore / question.getScore());
                                values.put(HOMEWORKSET_TABLE.DODATE, (String) DateFormat.format("yyyy-MM-dd", new Date()));
                                values.put(HOMEWORKSET_TABLE.AMOUNTWRONG, (questionIds.size() - 1) - Integer.parseInt(values.getAsString(HOMEWORKSET_TABLE.AMOUNTCORRECT)));

                                context.getContentResolver().update(MyContentProvider.HOMEWORKSET_CONTENT_URI, values, selection, null);

                                SendAnswer(context);
                                Log.d("", "");

                                //calExamModel.setNumDate((String) DateFormat.format("dd", convertedDate));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        cursor.close();
                    }
                    cHomeworkset.close();
                } catch (Exception e) {
                    e.toString();
                }
                break;
        }
    }

    private boolean CheckSelectItem() {
        for (int i = 0; i < questionIds.size(); i++) {
            if ((map_selection.get(questionIds.get(i)).equalsIgnoreCase("-1")) && (!String.valueOf(questionIds.get(i)).equalsIgnoreCase("-1"))) {
                Toast.makeText(context, context.getText(R.string.notcomplete), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private static class ViewHolder {
        LinearLayout Contain_question;
        TextView text_question, txtNameChapter;
        ImageView imageView;
        Button BTN_SEND;
        RadioGroup rdoBtn;
        RadioButton radio_01, radio_02, radio_03, radio_04, radio_05;

        private ViewHolder(View convertView) {
            Contain_question = (LinearLayout) convertView.findViewById(R.id.Contain_question);
            text_question = (TextView) convertView.findViewById(R.id.text_question);
            txtNameChapter = (TextView) convertView.findViewById(R.id.txtNameChapter);
            rdoBtn = (RadioGroup) convertView.findViewById(R.id.rdoBtn);
            imageView = (ImageView) convertView.findViewById(R.id.imageView);
            radio_01 = (RadioButton) convertView.findViewById(R.id.radio_01);
            radio_02 = (RadioButton) convertView.findViewById(R.id.radio_02);
            radio_03 = (RadioButton) convertView.findViewById(R.id.radio_03);
            radio_04 = (RadioButton) convertView.findViewById(R.id.radio_04);
            radio_05 = (RadioButton) convertView.findViewById(R.id.radio_05);
            BTN_SEND = (Button) convertView.findViewById(R.id.BTN_SEND);

        }
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

    public void SendAnswer(final Context context) {
        RequestBody body = new FormBody.Builder()
                .add("answer", getAnswer())
                .add("homeworkset", getHomeworkset())
                .add("random", getRandom())
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(ConstanstURL.apiDomainName + "/SetStudentAnswer")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                AssignmentActivity.assignmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result;
                try {
                    result = response.body().string();
                    final String success = new JSONObject(result).getString("status");

                    if (success.trim().equalsIgnoreCase("true")) {
                        String selection = HOMEWORKSET_TABLE.EXERCISEID + " = " + "'" + homeworkset.getQuestionHashMap().get(questionIds.get(0)).getExerciseID() + "'";
                        ContentValues values = new ContentValues();
                        values.put(HOMEWORKSET_TABLE.COMMITED, "Y");

                        context.getContentResolver().update(MyContentProvider.HOMEWORKSET_CONTENT_URI, values, selection, null);
                        AssignmentActivity.assignmentActivity.ShowScoreDialog(AssignmentActivity.assignmentActivity.exerciseID);

                        AssignmentActivity.assignmentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, context.getText(R.string.sendalready), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        AssignmentActivity.assignmentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
                            }
                        });
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

    private String getAnswer() {
        JSONObject jsobj = new JSONObject();
        JSONArray jsarray = new JSONArray();
        User user = LoginManager.getInstance().getUser(context);

        for (int i = 0; i < questionIds.size(); i++) {
            if ((Integer.parseInt(questionIds.get(i)) > 0)) {

                JSONObject json = new JSONObject();
                try {
                    json.put("Studentfk", user.getUserID());
                    json.put("Questionfk", questionIds.get(i));
                    json.put("Answerfk", DatahomeworkDatasource.getInstance().getAnswerid().get(questionIds.get(i)));

                    jsarray.put(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            jsobj.put("Student_answer", jsarray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsobj.toString();
    }

    private String getRandom() {
        JSONObject jsobj = new JSONObject();
        JSONArray jsarray = new JSONArray();
        User user = LoginManager.getInstance().getUser(context);

        for (int i = 0; i < questionIds.size(); i++) {
            if ((Integer.parseInt(questionIds.get(i)) > 0)) {

                JSONObject json = new JSONObject();
                try {
                    json.put("Hsetfk", homeworksetID);
                    json.put("Questionfk", questionIds.get(i));

                    jsarray.put(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            jsobj.put("random", jsarray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsobj.toString();
    }

    private String getHomeworkset() {
        JSONObject json = new JSONObject();

        try {
            Question question = homeworkset.getQuestionHashMap().get(questionIds.get(0));
            String w = HOMEWORKSET_TABLE.EXERCISEID + " = " + "'" + question.getExerciseID() + "'";

            Cursor cursor = context.getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, w, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    User user = LoginManager.getInstance().getUser(context);
                    //`HsetID`, `Score`, `SubmitDate`, `Studentfk`

                    try {
                        json.put("HsetID", cursor.getString(cursor.getColumnIndex(HOMEWORKSET_TABLE.HSETID)));
                        json.put("Score", cursor.getString(cursor.getColumnIndex(HOMEWORKSET_TABLE.SCORE)));
                        json.put("SubmitDate", (String) DateFormat.format("yyyy-MM-dd", new Date()));
                        json.put("Studentfk", user.getUserID());

                        this.homeworksetID = cursor.getString(cursor.getColumnIndex(HOMEWORKSET_TABLE.HSETID));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.toString();
        }

        return json.toString();
    }


}

