package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContestsResponsePojo {
    @SerializedName("count")
    @Expose
    private String name;

    @SerializedName("results")
    @Expose
    private ArrayList<ContestsPojo> contests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ContestsPojo> getContests() {
        return contests;
    }

    public void setContests(ArrayList<ContestsPojo> contests) {
        this.contests = contests;
    }
}
