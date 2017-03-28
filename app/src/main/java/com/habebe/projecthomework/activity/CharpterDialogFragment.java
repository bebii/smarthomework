package com.habebe.projecthomework.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.habebe.projecthomework.service.MyReceiver;

/**
 * Created by yutthanan on 11/4/16.
 */

public class CharpterDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    public static final String EXERCISE_EXTRA = "chapter";
    private Subject subject;
    private ChapterDatasource datasource;
    private Button BTN_CLOSE;
    private ListView mListView;
    private ProgressBar progress_loaddata;
    private TextView text_nodata;
    private Dialog dialogFragment;
    private ListChapterAdapter listChapterAdapter;

    public CharpterDialogFragment(Subject subject) {
        this.subject = subject;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString("title");
        View view = inflater.inflate(R.layout.activity_dialog, container, false);

        mListView = (ListView) view.findViewById(R.id.listView);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        progress_loaddata = (ProgressBar) view.findViewById(R.id.progress_loaddata);
        BTN_CLOSE = (Button) view.findViewById(R.id.BTN_CLOSE);

        datasource = new ChapterDatasource(getActivity());
        listChapterAdapter = new ListChapterAdapter();
        datasource.getItemFromDummy(subject.getSUBJECTID());

        mListView.setAdapter(listChapterAdapter);
        mListView.setOnItemClickListener(this);

        listChapterAdapter.setData();

        text_nodata.setVisibility(View.GONE);
        progress_loaddata.setVisibility(View.GONE);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChapterDao item = (ChapterDao) datasource.getItem(position);

        Intent returnIntent = new Intent();
        if(MyReceiver.NOTIFICATION_ACTION == true){
            Intent intent = new Intent(getActivity(), AssignmentActivity.class);
            intent.putExtra(CharpterDialogFragment.EXERCISE_EXTRA, item.getExerciseID());
            startActivity(intent);
            getActivity().finish();
        }else {
            returnIntent.putExtra(EXERCISE_EXTRA, item.getExerciseID());
            getActivity().setResult(Activity.RESULT_OK, returnIntent);
            getActivity().finish();
        }
    }

    private class ListChapterAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datasource.getCount();
        }

        @Override
        public ChapterDao getItem(int position) {
            return datasource.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.chapter_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ChapterDao item = getItem(position);

            //holder.imageView.setImageDrawable(drawable);
            holder.textView.setText(item.getChaptername());
            holder.textView2.setText(getText(R.string.label_exercise) + " " + item.getExerciseID());
            holder.textView3.setText(getText(R.string.Level) + " " + item.getLevel());

            return convertView;
        }

        public void setData() {
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder {

        private ImageView imageView;

        private TextView textView, textView2, textView3;

        private ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            textView3 = (TextView) view.findViewById(R.id.textView3);

        }
    }

}
