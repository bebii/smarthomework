package com.habebe.projecthomework.dao;

public class Exercise {
    public String EXERCISEID;
    public String SUBJECTID;
    public String CHARPTERID;
    public String CHARPTERNAME;
    public String DODATE;
    public String EXPDATE;
    public String AMOUNT;
    public String Score;
    public String Level;

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getEXERCISEID() {
        return EXERCISEID;
    }

    public void setEXERCISEID(String EXERCISEID) {
        this.EXERCISEID = EXERCISEID;
    }

    public String getSUBJECTID() {
        return SUBJECTID;
    }

    public void setSUBJECTID(String SUBJECTID) {
        this.SUBJECTID = SUBJECTID;
    }

    public String getCHARPTERID() {
        return CHARPTERID;
    }

    public void setCHARPTERID(String CHARPTERID) {
        this.CHARPTERID = CHARPTERID;
    }

    public String getCHARPTERNAME() {
        return CHARPTERNAME;
    }

    public void setCHARPTERNAME(String CHARPTERNAME) {
        this.CHARPTERNAME = CHARPTERNAME;
    }

    public String getDODATE() {
        return DODATE;
    }

    public void setDODATE(String DODATE) {
        this.DODATE = DODATE;
    }

    public String getEXPDATE() {
        return EXPDATE;
    }

    public void setEXPDATE(String EXPDATE) {
        this.EXPDATE = EXPDATE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }
}
