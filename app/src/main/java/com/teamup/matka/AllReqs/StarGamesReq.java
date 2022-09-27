package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

import org.json.JSONArray;

@Keep
public class StarGamesReq {

    String id, dt, result, stime;

    public StarGamesReq(String id, String dt, String result, String stime) {
        this.id = id;
        this.dt = dt;
        this.result = result;
        this.stime = stime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }
}