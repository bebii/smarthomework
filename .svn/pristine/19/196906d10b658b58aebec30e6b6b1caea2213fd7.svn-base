package com.habebe.projecthomework.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.CalExamModel;
import com.habebe.projecthomework.dao.DataSource;
import com.habebe.projecthomework.dao.ScoreDatasource;
import com.habebe.projecthomework.dao.CalExamDatasource;
import com.habebe.projecthomework.dao.ShowScoreDatasource;

import java.util.ArrayList;

public class CalExamActivity extends AppCompatActivity {
    private ListCalendarAdapter listCalendarAdapter;
    private CalExamDatasource mDataSource;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listview01);

        mDataSource = new CalExamDatasource(this);
        listCalendarAdapter = new ListCalendarAdapter();
        listView.setAdapter(listCalendarAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_event:
                String status = getSharedPreferences("login", 1).getString("status", null);
                if (status == null) {
                    return true;
                } else {
                    startActivity(new Intent(this, ExamDateActivity.class));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestDataHomeWork();
        getDataTest();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void requestDataHomeWork() {
    }

    public void getDataTest() {
        mDataSource.getItemFromDummy();

        CalExamActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listCalendarAdapter.setData();
            }
        });
    }

    private class ListCalendarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataSource.getCount();
        }

        @Override
        public CalExamModel getItem(int position) {
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
                convertView = View.inflate(getApplicationContext(), R.layout.calendar_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CalExamModel item = getItem(position);
            if(item.isMHeader()){
                holder.ContainHeader.setVisibility(View.VISIBLE);
                holder.contain_body.setVisibility(View.GONE);
                holder.txtMonthYear.setText(item.getMontYear());

            }else {
                holder.ContainHeader.setVisibility(View.GONE);
                holder.contain_body.setVisibility(View.VISIBLE);

                holder.txtNumDate.setText(item.getNumDate());
                holder.txtDate.setText(item.getNameDate());
                holder.txtExercise.setText(item.getExercise());
                holder.txtChapter.setText((getText(R.string.charpter)+" " + item.getChapter()));
                holder.txtSubject.setText(getText(R.string.subject)+" " + item.getSubject());

            }

            return convertView;
        }

        public void setData() {

            notifyDataSetChanged();
        }
    }

    private static class ViewHolder {
        private LinearLayout ContainHeader, contain_body;
        private TextView txtMonthYear;
        private TextView txtNumDate;
        private TextView txtDate;
        private TextView txtSubject;
        private TextView txtChapter;
        private TextView txtExercise;

        private ViewHolder(View view) {
            ContainHeader = (LinearLayout) view.findViewById(R.id.ContainHeader);
            contain_body = (LinearLayout) view.findViewById(R.id.contain_body);
            txtMonthYear = (TextView) view.findViewById(R.id.txtMonthYear);
            txtNumDate = (TextView) view.findViewById(R.id.txtNumDate);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtSubject = (TextView) view.findViewById(R.id.txtSubject);
            txtChapter = (TextView) view.findViewById(R.id.txtChapter);
            txtExercise = (TextView) view.findViewById(R.id.txtExercise);

        }
    }
}