package com.habebe.projecthomework.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.LoginResponseDao;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.USER_TABLE;
import com.habebe.projecthomework.manager.NetworkConnection;
import com.google.android.gms.common.api.GoogleApiClient;
import com.habebe.projecthomework.service.MyService;
import com.habebe.projecthomework.util.ConstanstURL;
import com.habebe.projecthomework.util.Position;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tvWelcome;
    EditText edtUsername;
    EditText edtPassword;
    Button btnSubmit;
    private GoogleApiClient client;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkUserSession();

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.trim().equals("") && password.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.please_enter_userandpwd), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!NetworkConnection.isAvailable(LoginActivity.this)) {
                    Toast.makeText(getApplicationContext(), "ตรวจสอบ Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestBody body = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build();

                Request request = new Request.Builder()
                        .post(body)
                        .url(ConstanstURL.apiDomainName + "/Login")
                        .build();

                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("กำลังโหลดข้อมูล");
                dialog.show();

                OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String result;
                                try {
                                    result = response.body().string();

                                    JSONArray userArray = new JSONObject(result).getJSONArray("user");

                                    JSONObject jsonObject = userArray.getJSONObject(0);
                                    String success = new JSONObject(result).getString("status");

                                    if (success.trim().equalsIgnoreCase("true")) {

                                        LoginResponseDao dao = new LoginResponseDao();

                                        dao.setId(jsonObject.getString("userid"));
                                        dao.setUsername(jsonObject.getString("username"));
                                        dao.setFName(jsonObject.getString("name") + " " + jsonObject.getString("lastname"));
                                        dao.setStatus(jsonObject.getString("position"));

                                        if(dao.getStatus().equalsIgnoreCase(Position.STATUS_PARENT)){
                                            createChildren(jsonObject.getString("StudentID"));
                                        }

                                        createUserSession(dao);
                                        createUserToDB(dao);

                                        StartService();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "ชื่อล็อกอินหรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                                    }


                                    Log.d("result", result);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "ชื่อล็อกอินหรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                                } finally {
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    private void createUserToDB(LoginResponseDao loginResponseDao) {
        ContentResolver cr = getApplicationContext().getContentResolver();
        try {
            ContentValues values = new ContentValues();
            values.put(USER_TABLE.USERID, loginResponseDao.getId());
            values.put(USER_TABLE.NAME, loginResponseDao.getFullName());
            values.put(USER_TABLE.POSITION, loginResponseDao.getStatus());

            cr.insert(MyContentProvider.USER_CONTENT_URI, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUserSession() {
        SharedPreferences pref = getSharedPreferences("login", 1);
        if (pref.getBoolean("login_flag", false)) {
            StartService();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void createChildren(String studentid) {
        SharedPreferences pref = getSharedPreferences("login", 1);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("studentid", studentid);
        edit.apply();
    }

    private void createUserSession(LoginResponseDao dao) {
        SharedPreferences pref = getSharedPreferences("login", 1);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("login_flag", true);
        edit.putString("id", dao.getId());
        edit.putString("username", dao.getUsername());
        edit.putString("status", dao.getStatus());
        edit.apply();
    }

    private void StartService() {
        if (MyService.myService == null) {
            startService(new Intent(this, MyService.class));
        }
    }
}