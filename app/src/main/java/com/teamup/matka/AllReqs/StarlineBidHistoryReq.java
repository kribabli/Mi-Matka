package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

@Keep
public class StarlineBidHistoryReq {

    String marketName, id, starline, digit, cdigit, points, dt,mtype,pana, win;

    public StarlineBidHistoryReq(String marketName, String id, String starline, String digit, String cdigit, String points, String dt, String mtype, String pana, String win) {
        this.marketName = marketName;
        this.id = id;
        this.starline = starline;
        this.digit = digit;
        this.cdigit = cdigit;
        this.points = points;
        this.dt = dt;
        this.mtype = mtype;
        this.pana = pana;
        this.win = win;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStarline() {
        return starline;
    }

    public void setStarline(String starline) {
        this.starline = starline;
    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getCdigit() {
        return cdigit;
    }

    public void setCdigit(String cdigit) {
        this.cdigit = cdigit;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getPana() {
        return pana;
    }

    public void setPana(String pana) {
        this.pana = pana;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }


}