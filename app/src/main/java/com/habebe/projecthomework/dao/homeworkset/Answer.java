
package com.habebe.projecthomework.dao.homeworkset;


import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("answer_id")
    private String answerId;
    @SerializedName("choice")
    private String choice;
    @SerializedName("text")
    private String text;
    @SerializedName("correct")
    private String correct;

    @SerializedName("Questionfk")
    private String Questionfk;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getQuestionfk() {
        return Questionfk;
    }

    public void setQuestionfk(String questionfk) {
        Questionfk = questionfk;
    }
}
