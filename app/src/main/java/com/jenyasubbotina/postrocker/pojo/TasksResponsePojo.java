package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TasksResponsePojo {
    @SerializedName("count")
    @Expose
    private Long count;

    @SerializedName("results")
    @Expose
    private ArrayList<TasksPojo> tasks;

    public Long getCount() {
        return count;
    }

    public ArrayList<TasksPojo> getTasks() {
        return tasks;
    }
}
