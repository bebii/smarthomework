package com.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Habebe on 2/10/2559.
 */

public class ExamDateCollectionDao {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ExamDateItemDao> data = new ArrayList<ExamDateItemDao>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ExamDateItemDao> getData() {
        return data;
    }

    public void setData(List<ExamDateItemDao> data) {
        this.data = data;
    }
}
