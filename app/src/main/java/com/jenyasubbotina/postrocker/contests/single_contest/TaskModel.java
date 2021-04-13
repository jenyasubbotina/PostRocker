package com.jenyasubbotina.postrocker.contests.single_contest;

import java.util.List;

public class TaskModel {
    private Long id;
    private String title;
    private String content;
    private Long contest;
    private Long tl;
    private Long ml;
    private List<List<String>> samples = null;

    public TaskModel(Long id, String title, String content, Long contest, Long tl, Long ml, List<List<String>> samples) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.contest = contest;
        this.tl = tl;
        this.ml = ml;
        this.samples = samples;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getContest() {
        return contest;
    }

    public void setContest(Long contest) {
        this.contest = contest;
    }

    public Long getTl() {
        return tl;
    }

    public void setTl(Long tl) {
        this.tl = tl;
    }

    public Long getMl() {
        return ml;
    }

    public void setMl(Long ml) {
        this.ml = ml;
    }

    public List<List<String>> getSamples() {
        return samples;
    }

    public void setSamples(List<List<String>> samples) {
        this.samples = samples;
    }
}
