package com.habebe.projecthomework.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.DataItem;
import com.habebe.projecthomework.dao.DataSource;
import com.habebe.projecthomework.dao.Subject;
import com.habebe.projecthomework.dao.SubjectDatasource;

public class SubjectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TYPE = "TYPE";
    private SubjectDatasource mDataSource;
    private ListSubjectAdapter listSubjectAdapter;
    private ListView mListView;
    private ProgressBar progress_loaddata;
    private TextView text_nodata;
    private Context context;
    private Object data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.listView);
        text_nodata = (TextView) findViewById(R.id.text_nodata);
        progress_loaddata = (ProgressBar) findViewById(R.id.progress_loaddata);

        mDataSource = new SubjectDatasource(this);
        mListView.setAdapter(listSubjectAdapter = new ListSubjectAdapter());
        mListView.setOnItemClickListener(this);

        context = getApplicationContext();

        text_nodata.setVisibility(View.GONE);
        progress_loaddata.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        finish();
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
        getData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle args = new Bundle();
        args.putString("title", "Dialog with Action Bar");
        CharpterDialogFragment actionbarDialog = new CharpterDialogFragment(mDataSource.getItem(position));
        actionbarDialog.setArguments(args);
        actionbarDialog.show(getSupportFragmentManager(), "action_bar_frag");

    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDataSource.getItemFromDummy();

                SubjectActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listSubjectAdapter.setData();
                    }
                });
            }
        }).start();
    }

    private class ListSubjectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataSource.getCount();
        }

        @Override
        public Subject getItem(int position) {
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
                convertView = View.inflate(context, R.layout.list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Subject item = getItem(position);

            holder.imageView.setImageDrawable(getDrawable(R.mipmap.ic_book_row));
            holder.textView.setText(item.getSUBJECTNAME());

            return convertView;
        }

        public void setData() {
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder {

        private ImageView imageView;

        private TextView textView;

        private ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
