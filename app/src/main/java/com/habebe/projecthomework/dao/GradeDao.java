package com.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Habebe on 29/9/2559.
 */

public class GradeDao {
    @SerializedName("GradeID") private String GradeID;
    @SerializedName("Grade") private int Grade;

    public String getId(){
        return GradeID;}

    public void setId(String GradeID) {
        this.GradeID = GradeID;
    }

    public int getName(){
        return Grade;
    }

    public void setName(int Grade){
        this.Grade=Grade;
    }
}

