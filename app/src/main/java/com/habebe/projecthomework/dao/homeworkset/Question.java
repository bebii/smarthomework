
package com.habebe.projecthomework.dao.homeworkset;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("question_id")
    private String questionId;
    @SerializedName("proposition")
    private String proposition;
    @SerializedName("answer")
    private List<Answer> answer = new ArrayList<Answer>();
    private boolean isQuestion;
    private String ExerciseID;
    private String Correct;
    private String AnsDesc;

    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    private int score = 1;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getProposition() {
        return proposition;
    }

    public void setProposition(String proposition) {
        this.proposition = proposition;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getExerciseID() {
        return ExerciseID;
    }

    public void setExerciseID(String exerciseID) {
        ExerciseID = exerciseID;
    }

    public String getCorrect() {
        return Correct;
    }

    public void setCorrectID(String correctID) {
        Correct = correctID;
    }

    public String getAnsDesc() {
        return AnsDesc;
    }

    public void setAnsDesc(String ansDesc) {
        AnsDesc = ansDesc;
    }
}
