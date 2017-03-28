package com.habebe.projecthomework.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.User;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.database.USER_TABLE;
import com.habebe.projecthomework.manager.LoginManager;
import com.habebe.projecthomework.piechart.PieModel;
import com.habebe.projecthomework.piechart.PieView;
import com.habebe.projecthomework.service.MyReceiver;
import com.habebe.projecthomework.service.MyService;
import com.habebe.projecthomework.util.Position;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public final int EXERCISE_REQUEST = 11;  // The request code
    public final int ANSWER_REQUEST = 12;  // The request code

    private TextView txtdate, txttime;
    private TimerRunnable timerRunnable = new TimerRunnable();

    private Handler handler = new Handler();

    public static String UpdateGraph = "com.habebe.UpdateGraph";
    private GraphReceiver graphReceiver;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphReceiver = new GraphReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(UpdateGraph);
        registerReceiver(graphReceiver, filter);

        myFragment = new MyFragment();
        handler.post(timerRunnable);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, myFragment)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(graphReceiver);
    }

    private void AlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        destroySession();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    private void destroySession() {
        SharedPreferences pref = getSharedPreferences("login", 1);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("login_flag", false);
        edit.clear();
        edit.apply();

        getContentResolver().delete(MyContentProvider.ANSWER_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.CHAPTER_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.CORRECTANSWER_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.EXAMINATION_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.EXERCISE_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.QUESTION_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.REGISTER_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.STUDENTANSWER_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.SUBJECT_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.CORRECTANSWER_CONTENT_URI, null, null);
        getContentResolver().delete(MyContentProvider.USER_CONTENT_URI, null, null);

        getApplicationContext().getContentResolver().delete(MyContentProvider.USER_CONTENT_URI, null, null);
        startActivity(new Intent(MainActivity.this,
                LoginActivity.class));
        getApplicationContext().stopService(new Intent(this, MyService.class));
        finish();
    }

    private class MyFragment extends Fragment implements View.OnClickListener {
        private View returnView;
        private RelativeLayout Relative_answer, Relative_Assign, Relative_score, Relative_exam, linear_graph;
        private LinearLayout layoutpieView, linear_menu1;
        private ImageView img_menu, img_logo;
        private TextView txt_name, txt_position, txt_exam, txt_notdo_homework, txt_answer_wrong, txt_answer_true;
        private PieView pieView;
        private PieModel pieModel;

        private Timer timer = new Timer();
        private TimerTask timerTask;

        public MyFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            returnView = inflater.inflate(R.layout.fragment_my, container, false);

            layoutpieView = (LinearLayout) returnView.findViewById(R.id.layoutpieView);
            linear_menu1 = (LinearLayout) returnView.findViewById(R.id.linear_menu1);

            img_menu = (ImageView) returnView.findViewById(R.id.image_menu);
            img_logo = (ImageView) returnView.findViewById(R.id.img_logo);
            Relative_answer = (RelativeLayout) returnView.findViewById(R.id.Relative_answer);
            Relative_Assign = (RelativeLayout) returnView.findViewById(R.id.Relative_assign);
            Relative_score = (RelativeLayout) returnView.findViewById(R.id.Relative_score);
            Relative_exam = (RelativeLayout) returnView.findViewById(R.id.Relative_exam);
            linear_graph = (RelativeLayout) returnView.findViewById(R.id.linear_graph);

            txt_name = (TextView) returnView.findViewById(R.id.txt_name);
            txt_position = (TextView) returnView.findViewById(R.id.txt_position);
            txt_exam = (TextView) returnView.findViewById(R.id.txt_exam);
            txt_notdo_homework = (TextView) returnView.findViewById(R.id.txt_notdo_homework);
            txt_answer_wrong = (TextView) returnView.findViewById(R.id.txt_answer_wrong);
            txt_answer_true = (TextView) returnView.findViewById(R.id.txt_answer_true);

            txtdate = (TextView) returnView.findViewById(R.id.TextViewdate);
            txttime = (TextView) returnView.findViewById(R.id.TextViewtime);

            img_menu.setOnClickListener(this);
            Relative_answer.setOnClickListener(this);
            Relative_Assign.setOnClickListener(this);
            Relative_score.setOnClickListener(this);
            Relative_exam.setOnClickListener(this);

            layoutpieView.setOnClickListener(this);

            pieModel = new PieModel(getApplicationContext());

            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            pieView = new PieView(getApplicationContext());
            linearLayout.addView(pieView, params);
            layoutpieView.addView(linearLayout);

            User user = LoginManager.getInstance().getUser(getApplicationContext());
            if(user.getPosition().equalsIgnoreCase(Position.STATUS_TEACHER)){
                linear_graph.setVisibility(View.GONE);

            }

            if(user.getPosition().equalsIgnoreCase(Position.STATUS_PARENT)){
                linear_menu1.setVisibility(View.GONE);
                layoutpieView.setEnabled(false);

            }

            returnView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return returnView;
        }

        @Override
        public void onResume() {
            super.onResume();
            setDisplayUser();
            getData();

        }

        public void setDisplayUser() {
            User user = new User();
            try {
                Cursor cursor = getContentResolver().query(MyContentProvider.USER_CONTENT_URI, null, null, null, null);
                if (cursor.getCount() > 0) {
                    try {
                        cursor.moveToFirst();
                        user.setFName(cursor.getString(cursor.getColumnIndex(USER_TABLE.NAME)));
                        user.setLName(cursor.getString(cursor.getColumnIndex(USER_TABLE.LASTNAME)));
                        user.setPosition(cursor.getString(cursor.getColumnIndex(USER_TABLE.POSITION)));

                        txt_name.setText(user.getFName() + " " + user.getLName());
                        txt_position.setText(user.getPosition());

                    } catch (Exception e) {
                        e.toString();
                    }
                }
                cursor.close();
            } catch (Exception e) {
                e.toString();
            }
        }

        private void initializeContent() {
            /*mClockViewSurface = (ClockViewSurface) returnView.findViewById(R.id.clockView);
            //mClockView = (ClockView) returnView.findViewById(R.id.clockView);
            //CompassView cv = (CompassView)returnView.findViewById(R.id.compassView);
            //cv.setBearing(0);

            getActivity().registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));

            bluetooth.enable();
            if (!bluetooth.isDiscovering()) {
                bluetooth.startDiscovery();
            }*/
        }

        public void getData() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pieModel.notifyDataSetChanged();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    synchronized (MainActivity.this) {
                                        img_logo.setVisibility((pieModel.getTotal() == 0) ? View.VISIBLE : View.GONE);
                                        pieView.setPieModel(pieModel);
                                        txt_exam.setText(String.valueOf(pieModel.getTotal()));
                                        txt_answer_true.setText(String.valueOf(pieModel.getAnswercorrect()));
                                        txt_answer_wrong.setText(String.valueOf(pieModel.getAnswerwrong()));
                                        txt_notdo_homework.setText(String.valueOf(pieModel.getDonothomework()));

                                    }
                                } catch (Exception e) {
                                    e.toString();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ;
                }
            }).start();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_menu:
                    AlertDialog();
                    break;
                case R.id.Relative_answer:
                    MyReceiver.NOTIFICATION_ACTION = false;
                    startActivityForResult(new Intent(MainActivity.this, SubjectActivity.class), ANSWER_REQUEST);
                    break;
                case R.id.Relative_assign:
                    MyReceiver.NOTIFICATION_ACTION = false;
                    startActivityForResult(new Intent(MainActivity.this, SubjectActivity.class), EXERCISE_REQUEST);
                    break;
                case R.id.Relative_score:
                    startActivity(new Intent(MainActivity.this, ShowScoreActivity.class));
                    break;
                case R.id.Relative_exam:
                    startActivity(new Intent(MainActivity.this, CalExamActivity.class));
                    break;
                case R.id.layoutpieView:
                    startActivity(new Intent(MainActivity.this, StatActivity.class));
                    break;
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == EXERCISE_REQUEST) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    // The user picked a contact.
                    // The Intent's data Uri identifies which contact was selected.

                    // Do something with the contact here (bigger example below)
                    String exerciseId = data.getStringExtra(CharpterDialogFragment.EXERCISE_EXTRA);

                    Intent intent = new Intent(MainActivity.this, AssignmentActivity.class);
                    intent.putExtra(CharpterDialogFragment.EXERCISE_EXTRA, exerciseId);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == ANSWER_REQUEST) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    // The user picked a contact.
                    // The Intent's data Uri identifies which contact was selected.

                    // Do something with the contact here (bigger example below)

                    String exerciseId = data.getStringExtra(CharpterDialogFragment.EXERCISE_EXTRA);

                    Intent intent = new Intent(MainActivity.this, AnswerExerCiseActivity.class);
                    intent.putExtra(CharpterDialogFragment.EXERCISE_EXTRA, exerciseId);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    private final class TimerRunnable implements Runnable {
        @Override
        public void run() {
            try {
                txtdate.setText((String) DateFormat.format("dd/MM/yyyyy", new Date()));
                txttime.setText((String) DateFormat.format("kk:mm:ss", new Date()));
            }catch (Exception e){
                e.toString();
            }
            runTimer();
        }
    }

    private void runTimer() {
        handler.postDelayed(timerRunnable, 1000);
    }

    public class GraphReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
           if(myFragment != null){
               myFragment.getData();

           }
        }
    }
}
