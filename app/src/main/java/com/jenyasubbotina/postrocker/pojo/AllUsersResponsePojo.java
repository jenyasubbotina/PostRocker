package com.jenyasubbotina.postrocker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllUsersResponsePojo {
    @SerializedName("count")
    @Expose
    private Long count;

    @SerializedName("next")
    @Expose
    private Object next;

    @SerializedName("previous")
    @Expose
    private Object previous;

    @SerializedName("results")
    @Expose
    private ArrayList<UserPojo> users;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public ArrayList<UserPojo> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserPojo> users) {
        this.users = users;
    }
}
