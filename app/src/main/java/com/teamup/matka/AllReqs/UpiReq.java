package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

@Keep
public class UpiReq {

    String upi;

    public UpiReq(String upi) {
        this.upi = upi;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }
}