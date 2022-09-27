package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

@Keep
public class MarketsReq {

    String id, name, imgpath, o_otime, o_ctime, c_otime, c_ctime, dt, highlight, result, type;

    public MarketsReq(String id, String name, String imgpath, String o_otime, String o_ctime, String c_otime, String c_ctime, String dt, String highlight, String result, String type) {
        this.id = id;
        this.name = name;
        this.imgpath = imgpath;
        this.o_otime = o_otime;
        this.o_ctime = o_ctime;
        this.c_otime = c_otime;
        this.c_ctime = c_ctime;
        this.dt = dt;
        this.highlight = highlight;
        this.result = result;
        this.type = type;
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

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getO_otime() {
        return o_otime;
    }

    public void setO_otime(String o_otime) {
        this.o_otime = o_otime;
    }

    public String getO_ctime() {
        return o_ctime;
    }

    public void setO_ctime(String o_ctime) {
        this.o_ctime = o_ctime;
    }

    public String getC_otime() {
        return c_otime;
    }

    public void setC_otime(String c_otime) {
        this.c_otime = c_otime;
    }

    public String getC_ctime() {
        return c_ctime;
    }

    public void setC_ctime(String c_ctime) {
        this.c_ctime = c_ctime;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}