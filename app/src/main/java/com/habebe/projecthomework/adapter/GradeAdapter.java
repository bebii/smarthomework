package com.habebe.projecthomework.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.GradeDao;

import java.util.List;

/**
 * Created by Habebe on 29/9/2559.
 */

public class GradeAdapter extends BaseAdapter {
    private Activity context;
    private List<GradeDao> data;

    public GradeAdapter(Activity context, List<GradeDao> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GradeAdapter.CustomViewHolder holder = new GradeAdapter.CustomViewHolder();
        if(convertView == null){
            convertView = context.getLayoutInflater().inflate(R.layout.course_item, parent, false);
            holder.tvGrade = (TextView) convertView.findViewById(R.id.tvGrade);
            convertView.setTag(holder);
        } else {
            holder = (GradeAdapter.CustomViewHolder) convertView.getTag();
        }
        holder.tvGrade.setText(data.get(position).getName());
        return convertView;
    }

    private class CustomViewHolder {
        TextView tvGrade;
    }
}
