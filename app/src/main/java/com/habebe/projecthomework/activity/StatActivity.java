package com.habebe.projecthomework.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.StatDatasource;
import com.habebe.projecthomework.dao.StatModel;

public class StatActivity extends AppCompatActivity {
    private ListStatAdapter listStatAdapter;
    private StatDatasource mDataSource;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graph);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listview01);

        mDataSource = new StatDatasource(this);
        listStatAdapter = new ListStatAdapter();
        listView.setAdapter(listStatAdapter);

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
    protected void onResume() {
        super.onResume();
        requestDataHomeWork();
        getDataTest();

    }

    private void requestDataHomeWork() {
    }

    public void getDataTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDataSource.getItemFromDummy();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listStatAdapter.setData();
                    }
                });
            }
        }).start();
    }

    private class ListStatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataSource.getCount();
        }

        @Override
        public StatModel getItem(int position) {
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
                convertView = View.inflate(getApplicationContext(), R.layout.showgraph_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            StatModel item = getItem(position);
            holder.txtSubject.setText(item.getSubject());
            holder.txt_exam.setText(item.getExercise_Total());
            holder.txt_answer_true.setText(item.getExercise_Correct());
            holder.txt_answer_wrong.setText(item.getExercise_Wrong());
            holder.txt_notdo_homework.setText(item.getExercise_Donot());

            return convertView;
        }

        public void setData() {
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder {
        private TextView txtSubject;
        private TextView txt_answer_true;
        private TextView txt_answer_wrong;
        private TextView txt_exam;
        private TextView txt_notdo_homework;

        private ViewHolder(View view) {
            txtSubject = (TextView) view.findViewById(R.id.txtSubject);
            txt_answer_true = (TextView) view.findViewById(R.id.txt_answer_true);
            txt_answer_wrong = (TextView) view.findViewById(R.id.txt_answer_wrong);
            txt_exam = (TextView) view.findViewById(R.id.txt_exam);
            txt_notdo_homework = (TextView) view.findViewById(R.id.txt_notdo_homework);

        }
    }
}
