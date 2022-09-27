package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

@Keep
public class WithdrawReq {

    String id, userid, withdraw, amount, message, dt;

    public WithdrawReq(String id, String userid, String withdraw, String amount, String message, String dt) {
        this.id = id;
        this.userid = userid;
        this.withdraw = withdraw;
        this.amount = amount;
        this.message = message;
        this.dt = dt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}