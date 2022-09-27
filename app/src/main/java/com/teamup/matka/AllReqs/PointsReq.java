package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

@Keep
public class PointsReq {

    String digit, points, type;

    public PointsReq(String digit, String points, String type) {
        this.digit = digit;
        this.points = points;
        this.type = type;
    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}