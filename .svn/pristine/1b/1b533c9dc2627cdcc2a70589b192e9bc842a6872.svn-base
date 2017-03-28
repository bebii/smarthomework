package com.habebe.projecthomework.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.ScoreDatasource;
import com.habebe.projecthomework.dao.ShowScoreDatasource;

import java.util.ArrayList;

public class ShowScoreActivity extends AppCompatActivity {
    private ListScoreAdapter listScoreAdapter;
    private ArrayList<ScoreDatasource> scoreDatasources;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listScoreAdapter = new ListScoreAdapter(ShowScoreDatasource.getInstance());
        listView = (ListView) findViewById(R.id.listview01);
        listView.setAdapter(listScoreAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataTest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void getDataTest() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ShowScoreActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowScoreDatasource.getInstance().getScoreDatasources();
                        listScoreAdapter.setData(ShowScoreDatasource.getInstance());
                    }
                });
            }
        }).start();
    }

    private class ListScoreAdapter extends BaseAdapter {
        private ShowScoreDatasource showScoreDatasource;

        public ListScoreAdapter(ShowScoreDatasource showScoreDatasource) {
            this.showScoreDatasource = showScoreDatasource;
            scoreDatasources = showScoreDatasource.getItem(getApplicationContext());
        }

        @Override
        public int getCount() {
            return scoreDatasources.size();
        }

        @Override
        public ScoreDatasource getItem(int position) {
            return scoreDatasources.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.score_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ScoreDatasource item = getItem(position);
            if(item.isHeader()){
                holder.contain_header.setVisibility(View.VISIBLE);
                holder.contain_body.setVisibility(View.GONE);
                holder.contain_score.setVisibility(View.GONE);

                holder.txtNameSubject.setText(item.getSubject());
            }else if(item.isSumScore()){
                holder.contain_header.setVisibility(View.GONE);
                holder.contain_body.setVisibility(View.GONE);
                holder.contain_score.setVisibility(View.VISIBLE);

                holder.txtSumScore.setText(item.getSumScore());
            }else {
                holder.contain_header.setVisibility(View.GONE);
                holder.contain_body.setVisibility(View.VISIBLE);
                holder.contain_score.setVisibility(View.GONE);

                holder.txtChapter.setText(item.getChapter());
                holder.txtExercise.setText(item.getExercise());
                holder.txtScore.setText(item.getScore());

            }

            return convertView;
        }

        public void setData(ShowScoreDatasource showScoreDatasource) {
            this.showScoreDatasource = showScoreDatasource;
            scoreDatasources = showScoreDatasource.getItem(getApplicationContext());

            notifyDataSetChanged();
        }
    }

    private static class ViewHolder {
        private LinearLayout contain_header, contain_body, contain_score;
        private TextView txtChapter;
        private TextView txtExercise;
        private TextView txtScore;
        private TextView txtSumScore;
        private TextView txtNameSubject;

        private ViewHolder(View view) {
            contain_header = (LinearLayout) view.findViewById(R.id.contain_header);
            contain_body = (LinearLayout) view.findViewById(R.id.contain_body);
            contain_score = (LinearLayout) view.findViewById(R.id.contain_score);

            txtChapter = (TextView) view.findViewById(R.id.txtChapter);
            txtExercise = (TextView) view.findViewById(R.id.txtExercise);
            txtScore = (TextView) view.findViewById(R.id.txtScore);
            txtSumScore = (TextView) view.findViewById(R.id.txtSumScore);
            txtNameSubject = (TextView) view.findViewById(R.id.txtNameSubject);

        }
    }

}
