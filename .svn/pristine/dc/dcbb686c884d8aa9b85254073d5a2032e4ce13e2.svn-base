package com.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.adapter.DataHomeworkAdapter;
import com.habebe.projecthomework.dao.AnswerDatasource;
import com.habebe.projecthomework.dao.CorrectAnswer;
import com.habebe.projecthomework.dao.DataHomeworkCollectionDao;
import com.habebe.projecthomework.dao.homeworkset.Homeworkset;
import com.habebe.projecthomework.dao.DatahomeworkDatasource;
import com.habebe.projecthomework.manager.NetworkConnection;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnswerExerCiseActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private DataHomeworkCollectionDao dao;
    private ListView listView;
    private ListAnswerAdapter listAnswerAdapter;
    private String exerciseId;
    private AnswerDatasource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datahomework);

        Intent intent = getIntent();
        exerciseId = intent.getStringExtra(CharpterDialogFragment.EXERCISE_EXTRA);

        TextView chapterid = (TextView) findViewById(R.id.chapterid);
        chapterid.setText(getText(R.string.label_exercise)+ exerciseId);
        mDataSource = new AnswerDatasource(this);
        initInstances();
    }

    private void initInstances() {
        listAnswerAdapter = new ListAnswerAdapter();

        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(listAnswerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getDataTest() {
        try{
            mDataSource.getItemFromDummy(exerciseId);

            AnswerExerCiseActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listAnswerAdapter.setData();

                }
            });
        }catch (Exception e){
            e.toString();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add:
                String status = getSharedPreferences("login", 1).getString("status", null);
                if (status == null) {
                    return true;
                } else if (status.equals("teacher")) {
                    startActivity(new Intent(this, AddHomeworkActivity.class));
                } else {
                    Toast.makeText(AnswerExerCiseActivity.this,
                            "ไม่สามารถเข้าใช้งานได้", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataTest();
        //requestDataHomeWork();
    }

    private class ListAnswerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataSource.getCount();
        }

        @Override
        public CorrectAnswer getItem(int position) {
            return mDataSource.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.ansexercise_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                CorrectAnswer item = getItem(position);
                holder.txtNameItem.setText(getText(R.string.item) + String.valueOf(position+1));
                holder.text_question.setText(item.getQuestion().getProposition());
                holder.text_answer.setText(item.getAnswer().getChoice() + ") " + item.getAnswer().getText());
                holder.text_answerdesc.setText(item.getANSDESC());
            }catch (Exception e){
                e.toString();
            }

            return convertView;
        }

        public void setData() {
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder {
        private TextView txtNameItem, text_question, text_answer, text_answerdesc;

        private ViewHolder(View view) {
            txtNameItem = (TextView) view.findViewById(R.id.txtNameItem);
            text_question = (TextView) view.findViewById(R.id.text_question);
            text_answer = (TextView) view.findViewById(R.id.text_answer);
            text_answerdesc = (TextView) view.findViewById(R.id.text_answerdesc);

        }
    }
}


