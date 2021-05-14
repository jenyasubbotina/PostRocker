package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenAuthResponsePojo {
    @SerializedName("refresh")
    @Expose
    private String refreshToken;

    @SerializedName("access")
    @Expose
    private String accessToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
