package com.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.adapter.CourseAdapter;
import com.habebe.projecthomework.adapter.GradeAdapter;
import com.habebe.projecthomework.dao.CourseCollectionDao;
import com.habebe.projecthomework.dao.GradeCollectionDao;
import com.habebe.projecthomework.dao.GradeDao;
import com.habebe.projecthomework.manager.NetworkConnection;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Habebe on 28/9/2559.
 */

public class SelectActivity extends AppCompatActivity {

    EditText edtGrade, edtSubject;
    ProgressDialog dialog;
    String chCourse,chGrade;

    private CourseCollectionDao courseDao;
    private GradeCollectionDao GradeDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        edtGrade =(EditText)findViewById(R.id.edtGrade);
        edtSubject=(EditText)findViewById(R.id.edtSubject);

        edtGrade.setOnFocusChangeListener(focusChangeListener);
        edtSubject.setOnFocusChangeListener(focusChangeListener);

        edtGrade.setOnClickListener(onClickListener);
        edtSubject.setOnClickListener(onClickListener);


    }
    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v == edtGrade && hasFocus) {
                showGrade();
            } else if (v == edtSubject&& hasFocus){
                showCourse();
            }
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == edtGrade) {
                showGrade();
            } else if (v == edtSubject) {
                showCourse();
            }
        }
    };
    private void showGrade() {
        if (!NetworkConnection.isAvailable(SelectActivity.this)){
            Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet",
                    Toast.LENGTH_SHORT).show();
            return;
    }else if (edtGrade==null) return;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://27.254.63.25/smartHomework/services/showGrade.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                GradeDao = gson.fromJson(response.body().string(), GradeCollectionDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (courseDao.getData().size() < 0)
                            return;
                        Toast.makeText(SelectActivity.this, GradeDao.getMessage(), Toast.LENGTH_SHORT).show();
                        final AlertDialog alertDialog = new AlertDialog.
                                Builder(SelectActivity.this).create();
                        View convertView = getLayoutInflater().inflate(R.layout.listview, null);
                        alertDialog.setView(convertView);
                        alertDialog.setTitle("เลือกระดับชั้น");
                        ListView lv = (ListView) convertView.findViewById(R.id.listView);
                        lv.setAdapter(new GradeAdapter(SelectActivity.this, GradeDao.getData()));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                edtGrade.setText(GradeDao.getData().get(position).getName());
                                chGrade = GradeDao.getData().get(position).getId();
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
            }
        });
        if (dialog == null) {
            dialog = new ProgressDialog(SelectActivity.this);
            dialog.setMessage("Loading...");
        }
        dialog.show();
    }







    private void showCourse() {
            if (!NetworkConnection.isAvailable(SelectActivity.this)) {
                Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet",
                        Toast.LENGTH_SHORT).show();
                return;
            }else if(edtSubject == null) return;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://27.254.63.25/smartHomework/services/showcourse.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                courseDao = gson.fromJson(response.body().string(), CourseCollectionDao.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (courseDao.getData().size() < 0)
                            return;
                        Toast.makeText(SelectActivity.this, courseDao.getMessage(), Toast.LENGTH_SHORT).show();
                        final AlertDialog alertDialog = new AlertDialog.
                                Builder(SelectActivity.this).create();
                        View convertView = getLayoutInflater().inflate(R.layout.listview, null);
                        alertDialog.setView(convertView);
                        alertDialog.setTitle("เลือกวิชาเรียน");
                        ListView lv = (ListView) convertView.findViewById(R.id.listView);
                        lv.setAdapter(new CourseAdapter(SelectActivity.this, courseDao.getData()));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                edtSubject.setText(courseDao.getData().get(position).getName());
                                chCourse = courseDao.getData().get(position).getId();
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
            }
        });
        if (dialog == null) {
            dialog = new ProgressDialog(SelectActivity.this);
            dialog.setMessage("Loading...");
        }
        dialog.show();

    }
}