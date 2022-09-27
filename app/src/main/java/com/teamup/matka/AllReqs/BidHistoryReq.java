package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

@Keep
public class BidHistoryReq {

    String id, name, type, message, amount, userid, dt, pana, mtype, win, winAmt;

    public BidHistoryReq(String id, String name, String type, String message, String amount, String userid, String dt, String pana, String mtype, String win, String winAmt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.message = message;
        this.amount = amount;
        this.userid = userid;
        this.dt = dt;
        this.pana = pana;
        this.mtype = mtype;
        this.win = win;
        this.winAmt = winAmt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getPana() {
        return pana;
    }

    public void setPana(String pana) {
        this.pana = pana;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWinAmt() {
        return winAmt;
    }

    public void setWinAmt(String winAmt) {
        this.winAmt = winAmt;
    }
}