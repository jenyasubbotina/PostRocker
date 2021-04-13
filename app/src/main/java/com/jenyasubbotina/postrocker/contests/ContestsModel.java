package com.jenyasubbotina.postrocker.contests;

import java.util.ArrayList;

public class ContestsModel {
    private Long id;
    private String title;
    private String description;
    private ArrayList<Long> tasks = null;
    private Long startTime;
    private Long duration;

    public ContestsModel(Long id, String title, String description, Long startTime, Long duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Long> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Long> tasks) {
        this.tasks = tasks;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
