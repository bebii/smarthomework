package com.habebe.projecthomework.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Habebe on 7/4/2559.
 */
public class LoginResponseDao {

    @SerializedName("success")          private boolean success;
    @SerializedName("message")          private String message;
    @SerializedName("status")           private String status;
    @SerializedName("id")               private String id;
    @SerializedName("full_name")        private String FName;
    @SerializedName("username")         private String username;
    @SerializedName("full_name")        private String LastName;
            ;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return FName;
    }

    public void setFName(String fullName) {
        this.FName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
