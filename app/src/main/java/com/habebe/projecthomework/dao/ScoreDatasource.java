package com.habebe.projecthomework.dao;

/**
 * Created by samra on 07-Nov-16.
 */

public class ScoreDatasource {
    private boolean isHeader;
    private String subject;
    private String Chapter;
    private String Exercise;
    private String Score;
    private String SumScore;
    private boolean isSumScore;

    public boolean isHeader() {
        return isHeader;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getChapter() {
        return Chapter;
    }

    public void setChapter(String chapter) {
        Chapter = chapter;
    }

    public String getExercise() {
        return Exercise;
    }

    public void setExercise(String exercise) {
        Exercise = exercise;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getSumScore() {
        return SumScore;
    }

    public void setSumScore(String sumScore) {
        SumScore = sumScore;
    }

    public boolean isSumScore() {
        return isSumScore;
    }

    public void setSumScore(boolean sumScore) {
        isSumScore = sumScore;
    }
}
