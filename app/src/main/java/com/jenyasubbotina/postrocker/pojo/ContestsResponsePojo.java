package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContestsResponsePojo {
    @SerializedName("count")
    @Expose
    private Long count;

    @SerializedName("results")
    @Expose
    private ArrayList<ContestsPojo> contests;

    public Long getName() {
        return count;
    }

    public void setName(Long c) {
        this.count = c;
    }

    public ArrayList<ContestsPojo> getContests() {
        return contests;
    }

    public void setContests(ArrayList<ContestsPojo> contests) {
        this.contests = contests;
    }
}
