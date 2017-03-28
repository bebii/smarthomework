package com.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.adapter.DataHomeworkAdapter;
import com.habebe.projecthomework.dao.DataHomeworkCollectionDao;
import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.dao.homeworkset.Homeworkset;
import com.habebe.projecthomework.dao.DatahomeworkDatasource;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.util.Position;

public class AssignmentActivity extends AppCompatActivity {

    ListView listView;
    ProgressDialog dialog;
    private DataHomeworkCollectionDao collectionDao;
    private DataHomeworkAdapter dataHomeworkAdapter;
    public String exerciseID;
    public static AssignmentActivity assignmentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignmentActivity = this;

        Intent intent = getIntent();
        exerciseID = intent.getStringExtra(CharpterDialogFragment.EXERCISE_EXTRA);

        initInstances();
    }

    public void ShowScoreDialog(String exerciseID){
        Bundle args = new Bundle();
        args.putString("title", "Dialog with Action Bar");
        ScoreDialogFragment actionbarDialog = new ScoreDialogFragment(exerciseID);
        actionbarDialog.setArguments(args);
        actionbarDialog.show(getSupportFragmentManager(), "action_bar_frag");
    }

    private void initInstances() {
        dataHomeworkAdapter = new DataHomeworkAdapter(AssignmentActivity.this, new Homeworkset());

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(dataHomeworkAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AssignmentActivity.this, DoExerciseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", collectionDao.getData().get(position).getId());
                bundle.putString("amount", collectionDao.getData().get(position).getAmount());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                finish();
                Log.d("finish", "success");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getData();

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

    public void getDataTest() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                AssignmentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        User user = LoginManager.getInstance().getUser(AssignmentActivity.this.getApplicationContext());
                        if(user.getPosition().equalsIgnoreCase(Position.STATUS_STUDENT)) {
                            dataHomeworkAdapter.setData(DatahomeworkDatasource.getInstance().getHomeworkset(getApplicationContext(), exerciseID));

                        }else if(user.getPosition().equalsIgnoreCase(Position.STATUS_TEACHER)){
                            dataHomeworkAdapter.setData(DatahomeworkDatasource.getInstance().getHomeworksetForTeacher(getApplicationContext(), exerciseID));

                        }else if(user.getPosition().equalsIgnoreCase(Position.STATUS_PARENT)){
                            dataHomeworkAdapter.setData(DatahomeworkDatasource.getInstance().getHomeworksetForParrent(getApplicationContext(), exerciseID));

                        }
                    }
                });
            }
        }).start();

    }
}