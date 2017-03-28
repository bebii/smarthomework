package com.habebe.projecthomework.dao;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 3:49 PM
 */
public class DataSource {

    public static final int NO_NAVIGATION = -1;

    private ArrayList<DataItem> mDataSource;
    private DrawableProvider mProvider;

    public DataSource(Context context) {
        mProvider = new DrawableProvider(context);
        mDataSource = new ArrayList<DataItem>();
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_RECT));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_RECT));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_RECT_BORDER));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_RECT_BORDER));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_BORDER));

    }

    public int getCount() {
        return mDataSource.size();
    }

    public DataItem getItem(int position) {
        return mDataSource.get(position);
    }

    private DataItem itemFromType(int type) {
        String label = null;
        Drawable drawable = null;
        switch (type) {
            case DrawableProvider.SAMPLE_RECT:
                label = "ภาษาไทย";
                drawable = mProvider.getRoundWithBorder("A");
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT:
                label = "คณิตศาสตร์";
                drawable = mProvider.getRoundWithBorder("B");
                break;
            case DrawableProvider.SAMPLE_ROUND:
                label = "ภาษาอังกฤษ";
                drawable = mProvider.getRoundWithBorder("C");
                break;
            case DrawableProvider.SAMPLE_RECT_BORDER:
                label = "สังคมศึกษา";
                drawable = mProvider.getRoundWithBorder("D");
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT_BORDER:
                label = "ศิลปศึกษา";
                drawable = mProvider.getRoundWithBorder("E");
                break;
            case DrawableProvider.SAMPLE_ROUND_BORDER:
                label = "ดนตรี นาฎศิลป์";
                drawable = mProvider.getRoundWithBorder("F");
                break;

        }
        return new DataItem(label, drawable, type);
    }
}
