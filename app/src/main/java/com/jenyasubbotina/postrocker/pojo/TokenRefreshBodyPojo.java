package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenRefreshBodyPojo {
    @SerializedName("refresh")
    @Expose
    private final String token;

    public TokenRefreshBodyPojo(String old) {
        this.token = old;
    }

    public String getToken() {
        return token;
    }
}
