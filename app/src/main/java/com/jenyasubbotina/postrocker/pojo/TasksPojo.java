package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TasksPojo {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("contest")
    @Expose
    private Long contest;

    @SerializedName("tl")
    @Expose
    private Long tl;

    @SerializedName("ml")
    @Expose
    private Long ml;

    @SerializedName("samples")
    @Expose
    private List<List<String>> samples = null;

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
