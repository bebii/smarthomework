package com.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.adapter.DataHomeworkAdapter;
import com.habebe.projecthomework.adapter.ExamDateAdapter;
import com.habebe.projecthomework.dao.DataHomeworkCollectionDao;
import com.habebe.projecthomework.dao.ExamDateCollectionDao;
import com.habebe.projecthomework.manager.NetworkConnection;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Habebe on 2/10/2559.
 */

public class ExamDateActivity extends AppCompatActivity {
    private ListView listView;
    private ProgressDialog dialog;
    private ExamDateCollectionDao dao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_exam);
        initInstances();
    }

    private void initInstances() {
        listView = (ListView) findViewById(R.id.listView3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void requestExamDate() {

        String id = getSharedPreferences("login", 1).getString("id", null);

        if(!NetworkConnection.isAvailable(ExamDateActivity.this)){
            Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url("http://27.254.63.25/smartHomework/services/showexamdate.php")
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Failed", Toast.LENGTH_SHORT).show();
                        if(dialog.isShowing()) dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().string();
                            Log.d("result", result);
                            Gson gson = new Gson();
                            dao = gson.fromJson(result, ExamDateCollectionDao.class);
                            listView.setAdapter(new ExamDateAdapter(ExamDateActivity.this, dao.getData()));
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    final AlertDialog alertDialog =
                                            new AlertDialog.Builder(ExamDateActivity.this).create();
                                    View convertView = getLayoutInflater().inflate(R.layout.dialog_exam, null);
                                    alertDialog.setView(convertView);
                                    alertDialog.setTitle(dao.getData().get(position).getName());
                                    TextView txtSubject = (TextView) convertView.findViewById(R.id.txtSubject);
                                    TextView txtChapter = (TextView) convertView.findViewById(R.id.txtChapter);
                                    TextView txtDetailExam = (TextView) convertView.findViewById(R.id.txtDetailExam);
                                    TextView txtDateExam = (TextView) convertView.findViewById(R.id.txtDateExam);

                                    txtSubject.setText("Subject : "+dao.getData().get(position).getName());
                                    txtChapter.setText("Subject : "+dao.getData().get(position).getName());
                                    txtDetailExam.setText("Detail :"+dao.getData().get(position).getDetail());
                                    txtDateExam.setText("Exp Date :"+dao.getData().get(position).getExamdate());

                                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                    alertDialog.show();
                                }
                            });
                            if(dialog.isShowing()) dialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        if(dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading. . .");
        }
        dialog.show();
    }

    protected void onResume() {
        super.onResume();
        requestExamDate();
    }


}
