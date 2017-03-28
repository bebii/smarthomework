package com.habebe.projecthomework.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.ChapterDao;
import com.habebe.projecthomework.dao.ChapterDatasource;
import com.habebe.projecthomework.dao.Subject;
import com.habebe.projecthomework.database.HOMEWORKSET_TABLE;
import com.habebe.projecthomework.database.MyContentProvider;
import com.habebe.projecthomework.service.MyReceiver;

/**
 * Created by yutthanan on 11/4/16.
 */

public class ScoreDialogFragment extends DialogFragment {

    private final String exerciseID;
    private Button BTN_CLOSE;
    private TextView tvCorrect, tvWrong, tvSummaryScore;
    private Dialog dialogFragment;

    public ScoreDialogFragment(String exerciseID) {
        this.exerciseID = exerciseID;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString("title");
        View view = inflater.inflate(R.layout.dialog_do_homework_success, container, false);

        BTN_CLOSE = (Button) view.findViewById(R.id.BTN_CLOSE);
        tvCorrect = (TextView) view.findViewById(R.id.tvCorrect);
        tvWrong = (TextView) view.findViewById(R.id.tvWrong);
        tvSummaryScore = (TextView) view.findViewById(R.id.tvSummaryScore);

        String w = HOMEWORKSET_TABLE.EXERCISEID + " = " + "'" + exerciseID + "'";
        Cursor cursor = getActivity().getContentResolver().query(MyContentProvider.HOMEWORKSET_CONTENT_URI, null, w, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            try {
                tvCorrect.setText(cursor.getString(cursor.getColumnIndex(HOMEWORKSET_TABLE.AMOUNTCORRECT)));
                tvWrong.setText(cursor.getString(cursor.getColumnIndex(HOMEWORKSET_TABLE.AMOUNTWRONG)));
                tvSummaryScore.setText(cursor.getString(cursor.getColumnIndex(HOMEWORKSET_TABLE.SCORE)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        BTN_CLOSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.dialogFragment = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        this.dialogFragment.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialogFragment;
    }
}
