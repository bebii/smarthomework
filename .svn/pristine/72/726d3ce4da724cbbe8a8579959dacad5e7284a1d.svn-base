package com.habebe.projecthomework.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.dao.ExamDateItemDao;

import java.util.List;

/**
 * Created by Habebe on 2/10/2559.
 */

public class ExamDateAdapter extends BaseAdapter{
    Activity context;
    List<ExamDateItemDao> list;

    public ExamDateAdapter(Activity context, List<ExamDateItemDao> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size(); //ส่งขนาดของ List ที่เก็บข้อมุลอยู่
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ExamDateAdapter.CustomViewHolder holder = new CustomViewHolder();
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
            initInstances(convertView, holder);
            convertView.setTag(holder);

        } else {
            holder = (ExamDateAdapter.CustomViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(list.get(position).getName());
        holder.tvDetail.setText(list.get(position).getDetail());
        holder.tvDate.setText(list.get(position).getExamdate());
        return convertView;
    }

    private void initInstances(View convertView, ExamDateAdapter.CustomViewHolder holder) {
        holder.tvTitle = (TextView) convertView.findViewById(R.id.tvLabel);
        holder.tvDetail = (TextView) convertView.findViewById(R.id.tvDetail);
        holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
    }

    public void setData(List<ExamDateItemDao> data){
        list = data;
        notifyDataSetChanged();
    }

    private class CustomViewHolder {
        TextView tvTitle, tvDetail,tvDate;
    }

}

