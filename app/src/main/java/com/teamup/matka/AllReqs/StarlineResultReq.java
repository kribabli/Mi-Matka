package com.teamup.matka.AllReqs;

import androidx.annotation.Keep;

@Keep
public class StarlineResultReq {

    String key, value;

    public StarlineResultReq(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}