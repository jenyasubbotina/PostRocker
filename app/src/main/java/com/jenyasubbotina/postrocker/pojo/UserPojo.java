package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPojo {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("date_joined")
    @Expose
    private Long dateJoined;

    @SerializedName("last_login")
    @Expose
    private Long lastLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Long dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }
}
