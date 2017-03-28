package com.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Habebe on 29/9/2559.
 */

public class GradeCollectionDao {
    @SerializedName("success")      private boolean success;
    @SerializedName("message")      private String message;
    @SerializedName("data")         private List<GradeDao> data = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<GradeDao> getData() {
        return data;
    }

    public void setData(List<GradeDao> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
